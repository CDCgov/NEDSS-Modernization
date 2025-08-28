package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.audit.Changed;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.demographic.name.SoundexResolver;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class PersonNameTest {

  @Test
  void should_inactivate_existing_name() {

    Person patient = new Person(117L, "local-id-value");
    SoundexResolver resolver = mock(SoundexResolver.class);

    PersonNameId identifier = new PersonNameId(117L, (short) 2);

    PersonName name = new PersonName(
        identifier,
        patient,
        resolver,
        new PatientCommand.AddName(
            117L,
            LocalDate.parse("2021-05-15"),
            "Other-First",
            "Other-Middle",
            "Other-Last",
            null,
            "L",
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    name.delete(
        new PatientCommand.DeleteNameInfo(
            117L,
            (short) 2,
            171L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    assertThat(name)
        .satisfies(
            removed -> assertThat(removed.audit())
                .describedAs("expected name audit state")
                .satisfies(
                    audit -> assertThat(audit.changed())
                        .returns(171L, Changed::changedBy)
                        .returns(LocalDateTime.parse("2020-03-03T10:15:30"), Changed::changedOn)
                )
        )
        .satisfies(
            removed -> assertThat(removed)
                .describedAs("expected name is inactive")
                .returns("INACTIVE", s -> s.recordStatus().status())
                .returns(LocalDateTime.parse("2020-03-03T10:15:30"), s -> s.recordStatus().appliedOn())
        )
        .extracting(PersonName::identifier)
        .returns(117L, PersonNameId::getPersonUid)
        .returns((short) 2, PersonNameId::getPersonNameSeq)
    ;
  }

  @Test
  void should_create_name_with_first_name_and_soundex() {
    Person patient = new Person(117L, "local-id-value");
    SoundexResolver resolver = value -> value + "_encoded";

    PersonNameId identifier = new PersonNameId(117L, (short) 2);

    PersonName actual = new PersonName(
        identifier,
        patient,
        resolver,
        new PatientCommand.AddName(
            117L,
            LocalDate.parse("2021-05-15"),
            "First",
            null,
            null,
            null,
            "L",
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    assertThat(actual)
        .returns("First", PersonName::first)
        .returns("First_encoded", PersonName::firstSoundex);
  }

  @Test
  void should_create_name_with_last_name_and_soundex() {
    Person patient = new Person(117L, "local-id-value");
    SoundexResolver resolver = value -> value + "_encoded";

    PersonNameId identifier = new PersonNameId(117L, (short) 2);

    PersonName actual = new PersonName(
        identifier,
        patient,
        resolver,
        new PatientCommand.AddName(
            117L,
            LocalDate.parse("2021-05-15"),
            null,
            null,
            "Last",
            null,
            "L",
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    assertThat(actual)
        .returns("Last", PersonName::last)
        .returns("Last_encoded", PersonName::lastSoundex);
  }

  @Test
  void should_create_name_with_second_last_name_and_soundex() {
    Person patient = new Person(117L, "local-id-value");
    SoundexResolver resolver = value -> value + "_encoded";

    PersonNameId identifier = new PersonNameId(117L, (short) 2);

    PersonName actual = new PersonName(
        identifier,
        patient,
        resolver,
        new PatientCommand.AddName(
            117L,
            LocalDate.parse("2023-05-15"),
            null,
            null,
            null,
            null,
            null,
            "Second-Last",
            null,
            null,
            "L",
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    assertThat(actual)
        .returns("Second-Last", PersonName::secondLast)
        .returns("Second-Last_encoded", PersonName::secondLastSoundex);
  }

  @Test
  void should_update_name_with_first_name_and_soundex() {
    Person patient = new Person(117L, "local-id-value");
    SoundexResolver resolver = value -> value + "_encoded";

    PersonNameId identifier = new PersonNameId(117L, (short) 2);

    PersonName actual = new PersonName(
        identifier,
        patient,
        resolver,
        new PatientCommand.AddName(
            117L,
            LocalDate.parse("2021-05-15"),
            null,
            null,
            null,
            null,
            "L",
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    ).update(
        resolver,
        new PatientCommand.UpdateNameInfo(
            117L,
            1,
            LocalDate.parse("2021-05-15"),
            null,
            "update_first_name",
            null,
            null,
            null,
            null,
            null,
            null,
            "L",
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    assertThat(actual)
        .returns("update_first_name", PersonName::first)
        .returns("update_first_name_encoded", PersonName::firstSoundex);
  }

  @Test
  void should_update_name_with_last_name_and_soundex() {
    Person patient = new Person(117L, "local-id-value");
    SoundexResolver resolver = value -> value + "_encoded";

    PersonNameId identifier = new PersonNameId(117L, (short) 2);

    PersonName actual = new PersonName(
        identifier,
        patient,
        resolver,
        new PatientCommand.AddName(
            117L,
            LocalDate.parse("2021-05-15"),
            null,
            null,
            null,
            null,
            "L",
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    ).update(
        resolver,
        new PatientCommand.UpdateNameInfo(
            117L,
            1,
            LocalDate.parse("2021-05-15"),
            null,
            null,
            null,
            null,
            "update_last_name",
            null,
            null,
            null,
            "L",
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    assertThat(actual)
        .returns("update_last_name", PersonName::last)
        .returns("update_last_name_encoded", PersonName::lastSoundex);
  }

  @Test
  void should_update_name_with_second_last_name_and_soundex() {
    Person patient = new Person(117L, "local-id-value");
    SoundexResolver resolver = value -> value + "_encoded";

    PersonNameId identifier = new PersonNameId(117L, (short) 2);

    PersonName actual = new PersonName(
        identifier,
        patient,
        resolver,
        new PatientCommand.AddName(
            117L,
            LocalDate.parse("2021-05-15"),
            null,
            null,
            null,
            null,
            "L",
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    ).update(
        resolver,
        new PatientCommand.UpdateNameInfo(
            117L,
            1,
            LocalDate.parse("2021-05-15"),
            null,
            null,
            null,
            null,
            null,
            "update_second_last_name",
            null,
            null,
            "L",
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    assertThat(actual)
        .returns("update_second_last_name", PersonName::secondLast)
        .returns("update_second_last_name_encoded", PersonName::secondLastSoundex);
  }
}
