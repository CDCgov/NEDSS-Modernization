package gov.cdc.nbs.patient.file;

import org.springframework.stereotype.Component;
import gov.cdc.nbs.patient.file.PatientFileHeader.MostRecentLegalName;
import gov.cdc.nbs.patient.profile.PatientProfile;
import gov.cdc.nbs.patient.profile.PatientProfileResolver;
import gov.cdc.nbs.patient.search.name.PatientSearchResultLegalNameFinder;
import java.time.Clock;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import org.springframework.web.bind.annotation.PathVariable;
import gov.cdc.nbs.patient.search.name.PatientSearchResultName;
import gov.cdc.nbs.patient.search.PatientSearchResult;
import gov.cdc.nbs.patient.search.PatientSearchResultFinder;
import gov.cdc.nbs.patient.profile.PatientProfileDeletableResolver;

@Component
class PatientFileHeaderFinder {
  private final PatientSearchResultLegalNameFinder nameFinder;
  private final PatientSearchResultFinder patientFinder;
  private final Clock clock;
  private final PatientProfileResolver ppResolver;
  private final PatientProfileDeletableResolver deleteResolver;

  private Map<String, String> statusMap = Map.of(
      "ACTIVE", "ACTIVE",
      "SUPERSEDED", "SUPERSEDED",
      "LOG_DEL", "INACTIVE");

  private String deletable(String statusCd, PatientProfile patientProfile) {
    if (statusCd.equals("LOG_DEL")) {
      return "Is_Inactive";
    }
    return deleteResolver.resolve(patientProfile) ? "Deletable" : "Has_Associations";
  }

  PatientFileHeaderFinder(
      final PatientSearchResultFinder patientFinder,
      final PatientProfileResolver ppResolver,
      final Clock clock,
      final PatientProfileDeletableResolver deleteResolver,
      final PatientSearchResultLegalNameFinder nameFinder) {
    this.patientFinder = patientFinder;
    this.nameFinder = nameFinder;
    this.clock = clock;
    this.ppResolver = ppResolver;
    this.deleteResolver = deleteResolver;
  }

  @SuppressWarnings("java:S3655")
  public PatientFileHeader find(@PathVariable final long patientId) {
    Optional<PatientProfile> optionalPatientProfile = ppResolver.findByShortId(patientId);
    if (optionalPatientProfile.isEmpty()) {
      return null;
    }

    PatientProfile patientProfile = optionalPatientProfile.get();
    long personUid = patientProfile.id();

    Optional<PatientSearchResultName> optionalName = this.nameFinder.find(personUid, LocalDate.now(clock));
    if (optionalName.isEmpty()) {
      return null;
    }

    PatientSearchResultName name = optionalName.get();
    Collection<PatientSearchResult> results = patientFinder.find(Arrays.asList(personUid));
    if (results.isEmpty()) {
      return null;
    }

    PatientSearchResult patientInfo = results.stream().findFirst().get();
    return new PatientFileHeader(personUid, String.valueOf(patientId), patientInfo.local(),
        statusMap.get(patientInfo.status()),
        deletable(patientInfo.status(), patientProfile),
        patientInfo.gender(), patientInfo.birthday().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")),
        new MostRecentLegalName(name.first(), name.last(), name.middle(), name.suffix()));
  }
}

