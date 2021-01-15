package uu.processcontrol.main.abl;

public enum ValidationResultSeverity {
  OK(1),
  WARNING(2),
  ERROR(3);

  private final int importance;

  ValidationResultSeverity(int importance) {
    this.importance = importance;
  }

  public ValidationResultSeverity getHigherImportance(ValidationResultSeverity o) {
    return this.importance >= o.importance ? this : o;
  }

  public int getImportance() {
    return importance;
  }


}
