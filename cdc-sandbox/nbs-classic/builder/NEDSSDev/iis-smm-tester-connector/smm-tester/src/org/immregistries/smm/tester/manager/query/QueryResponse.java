package org.immregistries.smm.tester.manager.query;

import java.util.List;

public class QueryResponse extends ImmunizationUpdate {
  private QueryResponseType queryResponseType = null;
  private List<Patient> patientList = null;

  public List<Patient> getPatientList() {
    return patientList;
  }

  public void setPatientList(List<Patient> patientList) {
    this.patientList = patientList;
  }

  public QueryResponseType getQueryResponseType() {
    return queryResponseType;
  }

  public void setQueryResponseType(QueryResponseType queryResponseType) {
    this.queryResponseType = queryResponseType;
  }
}
