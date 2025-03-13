package gov.cdc.nbs.patient.search.indexing;

import gov.cdc.nbs.patient.search.SearchablePatient;
import gov.cdc.nbs.patient.search.indexing.address.SearchablePatientAddressFinder;
import gov.cdc.nbs.patient.search.indexing.identification.SearchablePatientIdentificationFinder;
import gov.cdc.nbs.patient.search.indexing.name.SearchablePatientNameFinder;
import gov.cdc.nbs.patient.search.indexing.race.SearchablePatientRaceFinder;
import gov.cdc.nbs.patient.search.indexing.telecom.SearchablePatientTelecom;
import gov.cdc.nbs.patient.search.indexing.telecom.SearchablePatientTelecomFinder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("java:S3776") // cog complexity on name sorting
@Component
class SearchablePatientResolver {

  private final SearchablePatientFinder patientFinder;
  private final SearchablePatientNameFinder nameFinder;
  private final SearchablePatientAddressFinder addressFinder;
  private final SearchablePatientRaceFinder raceFinder;
  private final SearchablePatientIdentificationFinder identificationFinder;
  private final SearchablePatientTelecomFinder telecomFinder;

  SearchablePatientResolver(
      final SearchablePatientFinder patientFinder,
      final SearchablePatientNameFinder nameFinder,
      final SearchablePatientAddressFinder addressFinder,
      final SearchablePatientRaceFinder raceFinder,
      final SearchablePatientIdentificationFinder identificationFinder,
      final SearchablePatientTelecomFinder telecomFinder) {
    this.patientFinder = patientFinder;
    this.nameFinder = nameFinder;
    this.addressFinder = addressFinder;
    this.raceFinder = raceFinder;
    this.identificationFinder = identificationFinder;
    this.telecomFinder = telecomFinder;
  }

  Optional<SearchablePatient> resolve(final long identifier) {
    return this.patientFinder.find(identifier).map(this::withDemographics);
  }

  private SearchablePatient withDemographics(final SearchablePatient patient) {

    List<SearchablePatient.Name> names = this.nameFinder.find(patient.identifier());
    String name = names.stream()
        .map(elem -> (elem.last() == null ? "" : elem.last()) + " "
            + (elem.first() == null ? "" : elem.first()))
        .findFirst()
        .orElse("");
    name = name != null ? name.toUpperCase() : null;

    List<SearchablePatient.Address> addresses = this.addressFinder.find(patient.identifier());
    List<SearchablePatient.Race> races = this.raceFinder.find(patient.identifier());
    List<SearchablePatient.Identification> identifications = identificationFinder.find(patient.identifier());
    String identification = identifications.stream()
        .map(SearchablePatient.Identification::value)
        .findFirst()
        .orElse(null);
    identification = identification != null ? identification.toUpperCase() : null;

    SearchablePatientTelecom telecom = this.telecomFinder.find(patient.identifier());

    List<SearchablePatient.Phone> phones = telecom.phones();
    String phone = phones.stream()
        .map(SearchablePatient.Phone::number)
        .findFirst()
        .orElse(null);
    phone = phone != null ? phone.toUpperCase() : null;

    List<SearchablePatient.Email> emails = telecom.emails();

    String email = emails.stream()
        .map(SearchablePatient.Email::address)
        .findFirst()
        .orElse(null);
    email = email != null ? email.toUpperCase() : null;

    String address = addresses.stream()
        .map(elem -> (elem.address1() == null ? "" : elem.address1()) + " "
            + (elem.address2() == null ? "" : elem.address2()) + " " + (elem.city() == null ? "" : elem.city()) + " "
            + (elem.state() == null ? "" : elem.state()) + " " + (elem.zip() == null ? "" : elem.zip()))
        .findFirst()
        .orElse("");
    address = address != null ? address.toUpperCase() : null;

    SearchablePatient.Sort sort = new SearchablePatient.Sort(name, identification, email, phone, address);

    return new SearchablePatient(
        patient.identifier(),
        patient.local(),
        patient.shortId(),
        patient.status(),
        patient.birthday(),
        patient.deceased(),
        patient.gender(),
        patient.ethnicity(),
        names,
        addresses,
        phones,
        emails,
        races,
        identifications,
        patient.documentIds(),
        patient.morbidityReportIds(),
        patient.treatmentIds(),
        patient.vaccinationIds(),
        patient.abcsCaseIds(),
        patient.cityCaseIds(),
        patient.stateCaseIds(),
        patient.accessionIds(),
        patient.investigationIds(),
        patient.labReportIds(),
        patient.notificationIds(),
        sort);
  }
}
