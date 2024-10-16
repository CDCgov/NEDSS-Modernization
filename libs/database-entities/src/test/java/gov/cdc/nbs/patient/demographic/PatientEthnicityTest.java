package gov.cdc.nbs.patient.demographic;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PersonEthnicGroup;
import gov.cdc.nbs.entity.odse.PersonEthnicGroupId;
import gov.cdc.nbs.patient.PatientCommand;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class PatientEthnicityTest {

  @Test
  void should_change_ethnicity() {

    Person patient = new Person(121L, "local-id-value");

    patient.update(
        new PatientCommand.UpdateEthnicityInfo(
            121L,
            Instant.parse("2012-03-03T10:15:30.00Z"),
            "ethnic-group-value",
            "unknown-reason-value",
            131L,
            Instant.parse("2020-03-03T10:15:30.00Z")
        )
    );

    assertThat(patient)
        .returns(131L, Person::getLastChgUserId)
        .returns(Instant.parse("2020-03-03T10:15:30.00Z"), Person::getLastChgTime)
        .extracting(Person::getEthnicity)
        .returns(Instant.parse("2012-03-03T10:15:30.00Z"), PatientEthnicity::asOf)
        .returns("ethnic-group-value", PatientEthnicity::ethnicGroup)
        .returns("unknown-reason-value", PatientEthnicity::unknownReason);
  }

  @Test
  void should_add_detailed_ethnicity() {

    Person patient = new Person(121L, "local-id-value")
        .update(
            new PatientCommand.UpdateEthnicityInfo(
                121L,
                Instant.parse("2012-03-03T10:15:30.00Z"),
                "ethnic-group-value",
                null,
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        );

    patient.add(
        new PatientCommand.AddDetailedEthnicity(
            121L,
            "ethnicity-value",
            131L,
            Instant.parse("2020-03-03T10:15:30.00Z")
        )
    );

    assertThat(patient.getEthnicity().ethnicities())
        .satisfiesExactlyInAnyOrder(
            detail -> assertThat(detail)
                .returns("ACTIVE", PersonEthnicGroup::getRecordStatusCd)
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
            Instant.parse("2020-03-03T10:15:30.00Z")
        )
    );

    assertThat(patient.getEthnicity().ethnicities()).isEmpty();

  }


  @Test
  void should_add_another_detailed_ethnicity() {

    Person patient = new Person(121L, "local-id-value")
        .update(
            new PatientCommand.UpdateEthnicityInfo(
                121L,
                Instant.parse("2012-03-03T10:15:30.00Z"),
                "ethnic-group-value",
                null,
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        )
        .add(
            new PatientCommand.AddDetailedEthnicity(
                121L,
                "ethnicity-value",
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        );

    patient.add(
        new PatientCommand.AddDetailedEthnicity(
            121L,
            "next-ethnicity-value",
            131L,
            Instant.parse("2020-03-03T10:15:30.00Z")
        )
    );

    assertThat(patient.getEthnicity().ethnicities())
        .satisfiesExactlyInAnyOrder(
            actual -> assertThat(actual)
                .returns("ACTIVE", PersonEthnicGroup::getRecordStatusCd)
                .extracting(PersonEthnicGroup::getId)
                .returns(121L, PersonEthnicGroupId::getPersonUid)
                .returns("ethnicity-value", PersonEthnicGroupId::getEthnicGroupCd),
            actual -> assertThat(actual)
                .returns("ACTIVE", PersonEthnicGroup::getRecordStatusCd)
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
                Instant.parse("2012-03-03T10:15:30.00Z"),
                "ethnic-group-value",
                null,
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        ).add(
            new PatientCommand.AddDetailedEthnicity(
                121L,
                "ethnicity-value",
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        ).add(
            new PatientCommand.AddDetailedEthnicity(
                121L,
                "next-ethnicity-value",
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        );

    patient.remove(
        new PatientCommand.RemoveDetailedEthnicity(
            121L,
            "next-ethnicity-value",
            131L,
            Instant.parse("2020-03-03T10:15:30.00Z")
        )
    );

    assertThat(patient.getEthnicity().ethnicities())
        .satisfiesExactly(
            actual -> assertThat(actual)
                .returns("ACTIVE", PersonEthnicGroup::getRecordStatusCd)
                .extracting(PersonEthnicGroup::getId)
                .returns(121L, PersonEthnicGroupId::getPersonUid)
                .returns("ethnicity-value", PersonEthnicGroupId::getEthnicGroupCd)
        );

  }


}
