package uu.processcontrol.main.abl.entity;

public enum PhaseCode {
  RECEIVING("RECEIVING"),
  VALIDATION("VALIDATION"),
  MODERATING("MODERATING");

  String code;

  PhaseCode(String code) {
    this.code = code;
  }
}
