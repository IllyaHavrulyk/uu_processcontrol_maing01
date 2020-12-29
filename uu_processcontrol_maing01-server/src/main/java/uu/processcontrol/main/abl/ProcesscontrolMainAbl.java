package uu.processcontrol.main.abl;

import javax.inject.Inject;
import org.springframework.stereotype.Component;
import uu.app.validation.ValidationResult;
import uu.app.validation.Validator;
import uu.app.validation.utils.ValidationResultUtils;
import uu.app.workspace.Profile;
import uu.app.workspace.dto.profile.SysSetProfileDtoIn;
import uu.processcontrol.main.dao.ProcesscontrolMainDao;
import uu.processcontrol.main.api.dto.ProcesscontrolMainInitDtoIn;
import uu.processcontrol.main.api.dto.ProcesscontrolMainInitDtoOut;
import uu.processcontrol.main.api.exceptions.ProcesscontrolMainInitRuntimeException;
import uu.processcontrol.main.api.exceptions.ProcesscontrolMainInitRuntimeException.Error;

@Component
public final class ProcesscontrolMainAbl {

  private static final String AUTHORITIES_CODE = "Authorities";

  @Inject
  private Validator validator;

  @Inject
  private Profile profile;

  @Inject
  private ProcesscontrolMainDao processcontrolMainDao;

  public ProcesscontrolMainInitDtoOut init(String awid, ProcesscontrolMainInitDtoIn dtoIn) {
    // HDS 1
    ValidationResult validationResult = validator.validate(dtoIn);
    if (!validationResult.isValid()) {
      // A1
      throw new ProcesscontrolMainInitRuntimeException(Error.INVALID_DTO_IN, ValidationResultUtils.validationResultToAppErrorMap(validationResult));
    }

    // HDS 2
    SysSetProfileDtoIn setProfileDtoIn = new SysSetProfileDtoIn();
    setProfileDtoIn.setCode(AUTHORITIES_CODE);
    setProfileDtoIn.setRoleUri(dtoIn.getAuthoritiesUri());
    profile.setProfile(awid, setProfileDtoIn);

    // HDS 3 - HDS N
    // TODO Implement according to application needs...

    // HDS N+1
    return new ProcesscontrolMainInitDtoOut();
  }

}
