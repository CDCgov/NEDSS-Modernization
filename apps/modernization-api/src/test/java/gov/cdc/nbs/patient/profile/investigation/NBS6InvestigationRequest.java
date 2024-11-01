package gov.cdc.nbs.patient.profile.investigation;

record NBS6InvestigationRequest(String location, String contextAction) {

  NBS6InvestigationRequest(String location) {
    this(location, null);
  }

  NBS6InvestigationRequest withContextAction(String contextAction) {
    return new NBS6InvestigationRequest(location, contextAction);
  }
}
