package uu.processcontrol.main.abl;

import java.util.HashMap;
import java.util.Map;

public class ValidationMessage {
  private ValidationResultSeverity severity = ValidationResultSeverity.OK;
  private String code;
  private String detail;
  private Map<String, Object> parameters = new HashMap<>();

  public ValidationMessage(ValidationResultSeverity severity, String code, String detail, Map<String, Object> parameters) {
    this.severity = severity;
    this.code = code;
    this.detail = detail;
    this.parameters = parameters;
  }

  public ValidationMessage() {
  }

  public ValidationResultSeverity getSeverity() {
    return severity;
  }

  public void setSeverity(ValidationResultSeverity severity) {
    this.severity = severity;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }

  public Map<String, Object> getParameters() {
    return parameters;
  }

  public void setParameters(Map<String, Object> parameters) {
    this.parameters = parameters;
  }
}
