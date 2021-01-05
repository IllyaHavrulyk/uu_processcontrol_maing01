package uu.processcontrol.main.api.dto;

import java.time.ZonedDateTime;
import java.util.List;
import uu.processcontrol.main.abl.entity.Phase;

public class ProcessControlDtoOut {
  private String id;
  private ZonedDateTime startTime;
  private ZonedDateTime endTime;
  private List<Phase> phases;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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

  public List<Phase> getPhases() {
    return phases;
  }

  public void setPhases(List<Phase> phases) {
    this.phases = phases;
  }
}
