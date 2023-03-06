package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.address.City;
import gov.cdc.nbs.address.Country;
import gov.cdc.nbs.address.County;
import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.message.PatientInput;
import gov.cdc.nbs.message.enums.Deceased;
import gov.cdc.nbs.message.enums.Gender;
import gov.cdc.nbs.message.enums.Suffix;
import gov.cdc.nbs.patient.PatientCommand;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class PersonTest {

    @Test
    @SuppressWarnings("squid:S5961")
        // Allow more than 25 assertions
    void should_create_new_person_when_patient_added() {

        PatientCommand.AddPatient request = new PatientCommand.AddPatient(
                117L,
                "patient-local-id",
                "ssn-value",
                Instant.parse("2000-09-03T15:17:39.00Z"),
                Gender.M,
                Gender.F,
                Deceased.N,
                null,
                "Marital Status",
                "EthCode",
                Instant.parse("2019-03-03T10:15:30.00Z"),
                "comments",
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")
        );

        Person actual = new Person(request);

        assertThat(actual.getId()).isEqualTo(117L);
        assertThat(actual.getLocalId()).isEqualTo("patient-local-id");

        assertThat(actual.getVersionCtrlNbr()).isEqualTo((short) 1);
        assertThat(actual.getCd()).isEqualTo("PAT");
        assertThat(actual.getElectronicInd()).isEqualTo('N');
        assertThat(actual.getEdxInd()).isEqualTo("Y");
        assertThat(actual.getDedupMatchInd()).isEqualTo('F');

        assertThat(actual.getAddUserId()).isEqualTo(131L);
        assertThat(actual.getAddTime()).isEqualTo("2020-03-03T10:15:30.00Z");
        assertThat(actual.getLastChgUserId()).isEqualTo(131L);
        assertThat(actual.getLastChgTime()).isEqualTo("2020-03-03T10:15:30.00Z");

        assertThat(actual.getStatusCd()).isEqualTo('A');
        assertThat(actual.getStatusTime()).isEqualTo("2020-03-03T10:15:30.00Z");
     
        assertThat(actual.getRecordStatusCd()).isEqualTo(RecordStatus.ACTIVE);
        assertThat(actual.getRecordStatusTime()).isEqualTo("2020-03-03T10:15:30.00Z");

        assertThat(actual.getSsn()).isEqualTo("ssn-value");
        assertThat(actual.getBirthTime()).isEqualTo("2000-09-03T15:17:39.00Z");
        assertThat(actual.getBirthGenderCd()).isEqualTo(Gender.M);
        assertThat(actual.getCurrSexCd()).isEqualTo(Gender.F);
        assertThat(actual.getDeceasedIndCd()).isEqualTo(Deceased.N);
        assertThat(actual.getMaritalStatusCd()).isEqualTo("Marital Status");
        assertThat(actual.getEthnicGroupInd()).isEqualTo("EthCode");
        assertThat(actual.getAsOfDateGeneral()).isEqualTo("2019-03-03T10:15:30.00Z");
        assertThat(actual.getAsOfDateAdmin()).isEqualTo("2019-03-03T10:15:30.00Z");
        assertThat(actual.getAsOfDateSex()).isEqualTo("2019-03-03T10:15:30.00Z");
        assertThat(actual.getDescription()).isEqualTo("comments");

        assertThat(actual.getPersonParentUid())
                .as("Master Patient Record set itself as parent")
                .isSameAs(actual);

    }

    @Test
    void should_add_race() {

        Person actual = new Person(117L, "local-id-value");
        actual.add(
                new PatientCommand.AddRace(
                        117L,
                        "race-code-value",
                        131L,
                        Instant.parse("2020-03-03T10:15:30.00Z")
                )
        );

        assertThat(actual.getRaces()).satisfiesExactly(
                actual_race -> assertThat(actual_race)
                        .returns("race-code-value", PersonRace::getRaceCd)
        );

    }

    @Test
    void should_add_primary_name() {

        PatientInput patient = new PatientInput();

        patient.setNames(
                Arrays.asList(
                        new PatientInput.Name(
                                "First",
                                "Middle",
                                "Last",
                                Suffix.JR,
                                PatientInput.NameUseCd.L
                        ),
                        new PatientInput.Name(
                                "Second",
                                "SecondMiddle",
                                "SecondLast",
                                Suffix.SR,
                                PatientInput.NameUseCd.AL
                        )
                )
        );

        Person actual = new Person(117L, "local-id-value");

        actual.add(
                new PatientCommand.AddName(
                        117L,
                        "First",
                        "Middle",
                        "Last",
                        Suffix.JR,
                        PatientInput.NameUseCd.L,
                        131L,
                        Instant.parse("2020-03-03T10:15:30.00Z")
                )
        );

        assertThat(actual.getFirstNm()).isEqualTo("First");
        assertThat(actual.getMiddleNm()).isEqualTo("Middle");
        assertThat(actual.getLastNm()).isEqualTo("Last");
        assertThat(actual.getNmSuffix()).isEqualTo(Suffix.JR);

        assertThat(actual.getNames()).satisfiesExactly(
                actual_primary -> assertThat(actual_primary)
                        .returns("First", PersonName::getFirstNm)
                        .returns("Middle", PersonName::getMiddleNm)
                        .returns("Last", PersonName::getLastNm)
                        .returns(Suffix.JR, PersonName::getNmSuffix)
                        .returns("L", PersonName::getNmUseCd)
        );

    }

    @Test
    void should_add_secondary_name() {

        Person actual = new Person(117L, "local-id-value");

        actual.add(
                new PatientCommand.AddName(
                        117L,
                        "First",
                        "Middle",
                        "Last",
                        Suffix.JR,
                        PatientInput.NameUseCd.L,
                        131L,
                        Instant.parse("2020-03-03T10:15:30.00Z")
                )
        );

        actual.add(
                new PatientCommand.AddName(
                        117L,
                        "Second",
                        "SecondMiddle",
                        "SecondLast",
                        Suffix.SR,
                        PatientInput.NameUseCd.AL,
                        131L,
                        Instant.parse("2020-03-03T10:15:30.00Z")
                )
        );

        assertThat(actual.getFirstNm()).isEqualTo("First");
        assertThat(actual.getMiddleNm()).isEqualTo("Middle");
        assertThat(actual.getLastNm()).isEqualTo("Last");
        assertThat(actual.getNmSuffix()).isEqualTo(Suffix.JR);

        assertThat(actual.getNames()).satisfiesExactly(
                actual_primary -> assertThat(actual_primary)
                        .returns("First", PersonName::getFirstNm)
                        .returns("Middle", PersonName::getMiddleNm)
                        .returns("Last", PersonName::getLastNm)
                        .returns(Suffix.JR, PersonName::getNmSuffix)
                        .returns("L", PersonName::getNmUseCd),
                actual_alias -> assertThat(actual_alias)
                        .returns("Second", PersonName::getFirstNm)
                        .returns("SecondMiddle", PersonName::getMiddleNm)
                        .returns("SecondLast", PersonName::getLastNm)
                        .returns(Suffix.SR, PersonName::getNmSuffix)
                        .returns("AL", PersonName::getNmUseCd)
        );

    }


    @Test
    void should_add_postal_address() {

        Person actual = new Person(117L, "local-id-value");

        actual.add(
                new PatientCommand.AddAddress(
                        117L,
                        4861L,
                        "SA1",
                        "SA2",
                        new City("city-code", "city-description"),
                        "State",
                        "Zip",
                        new County("county-code", "county-description"),
                        new Country("country-code", "country-description"),
                        "Census Tract",
                        131L,
                        Instant.parse("2020-03-03T10:15:30.00Z")
                )
        );

        assertThat(actual.getNbsEntity().getEntityLocatorParticipations())
                .satisfiesExactly(
                        actual_postal_locator -> assertThat(actual_postal_locator)
                                .isInstanceOf(PostalEntityLocatorParticipation.class)
                                .asInstanceOf(InstanceOfAssertFactories.type(PostalEntityLocatorParticipation.class))
                                .returns("H", EntityLocatorParticipation::getCd)
                                .extracting(PostalEntityLocatorParticipation::getLocator)
                                .returns(4861L, PostalLocator::getId)
                                .returns("SA1", PostalLocator::getStreetAddr1)
                                .returns("SA2", PostalLocator::getStreetAddr2)
                                //  should this be getCityDescTxt or getCityCd?
                                .returns("city-code", PostalLocator::getCityCd)
                                .returns("city-description", PostalLocator::getCityDescTxt)
                                .returns("State", PostalLocator::getStateCd)
                                .returns("county-code", PostalLocator::getCntyCd)
                                .returns("county-description", PostalLocator::getCntyDescTxt)
                                .returns("Zip", PostalLocator::getZipCd)
                                .returns("country-code", PostalLocator::getCntryCd)
                                .returns("country-description", PostalLocator::getCntryDescTxt)

                );


    }

    @Test
    void should_add_email_address() {

        Person actual = new Person(117L, "local-id-value");

        actual.add(
                new PatientCommand.AddEmailAddress(
                        117L,
                        5333L,
                        "AnEmail@email.com",
                        131L,
                        Instant.parse("2020-03-03T10:15:30.00Z")
                )
        );

        assertThat(actual.getNbsEntity().getEntityLocatorParticipations())
                .satisfiesExactly(
                        actual_email_locator ->
                                assertThat(actual_email_locator)
                                        .isInstanceOf(TeleEntityLocatorParticipation.class)
                                        .asInstanceOf(
                                                InstanceOfAssertFactories.type(TeleEntityLocatorParticipation.class))
                                        .returns("NET", EntityLocatorParticipation::getCd)
                                        .extracting(TeleEntityLocatorParticipation::getLocator)
                                        .returns(5333L, TeleLocator::getId)
                                        .returns("AnEmail@email.com", TeleLocator::getEmailAddress)

                );


    }

    @Test
    void should_add_cell_phone_number() {

        Person actual = new Person(117L, "local-id-value");

        actual.add(
                new PatientCommand.AddPhoneNumber(
                        117L,
                        5347L,
                        "Phone Number",
                        "Extension",
                        PatientInput.PhoneType.CELL,
                        131L,
                        Instant.parse("2020-03-03T10:15:30.00Z")
                )
        );

        assertThat(actual.getNbsEntity().getEntityLocatorParticipations())
                .satisfiesExactly(
                        actual_phone_locator ->
                                assertThat(actual_phone_locator)
                                        .isInstanceOf(TeleEntityLocatorParticipation.class)
                                        .asInstanceOf(
                                                InstanceOfAssertFactories.type(TeleEntityLocatorParticipation.class))
                                        .returns("CP", EntityLocatorParticipation::getCd)
                                        .extracting(TeleEntityLocatorParticipation::getLocator)
                                        .returns(5347L, TeleLocator::getId)
                                        .returns("Phone Number", TeleLocator::getPhoneNbrTxt)
                                        .returns("Extension", TeleLocator::getExtensionTxt)
                );

    }

}
