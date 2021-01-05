package uu.processcontrol.main.abl.entity;

import java.time.ZonedDateTime;
import java.util.List;
import uu.app.objectstore.mongodb.domain.AbstractUuObject;

public class ProcessControl extends AbstractUuObject {

  private ZonedDateTime startTime;
  private ZonedDateTime endTime;
  private List<Phase> phases;

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
