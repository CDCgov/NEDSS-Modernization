package org.immregistries.smm.tester.manager.query;

public enum PatientIdType {
  MR ("Medical Record Number"), PT ("Patient External Identifier"), SR ("State Registry ID")
  ;
  private String label;
  public String getLabel() {
    return label;
  }
  private PatientIdType(String label)
  {
    this.label = label;
  }
}
