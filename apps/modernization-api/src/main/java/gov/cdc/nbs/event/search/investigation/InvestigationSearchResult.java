package gov.cdc.nbs.event.search.investigation;

import java.time.LocalDate;
import java.util.List;

record InvestigationSearchResult(
    double relevance,
    String id,
    String cdDescTxt,
    String jurisdictionCd,
    String jurisdictionCodeDescTxt,
    String localId,
    LocalDate addTime,
    String investigationStatusCd,
    String notificationRecordStatusCd,
    List<PersonParticipation> personParticipations
    ) {

  record PersonParticipation(
      LocalDate birthTime,
      String currSexCd,
      String typeCd,
      String firstName,
      String lastName,
      String personCd,
      long personParentUid,
      String local
  ) {
  }
}
