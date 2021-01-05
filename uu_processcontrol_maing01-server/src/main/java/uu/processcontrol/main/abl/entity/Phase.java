package uu.processcontrol.main.abl.entity;

import java.time.ZonedDateTime;

public class Phase {

  private String name;
  private PhaseCode phaseCode;
  private ZonedDateTime startTime;
  private ZonedDateTime endTime;
  private PhaseStatus status;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public PhaseCode getPhaseCode() {
    return phaseCode;
  }

  public void setPhaseCode(PhaseCode phaseCode) {
    this.phaseCode = phaseCode;
  }

  public ZonedDateTime getStartTime() {
    return startTime;
  }

  public void setStartTime(ZonedDateTime startTime) {
    this.startTime = startTime;
  }

  public ZonedDateTime getEndTime() {
    return endTime;
  }

  public void setEndTime(ZonedDateTime endTime) {
    this.endTime = endTime;
  }

  public PhaseStatus getStatus() {
    return status;
  }

  public void setStatus(PhaseStatus status) {
    this.status = status;
  }
}
