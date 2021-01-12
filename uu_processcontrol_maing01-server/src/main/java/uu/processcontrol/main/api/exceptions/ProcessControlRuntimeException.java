package uu.processcontrol.main.api.exceptions;

import java.util.Map;
import uu.app.exception.AppErrorMap;
import uu.app.exception.AppRuntimeException;
import uu.app.exception.ErrorCode;

public class ProcessControlRuntimeException extends AppRuntimeException {

  public ProcessControlRuntimeException(ErrorCode code, String message, Object... params) {
    super(code, message, params);
  }

  public ProcessControlRuntimeException(ErrorCode code, String message, Throwable cause, Object... params) {
    super(code, message, cause, params);
  }

  public ProcessControlRuntimeException(ErrorCode code, String message, AppErrorMap uuAppErrorMap, Throwable cause, Object... params) {
    super(code, message, uuAppErrorMap, cause, params);
  }

  public ProcessControlRuntimeException(ErrorCode code, String message, AppErrorMap uuAppErrorMap, Map<String, ?> paramMap, Throwable cause, Object... params) {
    super(code, message, uuAppErrorMap, paramMap, cause, params);
  }

  public ProcessControlRuntimeException(ErrorCode code, String message, AppErrorMap uuAppErrorMap, Map<String, ?> paramMap, Throwable cause, Map<String, ?> dtoOut, Object... params) {
    super(code, message, uuAppErrorMap, paramMap, cause, dtoOut, params);
  }

  public ProcessControlRuntimeException(ProcessControlRuntimeException.Error code, Map<String, ?> paramMap) {
    super(code.getCode(), code.getMessage(), (AppErrorMap) null, paramMap, null);
  }

  public ProcessControlRuntimeException(ProcessControlRuntimeException.Error code) {
    super(code.getCode(), code.getMessage(), (AppErrorMap) null, null);
  }

  public enum Error {

    INVALID_DTO_IN(ErrorCode.application("uu-processcontrol-main/topic/invalidDtoIn"), "DtoIn is not valid."),
    PROCESS_DAO_CREATE_FAILED(ErrorCode.application("uu-processcontrol-main/process/create/createProcessControlFailed"), "Process didn't create."),
    PROCESS_DAO_LIST_FAILED(ErrorCode.application("uu-processcontrol-main/process/list/getListFailed"), "Can't show list of processes"),
    PROCESS_DAO_GET_FAILED(ErrorCode.application("uu-processcontrol-main/process/get/getProcessControlFailed"), "Haven't found any process with that id"),
    PROCESS_DAO_UPDATE_FAILED(ErrorCode.application("uu-processcontrol-main/process/update/ProcessControlUpdateFailed"), "Couldn't update process"),
    PROCESS_DAO_DELETE_FAILED(ErrorCode.application("uu-processcontrol-main/process/delete/ProcessControlDeleteFailed"), "Couldn't delete process with that id"),
    PROCESS_DAO_RETURN_NULL(ErrorCode.application("uu-processcontrol-main/process/get/ProcessControlGetNull"), "This object doesn't exist");

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
