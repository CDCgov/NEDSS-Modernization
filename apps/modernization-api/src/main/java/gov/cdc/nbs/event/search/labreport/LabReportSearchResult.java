package gov.cdc.nbs.event.search.labreport;

import java.time.LocalDate;
import java.util.List;
import gov.cdc.nbs.patient.documentsrequiringreview.detail.LabTestSummary;

record LabReportSearchResult(
    Double relevance,
    String id,
    String jurisdictionCd,
    String localId,
    LocalDate addTime,
    List<PersonParticipation> personParticipations,
    List<OrganizationParticipation> organizationParticipations,
    List<Observation> observations,
    List<AssociatedInvestigation> associatedInvestigations,
    List<LabTestSummary> tests) {

  record PersonParticipation(
      LocalDate birthTime,
      String currSexCd,
      String typeCd,
      String firstName,
      String lastName,
      String personCd,
      long personParentUid,
      String local) {
  }

  record OrganizationParticipation(
      String typeCd,
      String name) {

  }

  record Observation(
      String cdDescTxt,
      String altCd,
      String displayName) {

  }

  record AssociatedInvestigation(
      String cdDescTxt,
      String localId) {

  }
}
