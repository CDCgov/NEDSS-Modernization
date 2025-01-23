package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.message.enums.Deceased;
import gov.cdc.nbs.message.enums.Gender;
import gov.cdc.nbs.message.enums.Indicator;
import gov.cdc.nbs.message.enums.Suffix;
import gov.cdc.nbs.patient.PatientAssociationCountFinder;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.PatientHasAssociatedEventsException;
import gov.cdc.nbs.patient.demographic.AddressIdentifierGenerator;
import gov.cdc.nbs.patient.demographic.GeneralInformation;
import gov.cdc.nbs.patient.demographic.PatientEthnicity;
import gov.cdc.nbs.patient.demographic.name.SoundexResolver;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;

import java.time.Instant;
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
  @SuppressWarnings("squid:S5961")
    // Allow more than 25 assertions
  void should_create_new_person_when_patient_added() {

    PatientCommand.AddPatient request = new PatientCommand.AddPatient(
        117L,
        "patient-local-id",
        LocalDate.parse("2000-09-03"),
        Gender.M,
        Gender.F,
        Deceased.N,
        null,
        "Marital Status",
        "EthCode",
        Instant.parse("2019-03-03T10:15:30Z"),
        "comments",
        "HIV-Case",
        131L,
        LocalDateTime.parse("2020-03-03T10:15:30")
    );

    Person actual = new Person(request);

    assertThat(actual.getId()).isEqualTo(117L);
    assertThat(actual.getLocalId()).isEqualTo("patient-local-id");

    assertThat(actual.getVersionCtrlNbr()).isEqualTo((short) 1);
    assertThat(actual.getCd()).isEqualTo("PAT");
    assertThat(actual.getElectronicInd()).isEqualTo('N');
    assertThat(actual.getEdxInd()).isEqualTo("Y");

    assertThat(actual)
        .extracting(Person::audit)
        .satisfies(AuditAssertions.added(131L, "2020-03-03T10:15:30"))
        .satisfies(AuditAssertions.changed(131L, "2020-03-03T10:15:30"));

    assertThat(actual)
        .extracting(Person::status)
        .satisfies(StatusAssertions.active("2020-03-03T10:15:30"));

    assertThat(actual)
        .extracting(Person::recordStatus)
        .satisfies(RecordStatusAssertions.active("2020-03-03T10:15:30"));

    assertThat(actual.getBirthTime()).isEqualTo("2000-09-03T00:00");
    assertThat(actual.getBirthGenderCd()).isEqualTo(Gender.M);
    assertThat(actual.getCurrSexCd()).isEqualTo(Gender.F);
    assertThat(actual.getDeceasedIndCd()).isEqualTo(Deceased.N);
    assertThat(actual.getGeneralInformation().maritalStatus()).isEqualTo("Marital Status");

    assertThat(actual.getGeneralInformation().asOf()).isEqualTo("2019-03-03T10:15:30.00Z");
    assertThat(actual.getAsOfDateAdmin()).isEqualTo("2019-03-03T10:15:30.00Z");
    assertThat(actual.getAsOfDateSex()).isEqualTo("2019-03-03T10:15:30.00Z");
    assertThat(actual.getDescription()).isEqualTo("comments");

    assertThat(actual.getGeneralInformation().stateHIVCase()).isEqualTo("HIV-Case");

    assertThat(actual)
        .extracting(Person::getEthnicity)
        .returns("EthCode", PatientEthnicity::ethnicGroup)
        .returns(Instant.parse("2019-03-03T10:15:30Z"), PatientEthnicity::asOf);

    assertThat(actual.getPersonParentUid())
        .as("Master Patient Record set itself as parent")
        .isSameAs(actual);

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

    assertThat(patient.getNames()).satisfiesExactly(
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

    assertThat(patient.getNames()).satisfiesExactly(
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

    assertThat(patient.getNames()).satisfiesExactly(
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

    assertThat(patient.getNames()).satisfiesExactlyInAnyOrder(
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

    assertThat(actual.getNames()).satisfiesExactly(
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

    assertThat(actual.getFirstNm()).isEqualTo("First");
    assertThat(actual.getMiddleNm()).isEqualTo("Middle");
    assertThat(actual.getLastNm()).isEqualTo("Last");
    assertThat(actual.getNmSuffix()).isEqualTo(Suffix.JR);

    assertThat(actual.getNames()).satisfiesExactly(
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
            4861L,
            Instant.parse("2021-07-07T03:35:13Z"),
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

    assertThat(actual.addresses())
        .satisfiesExactly(
            actualPostalLocator -> assertThat(actualPostalLocator)
                .returns(4861L, p -> p.getId().getLocatorUid())
                .returns("H", EntityLocatorParticipation::getCd)
                .returns("H", EntityLocatorParticipation::getUseCd)
                .returns(Instant.parse("2021-07-07T03:35:13Z"), EntityLocatorParticipation::getAsOfDate)
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
            4861L,
            Instant.parse("2021-07-07T03:35:13Z"),
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
        )
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
                .returns(Instant.parse("2021-07-07T03:35:13Z"), EntityLocatorParticipation::getAsOfDate)
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

    patient.update(
        new PatientCommand.UpdateAddress(
            117L,
            4861L,
            Instant.parse("2021-07-07T03:35:13Z"),
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
                .returns(Instant.parse("2021-07-07T03:35:13Z"), EntityLocatorParticipation::getAsOfDate)
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

    patient.add(
        new PatientCommand.AddAddress(
            117L,
            5331L,
            Instant.parse("2021-07-07T03:06:09Z"),
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
        )
    );

    patient.delete(
        new PatientCommand.DeleteAddress(
            117L,
            5331L,
            191L,
            LocalDateTime.parse("2021-05-24T11:01:17")
        )
    );

    assertThat(patient)
        .returns(191L, person -> person.audit().changed().changedBy())
        .returns(LocalDateTime.parse("2021-05-24T11:01:17"), person -> person.audit().changed().changedOn());


    assertThat(patient.addresses())
        .satisfiesExactly(
            actual -> assertThat(actual)
                .returns(4861L, p -> p.getId().getLocatorUid())
        );

  }


  @Test
  void should_add_minimal_email_address() {

    Person actual = new Person(117L, "local-id-value");

    actual.add(
        new PatientCommand.AddEmailAddress(
            117L,
            5333L,
            Instant.parse("2017-05-16T11:13:19Z"),
            "AnEmail@email.com",
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30.00")
        )
    );

    assertThat(actual.emailAddresses())
        .satisfiesExactly(
            actualEmailLocator -> assertThat(actualEmailLocator)
                .returns(5333L, p -> p.getId().getLocatorUid())
                .returns("NET", EntityLocatorParticipation::getCd)
                .returns("H", EntityLocatorParticipation::getUseCd)
                .returns(Instant.parse("2017-05-16T11:13:19Z"), EntityLocatorParticipation::getAsOfDate)
                .extracting(TeleEntityLocatorParticipation::getLocator)
                .returns(5333L, TeleLocator::getId)
                .returns("AnEmail@email.com", TeleLocator::getEmailAddress)

        );

  }

  @Test
  void should_add_minimal_phone_number() {

    Person actual = new Person(117L, "local-id-value");

    actual.add(
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

    assertThat(actual)
        .returns(131L, person -> person.audit().changed().changedBy())
        .returns(LocalDateTime.parse("2020-03-03T10:15:30"), person -> person.audit().changed().changedOn());


    assertThat(actual.phoneNumbers())
        .satisfiesExactly(
            actualPhoneLocator -> assertThat(actualPhoneLocator)
                .returns(5347L, p -> p.getId().getLocatorUid())
                .returns(Instant.parse("2017-05-16T11:13:19Z"), EntityLocatorParticipation::getAsOfDate)
                .returns("CP", EntityLocatorParticipation::getCd)
                .returns("MC", EntityLocatorParticipation::getUseCd)
                .extracting(TeleEntityLocatorParticipation::getLocator)
                .returns(5347L, TeleLocator::getId)
                .returns("Phone Number", TeleLocator::getPhoneNbrTxt)
                .returns("Extension", TeleLocator::getExtensionTxt)
                .satisfies(
                    added -> assertThat(added.audit())
                        .satisfies(AuditAssertions.added(131L, "2020-03-03T10:15:30"))
                        .satisfies(AuditAssertions.changed(131L, "2020-03-03T10:15:30"))
                )

        );

  }

  @Test
  void should_add_phone() {
    Person patient = new Person(117L, "local-id-value");

    patient.add(
        new PatientCommand.AddPhone(
            117L,
            5347L,
            "type-value",
            "use-value",
            Instant.parse("2023-11-27T22:53:07Z"),
            "country-code",
            "number",
            "extension",
            "email",
            "url",
            "comment",
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    assertThat(patient).satisfies(
        added -> assertThat(added.audit())
            .satisfies(AuditAssertions.changed(131L, "2020-03-03T10:15:30"))
    );

    assertThat(patient.phoneNumbers())
        .satisfiesExactly(
            actual -> assertThat(actual)
                .returns(5347L, p -> p.getId().getLocatorUid())
                .returns("type-value", EntityLocatorParticipation::getCd)
                .returns("use-value", EntityLocatorParticipation::getUseCd)
                .returns(Instant.parse("2023-11-27T22:53:07Z"), EntityLocatorParticipation::getAsOfDate)
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
            5347L,
            "type-value",
            "use-value",
            Instant.parse("2023-11-27T22:53:07Z"),
            "country-code",
            "number",
            "extension",
            "email",
            "url",
            "comment",
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    patient.update(
        new PatientCommand.UpdatePhone(
            117L,
            5347L,
            "updated-type-value",
            "updated-use-value",
            Instant.parse("2023-11-27T22:53:07Z"),
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

    assertThat(patient)
        .returns(171L, person -> person.audit().changed().changedBy())
        .returns(LocalDateTime.parse("2023-07-01T13:17:00"), person -> person.audit().changed().changedOn());


    assertThat(patient.phoneNumbers())
        .satisfiesExactly(
            actual -> assertThat(actual)
                .isInstanceOf(TeleEntityLocatorParticipation.class)
                .asInstanceOf(InstanceOfAssertFactories.type(TeleEntityLocatorParticipation.class))
                .returns(5347L, p -> p.getId().getLocatorUid())
                .returns("updated-type-value", EntityLocatorParticipation::getCd)
                .returns("updated-use-value", EntityLocatorParticipation::getUseCd)
                .returns(Instant.parse("2023-11-27T22:53:07Z"), EntityLocatorParticipation::getAsOfDate)
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
            5347L,
            "type-value",
            "use-value",
            Instant.parse("2023-11-27T22:53:07Z"),
            "country-code",
            "number",
            "extension",
            "email",
            "url",
            "comment",
            131L,
            LocalDateTime.parse("2023-03-03T10:15:30")
        )
    );

    patient.add(
        new PatientCommand.AddPhone(
            117L,
            1567L,
            "type-value",
            "use-value",
            Instant.parse("2023-11-27T22:53:07Z"),
            "country-code",
            "number",
            "extension",
            "email",
            "url",
            "comment",
            171L,
            LocalDateTime.parse("2023-03-03T10:15:30")
        )
    );

    patient.delete(
        new PatientCommand.DeletePhone(
            117L,
            1567L,
            293L,
            LocalDateTime.parse("2023-03-03T10:15:30")
        )
    );

    assertThat(patient)
        .returns(293L, person -> person.audit().changed().changedBy())
        .returns(LocalDateTime.parse("2023-03-03T10:15:30"), person -> person.audit().changed().changedOn());


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
        .returns((short) 2, Person::getVersionCtrlNbr)
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
        LocalDateTime.parse("2019-03-03T10:15:30"));

    actual.update(command);

    assertThat(actual)
        .returns(12L, person -> person.audit().changed().changedBy())
        .returns(LocalDateTime.parse("2019-03-03T10:15:30"), person -> person.audit().changed().changedOn());

    assertThat(actual)
        .extracting(Person::getGeneralInformation)
        .returns(Instant.parse("2010-03-03T10:15:30.00Z"), GeneralInformation::asOf)
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
  void should_update_patient_mortality_when_patient_is_deceased() {

    Person patient = new Person(121L, "local-id-value");

    AddressIdentifierGenerator generator = () -> 1157L;

    patient.update(
        new PatientCommand.UpdateMortality(
            121L,
            Instant.parse("2023-06-01T03:21:00Z"),
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
        .returns(Instant.parse("2023-06-01T03:21:00Z"), Person::getAsOfDateMorbidity)
        .returns(Deceased.Y, Person::getDeceasedIndCd)
        .returns(Instant.parse("1987-11-17T00:00:00Z"), Person::getDeceasedTime)
        .returns(131L, person -> person.audit().changed().changedBy())
        .returns(LocalDateTime.parse("2019-03-03T10:15:30"), person -> person.audit().changed().changedOn());
  }

  @Test
  void should_update_patient_mortality_with_new_mortality_location_when_patient_is_deceased() {

    Person patient = new Person(121L, "local-id-value");

    AddressIdentifierGenerator generator = () -> 1157L;

    patient.update(
        new PatientCommand.UpdateMortality(
            121L,
            Instant.parse("2023-06-01T03:21:00Z"),
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
                .returns(131L, person -> person.audit().changed().changedBy())
                .returns(LocalDateTime.parse("2019-03-03T10:15:30"), person -> person.audit().changed().changedOn())
        )
        .satisfies(
            actual -> assertThat(actual.addresses())
                .satisfiesExactly(
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
            Instant.parse("2023-06-01T03:21:00Z"),
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
            Instant.parse("2023-06-01T03:21:00Z"),
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
        .returns(Instant.parse("2023-06-01T03:21:00Z"), Person::getAsOfDateMorbidity)
        .returns(Deceased.N, Person::getDeceasedIndCd)
        .returns(null, Person::getDeceasedTime)
        .satisfies(
            changed -> assertThat(changed)
                .extracting(Person::audit)
                .satisfies(AuditAssertions.changed(131L, "2020-03-03T10:15:30"))
        )
        .satisfies(
            actual -> assertThat(actual.addresses())
                .satisfiesExactly(
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
    ;
  }

  @Test
  void should_clear_patient_mortality_when_patient_is_not_known_to_be_deceased() {

    Person patient = new Person(121L, "local-id-value");

    AddressIdentifierGenerator generator = () -> 1157L;

    patient.update(
        new PatientCommand.UpdateMortality(
            121L,
            Instant.parse("2023-06-01T03:21:00Z"),
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
            Instant.parse("2023-06-01T03:21:00Z"),
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
        .returns(Instant.parse("2023-06-01T03:21:00Z"), Person::getAsOfDateMorbidity)
        .returns(Deceased.UNK, Person::getDeceasedIndCd)
        .returns(null, Person::getDeceasedTime)
        .satisfies(
            changed -> assertThat(changed)
                .extracting(Person::audit)
                .satisfies(AuditAssertions.changed(131L, "2019-03-03T10:15:30"))
        )
        .satisfies(
            actual -> assertThat(actual.addresses())
                .satisfiesExactly(
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
    ;
  }

  @Test
  void should_update_patient_mortality_with_changed_mortality_location_when_patient_is_deceased() {

    Person patient = new Person(121L, "local-id-value");

    AddressIdentifierGenerator generator = () -> 1157L;

    patient.update(
        new PatientCommand.UpdateMortality(
            121L,
            Instant.parse("2023-06-01T03:21:00Z"),
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
            Instant.parse("2023-06-21T03:21:00Z"),
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
        .returns(Instant.parse("2023-06-21T03:21:00Z"), Person::getAsOfDateMorbidity)
        .returns(Instant.parse("1986-11-16T00:00:00Z"), Person::getDeceasedTime)
        .satisfies(
            changed -> assertThat(changed)
                .extracting(Person::audit)
                .satisfies(AuditAssertions.changed(171L, "2019-03-03T10:15:30"))
        )
        .satisfies(
            actual -> assertThat(actual.addresses())
                .satisfiesExactly(
                    address -> assertThat(address)
                        .returns("U", PostalEntityLocatorParticipation::getCd)
                        .returns("DTH", PostalEntityLocatorParticipation::getUseCd)
                        .extracting(PostalEntityLocatorParticipation::getLocator)
                        .returns("changed", PostalLocator::getCityDescTxt)
                )
        )
    ;
  }

  @Test
  void should_update_patient_gender() {

    Person patient = new Person(121L, "local-id-value");

    patient.update(
        new PatientCommand.UpdateGender(
            121L,
            Instant.parse("2023-06-01T03:21:00Z"),
            Gender.U.value(),
            "gender-unknown-reason",
            "gender-preferred",
            "gender-additional",
            131L,
            LocalDateTime.parse("2019-03-03T10:15:30")
        )
    );

    assertThat(patient)
        .returns(Instant.parse("2023-06-01T03:21:00Z"), Person::getAsOfDateSex)
        .returns(Gender.U, Person::getCurrSexCd)
        .returns("gender-unknown-reason", Person::getSexUnkReasonCd)
        .returns("gender-preferred", Person::getPreferredGenderCd)
        .returns("gender-additional", Person::getAdditionalGenderCd)
        .satisfies(
            changed -> assertThat(changed)
                .returns(131L, person -> person.audit().changed().changedBy())
                .returns(LocalDateTime.parse("2019-03-03T10:15:30"), person -> person.audit().changed().changedOn())
        )
    ;

  }

  @Test
  void should_update_patient_birth() {
    Person patient = new Person(121L, "local-id-value");

    AddressIdentifierGenerator generator = () -> 1157L;

    patient.update(
        new PatientCommand.UpdateBirth(
            121L,
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
            LocalDateTime.parse("2019-03-03T10:15:30")
        ),
        generator
    );

    assertThat(patient)
        .returns(Instant.parse("2023-06-01T03:21:00Z"), Person::getAsOfDateSex)
        .returns(LocalDateTime.parse("1949-10-15T00:00:00"), Person::getBirthTime)
        .returns(Gender.U, Person::getBirthGenderCd)
        .returns((short) 17, Person::getBirthOrderNbr)
        .satisfies(
            changed -> assertThat(changed)
                .returns(131L, person -> person.audit().changed().changedBy())
                .returns(LocalDateTime.parse("2019-03-03T10:15:30"), person -> person.audit().changed().changedOn())
        )
    ;
  }

  @Test
  void should_update_patient_birth_with_location() {
    Person patient = new Person(121L, "local-id-value");

    AddressIdentifierGenerator generator = () -> 1157L;

    patient.update(
        new PatientCommand.UpdateBirth(
            121L,
            Instant.parse("2023-06-01T03:21:00Z"),
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
            actual -> assertThat(actual.addresses())
                .satisfiesExactly(
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
        .satisfies(
            changed -> assertThat(changed)
                .returns(131L, person -> person.audit().changed().changedBy())
                .returns(LocalDateTime.parse("2019-03-03T10:15:30"), person -> person.audit().changed().changedOn())
        )
    ;
  }

  @Test
  void should_update_patient_administrative() {

    Person patient = new Person(121L, "local-id-value");

    patient.update(
        new PatientCommand.UpdateAdministrativeInfo(
            121L,
            Instant.parse("2023-06-01T03:21:00Z"),
            "comments",
            131L,
            LocalDateTime.parse("2019-03-03T10:15:30")
        )
    );

    assertThat(patient)
        .returns(Instant.parse("2023-06-01T03:21:00Z"), Person::getAsOfDateAdmin)
        .returns("comments", Person::getDescription)
        .extracting(Person::audit)
        .satisfies(AuditAssertions.changed(131L, "2019-03-03T10:15:30"));
  }
}
