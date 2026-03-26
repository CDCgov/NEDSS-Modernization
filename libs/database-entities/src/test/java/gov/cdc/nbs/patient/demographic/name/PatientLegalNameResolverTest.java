package gov.cdc.nbs.patient.demographic.name;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PersonName;
import gov.cdc.nbs.entity.odse.PersonNameId;
import gov.cdc.nbs.patient.PatientCommand;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class PatientLegalNameResolverTest {

  @Test
  void should_resolve_empty_when_no_names() {

    Optional<PersonName> actual = PatientLegalNameResolver.resolve(List.of(), LocalDate.now());

    assertThat(actual).isNotPresent();
  }

  @Test
  void should_resolve_empty_when_no_legal_name() {

    Person person = mock(Person.class);
    SoundexResolver encoder = mock(SoundexResolver.class);

    Optional<PersonName> actual =
        PatientLegalNameResolver.resolve(
            List.of(
                new PersonName(
                    new PersonNameId(457L, (short) 2),
                    person,
                    encoder,
                    new PatientCommand.AddName(
                        457L,
                        LocalDate.parse("1933-11-09"),
                        null,
                        null,
                        null,
                        null,
                        "AD",
                        743L,
                        LocalDateTime.MIN)),
                new PersonName(
                    new PersonNameId(457L, (short) 3),
                    person,
                    encoder,
                    new PatientCommand.AddName(
                        457L,
                        LocalDate.parse("2003-06-04"),
                        null,
                        null,
                        null,
                        null,
                        "MO",
                        743L,
                        LocalDateTime.MIN))),
            LocalDate.now());

    assertThat(actual).isNotPresent();
  }

  @Test
  void should_resolve_most_recent_legal_name() {

    Person person = mock(Person.class);
    SoundexResolver encoder = mock(SoundexResolver.class);

    Optional<PersonName> actual =
        PatientLegalNameResolver.resolve(
            List.of(
                new PersonName(
                    new PersonNameId(457L, (short) 2),
                    person,
                    encoder,
                    new PatientCommand.AddName(
                        457L,
                        LocalDate.parse("1933-11-09"),
                        null,
                        null,
                        null,
                        null,
                        "L",
                        743L,
                        LocalDateTime.MIN)),
                new PersonName(
                    new PersonNameId(457L, (short) 3),
                    person,
                    encoder,
                    new PatientCommand.AddName(
                        457L,
                        LocalDate.parse("2003-06-04"),
                        null,
                        null,
                        null,
                        null,
                        "L",
                        743L,
                        LocalDateTime.MIN)),
                new PersonName(
                    new PersonNameId(457L, (short) 5),
                    person,
                    encoder,
                    new PatientCommand.AddName(
                        457L,
                        LocalDate.parse("2021-07-29"),
                        null,
                        null,
                        null,
                        null,
                        "L",
                        743L,
                        LocalDateTime.MIN)),
                new PersonName(
                    new PersonNameId(457L, (short) 7),
                    person,
                    encoder,
                    new PatientCommand.AddName(
                        457L,
                        LocalDate.parse("2024-03-19"),
                        null,
                        null,
                        null,
                        null,
                        "L",
                        743L,
                        LocalDateTime.MIN))),
            LocalDate.of(2023, Month.DECEMBER, 15));

    assertThat(actual)
        .hasValueSatisfying(
            name -> assertThat(name.identifier().getPersonNameSeq()).isEqualTo((short) 5));
  }

  @Test
  void should_resolve_most_effective_legal_name_including_that_day() {

    Person person = mock(Person.class);
    SoundexResolver encoder = mock(SoundexResolver.class);

    Optional<PersonName> actual =
        PatientLegalNameResolver.resolve(
            List.of(
                new PersonName(
                    new PersonNameId(457L, (short) 2),
                    person,
                    encoder,
                    new PatientCommand.AddName(
                        457L,
                        LocalDate.parse("1933-11-09"),
                        null,
                        null,
                        null,
                        null,
                        "L",
                        743L,
                        LocalDateTime.MIN)),
                new PersonName(
                    new PersonNameId(457L, (short) 3),
                    person,
                    encoder,
                    new PatientCommand.AddName(
                        457L,
                        LocalDate.parse("2003-06-04"),
                        null,
                        null,
                        null,
                        null,
                        "L",
                        743L,
                        LocalDateTime.MIN)),
                new PersonName(
                    new PersonNameId(457L, (short) 5),
                    person,
                    encoder,
                    new PatientCommand.AddName(
                        457L,
                        LocalDate.parse("2021-07-29"),
                        null,
                        null,
                        null,
                        null,
                        "L",
                        743L,
                        LocalDateTime.MIN)),
                new PersonName(
                    new PersonNameId(457L, (short) 7),
                    person,
                    encoder,
                    new PatientCommand.AddName(
                        457L,
                        LocalDate.parse("2024-03-19"),
                        null,
                        null,
                        null,
                        null,
                        "L",
                        743L,
                        LocalDateTime.MIN))),
            LocalDate.of(2003, Month.JUNE, 4));

    assertThat(actual)
        .hasValueSatisfying(
            name -> assertThat(name.identifier().getPersonNameSeq()).isEqualTo((short) 3));
  }

  @Test
  void should_resolve_most_recent_legal_name_using_sequence_when_as_of_matches() {

    Person person = mock(Person.class);
    SoundexResolver encoder = mock(SoundexResolver.class);

    Optional<PersonName> actual =
        PatientLegalNameResolver.resolve(
            List.of(
                new PersonName(
                    new PersonNameId(457L, (short) 2),
                    person,
                    encoder,
                    new PatientCommand.AddName(
                        457L,
                        LocalDate.parse("1933-11-09"),
                        null,
                        null,
                        null,
                        null,
                        "L",
                        743L,
                        LocalDateTime.MIN)),
                new PersonName(
                    new PersonNameId(457L, (short) 3),
                    person,
                    encoder,
                    new PatientCommand.AddName(
                        457L,
                        LocalDate.parse("2021-07-29"),
                        null,
                        null,
                        null,
                        null,
                        "L",
                        743L,
                        LocalDateTime.MIN)),
                new PersonName(
                    new PersonNameId(457L, (short) 5),
                    person,
                    encoder,
                    new PatientCommand.AddName(
                        457L,
                        LocalDate.parse("2021-07-29"),
                        null,
                        null,
                        null,
                        null,
                        "L",
                        743L,
                        LocalDateTime.MIN)),
                new PersonName(
                    new PersonNameId(457L, (short) 7),
                    person,
                    encoder,
                    new PatientCommand.AddName(
                        457L,
                        LocalDate.parse("2017-03-19"),
                        null,
                        null,
                        null,
                        null,
                        "L",
                        743L,
                        LocalDateTime.MIN))),
            LocalDate.of(2023, Month.DECEMBER, 15));

    assertThat(actual)
        .hasValueSatisfying(
            name -> assertThat(name.identifier().getPersonNameSeq()).isEqualTo((short) 5));
  }
}
