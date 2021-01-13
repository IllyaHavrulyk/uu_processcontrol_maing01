package uu.processcontrol.main.abl;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.inject.Inject;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import uu.app.datastore.concurrency.Lock;
import uu.app.datastore.domain.PagedResult;
import uu.app.datastore.exceptions.DatastoreIdentityRuntimeException;
import uu.app.datastore.exceptions.DatastoreRuntimeException;
import uu.app.exception.AppErrorMap;
import uu.app.validation.ValidationResult;
import uu.app.validation.Validator;
import uu.app.validation.utils.ValidationResultUtils;
import uu.processcontrol.main.abl.entity.Phase;
import uu.processcontrol.main.abl.entity.PhaseCode;
import uu.processcontrol.main.abl.entity.PhaseStatus;
import uu.processcontrol.main.abl.entity.ProcessControl;
import uu.processcontrol.main.abl.helper.ValidationCheckHelper;
import uu.processcontrol.main.api.dto.ProcessControlCreateDtoIn;
import uu.processcontrol.main.api.dto.ProcessControlCreateSingleDtoIn;
import uu.processcontrol.main.api.dto.ProcessControlDtoOut;
import uu.processcontrol.main.api.dto.ProcessControlGetDtoIn;
import uu.processcontrol.main.api.dto.ProcessControlListDtoIn;
import uu.processcontrol.main.api.dto.ProcessControlListDtoOut;
import uu.processcontrol.main.api.dto.ProcessControlUpdateDtoIn;
import uu.processcontrol.main.api.dto.ProcessControlValidateDtoIn;
import uu.processcontrol.main.api.exceptions.ProcessControlRuntimeException;
import uu.processcontrol.main.api.exceptions.ProcessControlRuntimeException.Error;
import uu.processcontrol.main.api.utils.ProcessControlUpdateMapper;
import uu.processcontrol.main.dao.mongo.ProcessControlMongoDao;

@Component
public class ProcessControlAbl {

  @Inject
  private Validator validator;

  @Inject
  private ModelMapper modelMapper;

  @Inject
  private ProcessControlMongoDao processControlMongoDao;

  @Inject
  private ValidationCheckHelper validationCheckHelper;

  public ProcessControlDtoOut start(String awid, ProcessControlCreateDtoIn dtoIn) {
    AppErrorMap appErrorMap = new AppErrorMap();
    //Validate dtoIn Data
    ValidationResult validationResult = validator.validate(dtoIn);

    if (!validationResult.isValid()) {
      throw new ProcessControlRuntimeException(Error.INVALID_DTO_IN, ValidationResultUtils.validationResultToAppErrorMap(validationResult));
    }

    // //for schedulers
    // Lock lock = processControlMongoDao.createLock(UUID.randomUUID().toString());
    // ProcessControl processControl1 = processControlMongoDao.lock(awid, dtoIn.getId(), lock);
    // processControlMongoDao.unlock(lock);

    try {
      processControlMongoDao.delete(awid, dtoIn.getId());
    } catch (DatastoreIdentityRuntimeException e) {
      throw new ProcessControlRuntimeException(Error.PROCESS_DAO_DELETE_FAILED, ValidationResultUtils.validationResultToAppErrorMap(validationResult));
    }

    //Creating empty instance for further setting
    ProcessControl processControl = new ProcessControl();
    processControl.setAwid(awid);
    ZonedDateTime processStart = ZonedDateTime.now();
    ZonedDateTime processEnd = processStart.plusMinutes(12);
    processControl.setStartTime(processStart);
    processControl.setEndTime(processEnd);
    //Setting list of phases
    List<Phase> phases = new ArrayList<Phase>();
    //Setting receiving phase
    Phase receivingPhase = new Phase();
    receivingPhase.setStartTime(processStart);
    receivingPhase.setEndTime(processStart.plusMinutes(5));
    receivingPhase.setName("Receiving");
    receivingPhase.setPhaseCode(PhaseCode.RECEIVING);
    receivingPhase.setStatus(PhaseStatus.RUNNING);
    phases.add(receivingPhase);
    //Setting validationPhase
    Phase validationPhase = new Phase();
    validationPhase.setStartTime(receivingPhase.getEndTime());
    validationPhase.setEndTime(validationPhase.getStartTime().plusMinutes(2));
    validationPhase.setName("Validation");
    validationPhase.setPhaseCode(PhaseCode.VALIDATION);
    validationPhase.setStatus(PhaseStatus.INIT);
    phases.add(validationPhase);
    //Setting moderatingPhase
    Phase moderatingPhase = new Phase();
    moderatingPhase.setStartTime(validationPhase.getEndTime());
    moderatingPhase.setEndTime(moderatingPhase.getStartTime().plusMinutes(5));
    moderatingPhase.setName("Moderating");
    moderatingPhase.setPhaseCode(PhaseCode.MODERATING);
    moderatingPhase.setStatus(PhaseStatus.INIT);
    phases.add(moderatingPhase);
    //Setting phases for processControl
    processControl.setPhases(phases);
    try {
      processControlMongoDao.create(processControl);
    } catch (DatastoreRuntimeException e) {
      throw new ProcessControlRuntimeException(Error.PROCESS_DAO_CREATE_FAILED, ValidationResultUtils.validationResultToAppErrorMap(validationResult));
    }

    ProcessControlDtoOut dtoOut = modelMapper.map(processControl, ProcessControlDtoOut.class);
    dtoOut.setUuAppErrorMap(appErrorMap);
    return dtoOut;
  }

