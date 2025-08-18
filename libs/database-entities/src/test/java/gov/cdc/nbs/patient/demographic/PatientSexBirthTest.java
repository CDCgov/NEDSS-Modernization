package gov.cdc.nbs.patient.demographic;

import gov.cdc.nbs.message.enums.Gender;
import gov.cdc.nbs.message.enums.Indicator;
import gov.cdc.nbs.patient.PatientCommand;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class PatientSexBirthTest {

  @Test
  void should_noop_when_clearing_patient_birth_when_no_birth() {

    PatientSexBirth demographic = new PatientSexBirth();

    demographic.clear(
        new PatientCommand.ClearBirthDemographics(
            121L,
            131L,
            LocalDateTime.parse("2023-03-07T11:19:23")
        )
    );

    assertThat(demographic)
        .returns(null, PatientSexBirth::asOf);
  }

  @Test
  void should_not_clear_as_of_if_gender_still_present() {
    PatientSexBirth demographic = new PatientSexBirth();

    demographic.update(
        new PatientCommand.UpdateGender(
            121L,
            LocalDate.parse("2023-06-01"),
            Gender.F.value(),
            null,
            null,
            null,
            131L,
            LocalDateTime.parse("2019-03-03T10:15:30")
        )
    );

    demographic.clear(
        new PatientCommand.ClearBirthDemographics(
            121L,
            131L,
            LocalDateTime.parse("2023-03-07T11:19:23")
        )
    );

    assertThat(demographic)
        .returns(LocalDate.parse("2023-06-01"), PatientSexBirth::asOf);
  }

  @Test
  void should_not_clear_as_of_if_preferred_gender_still_present() {
    PatientSexBirth demographic = new PatientSexBirth();

    demographic.update(
        new PatientCommand.UpdateGender(
            121L,
            LocalDate.parse("2023-06-01"),
            null,
            null,
            "gender-preferred",
            null,
            131L,
            LocalDateTime.parse("2019-03-03T10:15:30")
        )
    );

    demographic.clear(
        new PatientCommand.ClearBirthDemographics(
            121L,
            131L,
            LocalDateTime.parse("2023-03-07T11:19:23")
        )
    );

    assertThat(demographic)
        .returns(LocalDate.parse("2023-06-01"), PatientSexBirth::asOf);
  }

  @Test
  void should_not_clear_as_of_if_additional_gender_still_present() {
    PatientSexBirth demographic = new PatientSexBirth();

    demographic.update(
        new PatientCommand.UpdateGender(
            121L,
            LocalDate.parse("2023-06-01"),
            null,
            null,
            null,
            "gender-additional",
            131L,
            LocalDateTime.parse("2019-03-03T10:15:30")
        )
    );

    demographic.clear(
        new PatientCommand.ClearBirthDemographics(
            121L,
            131L,
            LocalDateTime.parse("2023-03-07T11:19:23")
        )
    );

    assertThat(demographic)
        .returns(LocalDate.parse("2023-06-01"), PatientSexBirth::asOf);
  }

  @Test
  void should_noop_when_clearing_patient_gender_when_no_gender() {

    PatientSexBirth demographic = new PatientSexBirth();

    demographic.clear(
        new PatientCommand.ClearGenderDemographics(
            121L,
            131L,
            LocalDateTime.parse("2023-03-07T11:19:23")
        )
    );

    assertThat(demographic)
        .returns(null, PatientSexBirth::asOf);
  }

  @Test
  void should_not_clear_as_of_when_birthday_is_present() {
    PatientSexBirth demographic = new PatientSexBirth();

    demographic.update(
        new PatientCommand.UpdateBirth(
            121L,
            LocalDate.parse("2023-06-01"),
            LocalDate.of(1949, 10, 15),
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            131L,
            LocalDateTime.parse("2019-03-03T10:15:30")
        )
    );

    demographic.clear(
        new PatientCommand.ClearGenderDemographics(
            121L,
            131L,
            LocalDateTime.parse("2023-03-07T11:19:23")
        )
    );

    assertThat(demographic)
        .returns(LocalDate.parse("2023-06-01"), PatientSexBirth::asOf);
  }

  @Test
  void should_not_clear_as_of_when_birth_gender_is_present() {
    PatientSexBirth demographic = new PatientSexBirth();

    demographic.update(
        new PatientCommand.UpdateBirth(
            121L,
            LocalDate.parse("2023-06-01"),
            null,
            Gender.U.value(),
            null,
            null,
            null,
            null,
            null,
            null,
            131L,
            LocalDateTime.parse("2019-03-03T10:15:30")
        )
    );

    demographic.clear(
        new PatientCommand.ClearGenderDemographics(
            121L,
            131L,
            LocalDateTime.parse("2023-03-07T11:19:23")
        )
    );

    assertThat(demographic)
        .returns(LocalDate.parse("2023-06-01"), PatientSexBirth::asOf);
  }

  @Test
  void should_not_clear_as_of_when_multiple_birth_is_present() {
    PatientSexBirth demographic = new PatientSexBirth();

    demographic.update(
        new PatientCommand.UpdateBirth(
            121L,
            LocalDate.parse("2023-06-01"),
            null,
            null,
            Indicator.NO.getId(),
            null,
            null,
            null,
            null,
            null,
            131L,
            LocalDateTime.parse("2019-03-03T10:15:30")
        )
    );

    demographic.clear(
        new PatientCommand.ClearGenderDemographics(
            121L,
            131L,
            LocalDateTime.parse("2023-03-07T11:19:23")
        )
    );

    assertThat(demographic)
        .returns(LocalDate.parse("2023-06-01"), PatientSexBirth::asOf);
  }

  @Test
  void should_not_clear_as_of_when_birth_order_is_present() {
    PatientSexBirth demographic = new PatientSexBirth();

    demographic.update(
        new PatientCommand.UpdateBirth(
            121L,
            LocalDate.parse("2023-06-01"),
            null,
           null,
            null,
            10,
            null,
            null,
            null,
            null,
            131L,
            LocalDateTime.parse("2019-03-03T10:15:30")
        )
    );

    demographic.clear(
        new PatientCommand.ClearGenderDemographics(
            121L,
            131L,
            LocalDateTime.parse("2023-03-07T11:19:23")
        )
    );

    assertThat(demographic)
        .returns(LocalDate.parse("2023-06-01"), PatientSexBirth::asOf);
  }
}
