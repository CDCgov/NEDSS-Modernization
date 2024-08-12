package gov.cdc.nbs.search.redirect.simple;

import gov.cdc.nbs.encryption.EncryptionService;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
class SimpleSearchResolver {

  private final SimplePatientSearchCriteriaResolver patientSearchCriteriaResolver;
  private final SimpleLaboratoryReportSearchCriteriaResolver laboratoryReportSearchCriteriaResolver;
  private final SimpleInvestigationSearchCriteriaResolver investigationSearchCriteriaResolver;
  private final EncryptionService encryptionService;

  SimpleSearchResolver(final EncryptionService encryptionService) {
    patientSearchCriteriaResolver = new SimplePatientSearchCriteriaResolver();
    laboratoryReportSearchCriteriaResolver = new SimpleLaboratoryReportSearchCriteriaResolver();
    investigationSearchCriteriaResolver = new SimpleInvestigationSearchCriteriaResolver();
    this.encryptionService = encryptionService;
  }



  Optional<SimpleSearch> resolve(final Map<String, String> criteria) {
    return maybePatient(criteria)
        .or(() -> maybeLaboratoryReport(criteria))
        .or(() -> maybeInvestigation(criteria));
  }

  private Optional<SimpleSearch> maybePatient(final Map<String, String> criteria) {
    return patientSearchCriteriaResolver.resolve(criteria)
        .map(encryptionService::handleEncryption)
        .map(encrypted -> new SimpleSearch("patients", encrypted));
  }

  private Optional<SimpleSearch> maybeLaboratoryReport(final Map<String, String> criteria) {
    return laboratoryReportSearchCriteriaResolver.resolve(criteria)
        .map(encryptionService::handleEncryption)
        .map(encrypted -> new SimpleSearch("lab-reports", encrypted));
  }

  private Optional<SimpleSearch> maybeInvestigation(final Map<String, String> criteria) {
    return investigationSearchCriteriaResolver.resolve(criteria)
        .map(encryptionService::handleEncryption)
        .map(encrypted -> new SimpleSearch("investigations", encrypted));
  }
}
