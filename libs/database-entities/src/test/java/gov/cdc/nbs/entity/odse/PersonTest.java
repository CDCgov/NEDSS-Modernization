package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.message.enums.Deceased;
import gov.cdc.nbs.message.enums.Gender;
import gov.cdc.nbs.message.enums.Indicator;
import gov.cdc.nbs.message.enums.Suffix;
import gov.cdc.nbs.patient.PatientAssociationCountFinder;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.PatientHasAssociatedEventsException;
import gov.cdc.nbs.patient.demographic.*;
import gov.cdc.nbs.patient.demographic.name.SoundexResolver;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PersonTest {

  @Test
  void should_create_patient() {

    Person actual = new Person(
        new PatientCommand.CreatePatient(
            1033L,
            "local-id-value",
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    assertThat(actual)
        .returns(1033L, Person::id)
        .returns("local-id-value", Person::localId)
        .satisfies(current -> assertThat(current.addresses()).isEmpty())
        .satisfies(current -> assertThat(current.phones()).isEmpty())
        .satisfies(current -> assertThat(current.names()).isEmpty())
        .satisfies(current -> assertThat(current.identifications()).isEmpty())
        .satisfies(current -> assertThat(current.race().details()).isEmpty())
    ;

  }
  
  @Test
  void should_add_name() {
    Person patient = new Person(117L, "local-id-value");
    SoundexResolver resolver = mock(SoundexResolver.class);

    patient.add(
        resolver,
        new PatientCommand.AddName(
            117L,
            LocalDate.parse("2023-05-15"),
            "prefix",
            "First",
            "Middle",
            "Second-Middle",
            "Last",
            "Second-Last",
            "JR",
            "Degree",
            "L",
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    assertThat(patient)
        .extracting(Person::audit)
        .satisfies(AuditAssertions.changed(131L, "2020-03-03T10:15:30"));

    assertThat(patient.names()).satisfiesExactly(
        actual -> assertThat(actual)
            .returns(LocalDate.parse("2023-05-15"), PersonName::getAsOfDate)
            .returns("prefix", PersonName::getNmPrefix)
            .returns("First", PersonName::getFirstNm)
            .returns("Second-Middle", PersonName::getMiddleNm2)
            .returns("Middle", PersonName::getMiddleNm)
            .returns("Last", PersonName::getLastNm)
            .returns("Second-Last", PersonName::getLastNm2)
            .returns(Suffix.JR, PersonName::getNmSuffix)
            .returns("Degree", PersonName::getNmDegree)
            .returns("L", PersonName::getNmUseCd)
            .satisfies(
                name -> assertThat(name)
                    .describedAs("expected name identifier starts at sequence 1")
                    .extracting(PersonName::getId)
                    .returns(117L, PersonNameId::getPersonUid)
                    .returns((short) 1, PersonNameId::getPersonNameSeq)
            )
            .satisfies(
                added -> assertThat(added.getAudit())
                    .describedAs("expected name audit state")
                    .satisfies(AuditAssertions.added(131L, "2020-03-03T10:15:30"))
                    .satisfies(AuditAssertions.changed(131L, "2020-03-03T10:15:30"))
            )


    );
  }

  @Test
  void should_add_another_name() {
    Person patient = new Person(117L, "local-id-value");
    SoundexResolver resolver = mock(SoundexResolver.class);

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
            171L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    patient.add(
        resolver,
        new PatientCommand.AddName(
            117L,
            LocalDate.parse("2023-05-15"),
            "Another-Prefix",
            "Another-First",
            "Another-Middle",
            "Another-Second-Middle",
            "Another-Last",
            "Another-Second-Last",
            "SR",
            "Another-Degree",
            "A",
            131L,
            LocalDateTime.parse("2021-02-03T04:05:06")
        )
    );

    assertThat(patient.names()).satisfiesExactly(
        actual -> assertThat(actual)
            .extracting(PersonName::getId)
            .returns(117L, PersonNameId::getPersonUid)
            .returns((short) 1, PersonNameId::getPersonNameSeq),
        actual -> assertThat(actual)
            .returns(LocalDate.parse("2023-05-15"), PersonName::getAsOfDate)
            .returns("Another-Prefix", PersonName::getNmPrefix)
            .returns("Another-First", PersonName::getFirstNm)
            .returns("Another-Second-Middle", PersonName::getMiddleNm2)
            .returns("Another-Middle", PersonName::getMiddleNm)
            .returns("Another-Last", PersonName::getLastNm)
            .returns("Another-Second-Last", PersonName::getLastNm2)
            .returns(Suffix.SR, PersonName::getNmSuffix)
            .returns("Another-Degree", PersonName::getNmDegree)
            .returns("A", PersonName::getNmUseCd)
            .satisfies(
                name -> assertThat(name)
                    .describedAs("expected name identifier starts at sequence 2")
                    .extracting(PersonName::getId)
                    .returns(117L, PersonNameId::getPersonUid)
                    .returns((short) 2, PersonNameId::getPersonNameSeq)
            )
            .satisfies(
                added -> assertThat(added.getAudit())
                    .describedAs("expected name audit state")
                    .satisfies(AuditAssertions.added(131L, "2021-02-03T04:05:06"))
                    .satisfies(AuditAssertions.changed(131L, "2021-02-03T04:05:06"))
            )
    );
  }

  @Test
  void should_update_existing_name() {
    Person patient = new Person(117L, "local-id-value");
    SoundexResolver resolver = mock(SoundexResolver.class);

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

    patient.update(
        resolver,
        new PatientCommand.UpdateNameInfo(
            117L,
            (short) 1,
            LocalDate.parse("2023-05-15"),
            "prefix",
            "First",
            "Middle",
            "Second-Middle",
            "Last",
            "Second-Last",
            "JR",
            "Degree",
            "L",
            171L,
            LocalDateTime.parse("2021-04-05T06:07:08")
        )
    );

    assertThat(patient)
        .extracting(Person::audit)
        .satisfies(AuditAssertions.changed(171L, "2021-04-05T06:07:08"));

    assertThat(patient.names()).satisfiesExactly(
        actual -> assertThat(actual)
            .returns(LocalDate.parse("2023-05-15"), PersonName::getAsOfDate)
            .returns("prefix", PersonName::getNmPrefix)
            .returns("First", PersonName::getFirstNm)
            .returns("Second-Middle", PersonName::getMiddleNm2)
            .returns("Middle", PersonName::getMiddleNm)
            .returns("Last", PersonName::getLastNm)
            .returns("Second-Last", PersonName::getLastNm2)
            .returns(Suffix.JR, PersonName::getNmSuffix)
            .returns("Degree", PersonName::getNmDegree)
            .returns("L", PersonName::getNmUseCd)
            .satisfies(
                name -> assertThat(name)
                    .describedAs("expected name identifier starts at sequence 1")
                    .extracting(PersonName::getId)
                    .returns(117L, PersonNameId::getPersonUid)
                    .returns((short) 1, PersonNameId::getPersonNameSeq)
            )
            .satisfies(
                added -> assertThat(added.getAudit())
                    .describedAs("expected name audit state")
                    .satisfies(AuditAssertions.changed(171L, "2021-04-05T06:07:08")
                    )
            )
    );
  }

  @Test
  void should_remove_existing_name() {
    Person patient = new Person(117L, "local-id-value");
    SoundexResolver resolver = mock(SoundexResolver.class);

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

    patient.add(
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

    patient.delete(
        new PatientCommand.DeleteNameInfo(
            117L,
            (short) 2,
            171L,
            LocalDateTime.parse("2021-03-03T10:15:30")
        )
    );

    assertThat(patient)
        .returns(171L, person -> person.audit().changed().changedBy())
        .returns(LocalDateTime.parse("2021-03-03T10:15:30"), person -> person.audit().changed().changedOn());

    assertThat(patient.names()).satisfiesExactlyInAnyOrder(
        actual -> assertThat(actual)
            .extracting(PersonName::getId)
            .returns(117L, PersonNameId::getPersonUid)
            .returns((short) 1, PersonNameId::getPersonNameSeq)

    );
  }

  @Test
  void should_add_minimal_name_at_sequence_one() {
    Person actual = new Person(117L, "local-id-value");
    SoundexResolver resolver = mock(SoundexResolver.class);

    actual.add(
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

    assertThat(actual.names()).satisfiesExactly(
        actualPrimary -> assertThat(actualPrimary)
            .returns(LocalDate.parse("2021-05-15"), PersonName::getAsOfDate)
            .returns("First", PersonName::getFirstNm)
            .returns("Middle", PersonName::getMiddleNm)
            .returns("Last", PersonName::getLastNm)
            .returns(Suffix.JR, PersonName::getNmSuffix)
            .returns("L", PersonName::getNmUseCd)
            .extracting(PersonName::getId)
            .returns(117L, PersonNameId::getPersonUid)
            .returns((short) 1, PersonNameId::getPersonNameSeq)
    );
  }

  @Test
  void should_add_secondary_name() {

    Person actual = new Person(117L, "local-id-value");
    SoundexResolver resolver = mock(SoundexResolver.class);

    actual.add(
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

    actual.add(
        resolver,
        new PatientCommand.AddName(
            117L,
            LocalDate.parse("2021-05-15"),
            "Other",
            "OtherMiddle",
            "OtherLast",
            "SR",
            "AL",
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    assertThat(actual.names()).satisfiesExactly(
        actualPrimary -> assertThat(actualPrimary)
            .returns("First", PersonName::getFirstNm)
            .returns("Middle", PersonName::getMiddleNm)
            .returns("Last", PersonName::getLastNm)
            .returns(Suffix.JR, PersonName::getNmSuffix)
            .returns("L", PersonName::getNmUseCd)
            .extracting(PersonName::getId)
            .returns(117L, PersonNameId::getPersonUid)
            .returns((short) 1, PersonNameId::getPersonNameSeq),
        actualAlias -> assertThat(actualAlias)
            .returns("Other", PersonName::getFirstNm)
            .returns("OtherMiddle", PersonName::getMiddleNm)
            .returns("OtherLast", PersonName::getLastNm)
            .returns(Suffix.SR, PersonName::getNmSuffix)
            .returns("AL", PersonName::getNmUseCd)
            .extracting(PersonName::getId)
            .returns(117L, PersonNameId::getPersonUid)
            .returns((short) 2, PersonNameId::getPersonNameSeq)
    );

  }

  @Test
  void should_add_minimal_postal_address() {

    Person actual = new Person(117L, "local-id-value");

    actual.add(
        new PatientCommand.AddAddress(
            117L,
            LocalDate.parse("2021-07-07"),
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
        ),
        () -> 4861L
    );

    assertThat(actual.addresses())
        .satisfiesExactly(
            actualPostalLocator -> assertThat(actualPostalLocator)
                .returns(4861L, p -> p.getId().getLocatorUid())
                .returns("H", EntityLocatorParticipation::getCd)
                .returns("H", EntityLocatorParticipation::getUseCd)
                .returns(LocalDate.parse("2021-07-07"), EntityLocatorParticipation::getAsOfDate)
                .extracting(PostalEntityLocatorParticipation::getLocator)
                .returns(4861L, PostalLocator::getId)
                .returns("SA1", PostalLocator::getStreetAddr1)
                .returns("SA2", PostalLocator::getStreetAddr2)
                .returns("city-description", PostalLocator::getCityDescTxt)
                .returns("State", PostalLocator::getStateCd)
                .returns("county-code", PostalLocator::getCntyCd)
                .returns("Zip", PostalLocator::getZipCd)
                .returns("country-code", PostalLocator::getCntryCd)

        );

  }

  @Test
  void should_add_postal_address() {

    Person patient = new Person(117L, "local-id-value");

    patient.add(
        new PatientCommand.AddAddress(
            117L,
            LocalDate.parse("2021-07-07"),
            "type-value",
            "use-value",
            "SA1",
            "SA2",
            "city-description",
            "State",
            "Zip",
            "county-code",
            "country-code",
            "Census Tract",
            "Comments",
            131L,
            LocalDateTime.parse("2023-03-03T10:15:30")
        ),
        () -> 4861L
    );

    assertThat(patient)
        .extracting(Person::audit)
        .describedAs("expected patient audit state")
        .satisfies(AuditAssertions.changed(131L, "2023-03-03T10:15:30"));

    assertThat(patient.addresses())
        .satisfiesExactly(
            actual -> assertThat(actual)
                .returns(4861L, p -> p.getId().getLocatorUid())
                .returns("type-value", EntityLocatorParticipation::getCd)
                .returns("use-value", EntityLocatorParticipation::getUseCd)
                .returns(LocalDate.parse("2021-07-07"), EntityLocatorParticipation::getAsOfDate)
                .returns("Comments", EntityLocatorParticipation::getLocatorDescTxt)
                .satisfies(
                    added -> assertThat(added.audit())
                        .describedAs("expected participation audit state")
                        .satisfies(AuditAssertions.added(131L, "2023-03-03T10:15:30"))
                        .satisfies(AuditAssertions.changed(131L, "2023-03-03T10:15:30"))
                )
                .extracting(PostalEntityLocatorParticipation::getLocator)
                .returns(4861L, PostalLocator::getId)
                .returns("SA1", PostalLocator::getStreetAddr1)
                .returns("SA2", PostalLocator::getStreetAddr2)
                .returns("city-description", PostalLocator::getCityDescTxt)
                .returns("State", PostalLocator::getStateCd)
                .returns("county-code", PostalLocator::getCntyCd)
                .returns("Zip", PostalLocator::getZipCd)
                .returns("country-code", PostalLocator::getCntryCd)
                .returns("Census Tract", PostalLocator::getCensusTract)
                .satisfies(
                    added -> assertThat(added.audit())
                        .describedAs("expected address audit state")
                        .satisfies(AuditAssertions.added(131L, "2023-03-03T10:15:30"))
                        .satisfies(AuditAssertions.changed(131L, "2023-03-03T10:15:30"))
                )
        );

  }

  @Test
  void should_update_existing_postal_address() {

    Person patient = new Person(117L, "local-id-value");

    patient.add(
        new PatientCommand.AddAddress(
            4861L,
            LocalDate.parse("2021-07-07"),
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
        ),
        () -> 4861L
    );

    patient.update(
        new PatientCommand.UpdateAddress(
            117L,
            4861L,
            LocalDate.parse("2021-07-07"),
            "type-value",
            "use-value",
            "SA1",
            "SA2",
            "city-description",
            "State",
            "Zip",
            "county-code",
            "country-code",
            "Census Tract",
            "Comments",
            171L,
            LocalDateTime.parse("2020-03-04T00:00")
        )
    );

    assertThat(patient)
        .extracting(Person::audit)
        .satisfies(AuditAssertions.changed(171L, "2020-03-04T00:00"));


    assertThat(patient.addresses())
        .satisfiesExactly(
            actual -> assertThat(actual)
                .returns(4861L, p -> p.getId().getLocatorUid())
                .returns("type-value", EntityLocatorParticipation::getCd)
                .returns("use-value", EntityLocatorParticipation::getUseCd)
                .returns(LocalDate.parse("2021-07-07"), EntityLocatorParticipation::getAsOfDate)
                .returns("Comments", EntityLocatorParticipation::getLocatorDescTxt)
                .satisfies(
                    added -> assertThat(added.audit())
                        .satisfies(AuditAssertions.added(131L, "2020-03-03T10:15:30"))
                        .satisfies(AuditAssertions.changed(171L, "2020-03-04T00:00:00"))
                )
                .extracting(PostalEntityLocatorParticipation::getLocator)
                .returns(4861L, PostalLocator::getId)
                .returns("SA1", PostalLocator::getStreetAddr1)
                .returns("SA2", PostalLocator::getStreetAddr2)
                .returns("city-description", PostalLocator::getCityDescTxt)
                .returns("State", PostalLocator::getStateCd)
                .returns("county-code", PostalLocator::getCntyCd)
                .returns("Zip", PostalLocator::getZipCd)
                .returns("country-code", PostalLocator::getCntryCd)
                .returns("Census Tract", PostalLocator::getCensusTract)
                .satisfies(
                    added -> assertThat(added.audit())
                        .satisfies(AuditAssertions.added(131L, "2020-03-03T10:15:30"))
                        .satisfies(AuditAssertions.changed(171L, "2020-03-04T00:00:00"))
                )

        );

  }

  @Test
  void should_delete_existing_postal_address() {

    Person patient = new Person(117L, "local-id-value");

    patient.add(
        new PatientCommand.AddAddress(
            117L,
            LocalDate.parse("2021-07-07"),
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
        ),
        () -> 4861L
    );

    patient.add(
        new PatientCommand.AddAddress(
            117L,
            LocalDate.parse("2021-07-07"),
            "Other-SA1",
            "Other-SA2",
            "Other-city",
            "Other-State",
            "Other-Zip",
            "Other-county-code",
            "Other-country-code",
            null,
            171L,
            LocalDateTime.parse("2020-03-04T08:45:23")
        ),
        () -> 5331L
    );

    patient.delete(
        new PatientCommand.DeleteAddress(
            117L,
            5331L,
            191L,
            LocalDateTime.parse("2021-05-24T11:01:17")
        )
    );

    assertThat(patient).satisfies(
        added -> assertThat(added.audit())
            .satisfies(AuditAssertions.changed(191L, "2021-05-24T11:01:17"))
    );

    assertThat(patient.addresses())
        .satisfiesExactly(
            actual -> assertThat(actual)
                .returns(4861L, p -> p.getId().getLocatorUid())
        );

  }

  @Test
  void should_add_phone() {
    Person patient = new Person(117L, "local-id-value");

    patient.add(
        new PatientCommand.AddPhone(
            117L,
            "type-value",
            "use-value",
            LocalDate.parse("2023-11-27"),
            "country-code",
            "number",
            "extension",
            "email",
            "url",
            "comment",
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        ),
        () -> 5347L
    );

    assertThat(patient).satisfies(
        added -> assertThat(added.audit())
            .satisfies(AuditAssertions.changed(131L, "2020-03-03T10:15:30"))
    );

    assertThat(patient.phones())
        .satisfiesExactly(
            actual -> assertThat(actual)
                .returns(5347L, p -> p.getId().getLocatorUid())
                .returns("type-value", EntityLocatorParticipation::getCd)
                .returns("use-value", EntityLocatorParticipation::getUseCd)
                .returns(LocalDate.parse("2023-11-27"), EntityLocatorParticipation::getAsOfDate)
                .returns("comment", EntityLocatorParticipation::getLocatorDescTxt)
                .satisfies(
                    added -> assertThat(added.audit())
                        .satisfies(AuditAssertions.added(131L, "2020-03-03T10:15:30"))
                        .satisfies(AuditAssertions.changed(131L, "2020-03-03T10:15:30"))
                )
                .extracting(TeleEntityLocatorParticipation::getLocator)
                .returns(5347L, TeleLocator::getId)
                .returns("country-code", TeleLocator::getCntryCd)
                .returns("number", TeleLocator::getPhoneNbrTxt)
                .returns("extension", TeleLocator::getExtensionTxt)
                .returns("email", TeleLocator::getEmailAddress)
                .returns("url", TeleLocator::getUrlAddress)
                .satisfies(
                    added -> assertThat(added.audit())
                        .satisfies(AuditAssertions.added(131L, "2020-03-03T10:15:30"))
                        .satisfies(AuditAssertions.changed(131L, "2020-03-03T10:15:30"))
                )
        );
  }

  @Test
  void should_update_existing_phone() {
    Person patient = new Person(117L, "local-id-value");

    patient.add(
        new PatientCommand.AddPhone(
            117L,
            "type-value",
            "use-value",
            LocalDate.parse("2023-11-27"),
            "country-code",
            "number",
            "extension",
            "email",
            "url",
            "comment",
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        ),
        () -> 5347L
    );

    patient.update(
        new PatientCommand.UpdatePhone(
            117L,
            5347L,
            "updated-type-value",
            "updated-use-value",
            LocalDate.parse("2023-11-27"),
            "updated-country-code",
            "updated-number",
            "updated-extension",
            "updated-email",
            "updated-url",
            "updated-comment",
            171L,
            LocalDateTime.parse("2023-07-01T13:17:00")
        )
    );

    assertThat(patient).satisfies(
        added -> assertThat(added.audit())
            .satisfies(AuditAssertions.changed(171L, "2023-07-01T13:17:00"))
    );

    assertThat(patient.phones())
        .satisfiesExactly(
            actual -> assertThat(actual)
                .isInstanceOf(TeleEntityLocatorParticipation.class)
                .asInstanceOf(InstanceOfAssertFactories.type(TeleEntityLocatorParticipation.class))
                .returns(5347L, p -> p.getId().getLocatorUid())
                .returns("updated-type-value", EntityLocatorParticipation::getCd)
                .returns("updated-use-value", EntityLocatorParticipation::getUseCd)
                .returns(LocalDate.parse("2023-11-27"), EntityLocatorParticipation::getAsOfDate)
                .satisfies(
                    added -> assertThat(added.audit())
                        .satisfies(AuditAssertions.added(131L, "2020-03-03T10:15:30"))
                        .satisfies(AuditAssertions.changed(171L, "2023-07-01T13:17:00"))
                )
                .returns("updated-comment", EntityLocatorParticipation::getLocatorDescTxt)
                .extracting(TeleEntityLocatorParticipation::getLocator)
                .returns(5347L, TeleLocator::getId)
                .returns("updated-country-code", TeleLocator::getCntryCd)
                .returns("updated-number", TeleLocator::getPhoneNbrTxt)
                .returns("updated-extension", TeleLocator::getExtensionTxt)
                .returns("updated-email", TeleLocator::getEmailAddress)
                .returns("updated-url", TeleLocator::getUrlAddress)
                .satisfies(
                    added -> assertThat(added.audit())
                        .satisfies(AuditAssertions.added(131L, "2020-03-03T10:15:30"))
                        .satisfies(AuditAssertions.changed(171L, "2023-07-01T13:17:00"))
                )
        );
  }

  @Test
  void should_delete_existing_phone() {
    Person patient = new Person(117L, "local-id-value");

    patient.add(
        new PatientCommand.AddPhone(
            117L,
            "type-value",
            "use-value",
            LocalDate.parse("2023-11-27"),
            "country-code",
            "number",
            "extension",
            "email",
            "url",
            "comment",
            131L,
            LocalDateTime.parse("2023-03-03T10:15:30")
        ),
        () -> 5347L
    );

    patient.add(
        new PatientCommand.AddPhone(
            117L,
            "type-value",
            "use-value",
            LocalDate.parse("2023-11-27"),
            "country-code",
            "number",
            "extension",
            "email",
            "url",
            "comment",
            171L,
            LocalDateTime.parse("2023-03-03T10:15:30")
        ),
        () -> 1567L
    );

    patient.delete(
        new PatientCommand.DeletePhone(
            117L,
            1567L,
            293L,
            LocalDateTime.parse("2023-03-03T10:15:30")
        )
    );

    assertThat(patient).satisfies(
        added -> assertThat(added.audit())
            .satisfies(AuditAssertions.changed(293L, "2023-03-03T10:15:30"))
    );

    assertThat(patient.phones())
        .satisfiesExactly(
            actual -> assertThat(actual)
                .isInstanceOf(TeleEntityLocatorParticipation.class)
                .asInstanceOf(InstanceOfAssertFactories.type(TeleEntityLocatorParticipation.class))
                .returns(5347L, p -> p.getId().getLocatorUid())
        );
  }

  @Test
  void should_delete_patient_without_associations() {

    PatientAssociationCountFinder finder = mock(PatientAssociationCountFinder.class);

    when(finder.count(anyLong())).thenReturn(0L);

    Person actual = new Person(117L, "local-id-value");

    actual.delete(
        new PatientCommand.Delete(
            117L,
            131L,
            LocalDateTime.parse("2023-03-03T10:15:30")
        ),
        finder
    );

    assertThat(actual)
        .satisfies(
            updated -> assertThat(updated)
                .extracting(Person::audit)
                .satisfies(AuditAssertions.changed(131L, "2023-03-03T10:15:30"))
        ).satisfies(
            updated -> assertThat(updated)
                .extracting(Person::recordStatus)
                .satisfies(RecordStatusAssertions.status("LOG_DEL", "2023-03-03T10:15:30"))
        );

  }

  @Test
  void should_not_allow_deletion_of_patient_with_associations() {
    PatientAssociationCountFinder finder = mock(PatientAssociationCountFinder.class);

    when(finder.count(anyLong())).thenReturn(1L);

    Person actual = new Person(117L, "local-id-value");

    LocalDateTime deletedOn = LocalDateTime.parse("2023-03-03T10:15:30");
    var deleteCommand = new PatientCommand.Delete(
        117L,
        131L,
        deletedOn
    );

    assertThatThrownBy(() ->
        actual.delete(
            deleteCommand,
            finder
        )
    ).isInstanceOf(PatientHasAssociatedEventsException.class)
        .asInstanceOf(InstanceOfAssertFactories.type(PatientHasAssociatedEventsException.class))
        .returns(117L, PatientHasAssociatedEventsException::patient);
  }

  @Test
  void should_set_general_info_fields() {
    Person actual = new Person(121L, "local-id-value");
    var command = new PatientCommand.UpdateGeneralInfo(
        121L,
        LocalDate.parse("2010-03-03"),
        "marital status",
        "mothers maiden name",
        1,
        2,
        "occupation code",
        "education level",
        "prim language",
        "speaks english",
        12L,
        LocalDateTime.parse("2019-03-03T10:15:30"));

    actual.update(command);

    assertThat(actual)
        .returns(12L, person -> person.audit().changed().changedBy())
        .returns(LocalDateTime.parse("2019-03-03T10:15:30"), person -> person.audit().changed().changedOn());

    assertThat(actual)
        .extracting(Person::getGeneralInformation)
        .returns(LocalDate.parse("2010-03-03"), GeneralInformation::asOf)
        .returns("marital status", GeneralInformation::maritalStatus)
        .returns("mothers maiden name", GeneralInformation::mothersMaidenName)
        .returns(1, GeneralInformation::adultsInHouse)
        .returns(2, GeneralInformation::childrenInHouse)
        .returns("occupation code", GeneralInformation::occupation)
        .returns("education level", GeneralInformation::educationLevel)
        .returns("prim language", GeneralInformation::primaryLanguage)
        .returns("speaks english", GeneralInformation::speaksEnglish);
  }

  @Test
  void should_add_identity_with_sequence_one() {
    Person patient = new Person(117L, "local-id-value");

    patient.add(
        new PatientCommand.AddIdentification(
            117L,
            LocalDate.parse("1999-09-09"),
            "identification-value",
            "authority-value",
            "identification-type",
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    assertThat(patient)
        .extracting(Person::audit)
        .satisfies(AuditAssertions.changed(131L, "2020-03-03T10:15:30"));

    assertThat(patient.identifications()).satisfiesExactly(
        actual -> assertThat(actual)
            .satisfies(
                identification -> assertThat(identification)
                    .extracting(EntityId::getAudit)
                    .satisfies(AuditAssertions.added(131L, "2020-03-03T10:15:30"))
                    .satisfies(AuditAssertions.changed(131L, "2020-03-03T10:15:30"))
            )
            .returns("identification-type", EntityId::getTypeCd)
            .returns(LocalDate.parse("1999-09-09"), EntityId::getAsOfDate)
            .returns("authority-value", EntityId::getAssigningAuthorityCd)
            .returns("identification-value", EntityId::getRootExtensionTxt)
            .satisfies(
                identification -> assertThat(identification)
                    .extracting(EntityId::getId)
                    .returns((short) 1, EntityIdId::getEntityIdSeq)
            )

    );
  }

  @Test
  void should_update_existing_identity() {
    Person patient = new Person(117L, "local-id-value");

    patient.add(
        new PatientCommand.AddIdentification(
            117L,
            LocalDate.parse("1999-09-09"),
            "identification-value",
            "authority-value",
            "identification-type",
            131L,
            LocalDateTime.parse("2023-03-03T10:15:30")
        )
    );

    patient.update(
        new PatientCommand.UpdateIdentification(
            117L,
            1,
            LocalDate.parse("2001-05-19"),
            "updated-identification-value",
            "updated-authority-value",
            "updated-identification-type",
            171L,
            LocalDateTime.parse("2020-03-13T13:15:30")
        )
    );

    assertThat(patient)
        .extracting(Person::audit)
        .satisfies(AuditAssertions.changed(171L, "2020-03-13T13:15:30"));

    assertThat(patient.identifications()).satisfiesExactly(
        actual -> assertThat(actual)
            .satisfies(
                identification -> assertThat(identification)
                    .extracting(EntityId::getAudit)
                    .satisfies(AuditAssertions.added(131L, "2023-03-03T10:15:30"))
                    .satisfies(AuditAssertions.changed(171L, "2020-03-13T13:15:30"))
            )
            .returns("updated-identification-type", EntityId::getTypeCd)
            .returns(LocalDate.parse("2001-05-19"), EntityId::getAsOfDate)
            .returns("updated-authority-value", EntityId::getAssigningAuthorityCd)
            .returns("updated-identification-value", EntityId::getRootExtensionTxt)

    );
  }

  @Test
  void should_not_update_unknown_identity() {
    Person patient = new Person(117L, "local-id-value");

    patient.add(
        new PatientCommand.AddIdentification(
            117L,
            LocalDate.parse("1999-09-09"),
            "identification-value",
            "authority-value",
            "identification-type",
            131L,
            LocalDateTime.parse("2023-03-03T10:15:30")
        )
    );

    patient.update(
        new PatientCommand.UpdateIdentification(
            117L,
            1013,
            LocalDate.parse("2001-05-19"),
            "updated-identification-value",
            "updated-authority-value",
            "updated-identification-type",
            171L,
            LocalDateTime.parse("2020-03-13T13:15:30")
        )
    );

    assertThat(patient.identifications()).satisfiesExactly(
        actual -> assertThat(actual)
            .satisfies(
                identification -> assertThat(identification)
                    .extracting(EntityId::getAudit)
                    .satisfies(AuditAssertions.added(131L, "2023-03-03T10:15:30"))
                    .satisfies(AuditAssertions.changed(131L, "2023-03-03T10:15:30"))
            )
            .returns("identification-type", EntityId::getTypeCd)
            .returns(LocalDate.parse("1999-09-09"), EntityId::getAsOfDate)
            .returns("authority-value", EntityId::getAssigningAuthorityCd)
            .returns("identification-value", EntityId::getRootExtensionTxt)

    );
  }


  @Test
  void should_delete_existing_identity() {
    Person patient = new Person(117L, "local-id-value");

    patient.add(
        new PatientCommand.AddIdentification(
            117L,
            LocalDate.parse("1999-09-09"),
            "identification-value",
            "authority-value",
            "identification-type",
            131L,
            LocalDateTime.parse("2023-03-03T10:15:30")
        )
    );

    patient.add(
        new PatientCommand.AddIdentification(
            117L,
            LocalDate.parse("2001-05-19"),
            "other-identification-value",
            "other-authority-value",
            "other-identification-type",
            131L,
            LocalDateTime.parse("2023-03-03T10:15:30")
        )
    );

    patient.delete(
        new PatientCommand.DeleteIdentification(
            117,
            1,
            171L,
            LocalDateTime.parse("2020-03-13T13:15:30")
        )
    );

    assertThat(patient)
        .extracting(Person::audit)
        .satisfies(AuditAssertions.changed(171L, "2020-03-13T13:15:30"));

    assertThat(patient.identifications()).satisfiesExactly(
        actual -> assertThat(actual)
            .satisfies(
                identification -> assertThat(identification)
                    .satisfies(id -> assertThat(id)
                        .extracting(EntityId::recordStatus)
                        .satisfies(RecordStatusAssertions.inactive("2020-03-13T13:15:30"))
                    )
                    .extracting(EntityId::getId)
                    .returns((short) 1, EntityIdId::getEntityIdSeq)

            ),
        actual -> assertThat(actual)
            .satisfies(
                identification -> assertThat(identification)
                    .satisfies(id -> assertThat(id)
                        .extracting(EntityId::recordStatus)
                        .satisfies(RecordStatusAssertions.active("2023-03-03T10:15:30"))
                    )
                    .extracting(EntityId::getId)
                    .returns((short) 2, EntityIdId::getEntityIdSeq)
            )
    );
  }

  @Test
  void should_not_delete_unknown_identity() {
    Person patient = new Person(117L, "local-id-value");

    patient.add(
        new PatientCommand.AddIdentification(
            117L,
            LocalDate.parse("1999-09-09"),
            "identification-value",
            "authority-value",
            "identification-type",
            131L,
            LocalDateTime.parse("2023-03-03T10:15:30")
        )
    );

    patient.add(
        new PatientCommand.AddIdentification(
            117L,
            LocalDate.parse("2001-05-19"),
            "other-identification-value",
            "other-authority-value",
            "other-identification-type",
            131L,
            LocalDateTime.parse("2023-03-03T10:15:30")
        )
    );

    patient.delete(
        new PatientCommand.DeleteIdentification(
            117,
            7,
            171L,
            LocalDateTime.parse("2020-03-13T13:15:30")
        )
    );

    assertThat(patient)
        .extracting(Person::audit)
        .satisfies(AuditAssertions.changed(171L, "2020-03-13T13:15:30"));

    assertThat(patient.identifications()).satisfiesExactly(
        actual -> assertThat(actual)
            .satisfies(
                identification -> assertThat(identification)
                    .satisfies(id -> assertThat(id)
                        .extracting(EntityId::recordStatus)
                        .satisfies(RecordStatusAssertions.active("2023-03-03T10:15:30"))
                    )
                    .extracting(EntityId::getId)
                    .returns((short) 1, EntityIdId::getEntityIdSeq)

            ),
        actual -> assertThat(actual)
            .satisfies(
                identification -> assertThat(identification)
                    .satisfies(id -> assertThat(id)
                        .extracting(EntityId::recordStatus)
                        .satisfies(RecordStatusAssertions.active("2023-03-03T10:15:30"))
                    )
                    .extracting(EntityId::getId)
                    .returns((short) 2, EntityIdId::getEntityIdSeq)
            )
    );
  }


  @Test
  void should_update_patient_mortality_when_patient_is_deceased() {

    Person patient = new Person(121L, "local-id-value");

    AddressIdentifierGenerator generator = () -> 1157L;

    patient.update(
        new PatientCommand.UpdateMortality(
            121L,
            LocalDate.parse("2023-06-01"),
            "Y",
            LocalDate.of(1987, Month.NOVEMBER, 17),
            null,
            null,
            null,
            null,
            131L,
            LocalDateTime.parse("2019-03-03T10:15:30")
        ),
        generator
    );

    assertThat(patient)
        .satisfies(updated -> assertThat(updated)
            .extracting(Person::mortality)
            .returns(LocalDate.parse("2023-06-01"), PatientMortality::asOf)
            .returns(Deceased.Y, PatientMortality::deceased)
            .returns(LocalDate.parse("1987-11-17"), PatientMortality::deceasedOn)
        )
        .extracting(Person::audit)
        .satisfies(AuditAssertions.changed(131L, "2019-03-03T10:15:30"));
  }

  @Test
  void should_update_patient_mortality_with_new_mortality_location_when_patient_is_deceased() {

    Person patient = new Person(121L, "local-id-value");

    AddressIdentifierGenerator generator = () -> 1157L;

    patient.update(
        new PatientCommand.UpdateMortality(
            121L,
            LocalDate.parse("2023-06-01"),
            "Y",
            LocalDate.of(1987, Month.NOVEMBER, 17),
            "city",
            "state",
            "county",
            "country",
            131L,
            LocalDateTime.parse("2019-03-03T10:15:30")
        ),
        generator
    );

    assertThat(patient)
        .satisfies(
            changed -> assertThat(changed)
                .extracting(Person::audit)
                .satisfies(AuditAssertions.changed(131L, "2019-03-03T10:15:30"))
        )
        .satisfies(
            actual -> assertThat(actual.locationOfDeath())
                .hasValueSatisfying(
                    address -> assertThat(address)
                        .returns("U", PostalEntityLocatorParticipation::getCd)
                        .returns("DTH", PostalEntityLocatorParticipation::getUseCd)
                        .extracting(PostalEntityLocatorParticipation::getLocator)
                        .returns("city", PostalLocator::getCityDescTxt)
                        .returns("state", PostalLocator::getStateCd)
                        .returns("county", PostalLocator::getCntyCd)
                        .returns("country", PostalLocator::getCntryCd)
                )
        )
    ;
  }

  @Test
  void should_clear_patient_mortality_when_patient_is_not_deceased() {

    Person patient = new Person(121L, "local-id-value");

    AddressIdentifierGenerator generator = () -> 1157L;

    patient.update(
        new PatientCommand.UpdateMortality(
            121L,
            LocalDate.parse("2023-06-01"),
            "Y",
            LocalDate.of(1987, Month.NOVEMBER, 17),
            "city",
            "state",
            "county",
            "country",
            121L,
            LocalDateTime.parse("2019-03-03T10:15:30")
        ),
        generator
    );

    patient.update(
        new PatientCommand.UpdateMortality(
            121L,
            LocalDate.parse("2023-06-01"),
            "N",
            LocalDate.of(1987, Month.NOVEMBER, 17),
            "city",
            "state",
            "county",
            "country",
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        ),
        generator
    );

    assertThat(patient)
        .satisfies(
            changed -> assertThat(changed)
                .extracting(Person::audit)
                .satisfies(AuditAssertions.changed(131L, "2020-03-03T10:15:30"))
        )
        .satisfies(
            actual -> assertThat(actual.locationOfDeath())
                .hasValueSatisfying(
                    address -> assertThat(address)
                        .returns("U", PostalEntityLocatorParticipation::getCd)
                        .returns("DTH", PostalEntityLocatorParticipation::getUseCd)
                        .extracting(PostalEntityLocatorParticipation::getLocator)
                        .returns(null, PostalLocator::getCityDescTxt)
                        .returns(null, PostalLocator::getStateCd)
                        .returns(null, PostalLocator::getCntyCd)
                        .returns(null, PostalLocator::getCntryCd)
                )
        )
        .extracting(Person::mortality)
        .returns(LocalDate.parse("2023-06-01"), PatientMortality::asOf)
        .returns(Deceased.N, PatientMortality::deceased)
        .returns(null, PatientMortality::deceasedOn)
    ;
  }

  @Test
  void should_clear_patient_mortality_when_patient_is_not_known_to_be_deceased() {

    Person patient = new Person(121L, "local-id-value");

    AddressIdentifierGenerator generator = () -> 1157L;

    patient.update(
        new PatientCommand.UpdateMortality(
            121L,
            LocalDate.parse("2023-06-01"),
            "Y",
            LocalDate.of(1987, Month.NOVEMBER, 17),
            "city",
            "state",
            "county",
            "country",
            131L,
            LocalDateTime.parse("2019-03-03T10:15:30")
        ),
        generator
    );

    patient.update(
        new PatientCommand.UpdateMortality(
            121L,
            LocalDate.parse("2023-06-01"),
            "UNK",
            LocalDate.of(1987, Month.NOVEMBER, 17),
            "city",
            "state",
            "county",
            "country",
            131L,
            LocalDateTime.parse("2019-03-03T10:15:30")
        ),
        generator
    );

    assertThat(patient)
        .satisfies(updated -> assertThat(updated)
            .extracting(Person::mortality)
            .returns(LocalDate.parse("2023-06-01"), PatientMortality::asOf)
            .returns(Deceased.UNK, PatientMortality::deceased)
            .returns(null, PatientMortality::deceasedOn)
        )
        .satisfies(
            actual -> assertThat(actual.locationOfDeath())
                .hasValueSatisfying(
                    address -> assertThat(address)
                        .returns("U", PostalEntityLocatorParticipation::getCd)
                        .returns("DTH", PostalEntityLocatorParticipation::getUseCd)
                        .extracting(PostalEntityLocatorParticipation::getLocator)
                        .returns(null, PostalLocator::getCityDescTxt)
                        .returns(null, PostalLocator::getStateCd)
                        .returns(null, PostalLocator::getCntyCd)
                        .returns(null, PostalLocator::getCntryCd)
                )
        )
        .extracting(Person::audit)
        .satisfies(AuditAssertions.changed(131L, "2019-03-03T10:15:30"));
  }

  @Test
  void should_update_patient_mortality_with_changed_mortality_location_when_patient_is_deceased() {

    Person patient = new Person(121L, "local-id-value");

    AddressIdentifierGenerator generator = () -> 1157L;

    patient.update(
        new PatientCommand.UpdateMortality(
            121L,
            LocalDate.parse("2023-06-01"),
            "Y",
            null,
            "city",
            null,
            null,
            null,
            131L,
            LocalDateTime.parse("2019-03-03T10:15:30")
        ),
        generator
    );

    patient.update(
        new PatientCommand.UpdateMortality(
            121L,
            LocalDate.parse("2023-06-21"),
            "Y",
            LocalDate.of(1986, Month.NOVEMBER, 16),
            "changed",
            null,
            null,
            null,
            171L,
            LocalDateTime.parse("2019-03-03T10:15:30")
        ),
        generator
    );

    assertThat(patient)
        .satisfies(updated -> assertThat(updated)
            .extracting(Person::mortality)
            .returns(LocalDate.parse("2023-06-21"), PatientMortality::asOf)
            .returns(Deceased.Y, PatientMortality::deceased)
            .returns(LocalDate.parse("1986-11-16"), PatientMortality::deceasedOn)
        )
        .satisfies(
            actual -> assertThat(actual.locationOfDeath())
                .hasValueSatisfying(
                    address -> assertThat(address)
                        .returns("U", PostalEntityLocatorParticipation::getCd)
                        .returns("DTH", PostalEntityLocatorParticipation::getUseCd)
                        .extracting(PostalEntityLocatorParticipation::getLocator)
                        .returns("changed", PostalLocator::getCityDescTxt)
                )
        ).extracting(Person::audit)
        .satisfies(AuditAssertions.changed(171L, "2019-03-03T10:15:30"))
    ;
  }

  @Test
  void should_update_patient_gender() {

    Person patient = new Person(121L, "local-id-value");

    patient.update(
        new PatientCommand.UpdateGender(
            121L,
            LocalDate.parse("2023-06-01"),
            Gender.F.value(),
            "gender-unknown-reason",
            "gender-preferred",
            "gender-additional",
            131L,
            LocalDateTime.parse("2019-03-03T10:15:30")
        )
    );

    assertThat(patient)
        .satisfies(updated -> assertThat(updated)
            .extracting(Person::sexBirth)
            .returns(LocalDate.parse("2023-06-01"), PatientSexBirth::asOf)
            .returns(Gender.F, PatientSexBirth::gender)
            .returns("gender-preferred", PatientSexBirth::preferredGender)
            .returns("gender-additional", PatientSexBirth::additionalGender)
            .returns(null, PatientSexBirth::genderUnknownReason)
        )
        .extracting(Person::audit)
        .satisfies(AuditAssertions.changed(131L, "2019-03-03T10:15:30"));

  }

  @Test
  void should_update_unknown_reason_when_gender_is_Unknown() {
    Person patient = new Person(121L, "local-id-value");

    patient.update(
        new PatientCommand.UpdateGender(
            121L,
            LocalDate.parse("2023-06-01"),
            Gender.U.value(),
            "gender-unknown-reason",
            null,
            null,
            131L,
            LocalDateTime.parse("2019-03-03T10:15:30")
        )
    );

    assertThat(patient)
        .extracting(Person::sexBirth)
        .returns(LocalDate.parse("2023-06-01"), PatientSexBirth::asOf)
        .returns(Gender.U, PatientSexBirth::gender)
        .returns("gender-unknown-reason", PatientSexBirth::genderUnknownReason);

  }

  @Test
  void should_update_patient_birth() {
    Person patient = new Person(121L, "local-id-value");

    AddressIdentifierGenerator generator = () -> 1157L;

    patient.update(
        new PatientCommand.UpdateBirth(
            121L,
            LocalDate.parse("2023-06-01"),
            LocalDate.of(1949, 10, 15),
            Gender.U.value(),
            Indicator.NO.getId(),
            null,
            null,
            null,
            null,
            null,
            131L,
            LocalDateTime.parse("2019-03-03T10:15:30")
        ),
        generator
    );

    assertThat(patient)
        .satisfies(updated -> assertThat(updated)
            .extracting(Person::sexBirth)
            .returns(LocalDate.parse("2023-06-01"), PatientSexBirth::asOf)
            .returns(LocalDateTime.parse("1949-10-15T00:00:00"), PatientSexBirth::birthday)
            .returns(Gender.U, PatientSexBirth::birthGender)
        )
        .extracting(Person::audit)
        .satisfies(AuditAssertions.changed(131L, "2019-03-03T10:15:30"));
  }

  @Test
  void should_update_patient_with_multiple_birth() {
    Person patient = new Person(1049L, "local-id-value");

    AddressIdentifierGenerator generator = () -> 1157L;

    patient.update(
        new PatientCommand.UpdateBirth(
            1049L,
            LocalDate.parse("2023-06-01"),
            LocalDate.of(1949, 10, 15),
            Gender.U.value(),
            Indicator.YES.getId(),
            17,
            null,
            null,
            null,
            null,
            131L,
            LocalDateTime.parse("2019-03-03T10:15:30")
        ),
        generator
    );

    assertThat(patient)
        .satisfies(updated -> assertThat(updated)
            .extracting(Person::sexBirth)
            .returns(LocalDate.parse("2023-06-01"), PatientSexBirth::asOf)
            .returns(LocalDateTime.parse("1949-10-15T00:00:00"), PatientSexBirth::birthday)
            .returns(Indicator.YES.getId(), PatientSexBirth::multipleBirth)
            .returns((short) 17, PatientSexBirth::birthOrder)
        )
        .extracting(Person::audit)
        .satisfies(AuditAssertions.changed(131L, "2019-03-03T10:15:30"));
  }

  @Test
  void should_update_patient_birth_with_location() {
    Person patient = new Person(121L, "local-id-value");

    AddressIdentifierGenerator generator = () -> 1157L;

    patient.update(
        new PatientCommand.UpdateBirth(
            121L,
            LocalDate.parse("2023-06-01"),
            null,
            null,
            null,
            null,
            "city",
            "state",
            "county",
            "country",
            131L,
            LocalDateTime.parse("2019-03-03T10:15:30")
        ),
        generator
    );

    assertThat(patient)
        .satisfies(
            actual -> assertThat(actual.locationOfBirth())
                .hasValueSatisfying(
                    address -> assertThat(address)
                        .returns("F", PostalEntityLocatorParticipation::getCd)
                        .returns("BIR", PostalEntityLocatorParticipation::getUseCd)
                        .extracting(PostalEntityLocatorParticipation::getLocator)
                        .returns("city", PostalLocator::getCityDescTxt)
                        .returns("state", PostalLocator::getStateCd)
                        .returns("county", PostalLocator::getCntyCd)
                        .returns("country", PostalLocator::getCntryCd)
                )
        )
        .extracting(Person::audit)
        .satisfies(AuditAssertions.changed(131L, "2019-03-03T10:15:30"));
  }

  @Test
  void should_update_patient_administrative() {

    Person patient = new Person(121L, "local-id-value");

    patient.update(
        new PatientCommand.UpdateAdministrativeInfo(
            121L,
            LocalDate.parse("2023-06-01"),
            "comments",
            131L,
            LocalDateTime.parse("2019-03-03T10:15:30")
        )
    );

    assertThat(patient)
        .satisfies(updated -> assertThat(updated)
            .extracting(Person::administrative)
            .returns(LocalDate.parse("2023-06-01"), PatientAdministrativeInformation::asOf)
            .returns("comments", PatientAdministrativeInformation::comments)
        )
        .extracting(Person::audit)
        .satisfies(AuditAssertions.changed(131L, "2019-03-03T10:15:30"));
  }
}
