package uu.processcontrol.main.abl.entity;

public enum PhaseCode {
  Receiving("Receiving"),
  Validation("Validation"),
  Moderating("Moderating");

  private String code;

  PhaseCode(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }
}
