package gov.cdc.nbs.patient.file.demographics.summary;

import gov.cdc.nbs.demographics.address.DisplayableAddress;
import gov.cdc.nbs.demographics.indentification.DisplayableIdentification;
import gov.cdc.nbs.demographics.phone.DisplayablePhone;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
class PatientDemographicsSummaryResolver {

  private final SummarizedPatientDemographicsFinder finder;
  private final PatientDemographicsSummaryAddressFinder addressFinder;
  private final PatientDemographicsSummaryPhoneFinder phoneFinder;
  private final PatientDemographicsSummaryEmailFinder emailFinder;
  private final PatientDemographicsSummaryIdentificationFinder identificationFinder;
  private final PatientDemographicsSummaryRaceFinder raceFinder;

  PatientDemographicsSummaryResolver(
      final SummarizedPatientDemographicsFinder finder,
      final PatientDemographicsSummaryAddressFinder addressFinder,
      final PatientDemographicsSummaryPhoneFinder phoneFinder,
      final PatientDemographicsSummaryEmailFinder emailFinder,
      final PatientDemographicsSummaryIdentificationFinder identificationFinder,
      final PatientDemographicsSummaryRaceFinder raceFinder) {
    this.finder = finder;
    this.addressFinder = addressFinder;
    this.phoneFinder = phoneFinder;
    this.emailFinder = emailFinder;
    this.identificationFinder = identificationFinder;
    this.raceFinder = raceFinder;
  }

  /**
   * Resolves the {@link PatientDemographicsSummary} for a patient based on the given {@code asOf}
   * if the patient can be found.
   *
   * @param patient The unique identifier of a patient
   * @param asOf The date to find the most recent demographics for
   * @return An {@code Optional} containing the {@code PatientDemographicsSummary} of the patient
   *     for the given {@code asOf} or empty if the patient cannot be found.
   */
  Optional<PatientDemographicsSummary> resolve(final long patient, final LocalDate asOf) {
    return finder
        .find(patient)
        .map(summarized -> resolveMultiValueDemographics(summarized, patient, asOf));
  }

  private PatientDemographicsSummary resolveMultiValueDemographics(
      final SummarizedPatientDemographics summarized, final long patient, final LocalDate asOf) {

    DisplayableAddress address = this.addressFinder.find(patient, asOf).orElse(null);
    DisplayablePhone phone = this.phoneFinder.find(patient, asOf).orElse(null);
    String email = this.emailFinder.find(patient, asOf).orElse(null);
    String ethnicity = summarized.ethnicity();
    Collection<DisplayableIdentification> identifications =
        this.identificationFinder.find(patient, asOf);
    Collection<String> races = this.raceFinder.find(patient);

    return new PatientDemographicsSummary(address, phone, email, ethnicity, identifications, races);
  }
}
