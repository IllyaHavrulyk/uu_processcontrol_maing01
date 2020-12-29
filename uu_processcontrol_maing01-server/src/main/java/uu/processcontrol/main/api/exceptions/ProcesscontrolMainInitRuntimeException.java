package uu.processcontrol.main.api.exceptions;

import java.util.Map;
import uu.app.exception.AppErrorMap;
import uu.app.exception.AppRuntimeException;
import uu.app.exception.ErrorCode;

public final class ProcesscontrolMainInitRuntimeException extends AppRuntimeException {

  public ProcesscontrolMainInitRuntimeException(ProcesscontrolMainInitRuntimeException.Error code, Map<String, ?> paramMap, Throwable cause) {
    super(code.getCode(), code.getMessage(), (AppErrorMap) null, paramMap, cause);
  }

  public ProcesscontrolMainInitRuntimeException(ProcesscontrolMainInitRuntimeException.Error code, String message, Object... params) {
    super(code.getCode(), message, params);
  }

  public ProcesscontrolMainInitRuntimeException(ProcesscontrolMainInitRuntimeException.Error code, Map<String, ?> paramMap) {
    super(code.getCode(), code.getMessage(), (AppErrorMap) null, paramMap, null);
  }

  public enum Error {

    INVALID_DTO_IN(ErrorCode.application("uu-processcontrol-main/init/invalidDtoIn"), "DtoIn is not valid."),

    SET_PROFILE_FAILED(ErrorCode.application("uu-processcontrol-main/init/sys/setProfileFailed"), "Set profile failed.");

    private ErrorCode code;

    private String message;

    Error(ErrorCode code, String message) {
      this.code = code;
      this.message = message;
    }

    public ErrorCode getCode() {
      return code;
    }

    public String getMessage() {
      return message;
    }

  }

}
