package uu.processcontrol.main.abl.entity;

public enum PhaseStatus {
  INIT("INIT"),
  RUNNING("RUNNING"),
  OK("OK"),
  NOTOK("NOTOK");

  private String status;

  PhaseStatus(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
