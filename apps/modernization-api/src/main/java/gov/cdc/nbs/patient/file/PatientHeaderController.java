package gov.cdc.nbs.patient.file;

import gov.cdc.nbs.patient.file.PatientFileHeader.MostRecentLegalName;
import gov.cdc.nbs.patient.profile.PatientProfile;
import gov.cdc.nbs.patient.profile.PatientProfileResolver;
import gov.cdc.nbs.patient.search.name.PatientSearchResultLegalNameFinder;
import io.swagger.v3.oas.annotations.Operation;
import java.time.Clock;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import gov.cdc.nbs.patient.search.name.PatientSearchResultName;
import gov.cdc.nbs.patient.search.PatientSearchResult;
import gov.cdc.nbs.patient.search.PatientSearchResultFinder;
import gov.cdc.nbs.patient.profile.PatientProfileDeletableResolver;

@RestController
public class PatientHeaderController {

  private final PatientSearchResultLegalNameFinder nameFinder;
  private final PatientSearchResultFinder patientFinder;
  private final Clock clock;
  private final PatientProfileResolver ppResolver;
  private final PatientProfileDeletableResolver deleteResolver;

  PatientHeaderController(
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

  @Operation(operationId = "patientFileHeader", summary = "Patient File Header", description = "Patient File Header",
      tags = "PatientFileHeader")
  @GetMapping("/nbs/api/patient/{patientId}/file")
  public PatientFileHeader find(@PathVariable final long patientId) {
    Optional<PatientProfile> optionalPatientProfile = ppResolver.findByShortId(patientId);

    if (optionalPatientProfile.isEmpty()) {
      return null;
    }
    PatientProfile patientProfile = optionalPatientProfile.get();
    long personUid = patientProfile.id();

    Optional<PatientSearchResultName> optionalName = this.nameFinder.find(personUid, LocalDate.now(clock));
    Collection<PatientSearchResult> results = patientFinder.find(Arrays.asList(personUid));
    if (optionalName.isEmpty() || results.isEmpty()) {
      return null;
    }
    PatientSearchResult patientInfo = results.stream().findFirst().get();
    PatientSearchResultName name = optionalName.get();

    return new PatientFileHeader(personUid, String.valueOf(patientId), patientInfo.local(),
        statusMap.get(patientInfo.status()),
        deletable(patientInfo.status(), patientProfile),
        patientInfo.gender(), patientInfo.birthday().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")),
        new MostRecentLegalName(name.first(), name.last(), name.middle(), name.suffix()));
  }
}
// birthday as the value of Person.birth_time formatted as MM/DD/YYYY

