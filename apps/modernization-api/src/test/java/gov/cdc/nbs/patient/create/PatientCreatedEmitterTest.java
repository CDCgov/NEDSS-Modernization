package gov.cdc.nbs.patient.create;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.message.enums.Deceased;
import gov.cdc.nbs.message.enums.Gender;
import gov.cdc.nbs.message.patient.event.PatientEvent;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.demographic.name.SoundexResolver;
import gov.cdc.nbs.patient.event.PatientEventEmitter;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class PatientCreatedEmitterTest {

  @Test
  void should_emit_person_as_created_event() {

    PatientEventEmitter emitter = mock(PatientEventEmitter.class);

    PatientCreatedEmitter createdEmitter = new PatientCreatedEmitter(emitter);

    Person patient = new Person(
        new PatientCommand.AddPatient(
            117L,
            "patient-local-id",
            LocalDate.parse("2000-09-03"),
            Gender.M,
            Gender.F,
            Deceased.Y,
            Instant.parse("2085-09-07T13:09:07Z"),
            "Marital Status",
            "EthCode",
            Instant.parse("2019-03-03T10:15:30Z"),
            "comments",
            "HIV-Case",
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );


    createdEmitter.created(patient);

    ArgumentCaptor<PatientEvent.Created> captor = ArgumentCaptor.forClass(PatientEvent.Created.class);

    verify(emitter).emit(captor.capture());

    PatientEvent.Created actual = captor.getValue();

    assertThat(actual)
        .returns(117L, PatientEvent.Created::patient)
        .returns("patient-local-id", PatientEvent.Created::localId)
        .returns(LocalDate.parse("2000-09-03"), PatientEvent.Created::dateOfBirth)
        .returns("M", PatientEvent.Created::birthGender)
        .returns("F", PatientEvent.Created::currentGender)
        .returns("Y", PatientEvent.Created::deceased)
        .returns(Instant.parse("2085-09-07T13:09:07Z"), PatientEvent.Created::deceasedOn)
        .returns("Marital Status", PatientEvent.Created::maritalStatus)
        .returns("EthCode", PatientEvent.Created::ethnicGroup)
        .returns(Instant.parse("2019-03-03T10:15:30Z"), PatientEvent.Created::asOf)
        .returns("comments", PatientEvent.Created::comments)
        .returns("HIV-Case", PatientEvent.Created::stateHIVCase)
        .returns(131L, PatientEvent.Created::createdBy)
        .returns(LocalDateTime.parse("2020-03-03T10:15:30"), PatientEvent.Created::createdOn)
    ;

    assertThat(actual.names()).isEmpty();
    assertThat(actual.races()).isEmpty();
    assertThat(actual.addresses()).isEmpty();
    assertThat(actual.phoneNumbers()).isEmpty();
    assertThat(actual.emails()).isEmpty();
    assertThat(actual.identifications()).isEmpty();
  }

  @Test
  void should_emit_person_as_created_event_with_names() {

    PatientEventEmitter emitter = mock(PatientEventEmitter.class);
    PatientCreatedEmitter createdEmitter = new PatientCreatedEmitter(emitter);
    SoundexResolver resolver = mock(SoundexResolver.class);

    Person patient = new Person(
        new PatientCommand.CreatePatient(
            117L,
            "patient-local-id",
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    patient.add(
        resolver,
        new PatientCommand.AddName(
            117L,
            LocalDate.parse("2021-05-15"),
            "First",
            "Middle",
            "Last",
            "JR",
            "L",
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    createdEmitter.created(patient);

    ArgumentCaptor<PatientEvent.Created> captor = ArgumentCaptor.forClass(PatientEvent.Created.class);

    verify(emitter).emit(captor.capture());

    PatientEvent.Created created = captor.getValue();

    assertThat(created.names())
        .satisfiesExactly(
            actual -> assertThat(actual)
                .returns("L", PatientEvent.Created.Name::use)
                .returns("First", PatientEvent.Created.Name::first)
                .returns("Middle", PatientEvent.Created.Name::middle)
                .returns("Last", PatientEvent.Created.Name::last)
                .returns("JR", PatientEvent.Created.Name::suffix)
        );
  }

  @Test
  void should_emit_person_as_created_event_with_races() {

    PatientEventEmitter emitter = mock(PatientEventEmitter.class);

    PatientCreatedEmitter createdEmitter = new PatientCreatedEmitter(emitter);

    Person patient = new Person(
        new PatientCommand.CreatePatient(
            117L,
            "patient-local-id",
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    patient.add(
        new PatientCommand.AddRace(
            117L,
            LocalDate.parse("2022-05-12"),
            "race-category-value",
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    createdEmitter.created(patient);

    ArgumentCaptor<PatientEvent.Created> captor = ArgumentCaptor.forClass(PatientEvent.Created.class);

    verify(emitter).emit(captor.capture());

    PatientEvent.Created created = captor.getValue();

    assertThat(created.races())
        .contains("race-category-value");
  }

  @Test
  void should_emit_person_as_created_event_with_addresses() {

    PatientEventEmitter emitter = mock(PatientEventEmitter.class);

    PatientCreatedEmitter createdEmitter = new PatientCreatedEmitter(emitter);

    Person patient = new Person(
        new PatientCommand.CreatePatient(
            117L,
            "patient-local-id",
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    patient.add(
        new PatientCommand.AddAddress(
            117L,
            4861L,
            Instant.parse("2021-07-07T03:06:09Z"),
            "SA1",
            "SA2",
            "city-description",
            "State",
            "Zip",
            "county-code",
            "country-code",
            "Census Tract",
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    createdEmitter.created(patient);

    ArgumentCaptor<PatientEvent.Created> captor = ArgumentCaptor.forClass(PatientEvent.Created.class);

    verify(emitter).emit(captor.capture());

    PatientEvent.Created created = captor.getValue();

    assertThat(created.addresses())
        .satisfiesExactly(
            actual -> assertThat(actual)
                .returns(4861L, PatientEvent.Created.Address::identifier)
                .returns(Instant.parse("2021-07-07T03:06:09Z"), PatientEvent.Created.Address::asOf)
                .returns("SA1", PatientEvent.Created.Address::streetAddress1)
                .returns("SA2", PatientEvent.Created.Address::streetAddress2)
                .returns("city-description", PatientEvent.Created.Address::city)
                .returns("State", PatientEvent.Created.Address::state)
                .returns("Zip", PatientEvent.Created.Address::zip)
                .returns("county-code", PatientEvent.Created.Address::county)
                .returns("country-code", PatientEvent.Created.Address::country)
                .returns("Census Tract", PatientEvent.Created.Address::censusTract)
        );
  }

  @Test
  void should_emit_person_as_created_event_with_phones() {

    PatientEventEmitter emitter = mock(PatientEventEmitter.class);

    PatientCreatedEmitter createdEmitter = new PatientCreatedEmitter(emitter);

    Person patient = new Person(
        new PatientCommand.CreatePatient(
            117L,
            "patient-local-id",
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    patient.add(
        new PatientCommand.AddPhoneNumber(
            117L,
            5347L,
            Instant.parse("2017-05-16T11:13:19Z"),
            "CP",
            "MC",
            "Phone Number",
            "Extension",
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    createdEmitter.created(patient);

    ArgumentCaptor<PatientEvent.Created> captor = ArgumentCaptor.forClass(PatientEvent.Created.class);

    verify(emitter).emit(captor.capture());

    PatientEvent.Created created = captor.getValue();

    assertThat(created.phoneNumbers())
        .satisfiesExactly(
            actual -> assertThat(actual)
                .returns(5347L, PatientEvent.Created.Phone::identifier)
                .returns(Instant.parse("2017-05-16T11:13:19Z"), PatientEvent.Created.Phone::asOf)
                .returns("CP", PatientEvent.Created.Phone::type)
                .returns("MC", PatientEvent.Created.Phone::use)
                .returns("Phone Number", PatientEvent.Created.Phone::number)
                .returns("Extension", PatientEvent.Created.Phone::extension)
        );
  }

  @Test
  void should_emit_person_as_created_event_with_email() {

    PatientEventEmitter emitter = mock(PatientEventEmitter.class);

    PatientCreatedEmitter createdEmitter = new PatientCreatedEmitter(emitter);

    Person patient = new Person(
        new PatientCommand.CreatePatient(
            117L,
            "patient-local-id",
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    patient.add(
        new PatientCommand.AddEmailAddress(
            117L,
            5333L,
            Instant.parse("2017-05-16T11:13:19Z"),
            "AnEmail@email.com",
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    createdEmitter.created(patient);

    ArgumentCaptor<PatientEvent.Created> captor = ArgumentCaptor.forClass(PatientEvent.Created.class);

    verify(emitter).emit(captor.capture());

    PatientEvent.Created created = captor.getValue();

    assertThat(created.emails())
        .satisfiesExactly(
            actual -> assertThat(actual)
                .returns(5333L, PatientEvent.Created.Email::identifier)
                .returns(Instant.parse("2017-05-16T11:13:19Z"), PatientEvent.Created.Email::asOf)
                .returns("NET", PatientEvent.Created.Email::type)
                .returns("H", PatientEvent.Created.Email::use)
                .returns("AnEmail@email.com", PatientEvent.Created.Email::address)
        );
  }

  @Test
  void should_emit_person_as_created_event_with_identifications() {

    PatientEventEmitter emitter = mock(PatientEventEmitter.class);

    PatientCreatedEmitter createdEmitter = new PatientCreatedEmitter(emitter);

    Person patient = new Person(
        new PatientCommand.CreatePatient(
            117L,
            "patient-local-id",
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    patient.add(
        new PatientCommand.AddIdentification(
            117L,
            LocalDate.parse("2017-05-16"),
            "identification-value",
            "authority-value",
            "identification-type",
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    createdEmitter.created(patient);

    ArgumentCaptor<PatientEvent.Created> captor = ArgumentCaptor.forClass(PatientEvent.Created.class);

    verify(emitter).emit(captor.capture());

    PatientEvent.Created created = captor.getValue();

    assertThat(created.identifications())
        .satisfiesExactly(
            actual -> assertThat(actual)
                .returns(1, PatientEvent.Created.Identification::identifier)
                .returns(LocalDate.parse("2017-05-16"), PatientEvent.Created.Identification::asOf)
                .returns("identification-type", PatientEvent.Created.Identification::type)
                .returns("authority-value", PatientEvent.Created.Identification::authority)
                .returns("identification-value", PatientEvent.Created.Identification::value)
        );
  }

}
