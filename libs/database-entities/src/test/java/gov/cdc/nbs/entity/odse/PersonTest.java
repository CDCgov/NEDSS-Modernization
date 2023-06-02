package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.address.City;
import gov.cdc.nbs.address.Country;
import gov.cdc.nbs.address.County;
import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.message.enums.Deceased;
import gov.cdc.nbs.message.enums.Gender;
import gov.cdc.nbs.message.enums.Suffix;
import gov.cdc.nbs.message.patient.input.PatientInput;
import gov.cdc.nbs.patient.PatientAssociationCountFinder;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.PatientCommand.AddMortalityLocator;
import gov.cdc.nbs.patient.PatientCommand.UpdateSexAndBirthInfo;
import gov.cdc.nbs.patient.PatientHasAssociatedEventsException;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
            Instant.parse("2019-03-03T10:15:30.00Z"),
            "comments",
            "HIV-Case",
            131L,
            Instant.parse("2020-03-03T10:15:30.00Z"));

        Person actual = new Person(request);

        assertThat(actual.getId()).isEqualTo(117L);
        assertThat(actual.getLocalId()).isEqualTo("patient-local-id");

        assertThat(actual.getVersionCtrlNbr()).isEqualTo((short) 1);
        assertThat(actual.getCd()).isEqualTo("PAT");
        assertThat(actual.getElectronicInd()).isEqualTo('N');
        assertThat(actual.getEdxInd()).isEqualTo("Y");

        assertThat(actual.getAddUserId()).isEqualTo(131L);
        assertThat(actual.getAddTime()).isEqualTo("2020-03-03T10:15:30.00Z");
        assertThat(actual.getLastChgUserId()).isEqualTo(131L);
        assertThat(actual.getLastChgTime()).isEqualTo("2020-03-03T10:15:30.00Z");

        assertThat(actual.getStatusCd()).isEqualTo('A');
        assertThat(actual.getStatusTime()).isEqualTo("2020-03-03T10:15:30.00Z");

        assertThat(actual.getRecordStatusCd()).isEqualTo(RecordStatus.ACTIVE);
        assertThat(actual.getRecordStatusTime()).isEqualTo("2020-03-03T10:15:30.00Z");

        assertThat(LocalDate.ofInstant(actual.getBirthTime(), ZoneId.systemDefault())).isEqualTo("2000-09-03");
        assertThat(actual.getBirthGenderCd()).isEqualTo(Gender.M);
        assertThat(actual.getCurrSexCd()).isEqualTo(Gender.F);
        assertThat(actual.getDeceasedIndCd()).isEqualTo(Deceased.N);
        assertThat(actual.getMaritalStatusCd()).isEqualTo("Marital Status");
        assertThat(actual.getEthnicGroupInd()).isEqualTo("EthCode");
        assertThat(actual.getAsOfDateGeneral()).isEqualTo("2019-03-03T10:15:30.00Z");
        assertThat(actual.getAsOfDateAdmin()).isEqualTo("2019-03-03T10:15:30.00Z");
        assertThat(actual.getAsOfDateSex()).isEqualTo("2019-03-03T10:15:30.00Z");
        assertThat(actual.getDescription()).isEqualTo("comments");

        assertThat(actual.getEharsId()).isEqualTo("HIV-Case");

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
                Instant.parse("2022-05-12T11:15:17Z"),
                "race-code-value",
                "race-category-value",
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")));

        assertThat(actual.getRaces()).satisfiesExactly(
            actual_race -> assertThat(actual_race)
                .returns("race-code-value", PersonRace::getRaceCd)
                .returns("race-category-value", PersonRace::getRaceCategoryCd)
                .extracting(PersonRace::getAsOfDate).isEqualTo(Instant.parse("2022-05-12T11:15:17Z")));

    }


    @Test
    void should_start_name_sequence_at_one() {
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

        assertThat(actual.getNames()).satisfiesExactly(
            actual_primary -> assertThat(actual_primary)
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

        actual.add(
            new PatientCommand.AddName(
                117L,
                "First",
                "Middle",
                "Last",
                Suffix.JR,
                PatientInput.NameUseCd.L,
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")));

        actual.add(
            new PatientCommand.AddName(
                117L,
                "Second",
                "SecondMiddle",
                "SecondLast",
                Suffix.SR,
                PatientInput.NameUseCd.AL,
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")));

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
                .extracting(PersonName::getId)
                .returns(117L, PersonNameId::getPersonUid)
                .returns((short) 1, PersonNameId::getPersonNameSeq),
            actual_alias -> assertThat(actual_alias)
                .returns("Second", PersonName::getFirstNm)
                .returns("SecondMiddle", PersonName::getMiddleNm)
                .returns("SecondLast", PersonName::getLastNm)
                .returns(Suffix.SR, PersonName::getNmSuffix)
                .returns("AL", PersonName::getNmUseCd)
                .extracting(PersonName::getId)
                .returns(117L, PersonNameId::getPersonUid)
                .returns((short) 2, PersonNameId::getPersonNameSeq)
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
                Instant.parse("2020-03-03T10:15:30.00Z")));

        assertThat(actual.getNbsEntity().getEntityLocatorParticipations())
            .satisfiesExactly(
                actual_postal_locator -> assertThat(actual_postal_locator)
                    .isInstanceOf(PostalEntityLocatorParticipation.class)
                    .asInstanceOf(InstanceOfAssertFactories.type(PostalEntityLocatorParticipation.class))
                    .returns(4861L, p -> p.getId().getLocatorUid())
                    .returns("H", EntityLocatorParticipation::getCd)
                    .extracting(PostalEntityLocatorParticipation::getLocator)
                    .returns(4861L, PostalLocator::getId)
                    .returns("SA1", PostalLocator::getStreetAddr1)
                    .returns("SA2", PostalLocator::getStreetAddr2)
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
                Instant.parse("2020-03-03T10:15:30.00Z")));

        assertThat(actual.getNbsEntity().getEntityLocatorParticipations())
            .satisfiesExactly(
                actual_email_locator -> assertThat(actual_email_locator)
                    .isInstanceOf(TeleEntityLocatorParticipation.class)
                    .asInstanceOf(InstanceOfAssertFactories.type(TeleEntityLocatorParticipation.class))
                    .returns(5333L, p -> p.getId().getLocatorUid())
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
                "CP",
                "MC",
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")));

        assertThat(actual.getNbsEntity().getEntityLocatorParticipations())
            .satisfiesExactly(
                actual_phone_locator -> assertThat(actual_phone_locator)
                    .isInstanceOf(TeleEntityLocatorParticipation.class)
                    .asInstanceOf(InstanceOfAssertFactories.type(TeleEntityLocatorParticipation.class))
                    .returns(5347L, p -> p.getId().getLocatorUid())
                    .returns("CP", EntityLocatorParticipation::getCd)
                    .extracting(TeleEntityLocatorParticipation::getLocator)
                    .returns(5347L, TeleLocator::getId)
                    .returns("Phone Number", TeleLocator::getPhoneNbrTxt)
                    .returns("Extension", TeleLocator::getExtensionTxt));

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
                Instant.parse("2020-03-03T10:15:30.00Z")
            ),
            finder
        );

        assertThat(actual.getRecordStatusCd()).isEqualTo(RecordStatus.LOG_DEL);
        assertThat(actual.getRecordStatusTime()).isEqualTo("2020-03-03T10:15:30.00Z");
        assertThat(actual.getVersionCtrlNbr()).isEqualTo((short) 2);
        assertThat(actual.getLastChgUserId()).isEqualTo((short) 131L);
        assertThat(actual.getLastChgTime()).isEqualTo("2020-03-03T10:15:30.00Z");
    }

    @Test
    void should_not_allow_deletion_of_patient_with_associations() {
        PatientAssociationCountFinder finder = mock(PatientAssociationCountFinder.class);

        when(finder.count(anyLong())).thenReturn(1L);

        Person actual = new Person(117L, "local-id-value");

        assertThatThrownBy(() ->
            actual.delete(
                new PatientCommand.Delete(
                    117L,
                    131L,
                    Instant.parse("2020-03-03T10:15:30.00Z")
                ),
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
            (short) 1,
            (short) 2,
            "occupation code",
            "education level",
            "prim language",
            "speaks english",
            "eharsId",
            12L,
            Instant.parse("2019-03-03T10:15:30.00Z"));

        actual.update(command);

        assertEquals(command.asOf().getEpochSecond(), actual.getAsOfDateGeneral().getEpochSecond());
        assertEquals(command.maritalStatus(), actual.getMaritalStatusCd());
        assertEquals(command.mothersMaidenName(), actual.getMothersMaidenNm());
        assertEquals(command.adultsInHouseNumber(), actual.getAdultsInHouseNbr());
        assertEquals(command.childrenInHouseNumber(), actual.getChildrenInHouseNbr());
        assertEquals(command.occupationCode(), actual.getOccupationCd());
        assertEquals(command.educationLevelCode(), actual.getEducationLevelCd());
        assertEquals(command.primaryLanguageCode(), actual.getPrimLangCd());
        assertEquals(command.speaksEnglishCode(), actual.getSpeaksEnglishCd());
        assertEquals(command.eharsId(), actual.getEharsId());
        assertThat(actual.getAsOfDateGeneral()).isEqualTo("2010-03-03T10:15:30.00Z");
        assertThat(actual.getLastChgTime()).isEqualTo("2019-03-03T10:15:30.00Z");
        assertEquals(Long.valueOf(command.requester()), actual.getLastChgUserId());
        assertEquals(Short.valueOf((short) 2), actual.getVersionCtrlNbr());
        assertEquals(Long.valueOf(command.requester()), actual.getLastChgUserId());
    }

    @Test
    void should_set_birth_and_sex_fields() {
        final String asOf = "2012-03-03T10:15:30.00Z";
        final String dob = "2012-03-03";
        final String ageReportedTime = "1990-04-05T10:15:30.00Z";
        final String requestedOn = "2020-04-05T10:15:30.00Z";
        Person actual = new Person(121L, "local-id-value");
        PatientCommand.UpdateSexAndBirthInfo command = new UpdateSexAndBirthInfo(
            123L,
            Instant.parse(asOf),
            LocalDate.parse(dob),
            Gender.F,
            Gender.M,
            "additional gender info",
            "trans info",
            "birth city",
            "birth Cntry",
            "birth state",
            (short) 1,
            "multiple birth",
            "sex unknown",
            "current age",
            Instant.parse(ageReportedTime),
            321L,
            Instant.parse(requestedOn));

        actual.update(command);

        assertEquals(command.birthGender(), actual.getBirthGenderCd());
        assertEquals(command.currentGender(), actual.getCurrSexCd());
        assertThat(LocalDate.ofInstant(actual.getBirthTime(), ZoneId.systemDefault())).isEqualTo(dob);
        assertThat(actual.getAsOfDateSex()).isEqualTo(asOf);
        assertEquals(command.currentAge(), actual.getAgeReported());
        assertThat(actual.getAgeReportedTime()).isEqualTo(ageReportedTime);
        assertEquals(command.birthCity(), actual.getBirthCityCd());
        assertEquals(command.birthCntry(), actual.getBirthCntryCd());
        assertEquals(command.birthState(), actual.getBirthStateCd());
        assertEquals(command.birthOrderNbr(), actual.getBirthOrderNbr());
        assertEquals(command.multipleBirth(), actual.getMultipleBirthInd());
        assertEquals(command.sexUnknown(), actual.getSexUnkReasonCd());
        assertEquals(command.additionalGender(), actual.getAdditionalGenderCd());
        assertEquals(command.transGenderInfo(), actual.getPreferredGenderCd());
        assertEquals((short) 2, actual.getVersionCtrlNbr());
        assertThat(actual.getLastChgTime()).isEqualTo(requestedOn);
    }

    @Test
    void should_add_mortality_fields() {
        final String asOf = "2012-03-03T10:15:30.00Z";
        final String deceasedTime = "2012-03-03T10:15:30.00Z";
        final String requestedOn = "2020-04-05T10:15:30.00Z";
        Person actual = new Person(121L, "local-id-value");
        PatientCommand.AddMortalityLocator command = new AddMortalityLocator(
            121L,
            987L,
            Instant.parse(asOf),
            Deceased.FALSE,
            Instant.parse(deceasedTime),
            "city of death",
            "state of death",
            "county of death",
            "country of death",
            456L,
            Instant.parse(requestedOn));

        actual.add(command);
        var participation = actual.getNbsEntity().getEntityLocatorParticipations().get(0);
        // validate entityLocatorParticipation
        assertEquals(Long.valueOf(121L), participation.getId().getEntityUid());
        assertEquals(Long.valueOf(987L), participation.getId().getLocatorUid());
        assertThat(participation.getLastChgTime()).isEqualTo(requestedOn);
        assertThat(participation.getAddTime()).isEqualTo(requestedOn);
        assertEquals(Long.valueOf(456L), participation.getAddUserId());
        assertEquals(Long.valueOf(456L), participation.getLastChgUserId());
        assertThat(participation.getAsOfDate()).isEqualTo(asOf);
        assertEquals(Short.valueOf((short) 1), participation.getVersionCtrlNbr());

        // validate locator
        var locator = (PostalLocator) participation.getLocator();
        assertEquals(Long.valueOf(987L), locator.getId());
        assertEquals(command.cityOfDeath(), locator.getCityDescTxt());
        assertEquals(command.countryOfDeath(), locator.getCntryCd());
        assertEquals(command.countyOfDeath(), locator.getCntyCd());
        assertEquals(command.stateOfDeath(), locator.getStateCd());
        assertThat(locator.getLastChgTime()).isEqualTo(requestedOn);
        assertEquals(Long.valueOf(command.requester()), locator.getLastChgUserId());
    }

    @Test
    void should_not_allow_two_mortality_locators() {
        final String asOf = "2012-03-03T10:15:30.00Z";
        final String deceasedTime = "2012-03-03T10:15:30.00Z";
        final String requestedOn = "2020-04-05T10:15:30.00Z";
        Person actual = new Person(121L, "local-id-value");
        PatientCommand.AddMortalityLocator command = new AddMortalityLocator(
            121L,
            987L,
            Instant.parse(asOf),
            Deceased.FALSE,
            Instant.parse(deceasedTime),
            "city of death",
            "state of death",
            "county of death",
            "country of death",
            456L,
            Instant.parse(requestedOn));
        actual.add(command);
        UnsupportedOperationException ex = null;
        try {
            actual.add(command);
            // should not reach this assertion
            assertTrue(false);
        } catch (UnsupportedOperationException e) {
            ex = e;
        }
        assertNotNull(ex);
    }

}
