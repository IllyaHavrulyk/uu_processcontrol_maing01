package uu.processcontrol.main.abl;

import java.time.ZonedDateTime;

public class TimeInterval {
  private ZonedDateTime from;
  private ZonedDateTime to;
  private TimeResolution timeResolution;

  public TimeInterval() {
  }

  public TimeInterval(ZonedDateTime from, ZonedDateTime to) {
    this.from = from;
    this.to = to;
  }

  public ZonedDateTime getFrom() {
    return from;
  }

  public void setFrom(ZonedDateTime from) {
    this.from = from;
  }

  public ZonedDateTime getTo() {
    return to;
  }

  public void setTo(ZonedDateTime to) {
    this.to = to;
  }

  public TimeResolution getTimeResolution() {
    return timeResolution;
  }

  public void setTimeResolution(TimeResolution timeResolution) {
    this.timeResolution = timeResolution;
  }
}
