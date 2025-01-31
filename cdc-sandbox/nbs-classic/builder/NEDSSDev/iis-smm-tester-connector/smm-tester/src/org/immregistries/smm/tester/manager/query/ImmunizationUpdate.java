package org.immregistries.smm.tester.manager.query;

import java.util.List;

import org.immregistries.smm.tester.manager.response.ImmunizationMessage;

public class ImmunizationUpdate implements ImmunizationMessage {
  protected Patient patient = null;
  protected List<Vaccination> vaccinationList = null;

  public Patient getPatient() {
    return patient;
  }

  public void setPatient(Patient patient) {
    this.patient = patient;
  }

  public List<Vaccination> getVaccinationList() {
    return vaccinationList;
  }

  public void setVaccinationList(List<Vaccination> vaccinationList) {
    this.vaccinationList = vaccinationList;
  }

}
