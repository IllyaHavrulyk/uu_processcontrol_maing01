package uu.processcontrol.main.api.dto;

import uu.app.validation.ValidationType;

@ValidationType("ProcessControlValidateDtoInType")
public class ProcessControlValidateDtoIn {
  private String id;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
