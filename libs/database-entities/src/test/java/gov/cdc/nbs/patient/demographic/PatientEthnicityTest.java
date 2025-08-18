package gov.cdc.nbs.patient.demographic;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PersonEthnicGroup;
import gov.cdc.nbs.entity.odse.PersonEthnicGroupId;
import gov.cdc.nbs.patient.PatientCommand;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class PatientEthnicityTest {

  @Test
  void should_change_ethnicity() {

    Person patient = new Person(121L, "local-id-value");

    patient.update(
        new PatientCommand.UpdateEthnicityInfo(
            121L,
            LocalDate.parse("2010-03-03"),
            "ethnic-group-value",
            null,
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    assertThat(patient)
        .returns(131L, p -> p.audit().changed().changedBy())
        .returns(LocalDateTime.parse("2020-03-03T10:15:30"), p -> p.audit().changed().changedOn())
        .extracting(Person::ethnicity)
        .returns(LocalDate.parse("2010-03-03"), PatientEthnicity::asOf)
        .returns("ethnic-group-value", PatientEthnicity::ethnicGroup);
  }

  @Test
  void should_add_detailed_ethnicity() {

    Person patient = new Person(121L, "local-id-value")
        .update(
            new PatientCommand.UpdateEthnicityInfo(
                121L,
                LocalDate.parse("2010-03-03"),
                "ethnic-group-value",
                null,
                131L,
                LocalDateTime.parse("2020-03-03T10:15:30")
            )
        );

    patient.add(
        new PatientCommand.AddDetailedEthnicity(
            121L,
            "ethnicity-value",
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    assertThat(patient.ethnicity().ethnicities())
        .satisfiesExactlyInAnyOrder(
            detail -> assertThat(detail)
                .returns("ACTIVE", e -> e.recordStatus().status())
                .extracting(PersonEthnicGroup::getId)
                .returns(121L, PersonEthnicGroupId::getPersonUid)
                .returns("ethnicity-value", PersonEthnicGroupId::getEthnicGroupCd)
        );

  }

  @Test
  void should_not_add_detailed_ethnicity_if_patient_does_not_already_have_an_ethnicity() {

    Person patient = new Person(121L, "local-id-value");

    patient.add(
        new PatientCommand.AddDetailedEthnicity(
            121L,
            "ethnicity-value",
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    assertThat(patient.ethnicity().ethnicities()).isEmpty();

  }


  @Test
  void should_add_another_detailed_ethnicity() {

    Person patient = new Person(121L, "local-id-value")
        .update(
            new PatientCommand.UpdateEthnicityInfo(
                121L,
                LocalDate.parse("2010-03-03"),
                "ethnic-group-value",
                null,
                131L,
                LocalDateTime.parse("2020-03-03T10:15:30")
            )
        )
        .add(
            new PatientCommand.AddDetailedEthnicity(
                121L,
                "ethnicity-value",
                131L,
                LocalDateTime.parse("2020-03-03T10:15:30")
            )
        );

    patient.add(
        new PatientCommand.AddDetailedEthnicity(
            121L,
            "next-ethnicity-value",
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    assertThat(patient.ethnicity().ethnicities())
        .satisfiesExactlyInAnyOrder(
            actual -> assertThat(actual)
                .returns("ACTIVE", group -> group.recordStatus().status())
                .extracting(PersonEthnicGroup::getId)
                .returns(121L, PersonEthnicGroupId::getPersonUid)
                .returns("ethnicity-value", PersonEthnicGroupId::getEthnicGroupCd),
            actual -> assertThat(actual)
                .returns("ACTIVE", group -> group.recordStatus().status())
                .extracting(PersonEthnicGroup::getId)
                .returns(121L, PersonEthnicGroupId::getPersonUid)
                .returns("next-ethnicity-value", PersonEthnicGroupId::getEthnicGroupCd)
        );

  }

  @Test
  void should_remove_detailed_ethnicity() {

    Person patient = new Person(121L, "local-id-value")
        .update(
            new PatientCommand.UpdateEthnicityInfo(
                121L,
                LocalDate.parse("2010-03-03"),
                "ethnic-group-value",
                null,
                131L,
                LocalDateTime.parse("2020-03-03T10:15:30")
            )
        ).add(
            new PatientCommand.AddDetailedEthnicity(
                121L,
                "ethnicity-value",
                131L,
                LocalDateTime.parse("2020-03-03T10:15:30")
            )
        ).add(
            new PatientCommand.AddDetailedEthnicity(
                121L,
                "next-ethnicity-value",
                131L,
                LocalDateTime.parse("2020-03-03T10:15:30")
            )
        );

    patient.remove(
        new PatientCommand.RemoveDetailedEthnicity(
            121L,
            "next-ethnicity-value",
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    assertThat(patient.ethnicity().ethnicities())
        .satisfiesExactly(
            actual -> assertThat(actual)
                .returns("ACTIVE", group -> group.recordStatus().status())
                .extracting(PersonEthnicGroup::getId)
                .returns(121L, PersonEthnicGroupId::getPersonUid)
                .returns("ethnicity-value", PersonEthnicGroupId::getEthnicGroupCd)
        );

  }

  @Test
  void should_remove_detailed_ethnicity_when_updated_to_unknown() {
    Person patient = new Person(121L, "local-id-value")
        .update(
            new PatientCommand.UpdateEthnicityInfo(
                121L,
                LocalDate.parse("2010-03-03"),
                "ethnic-group-value",
                null,
                131L,
                LocalDateTime.parse("2020-03-03T10:15:30")
            )
        ).add(
            new PatientCommand.AddDetailedEthnicity(
                121L,
                "ethnicity-value",
                131L,
                LocalDateTime.parse("2020-03-03T10:15:30")
            )
        ).add(
            new PatientCommand.AddDetailedEthnicity(
                121L,
                "next-ethnicity-value",
                131L,
                LocalDateTime.parse("2020-03-03T10:15:30")
            )
        ).update(
            new PatientCommand.UpdateEthnicityInfo(
                121L,
                LocalDate.parse("2013-12-11"),
                "UNK",
                "unknown-reason-value",
                131L,
                LocalDateTime.parse("2020-03-03T10:15:30")
            )
        );

    assertThat(patient)
        .extracting(Person::ethnicity)
        .returns("UNK", PatientEthnicity::ethnicGroup)
        .returns("unknown-reason-value", PatientEthnicity::unknownReason)
        .extracting(PatientEthnicity::ethnicities)
        .satisfies(details -> assertThat(details).isEmpty());

  }

  @Test
  void should_only_apply_unknown_reason_for_unknown_ethnicity() {

    Person patient = new Person(121L, "local-id-value");

    patient.update(
        new PatientCommand.UpdateEthnicityInfo(
            121L,
            LocalDate.parse("2010-03-03"),
            "ethnic-group-value",
            "unknown-reason-value",
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    assertThat(patient)
        .extracting(Person::ethnicity)
        .extracting(PatientEthnicity::unknownReason)
        .satisfies(reason -> assertThat(reason).isNull())
    ;
  }
}