  public ProcessControlDtoOut get(String awid, ProcessControlGetDtoIn dtoIn) {
    AppErrorMap appErrorMap = new AppErrorMap();
    //Validate dtoIn Data
    ValidationResult validationResult = validator.validate(dtoIn);

    if (!validationResult.isValid()) {
      throw new ProcessControlRuntimeException(Error.INVALID_DTO_IN, ValidationResultUtils.validationResultToAppErrorMap(validationResult));
    }

    ProcessControl processControl = null;
    try {
      processControl = processControlMongoDao.get(awid, dtoIn.getId());
    } catch (DatastoreIdentityRuntimeException e) {
      throw new ProcessControlRuntimeException(Error.PROCESS_DAO_GET_FAILED, ValidationResultUtils.validationResultToAppErrorMap(validationResult));
    }

    ProcessControlDtoOut dtoOut = modelMapper.map(processControl, ProcessControlDtoOut.class);
    dtoOut.setUuAppErrorMap(appErrorMap);
    return dtoOut;
  }

  public ProcessControlListDtoOut list(String awid, ProcessControlListDtoIn dtoIn) {
    AppErrorMap appErrorMap = new AppErrorMap();
    //Validate dtoIn Data
    ValidationResult validationResult = validator.validate(dtoIn);

    if (!validationResult.isValid()) {
      throw new ProcessControlRuntimeException(Error.INVALID_DTO_IN, ValidationResultUtils.validationResultToAppErrorMap(validationResult));
    }

    PagedResult<ProcessControl> pagedResult = null;

    try {
      pagedResult = processControlMongoDao.list(awid, dtoIn.getPageInfo());
    } catch (DatastoreIdentityRuntimeException e) {
      throw new ProcessControlRuntimeException(Error.PROCESS_DAO_LIST_FAILED, ValidationResultUtils.validationResultToAppErrorMap(validationResult));
    }
    ProcessControlListDtoOut dtoOut = modelMapper.map(pagedResult, ProcessControlListDtoOut.class);
    dtoOut.setUuAppErrorMap(appErrorMap);
    return dtoOut;
  }

