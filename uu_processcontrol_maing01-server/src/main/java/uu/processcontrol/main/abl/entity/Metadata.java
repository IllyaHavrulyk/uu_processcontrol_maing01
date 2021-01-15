package uu.processcontrol.main.abl.entity;

import java.time.ZonedDateTime;
import uu.processcontrol.main.abl.DocumentType;
import uu.processcontrol.main.abl.TimeInterval;
import uu.processcontrol.main.abl.ValidationResult;

public class Metadata {
  private DocumentType documentType;
  private String sender;
  private String receiver;
  private String domain;
  private ZonedDateTime creationDateTime;
  private TimeInterval timeInterval;
  private String fileName;
  private Boolean isValid;
  private ValidationResult validationResult;

  public Metadata() {
  }

  public DocumentType getDocumentType() {
    return documentType;
  }

  public void setDocumentType(DocumentType documentType) {
    this.documentType = documentType;
  }

  public String getSender() {
    return sender;
  }

  public void setSender(String sender) {
    this.sender = sender;
  }

  public String getReceiver() {
    return receiver;
  }

  public void setReceiver(String receiver) {
    this.receiver = receiver;
  }

  public String getDomain() {
    return domain;
  }

  public void setDomain(String domain) {
    this.domain = domain;
  }

  public ZonedDateTime getCreationDateTime() {
    return creationDateTime;
  }

  public void setCreationDateTime(ZonedDateTime creationDateTime) {
    this.creationDateTime = creationDateTime;
  }

  public TimeInterval getTimeInterval() {
    return timeInterval;
  }

  public void setTimeInterval(TimeInterval timeInterval) {
    this.timeInterval = timeInterval;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public Boolean getValid() {
    return isValid;
  }

  public void setValid(Boolean valid) {
    isValid = valid;
  }

  public ValidationResult getValidationResult() {
    return validationResult;
  }

  public void setValidationResult(ValidationResult validationResult) {
    this.validationResult = validationResult;
  }
}
