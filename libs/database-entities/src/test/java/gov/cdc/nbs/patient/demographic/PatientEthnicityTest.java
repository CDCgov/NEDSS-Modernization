package gov.cdc.nbs.patient.demographic;

import static org.assertj.core.api.Assertions.assertThat;

import gov.cdc.nbs.entity.odse.AuditAssertions;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PersonEthnicGroup;
import gov.cdc.nbs.entity.odse.PersonEthnicGroupId;
import gov.cdc.nbs.patient.PatientCommand;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class PatientEthnicityTest {

  @Test
  void should_change_ethnicity() {

    Person patient =
        new Person(
                new PatientCommand.CreatePatient(
                    121L, "TEST307", 883L, LocalDateTime.parse("2002-03-05T07:11:13")))
            .update(
                new PatientCommand.UpdateEthnicityInfo(
                    121L,
                    LocalDate.parse("2010-03-03"),
                    "ethnic-group-value",
                    null,
                    131L,
                    LocalDateTime.parse("2020-03-03T10:15:30")));

    assertThat(patient)
        .returns(131L, p -> p.audit().changed().changedBy())
        .returns(LocalDateTime.parse("2020-03-03T10:15:30"), p -> p.audit().changed().changedOn())
        .extracting(Person::ethnicity)
        .returns(LocalDate.parse("2010-03-03"), PatientEthnicity::asOf)
        .returns("ethnic-group-value", PatientEthnicity::ethnicGroup);
  }

  @Test
  void should_add_detailed_ethnicity() {

    Person patient =
        new Person(
                new PatientCommand.CreatePatient(
                    121L, "TEST307", 883L, LocalDateTime.parse("2002-03-05T07:11:13")))
            .update(
                new PatientCommand.UpdateEthnicityInfo(
                    121L,
                    LocalDate.parse("2010-03-03"),
                    "ethnic-group-value",
                    null,
                    131L,
                    LocalDateTime.parse("2020-03-03T10:15:30")));

    patient.add(
        new PatientCommand.AddDetailedEthnicity(
            121L, "ethnicity-value", 131L, LocalDateTime.parse("2020-03-03T10:15:30")));

    assertThat(patient.ethnicity().ethnicities())
        .satisfiesExactlyInAnyOrder(
            detail ->
                assertThat(detail)
                    .returns("ACTIVE", e -> e.recordStatus().status())
                    .extracting(PersonEthnicGroup::getId)
                    .returns(121L, PersonEthnicGroupId::getPersonUid)
                    .returns("ethnicity-value", PersonEthnicGroupId::getEthnicGroupCd));
  }

  @Test
  void should_not_add_detailed_ethnicity_if_patient_does_not_already_have_an_ethnicity() {

    Person patient =
        new Person(
                new PatientCommand.CreatePatient(
                    121L, "TEST307", 883L, LocalDateTime.parse("2002-03-05T07:11:13")))
            .add(
                new PatientCommand.AddDetailedEthnicity(
                    121L, "ethnicity-value", 131L, LocalDateTime.parse("2020-03-03T10:15:30")));

    assertThat(patient.ethnicity().ethnicities()).isEmpty();
  }

  @Test
  void should_not_add_detailed_ethnicity_when_unknown() {

    Person patient =
        new Person(
                new PatientCommand.CreatePatient(
                    121L, "TEST307", 883L, LocalDateTime.parse("2002-03-05T07:11:13")))
            .update(
                new PatientCommand.UpdateEthnicityInfo(
                    121L,
                    LocalDate.parse("2010-03-03"),
                    "UNK",
                    null,
                    131L,
                    LocalDateTime.parse("2020-03-03T10:15:30")));

    patient.add(
        new PatientCommand.AddDetailedEthnicity(
            121L, "ethnicity-value", 131L, LocalDateTime.parse("2020-03-03T10:15:30")));

    assertThat(patient.ethnicity().ethnicities()).isEmpty();
  }

  @Test
  void should_add_another_detailed_ethnicity() {

    Person patient =
        new Person(
                new PatientCommand.CreatePatient(
                    121L, "TEST307", 883L, LocalDateTime.parse("2002-03-05T07:11:13")))
            .update(
                new PatientCommand.UpdateEthnicityInfo(
                    121L,
                    LocalDate.parse("2010-03-03"),
                    "ethnic-group-value",
                    null,
                    131L,
                    LocalDateTime.parse("2020-03-03T10:15:30")))
            .add(
                new PatientCommand.AddDetailedEthnicity(
                    121L, "ethnicity-value", 131L, LocalDateTime.parse("2020-03-03T10:15:30")));

    patient.add(
        new PatientCommand.AddDetailedEthnicity(
            121L, "next-ethnicity-value", 131L, LocalDateTime.parse("2020-03-03T10:15:30")));

    assertThat(patient.ethnicity().ethnicities())
        .satisfiesExactlyInAnyOrder(
            actual ->
                assertThat(actual)
                    .returns("ACTIVE", group -> group.recordStatus().status())
                    .extracting(PersonEthnicGroup::getId)
                    .returns(121L, PersonEthnicGroupId::getPersonUid)
                    .returns("ethnicity-value", PersonEthnicGroupId::getEthnicGroupCd),
            actual ->
                assertThat(actual)
                    .returns("ACTIVE", group -> group.recordStatus().status())
                    .extracting(PersonEthnicGroup::getId)
                    .returns(121L, PersonEthnicGroupId::getPersonUid)
                    .returns("next-ethnicity-value", PersonEthnicGroupId::getEthnicGroupCd));
  }

  @Test
  void should_remove_detailed_ethnicity() {

    Person patient =
        new Person(
                new PatientCommand.CreatePatient(
                    121L, "TEST307", 883L, LocalDateTime.parse("2002-03-05T07:11:13")))
            .update(
                new PatientCommand.UpdateEthnicityInfo(
                    121L,
                    LocalDate.parse("2010-03-03"),
                    "ethnic-group-value",
                    null,
                    131L,
                    LocalDateTime.parse("2020-03-03T10:15:30")))
            .add(
                new PatientCommand.AddDetailedEthnicity(
                    121L, "ethnicity-value", 131L, LocalDateTime.parse("2020-03-03T10:15:30")))
            .add(
                new PatientCommand.AddDetailedEthnicity(
                    121L,
                    "next-ethnicity-value",
                    131L,
                    LocalDateTime.parse("2020-03-03T10:15:30")));

    patient.remove(
        new PatientCommand.RemoveDetailedEthnicity(
            121L, "next-ethnicity-value", 131L, LocalDateTime.parse("2020-03-03T10:15:30")));

    assertThat(patient.ethnicity().ethnicities())
        .satisfiesExactly(
            actual ->
                assertThat(actual)
                    .returns("ACTIVE", group -> group.recordStatus().status())
                    .extracting(PersonEthnicGroup::getId)
                    .returns(121L, PersonEthnicGroupId::getPersonUid)
                    .returns("ethnicity-value", PersonEthnicGroupId::getEthnicGroupCd));
  }

  @Test
  void should_remove_detailed_ethnicity_when_updated_to_unknown() {
    Person patient =
        new Person(
                new PatientCommand.CreatePatient(
                    307L, "TEST307", 883L, LocalDateTime.parse("2002-03-05T07:11:13")))
            .update(
                new PatientCommand.UpdateEthnicityInfo(
                    121L,
                    LocalDate.parse("2010-03-03"),
                    "ethnic-group-value",
                    null,
                    131L,
                    LocalDateTime.parse("2020-03-03T10:15:30")))
            .add(
                new PatientCommand.AddDetailedEthnicity(
                    121L, "ethnicity-value", 131L, LocalDateTime.parse("2020-03-03T10:15:30")))
            .add(
                new PatientCommand.AddDetailedEthnicity(
                    121L, "next-ethnicity-value", 131L, LocalDateTime.parse("2020-03-03T10:15:30")))
            .update(
                new PatientCommand.UpdateEthnicityInfo(
                    121L,
                    LocalDate.parse("2013-12-11"),
                    "UNK",
                    "unknown-reason-value",
                    131L,
                    LocalDateTime.parse("2020-03-03T10:15:30")));

    assertThat(patient)
        .extracting(Person::ethnicity)
        .returns("UNK", PatientEthnicity::ethnicGroup)
        .returns("unknown-reason-value", PatientEthnicity::unknownReason)
        .extracting(PatientEthnicity::ethnicities)
        .satisfies(details -> assertThat(details).isEmpty());
  }

  @Test
  void should_only_apply_unknown_reason_for_unknown_ethnicity() {

    Person patient =
        new Person(
                new PatientCommand.CreatePatient(
                    307L, "TEST307", 883L, LocalDateTime.parse("2002-03-05T07:11:13")))
            .update(
                new PatientCommand.UpdateEthnicityInfo(
                    121L,
                    LocalDate.parse("2010-03-03"),
                    "ethnic-group-value",
                    "unknown-reason-value",
                    131L,
                    LocalDateTime.parse("2020-03-03T10:15:30")));

    assertThat(patient)
        .extracting(Person::ethnicity)
        .extracting(PatientEthnicity::unknownReason)
        .satisfies(reason -> assertThat(reason).isNull());
  }

  @Test
  void should_clear_ethnicity_demographics() {

    Person patient =
        new Person(
                new PatientCommand.CreatePatient(
                    307L, "TEST307", 883L, LocalDateTime.parse("2002-03-05T07:11:13")))
            .update(
                new PatientCommand.UpdateEthnicityInfo(
                    121L,
                    LocalDate.parse("2010-03-03"),
                    "ethnic-group-value",
                    null,
                    131L,
                    LocalDateTime.parse("2020-03-03T10:15:30")));

    patient.add(
        new PatientCommand.AddDetailedEthnicity(
            121L, "ethnicity-value", 131L, LocalDateTime.parse("2020-03-03T10:15:30")));

    patient.clear(
        new PatientCommand.ClearEthnicityDemographics(
            1046L, 673L, LocalDateTime.parse("2023-03-07T11:19:23")));

    assertThat(patient.ethnicity())
        .returns(null, PatientEthnicity::asOf)
        .returns(null, PatientEthnicity::ethnicGroup)
        .returns(null, PatientEthnicity::unknownReason)
        .extracting(PatientEthnicity::ethnicities)
        .satisfies(details -> assertThat(details).isEmpty());
  }

  @Test
  void should_noop_when_clearing_ethnicity_demographics_when_not_present() {
    Person patient =
        new Person(
                new PatientCommand.CreatePatient(
                    307L, "TEST307", 883L, LocalDateTime.parse("2002-03-05T07:11:13")))
            .clear(
                new PatientCommand.ClearEthnicityDemographics(
                    1046L, 673L, LocalDateTime.parse("2023-03-07T11:19:23")));

    assertThat(patient)
        .satisfies(
            changed ->
                assertThat(changed)
                    .extracting(Person::audit)
                    .satisfies(AuditAssertions.added(883L, "2002-03-05T07:11:13")))
        .returns(null, Person::ethnicity);
  }
}