  public ProcessControlDtoOut createSingle(String awid, ProcessControlCreateSingleDtoIn dtoIn){
    AppErrorMap appErrorMap = new AppErrorMap();
    //Validate dtoIn Data
    ValidationResult validationResult = validator.validate(dtoIn);

    if (!validationResult.isValid()) {
      throw new ProcessControlRuntimeException(Error.INVALID_DTO_IN, ValidationResultUtils.validationResultToAppErrorMap(validationResult));
    }

    //Creating empty instance for further setting
    ProcessControl processControl = new ProcessControl();
    processControl.setAwid(awid);
    ZonedDateTime processStart = ZonedDateTime.now();
    ZonedDateTime processEnd = processStart.plusMinutes(12);
    processControl.setStartTime(processStart);
    processControl.setEndTime(processEnd);
    //Setting list of phases
    List<Phase> phases = new ArrayList<Phase>();
    //Setting receiving phase
    Phase receivingPhase = new Phase();
    receivingPhase.setStartTime(processStart);
    receivingPhase.setEndTime(processStart.plusMinutes(5));
    receivingPhase.setName("Receiving");
    receivingPhase.setPhaseCode(PhaseCode.RECEIVING);
    receivingPhase.setStatus(PhaseStatus.RUNNING);
    phases.add(receivingPhase);
    //Setting validationPhase
    Phase validationPhase = new Phase();
    validationPhase.setStartTime(receivingPhase.getEndTime());
    validationPhase.setEndTime(validationPhase.getStartTime().plusMinutes(2));
    validationPhase.setName("Validation");
    validationPhase.setPhaseCode(PhaseCode.VALIDATION);
    validationPhase.setStatus(PhaseStatus.INIT);
    phases.add(validationPhase);
    //Setting moderatingPhase
    Phase moderatingPhase = new Phase();
    moderatingPhase.setStartTime(validationPhase.getEndTime());
    moderatingPhase.setEndTime(moderatingPhase.getStartTime().plusMinutes(5));
    moderatingPhase.setName("Moderating");
    moderatingPhase.setPhaseCode(PhaseCode.MODERATING);
    moderatingPhase.setStatus(PhaseStatus.INIT);
    phases.add(moderatingPhase);
    //Setting phases for processControl
    processControl.setPhases(phases);
    try {
      processControlMongoDao.create(processControl);
    } catch (DatastoreRuntimeException e) {
      throw new ProcessControlRuntimeException(Error.PROCESS_DAO_CREATE_FAILED, ValidationResultUtils.validationResultToAppErrorMap(validationResult));
    }

    ProcessControlDtoOut dtoOut = modelMapper.map(processControl, ProcessControlDtoOut.class);
    dtoOut.setUuAppErrorMap(appErrorMap);
    return dtoOut;
  }

  public ProcessControlDtoOut update(String awid, ProcessControlUpdateDtoIn dtoIn){
    AppErrorMap appErrorMap = new AppErrorMap();
    //Validate dtoIn Data
    ValidationResult validationResult = validator.validate(dtoIn);

    if (!validationResult.isValid()) {
      throw new ProcessControlRuntimeException(Error.INVALID_DTO_IN, ValidationResultUtils.validationResultToAppErrorMap(validationResult));
    }

    ProcessControl processControl = new ProcessControl();
    try {
      processControl = processControlMongoDao.get(awid, dtoIn.getId());
    } catch (DatastoreIdentityRuntimeException e) {
      throw new ProcessControlRuntimeException(Error.PROCESS_DAO_GET_FAILED, ValidationResultUtils.validationResultToAppErrorMap(validationResult));
    }

    if(processControl != null){
      ProcessControlUpdateMapper updateMapper = new ProcessControlUpdateMapper();
      processControl = updateMapper.map(processControl, dtoIn);
      processControl = processControlMongoDao.update(processControl);
    }else{
      throw new ProcessControlRuntimeException(Error.PROCESS_DAO_UPDATE_FAILED, ValidationResultUtils.validationResultToAppErrorMap(validationResult));
    }

    ProcessControlDtoOut dtoOut = modelMapper.map(processControl, ProcessControlDtoOut.class);
    dtoOut.setUuAppErrorMap(appErrorMap);
    return dtoOut;
  }

  public ProcessControlDtoOut validate(String awid, ProcessControlValidateDtoIn dtoIn){
    AppErrorMap appErrorMap = new AppErrorMap();
    //Validate dtoIn Data
    ValidationResult validationResult = validator.validate(dtoIn);

    if (!validationResult.isValid()) {
      throw new ProcessControlRuntimeException(Error.INVALID_DTO_IN, ValidationResultUtils.validationResultToAppErrorMap(validationResult));
    }

    ProcessControl processControl = new ProcessControl();
    try {
      processControl = processControlMongoDao.get(awid, dtoIn.getId());
    } catch (DatastoreIdentityRuntimeException e) {
      throw new ProcessControlRuntimeException(Error.PROCESS_DAO_GET_FAILED, ValidationResultUtils.validationResultToAppErrorMap(validationResult));
    }

    if(processControl != null){
      processControl.getPhases().set(1, validationCheckHelper.checkValidationPhase(processControl));
      processControl = processControlMongoDao.update(processControl);
    }else{
      throw new ProcessControlRuntimeException(Error.PROCESS_DAO_UPDATE_FAILED, ValidationResultUtils.validationResultToAppErrorMap(validationResult));
    }
    ProcessControlDtoOut dtoOut = modelMapper.map(processControl, ProcessControlDtoOut.class);
    dtoOut.setUuAppErrorMap(appErrorMap);
    return dtoOut;
  }
}
