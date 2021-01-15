package uu.processcontrol.main.abl;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class ValidationResult {
  private List<ValidationMessage> validationMessages = new ArrayList<>();
  private ZonedDateTime timestamp;
  private ValidationResultSeverity severity = ValidationResultSeverity.OK;

  public ValidationResult() {
    timestamp = ZonedDateTime.now();
  }

  public ValidationResult(List<ValidationMessage> validationMessages, ZonedDateTime timestamp, ValidationResultSeverity severity) {
    this.validationMessages = validationMessages;
    this.timestamp = timestamp;
    this.severity = severity;
  }

  public List<ValidationMessage> getValidationMessages() {
    return validationMessages;
  }

  public void setValidationMessages(List<ValidationMessage> validationMessages) {
    this.validationMessages = validationMessages;
  }

  public ZonedDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(ZonedDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public ValidationResultSeverity getSeverity() {
    return severity;
  }

  public void setSeverity(ValidationResultSeverity severity) {
    this.severity = severity;
  }
}
