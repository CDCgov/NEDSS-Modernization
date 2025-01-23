package gov.cdc.nbs.patient.profile;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.message.enums.Gender;
import gov.cdc.nbs.message.enums.Indicator;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.demographic.AddressIdentifierGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class PatientChangeHashTest {


  @Test
  void should_not_compute_different_hash_when_administrative_does_not_change() {

    Person patient = new Person(967L, "local-id-value");

    patient.update(
        new PatientCommand.UpdateAdministrativeInfo(
            967L,
            Instant.parse("2023-06-01T03:21:00Z"),
            "comments",
            131L,
            LocalDateTime.parse("2019-03-03T10:15:30")
        )
    );

    long before = PatientChangeHash.compute(patient);

    patient.update(
        new PatientCommand.UpdateAdministrativeInfo(
            967L,
            Instant.parse("2023-06-01T03:21:00Z"),
            "comments",
            131L,
            LocalDateTime.parse("2019-03-03T10:15:30")
        )
    );

    long after = PatientChangeHash.compute(patient);

    assertThat(before).isEqualTo(after);
  }

  static Stream<Arguments> administrativeUpdates() {
    return Stream.of(
        arguments(
            new PatientCommand.UpdateAdministrativeInfo(
                967L,
                Instant.now(),
                "comments",
                131L,
                LocalDateTime.now()
            )
        ),
        arguments(
            new PatientCommand.UpdateAdministrativeInfo(
                967L,
                Instant.parse("2023-06-01T03:21:00Z"),
                "changed comments",
                131L,
                LocalDateTime.now()
            )
        )
    );
  }

  @ParameterizedTest
  @MethodSource("administrativeUpdates")
  void should_compute_different_hash_when_administrative_changes(
      final PatientCommand.UpdateAdministrativeInfo changes
  ) {



    Person patient = new Person(967L, "local-id-value");

    patient.update(
        new PatientCommand.UpdateAdministrativeInfo(
            967L,
            Instant.parse("2023-06-01T03:21:00Z"),
            "comments",
            131L,
            LocalDateTime.now()
        )
    );

    long before = PatientChangeHash.compute(patient);

    patient.update(changes);

    long after = PatientChangeHash.compute(patient);

    assertThat(before).isNotEqualTo(after);
  }

  @Test
  void should_not_compute_different_hash_when_general_information_does_not_change() {



    Person patient = new Person(967L, "local-id-value");

    patient.update(
        new PatientCommand.UpdateGeneralInfo(
            967L,
            Instant.parse("2010-03-03T10:15:30.00Z"),
            "marital status",
            "mothers maiden name",
            1,
            2,
            "occupation code",
            "education level",
            "prim language",
            "speaks english",
            12L,
            LocalDateTime.parse("2019-03-03T10:15:30"))
    );

    long before = PatientChangeHash.compute(patient);

    patient.update(
        new PatientCommand.UpdateGeneralInfo(
            967L,
            Instant.parse("2010-03-03T10:15:30.00Z"),
            "marital status",
            "mothers maiden name",
            1,
            2,
            "occupation code",
            "education level",
            "prim language",
            "speaks english",
            12L,
            LocalDateTime.parse("2019-03-03T10:15:30"))
    );

    long after = PatientChangeHash.compute(patient);

    assertThat(before).isEqualTo(after);
  }

  static Stream<Arguments> generalInformationUpdates() {
    return Stream.of(
        arguments(
            new PatientCommand.UpdateGeneralInfo(
                967L,
                Instant.now(),
                "marital status",
                "mothers maiden name",
                1,
                2,
                "occupation code",
                "education level",
                "prim language",
                "speaks english",
                12L,
                LocalDateTime.now()
            )
        ),
        arguments(
            new PatientCommand.UpdateGeneralInfo(
                967L,
                Instant.parse("2010-03-03T10:15:30.00Z"),
                "changed marital status",
                "mothers maiden name",
                1,
                2,
                "occupation code",
                "education level",
                "prim language",
                "speaks english",
                12L,
                LocalDateTime.now()
            )
        ),
        arguments(
            new PatientCommand.UpdateGeneralInfo(
                967L,
                Instant.parse("2010-03-03T10:15:30.00Z"),
                "marital status",
                "changed mothers maiden name",
                1,
                2,
                "occupation code",
                "education level",
                "prim language",
                "speaks english",
                12L,
                LocalDateTime.now()
            )
        ),
        arguments(
            new PatientCommand.UpdateGeneralInfo(
                967L,
                Instant.parse("2010-03-03T10:15:30.00Z"),
                "marital status",
                "mothers maiden name",
                113,
                2,
                "occupation code",
                "education level",
                "prim language",
                "speaks english",
                12L,
                LocalDateTime.now()
            )
        ),
        arguments(
            new PatientCommand.UpdateGeneralInfo(
                967L,
                Instant.parse("2010-03-03T10:15:30.00Z"),
                "marital status",
                "mothers maiden name",
                1,
                257,
                "occupation code",
                "education level",
                "prim language",
                "speaks english",
                12L,
                LocalDateTime.now()
            )
        ),
        arguments(
            new PatientCommand.UpdateGeneralInfo(
                967L,
                Instant.parse("2010-03-03T10:15:30.00Z"),
                "marital status",
                "mothers maiden name",
                1,
                2,
                "changed occupation code",
                "education level",
                "prim language",
                "speaks english",
                12L,
                LocalDateTime.now()
            )
        ),
        arguments(
            new PatientCommand.UpdateGeneralInfo(
                967L,
                Instant.parse("2010-03-03T10:15:30.00Z"),
                "marital status",
                "mothers maiden name",
                1,
                2,
                "occupation code",
                "changed education level",
                "prim language",
                "speaks english",
                12L,
                LocalDateTime.now()
            )
        ),
        arguments(
            new PatientCommand.UpdateGeneralInfo(
                967L,
                Instant.parse("2010-03-03T10:15:30.00Z"),
                "marital status",
                "mothers maiden name",
                1,
                2,
                "occupation code",
                "education level",
                "changed prim language",
                "speaks english",
                12L,
                LocalDateTime.now()
            )
        ),
        arguments(
            new PatientCommand.UpdateGeneralInfo(
                967L,
                Instant.parse("2010-03-03T10:15:30.00Z"),
                "marital status",
                "mothers maiden name",
                1,
                2,
                "occupation code",
                "education level",
                "prim language",
                "changed speaks english",
                12L,
                LocalDateTime.now()
            )
        )
    );
  }

  @ParameterizedTest
  @MethodSource("generalInformationUpdates")
  void should_compute_different_hash_when_general_information_changes(final PatientCommand.UpdateGeneralInfo changes) {



    Person patient = new Person(967L, "local-id-value");

    patient.update(
        new PatientCommand.UpdateGeneralInfo(
            967L,
            Instant.parse("2010-03-03T10:15:30.00Z"),
            "marital status",
            "mothers maiden name",
            1,
            2,
            "occupation code",
            "education level",
            "prim language",
            "speaks english",
            12L,
            LocalDateTime.now()
        )

    );

    long before = PatientChangeHash.compute(patient);

    patient.update(changes);

    long after = PatientChangeHash.compute(patient);

    assertThat(before).isNotEqualTo(after);
  }

  @Test
  void should_not_compute_different_hash_when_mortality_does_not_change() {



    AddressIdentifierGenerator generator = () -> 1157L;

    Person patient = new Person(967L, "local-id-value");

    patient.update(
        new PatientCommand.UpdateMortality(
            1157,
            Instant.parse("2023-06-01T03:21:00Z"),
            "Y",
            LocalDate.of(1987, Month.NOVEMBER, 17),
            null,
            null,
            null,
            null,
            131L,
            LocalDateTime.now()
        ),
        generator
    );

    long before = PatientChangeHash.compute(patient);

    patient.update(
        new PatientCommand.UpdateMortality(
            1157,
            Instant.parse("2023-06-01T03:21:00Z"),
            "Y",
            LocalDate.of(1987, Month.NOVEMBER, 17),
            null,
            null,
            null,
            null,
            131L,
            LocalDateTime.now()
        ),
        generator
    );

    long after = PatientChangeHash.compute(patient);

    assertThat(before).isEqualTo(after);
  }

  static Stream<Arguments> mortalityUpdates() {
    return Stream.of(
        arguments(
            new PatientCommand.UpdateMortality(
                1157,
                Instant.now(),
                "Y",
                LocalDate.of(1987, Month.NOVEMBER, 17),
                null,
                null,
                null,
                null,
                131L,
                LocalDateTime.now()
            )
        ),
        arguments(
            new PatientCommand.UpdateMortality(
                1157,
                Instant.parse("2023-06-01T03:21:00Z"),
                "N",
                LocalDate.of(1987, Month.NOVEMBER, 17),
                null,
                null,
                null,
                null,
                131L,
                LocalDateTime.now()
            )
        ),
        arguments(
            new PatientCommand.UpdateMortality(
                1157,
                Instant.parse("2023-06-01T03:21:00Z"),
                "Y",
                LocalDate.now(),
                null,
                null,
                null,
                null,
                131L,
                LocalDateTime.now()
            )
        )
    );
  }

  @ParameterizedTest
  @MethodSource("mortalityUpdates")
  void should_compute_different_hash_when_mortality_changes(final PatientCommand.UpdateMortality changes) {



    AddressIdentifierGenerator generator = () -> 1157L;

    Person patient = new Person(967L, "local-id-value");

    patient.update(
        new PatientCommand.UpdateMortality(
            1157,
            Instant.parse("2023-06-01T03:21:00Z"),
            "Y",
            LocalDate.of(1987, Month.NOVEMBER, 17),
            null,
            null,
            null,
            null,
            131L,
            LocalDateTime.now()
        ),
        generator
    );

    long before = PatientChangeHash.compute(patient);

    patient.update(changes, generator);

    long after = PatientChangeHash.compute(patient);

    assertThat(before).isNotEqualTo(after);
  }

  @Test
  void should_not_compute_different_hash_when_ethnicity_does_not_change() {



    Person patient = new Person(967L, "local-id-value");

    patient.update(
        new PatientCommand.UpdateEthnicityInfo(
            121L,
            Instant.parse("2012-03-03T10:15:30.00Z"),
            "ethnic-group-value",
            "unknown-reason-value",
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    long before = PatientChangeHash.compute(patient);

    patient.update(
        new PatientCommand.UpdateEthnicityInfo(
            967L,
            Instant.parse("2012-03-03T10:15:30.00Z"),
            "ethnic-group-value",
            "unknown-reason-value",
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    long after = PatientChangeHash.compute(patient);

    assertThat(before).isEqualTo(after);
  }

  static Stream<Arguments> ethnicityUpdates() {
    return Stream.of(
        arguments(
            new PatientCommand.UpdateEthnicityInfo(
                121L,
                Instant.now(),
                "ethnic-group-value",
                "unknown-reason-value",
                131L,
                LocalDateTime.now()
            )
        ),
        arguments(
            new PatientCommand.UpdateEthnicityInfo(
                121L,
                Instant.parse("2012-03-03T10:15:30.00Z"),
                "ethnic-group-value",
                "changed unknown-reason-value",
                131L,
                LocalDateTime.now()
            )
        )
    );
  }

  @ParameterizedTest
  @MethodSource("ethnicityUpdates")
  void should_compute_different_hash_when_ethnicity_changes(final PatientCommand.UpdateEthnicityInfo changes) {



    Person patient = new Person(967L, "local-id-value");

    patient.update(
        new PatientCommand.UpdateEthnicityInfo(
            121L,
            Instant.parse("2012-03-03T10:15:30.00Z"),
            "ethnic-group-value",
            "unknown-reason-value",
            131L,
            LocalDateTime.now()
        )
    );

    long before = PatientChangeHash.compute(patient);

    patient.update(changes);

    long after = PatientChangeHash.compute(patient);

    assertThat(before).isNotEqualTo(after);
  }

  @Test
  void should_not_compute_different_hash_when_gender_does_not_change() {



    Person patient = new Person(967L, "local-id-value");

    patient.update(
        new PatientCommand.UpdateGender(
            967L,
            Instant.parse("2023-06-01T03:21:00Z"),
            Gender.U.value(),
            "gender-unknown-reason",
            "gender-preferred",
            "gender-additional",
            131L,
            LocalDateTime.now()
        )
    );

    long before = PatientChangeHash.compute(patient);

    patient.update(
        new PatientCommand.UpdateGender(
            967L,
            Instant.parse("2023-06-01T03:21:00Z"),
            Gender.U.value(),
            "gender-unknown-reason",
            "gender-preferred",
            "gender-additional",
            131L,
            LocalDateTime.now()
        )
    );

    long after = PatientChangeHash.compute(patient);

    assertThat(before).isEqualTo(after);
  }

  static Stream<Arguments> genderUpdates() {
    return Stream.of(
        arguments(
            new PatientCommand.UpdateGender(
                967L,
                Instant.now(),
                Gender.U.value(),
                "gender-unknown-reason",
                "gender-preferred",
                "gender-additional",
                131L,
                LocalDateTime.now()
            )
        ),
        arguments(
            new PatientCommand.UpdateGender(
                967L,
                Instant.parse("2023-06-01T03:21:00Z"),
                Gender.M.value(),
                "gender-unknown-reason",
                "gender-preferred",
                "gender-additional",
                131L,
                LocalDateTime.now()
            )
        ),
        arguments(
            new PatientCommand.UpdateGender(
                967L,
                Instant.parse("2023-06-01T03:21:00Z"),
                Gender.U.value(),
                "changed gender-unknown-reason",
                "gender-preferred",
                "gender-additional",
                131L,
                LocalDateTime.now()
            )
        ),
        arguments(
            new PatientCommand.UpdateGender(
                967L,
                Instant.parse("2023-06-01T03:21:00Z"),
                Gender.U.value(),
                "gender-unknown-reason",
                "changed gender-preferred",
                "gender-additional",
                131L,
                LocalDateTime.now()
            )
        ),
        arguments(
            new PatientCommand.UpdateGender(
                967L,
                Instant.parse("2023-06-01T03:21:00Z"),
                Gender.U.value(),
                "gender-unknown-reason",
                "gender-preferred",
                "changed gender-additional",
                131L,
                LocalDateTime.now()
            )
        )
    );
  }

  @ParameterizedTest
  @MethodSource("genderUpdates")
  void should_compute_different_hash_when_gender_changes(final PatientCommand.UpdateGender changes) {



    Person patient = new Person(967L, "local-id-value");

    patient.update(
        new PatientCommand.UpdateGender(
            967L,
            Instant.parse("2023-06-01T03:21:00Z"),
            Gender.U.value(),
            "gender-unknown-reason",
            "gender-preferred",
            "gender-additional",
            131L,
            LocalDateTime.now()
        )
    );

    long before = PatientChangeHash.compute(patient);

    patient.update(changes);

    long after = PatientChangeHash.compute(patient);

    assertThat(before).isNotEqualTo(after);
  }

  @Test
  void should_not_compute_different_hash_when_birth_does_not_change() {



    AddressIdentifierGenerator generator = () -> 1157L;

    Person patient = new Person(967L, "local-id-value");

    patient.update(
        new PatientCommand.UpdateBirth(
            967L,
            Instant.parse("2023-06-01T03:21:00Z"),
            LocalDate.of(1949, 10, 15),
            Gender.U.value(),
            Indicator.NO.getId(),
            17,
            null,
            null,
            null,
            null,
            131L,
            LocalDateTime.now()
        ),
        generator
    );

    long before = PatientChangeHash.compute(patient);

    patient.update(
        new PatientCommand.UpdateBirth(
            967L,
            Instant.parse("2023-06-01T03:21:00Z"),
            LocalDate.of(1949, 10, 15),
            Gender.U.value(),
            Indicator.NO.getId(),
            17,
            null,
            null,
            null,
            null,
            131L,
            LocalDateTime.now()
        ),
        generator
    );

    long after = PatientChangeHash.compute(patient);

    assertThat(before).isEqualTo(after);
  }

  static Stream<Arguments> birthUpdates() {
    return Stream.of(
        arguments(
            new PatientCommand.UpdateBirth(
                967L,
                Instant.now(),
                LocalDate.of(1949, 10, 15),
                Gender.U.value(),
                Indicator.NO.getId(),
                17,
                null,
                null,
                null,
                null,
                131L,
                LocalDateTime.now()
            )
        ),
        arguments(
            new PatientCommand.UpdateBirth(
                967L,
                Instant.parse("2023-06-01T03:21:00Z"),
                LocalDate.now(),
                Gender.U.value(),
                Indicator.NO.getId(),
                17,
                null,
                null,
                null,
                null,
                131L,
                LocalDateTime.now()
            )
        ),
        arguments(
            new PatientCommand.UpdateBirth(
                967L,
                Instant.parse("2023-06-01T03:21:00Z"),
                LocalDate.of(1949, 10, 15),
                Gender.F.value(),
                Indicator.NO.getId(),
                17,
                null,
                null,
                null,
                null,
                131L,
                LocalDateTime.now()
            )
        ),
        arguments(
            new PatientCommand.UpdateBirth(
                967L,
                Instant.parse("2023-06-01T03:21:00Z"),
                LocalDate.of(1949, 10, 15),
                Gender.U.value(),
                Indicator.YES.getId(),
                17,
                null,
                null,
                null,
                null,
                131L,
                LocalDateTime.now()
            )
        ),
        arguments(
            new PatientCommand.UpdateBirth(
                967L,
                Instant.parse("2023-06-01T03:21:00Z"),
                LocalDate.of(1949, 10, 15),
                Gender.U.value(),
                Indicator.NO.getId(),
                151,
                null,
                null,
                null,
                null,
                131L,
                LocalDateTime.now()
            )
        )
    );
  }

  @ParameterizedTest
  @MethodSource("birthUpdates")
  void should_compute_different_hash_when_birth_changes(final PatientCommand.UpdateBirth changes) {



    AddressIdentifierGenerator generator = () -> 1157L;

    Person patient = new Person(967L, "local-id-value");

    patient.update(
        new PatientCommand.UpdateBirth(
            967L,
            Instant.parse("2023-06-01T03:21:00Z"),
            LocalDate.of(1949, 10, 15),
            Gender.U.value(),
            Indicator.NO.getId(),
            17,
            null,
            null,
            null,
            null,
            131L,
            LocalDateTime.now()
        ),
        generator
    );

    long before = PatientChangeHash.compute(patient);

    patient.update(changes, generator);

    long after = PatientChangeHash.compute(patient);

    assertThat(before).isNotEqualTo(after);
  }
}
