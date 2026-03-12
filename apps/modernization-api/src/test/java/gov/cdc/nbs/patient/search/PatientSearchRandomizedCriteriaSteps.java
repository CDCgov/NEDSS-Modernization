package gov.cdc.nbs.patient.search;

import gov.cdc.nbs.message.enums.Deceased;
import gov.cdc.nbs.patient.identifier.PatientShortIdentifierResolver;
import gov.cdc.nbs.search.criteria.date.DateCriteria;
import gov.cdc.nbs.search.criteria.date.DateCriteria.Between;
import gov.cdc.nbs.search.criteria.date.DateCriteria.Equals;
import gov.cdc.nbs.support.util.RandomUtil;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import java.util.OptionalLong;

public class PatientSearchRandomizedCriteriaSteps {

  private final Active<PatientSearchCriteria> criteria;
  private final PatientShortIdentifierResolver resolver;
  private final Active<SearchablePatient> searchable;

  public PatientSearchRandomizedCriteriaSteps(
      final Active<PatientSearchCriteria> criteria,
      final PatientShortIdentifierResolver resolver,
      final Active<SearchablePatient> searchable) {
    this.criteria = criteria;
    this.resolver = resolver;
    this.searchable = searchable;
  }

  @Given("I add the patient criteria {string} {string}")
  public void i_add_the_patient_criteria(final String field, final String qualifier) {

    if (field == null || field.isEmpty()) {
      return;
    }
    this.criteria.active(current -> applyCriteriaFromTarget(current, field, qualifier));
  }

  private PatientSearchCriteria applyCriteriaFromTarget(
      final PatientSearchCriteria filter, final String field, final String qualifier) {
    switch (field) {
      case "email" ->
          emailFromTarget().map(SearchablePatient.Email::address).ifPresent(filter::setEmail);

      case "last name" ->
          nameFromTarget().map(SearchablePatient.Name::last).ifPresent(filter::setLastName);

      case "first name" ->
          nameFromTarget().map(SearchablePatient.Name::first).ifPresent(filter::setFirstName);

      case "race" ->
          raceFromTarget().map(SearchablePatient.Race::category).ifPresent(filter::setRace);

      case "patient short id" ->
          this.searchable
              .maybeActive()
              .map(SearchablePatient::local)
              .map(resolver::resolve)
              .filter(OptionalLong::isPresent)
              .map(OptionalLong::getAsLong)
              .map(String::valueOf)
              .ifPresent(filter::setId);

      case "phone number" ->
          phoneFromTarget().map(SearchablePatient.Phone::number).ifPresent(filter::setPhoneNumber);

      case "birthday" ->
          birthdayFromTarget(qualifier)
              .ifPresent(
                  birthday -> {
                    filter.setDateOfBirth(birthday);
                    filter.setDateOfBirthOperator(resolveQualifier(qualifier));
                  });

      case "birthday day" ->
          birthdayDayFromTarget()
              .ifPresent(
                  day -> {
                    filter.setBornOn(new DateCriteria(new Equals(day, null, null), null));
                  });

      case "birthday month" ->
          birthdayMonthFromTarget()
              .ifPresent(
                  month -> {
                    filter.setBornOn(new DateCriteria(new Equals(null, month, null), null));
                  });

      case "birthday year" ->
          birthdayYearFromTarget()
              .ifPresent(
                  year -> {
                    filter.setBornOn(new DateCriteria(new Equals(null, null, year), null));
                  });

      case "birthday low" ->
          birthdayFromTarget("equal")
              .ifPresent(
                  birthday -> {
                    filter.setBornOn(
                        new DateCriteria(null, new Between(birthday.minusDays(2), null)));
                  });

      case "birthday high" ->
          birthdayFromTarget("equal")
              .ifPresent(
                  birthday -> {
                    filter.setBornOn(
                        new DateCriteria(null, new Between(null, birthday.plusDays(2))));
                  });

      case "gender" ->
          this.searchable.maybeActive().map(SearchablePatient::gender).ifPresent(filter::setGender);

      case "deceased" ->
          this.searchable
              .maybeActive()
              .map(SearchablePatient::deceased)
              .map(Deceased::resolve)
              .ifPresent(filter::setDeceased);

      case "ethnicity" ->
          this.searchable
              .maybeActive()
              .map(SearchablePatient::ethnicity)
              .ifPresent(filter::setEthnicity);

      case "address" ->
          addressFromTarget()
              .map(SearchablePatient.Address::address1)
              .ifPresent(filter::setAddress);

      case "city" ->
          addressFromTarget().map(SearchablePatient.Address::city).ifPresent(filter::setCity);

      case "state" ->
          addressFromTarget().map(SearchablePatient.Address::state).ifPresent(filter::setState);

      case "country" ->
          addressFromTarget().map(SearchablePatient.Address::country).ifPresent(filter::setCountry);

      case "zip code" ->
          addressFromTarget().map(SearchablePatient.Address::zip).ifPresent(filter::setZip);

      default -> throw new IllegalArgumentException("Invalid search field specified: " + field);
    }

    return filter;
  }

