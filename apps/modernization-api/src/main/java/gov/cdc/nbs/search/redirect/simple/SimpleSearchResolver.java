package gov.cdc.nbs.search.redirect.simple;

import gov.cdc.nbs.encryption.EncryptionService;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
class SimpleSearchResolver {

  private final SimplePatientSearchCriteriaResolver patientSearchCriteriaResolver;
  private final EncryptionService encryptionService;

  SimpleSearchResolver(final EncryptionService encryptionService) {
    patientSearchCriteriaResolver = new SimplePatientSearchCriteriaResolver();
    this.encryptionService = encryptionService;
  }

  Optional<SimpleSearch> resolve(final Map<String, String> criteria) {
    return maybePatient(criteria);
  }

  private Optional<SimpleSearch> maybePatient(final Map<String, String> criteria) {
    return patientSearchCriteriaResolver.resolve(criteria)
        .map(encryptionService::encrypt)
        .map(encrypted -> new SimpleSearch("patients", encrypted));
  }
}
