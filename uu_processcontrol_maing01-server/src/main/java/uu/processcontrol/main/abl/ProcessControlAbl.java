package uu.processcontrol.main.abl;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import uu.app.datastore.domain.PagedResult;
import uu.app.exception.AppErrorMap;
import uu.app.validation.ValidationResult;
import uu.app.validation.Validator;
import uu.processcontrol.main.abl.entity.Phase;
import uu.processcontrol.main.abl.entity.PhaseCode;
import uu.processcontrol.main.abl.entity.PhaseStatus;
import uu.processcontrol.main.abl.entity.ProcessControl;
import uu.processcontrol.main.api.dto.ProcessControlCreateDtoIn;
import uu.processcontrol.main.api.dto.ProcessControlDtoOut;
import uu.processcontrol.main.api.dto.ProcessControlGetDtoIn;
import uu.processcontrol.main.api.dto.ProcessControlListDtoIn;
import uu.processcontrol.main.api.dto.ProcessControlListDtoOut;
import uu.processcontrol.main.dao.mongo.ProcessControlMongoDao;

@Component
public class ProcessControlAbl {

  @Inject
  private Validator validator;

  @Inject
  private ModelMapper modelMapper;

  @Inject
  private ProcessControlMongoDao processControlMongoDao;

  public ProcessControlDtoOut create(String awid, ProcessControlCreateDtoIn dtoIn) {

    AppErrorMap appErrorMap = new AppErrorMap();
    //Validate dtoIn Data
    ValidationResult validationResult = validator.validate(dtoIn);

    // //Checks if dtoIn valid, if no , then throws INVALID_DTO_IN exception
    // if (!validationResult.isValid()) {
    //   throw new TopicRuntimeException(TopicRuntimeException.Error.INVALID_DTO_IN,
    //     ValidationResultUtils.validationResultToAppErrorMap(validationResult));
    // }

    // dtoIn = checkDefaultDate(dtoIn);
    //Sets an object for creation
    // Topic topic = modelMapper.map(dtoIn, Topic.class);
    // topic.setAwid(awid);

    //Trying to create object in database, in case of failure throws TOPIC_DAO_CREATE_FAILED exception
    // try {
    //   topic = topicDao.create(topic);
    // } catch (DatastoreRuntimeException e) {
    //   throw new TopicRuntimeException(TopicRuntimeException.Error.TOPIC_DAO_CREATE_FAILED,
    //     ValidationResultUtils.validationResultToAppErrorMap(validationResult));
    // }

    //Sets dtoOut data for output.
    // TopicDtoOut dtoOut = modelMapper.map(topic, TopicDtoOut.class);
    // dtoOut.setUuAppErrorMap(appErrorMap);
    // dtoOut.setProfiles(profiles);
    // return dtoOut;

    processControlMongoDao.delete(awid, dtoIn.getId());

    ProcessControl processControl = new ProcessControl();

    processControl.setAwid(awid);
    ZonedDateTime processStart = ZonedDateTime.now();
    ZonedDateTime processEnd = processStart.plusMinutes(12);
    processControl.setStartTime(processStart);
    processControl.setEndTime(processEnd);

    List<Phase> phases = new ArrayList<Phase>();
    //Setting receiving phase
    Phase receivingPhase = new Phase();
    receivingPhase.setStartTime(processStart);
    receivingPhase.setEndTime(processStart.plusMinutes(5));
    receivingPhase.setName("Receiving");
    receivingPhase.setPhaseCode(PhaseCode.Receiving);
    receivingPhase.setStatus(PhaseStatus.RUNNING);
    phases.add(receivingPhase);
    //Setting validationPhase
    Phase validationPhase = new Phase();
    validationPhase.setStartTime(receivingPhase.getEndTime());
    validationPhase.setEndTime(validationPhase.getStartTime().plusMinutes(2));
    validationPhase.setName("Validation");
    validationPhase.setPhaseCode(PhaseCode.Validation);
    validationPhase.setStatus(PhaseStatus.INIT);
    phases.add(validationPhase);
    //Setting moderatingPhase
    Phase moderatingPhase = new Phase();
    moderatingPhase.setStartTime(validationPhase.getEndTime());
    moderatingPhase.setEndTime(moderatingPhase.getStartTime().plusMinutes(5));
    moderatingPhase.setName("Moderating");
    moderatingPhase.setPhaseCode(PhaseCode.Moderating);
    moderatingPhase.setStatus(PhaseStatus.INIT);
    phases.add(moderatingPhase);

    processControl.setPhases(phases);

    processControlMongoDao.create(processControl);
    ProcessControlDtoOut dtoOut = modelMapper.map(processControl, ProcessControlDtoOut.class);
    return dtoOut;
  }

  public ProcessControlDtoOut get(String awid, ProcessControlGetDtoIn dtoIn){
    AppErrorMap appErrorMap = new AppErrorMap();
    //Validate dtoIn Data
    ValidationResult validationResult = validator.validate(dtoIn);

    ProcessControl processControl = null;
    processControl = processControlMongoDao.get(awid, dtoIn.getId());
    ProcessControlDtoOut dtoOut = modelMapper.map(processControl, ProcessControlDtoOut.class);
    return dtoOut;
  }

  public ProcessControlListDtoOut list(String awid, ProcessControlListDtoIn dtoIn){

    PagedResult<ProcessControl> pagedResult = processControlMongoDao.list(awid, dtoIn.getPageInfo());
    ProcessControlListDtoOut dtoOut = modelMapper.map(pagedResult, ProcessControlListDtoOut.class);
    return dtoOut;
  }
}