  private Optional<SearchablePatient.Email> emailFromTarget() {
    return this.searchable.maybeActive().stream()
        .map(SearchablePatient::emails)
        .flatMap(Collection::stream)
        .findFirst();
  }

  private Optional<SearchablePatient.Name> nameFromTarget() {
    return this.searchable.maybeActive().stream()
        .map(SearchablePatient::names)
        .flatMap(Collection::stream)
        .findFirst();
  }

  private Optional<SearchablePatient.Race> raceFromTarget() {
    return this.searchable.maybeActive().stream()
        .map(SearchablePatient::races)
        .flatMap(Collection::stream)
        .findFirst();
  }

  private Optional<SearchablePatient.Phone> phoneFromTarget() {
    return this.searchable.maybeActive().stream()
        .map(SearchablePatient::phones)
        .flatMap(Collection::stream)
        .findFirst();
  }

  private Optional<SearchablePatient.Address> addressFromTarget() {
    return this.searchable.maybeActive().stream()
        .map(SearchablePatient::addresses)
        .flatMap(Collection::stream)
        .findFirst();
  }

  private Optional<SearchablePatient.Identification> identificationFromTarget() {
    return this.searchable.maybeActive().stream()
        .map(SearchablePatient::identifications)
        .flatMap(Collection::stream)
        .findFirst();
  }

  private Optional<LocalDate> birthdayFromTarget(final String qualifier) {
    return this.searchable.maybeActive().map(found -> resolveDateOfBirth(found, qualifier));
  }

  private Optional<Integer> birthdayDayFromTarget() {
    return this.searchable.maybeActive().map(found -> found.birthday().getDayOfMonth());
  }

  private Optional<Integer> birthdayMonthFromTarget() {
    return this.searchable.maybeActive().map(found -> found.birthday().getMonthValue());
  }

  private Optional<Integer> birthdayYearFromTarget() {
    return this.searchable.maybeActive().map(found -> found.birthday().getYear());
  }

  private LocalDate resolveDateOfBirth(final SearchablePatient search, final String qualifier) {
    LocalDate dateOfBirth = search.birthday();
    return switch (qualifier.toLowerCase()) {
      case "before" -> dateOfBirth.plusDays(15);
      case "after" -> dateOfBirth.minusDays(15);
      default -> dateOfBirth;
    };
  }

  private String resolveQualifier(final String value) {
    //  ensures that the date qualifier matches the expected values of the Operator ENUM in the
    // graphql schema
    return "equals".equalsIgnoreCase(value) ? "EQUAL" : value.toUpperCase();
  }

  @Given("I add the partial patient criteria {string}")
  public void i_add_the_partial_patient_criteria(final String field) {
    if (field == null || field.isEmpty()) {
      return;
    }

    this.criteria.active(filter -> applyPartialCriteriaFromTarget(filter, field));
  }

  private PatientSearchCriteria applyPartialCriteriaFromTarget(
      final PatientSearchCriteria filter, final String field) {
    switch (field.toLowerCase()) {
      case "identification" ->
          identificationFromTarget()
              .map(
                  found ->
                      new PatientSearchCriteria.Identification(
                          RandomUtil.randomPartialDataSearchString(found.value()),
                          null,
                          found.type()))
              .ifPresent(filter::setIdentification);

      case "phone number" ->
          phoneFromTarget()
              .map(found -> RandomUtil.randomPartialDataSearchString(found.number()))
              .ifPresent(filter::setPhoneNumber);

      case "last name" ->
          nameFromTarget()
              .map(found -> RandomUtil.randomPartialDataSearchString(found.last()))
              .ifPresent(filter::setLastName);

      case "first name" ->
          nameFromTarget()
              .map(found -> RandomUtil.randomPartialDataSearchString(found.first()))
              .ifPresent(filter::setFirstName);

      case "address" ->
          addressFromTarget()
              .map(found -> RandomUtil.randomPartialDataSearchString(found.address1()))
              .ifPresent(filter::setAddress);

      case "city" ->
          addressFromTarget()
              .map(found -> RandomUtil.randomPartialDataSearchString(found.city()))
              .ifPresent(filter::setCity);

      case "zip code" ->
          addressFromTarget()
              .map(found -> RandomUtil.randomPartialDataSearchString(found.zip()))
              .ifPresent(filter::setZip);

      default -> throw new IllegalArgumentException("Invalid field specified: " + field);
    }

    return filter;
  }
}
