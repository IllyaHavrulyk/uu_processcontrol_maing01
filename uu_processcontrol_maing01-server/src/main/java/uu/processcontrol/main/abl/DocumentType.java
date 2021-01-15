package uu.processcontrol.main.abl;

public enum DocumentType {

  B22("B22");

  String type;

  DocumentType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}
