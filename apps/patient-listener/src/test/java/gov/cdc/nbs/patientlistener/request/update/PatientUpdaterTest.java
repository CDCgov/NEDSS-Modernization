package gov.cdc.nbs.patientlistener.request.update;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.odse.EntityId;
import gov.cdc.nbs.entity.odse.EntityIdId;
import gov.cdc.nbs.entity.odse.EntityLocatorParticipationId;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PersonName;
import gov.cdc.nbs.entity.odse.PersonNameId;
import gov.cdc.nbs.entity.odse.PersonRace;
import gov.cdc.nbs.entity.odse.PostalEntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.PostalLocator;
import gov.cdc.nbs.entity.odse.TeleEntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.TeleLocator;
import gov.cdc.nbs.message.enums.Deceased;
import gov.cdc.nbs.message.enums.Gender;
import gov.cdc.nbs.message.enums.Suffix;
import gov.cdc.nbs.message.patient.event.AddAddressData;
import gov.cdc.nbs.message.patient.event.AddEmailData;
import gov.cdc.nbs.message.patient.event.AddIdentificationData;
import gov.cdc.nbs.message.patient.event.AddNameData;
import gov.cdc.nbs.message.patient.event.AddPhoneData;
import gov.cdc.nbs.message.patient.event.AddRaceData;
import gov.cdc.nbs.message.patient.event.DeleteAddressData;
import gov.cdc.nbs.message.patient.event.DeleteEmailData;
import gov.cdc.nbs.message.patient.event.DeleteIdentificationData;
import gov.cdc.nbs.message.patient.event.DeleteMortalityData;
import gov.cdc.nbs.message.patient.event.DeleteNameData;
import gov.cdc.nbs.message.patient.event.DeletePhoneData;
import gov.cdc.nbs.message.patient.event.DeleteRaceData;
import gov.cdc.nbs.message.patient.event.UpdateAddressData;
import gov.cdc.nbs.message.patient.event.UpdateAdministrativeData;
import gov.cdc.nbs.message.patient.event.UpdateEmailData;
import gov.cdc.nbs.message.patient.event.UpdateEthnicityData;
import gov.cdc.nbs.message.patient.event.UpdateGeneralInfoData;
import gov.cdc.nbs.message.patient.event.UpdateIdentificationData;
import gov.cdc.nbs.message.patient.event.UpdateMortalityData;
import gov.cdc.nbs.message.patient.event.UpdateNameData;
import gov.cdc.nbs.message.patient.event.UpdatePhoneData;
import gov.cdc.nbs.message.patient.event.UpdateRaceData;
import gov.cdc.nbs.message.patient.event.UpdateSexAndBirthData;
import gov.cdc.nbs.message.patient.input.PatientInput.NameUseCd;
import gov.cdc.nbs.message.patient.input.PatientInput.PhoneType;
import gov.cdc.nbs.patient.IdGeneratorService;
import gov.cdc.nbs.patient.IdGeneratorService.GeneratedId;
import gov.cdc.nbs.repository.PersonRepository;

class PatientUpdaterTest {

    @Mock
    private IdGeneratorService idGenerator;
    @Mock
    private PersonRepository personRepository;

    @Captor
    private ArgumentCaptor<Person> personCaptor;

    @InjectMocks
    private PatientUpdater patientUpdater;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should_update_general_info() {
        var data = getGeneralInfoData();
        var person = new Person(123L, "localId");
        patientUpdater.update(person, data);

        verify(personRepository).save(personCaptor.capture());
        var savedPerson = personCaptor.getValue();

        var now = Instant.now();

        assertEquals(data.asOf().getEpochSecond(), savedPerson.getAsOfDateGeneral().getEpochSecond());
        assertEquals(data.maritalStatus(), savedPerson.getMaritalStatusCd());
        assertEquals(data.mothersMaidenName(), savedPerson.getMothersMaidenNm());
        assertEquals(data.adultsInHouseNumber(), savedPerson.getAdultsInHouseNbr());
        assertEquals(data.childrenInHouseNumber(), savedPerson.getChildrenInHouseNbr());
        assertEquals(data.occupationCode(), savedPerson.getOccupationCd());
        assertEquals(data.educationLevelCode(), savedPerson.getEducationLevelCd());
        assertEquals(data.primaryLanguageCode(), savedPerson.getPrimLangCd());
        assertEquals(data.speaksEnglishCode(), savedPerson.getSpeaksEnglishCd());
        assertEquals(data.eharsId(), savedPerson.getEharsId());
        assertEquals(Long.valueOf(data.updatedBy()), savedPerson.getLastChgUserId());
        assertEquals(Short.valueOf((short) 2), savedPerson.getVersionCtrlNbr());
        assertEquals(Long.valueOf(data.updatedBy()), savedPerson.getLastChgUserId());
        assertTrue(savedPerson.getLastChgTime().until(now, ChronoUnit.SECONDS) < 5);
    }

    private UpdateGeneralInfoData getGeneralInfoData() {
        return new UpdateGeneralInfoData(123L,
                "RequestId",
                321L,
                Instant.now(),
                "marital status",
                "mothers maiden name",
                (short) 1,
                (short) 2,
                "occupation code",
                "education level",
                "prim language",
                "speaks english",
                "eharsId");
    }

    @Test
    @SuppressWarnings("squid:S5961")
    // Allow more than 25 assertions
    void should_update_mortality_info_new_locator() {
        when(idGenerator.getNextValidId(Mockito.any())).thenReturn(new GeneratedId(999L,"prefix","suffix"));

        var data = getMortalityData();
        var person = new Person(123L, "localId");

        patientUpdater.update(person, data);

        verify(personRepository).save(personCaptor.capture());
        var postalParticipation = (PostalEntityLocatorParticipation) personCaptor.getValue()
                .getNbsEntity()
                .getEntityLocatorParticipations()
                .get(0);

        // validate entityLocatorParticipation
        var now = Instant.now();
        assertEquals(Long.valueOf(123L) ,postalParticipation.getId().getEntityUid());
        assertEquals(Long.valueOf(999L) ,postalParticipation.getId().getLocatorUid());
        assertEquals(Long.valueOf(data.updatedBy()), postalParticipation.getAddUserId());
        assertTrue(postalParticipation.getAddTime().until(now, ChronoUnit.SECONDS) < 5);
        assertTrue(postalParticipation.getLastChgTime().until(now, ChronoUnit.SECONDS) < 5);
        assertEquals(Long.valueOf(data.updatedBy()), postalParticipation.getLastChgUserId());
        assertEquals(RecordStatus.ACTIVE.toString(), postalParticipation.getRecordStatusCd());
        assertTrue(postalParticipation.getRecordStatusTime().until(now, ChronoUnit.SECONDS) < 5);
        assertEquals(Character.valueOf('A'), postalParticipation.getStatusCd());
        assertTrue(postalParticipation.getStatusTime().until(now, ChronoUnit.SECONDS) < 5);
        assertEquals(data.asOf().getEpochSecond(), postalParticipation.getAsOfDate().getEpochSecond());
        assertEquals(Short.valueOf((short)1), postalParticipation.getVersionCtrlNbr());
        assertEquals("U", postalParticipation.getCd());
        assertEquals("DTH", postalParticipation.getUseCd());

        // validate locator
        var locator = postalParticipation.getLocator();
        assertEquals(Long.valueOf(999L), locator.getId());
        assertEquals(data.cityOfDeath(), locator.getCityDescTxt());
        assertEquals(data.countryOfDeath(), locator.getCntryCd());
        assertEquals(data.countyOfDeath(), locator.getCntyCd());
        assertEquals(data.stateOfDeath(), locator.getStateCd());
        assertEquals(Long.valueOf(data.updatedBy()), locator.getAddUserId());
        assertTrue(locator.getAddTime().until(now, ChronoUnit.SECONDS) < 5);
        assertTrue(locator.getLastChgTime().until(now, ChronoUnit.SECONDS) < 5);
        assertEquals(Long.valueOf(data.updatedBy()), locator.getLastChgUserId());
        assertEquals(RecordStatus.ACTIVE.toString(), locator.getRecordStatusCd());
        assertTrue(locator.getRecordStatusTime().until(now, ChronoUnit.SECONDS) < 5);
    }

    @Test
    void should_update_mortality_info_existing_locator() {
        var data = getMortalityData();
        var person = new Person(123L, "localId");
        // Create EntityLocatorParticipation
        var elp = new PostalEntityLocatorParticipation();
        elp.setId(new EntityLocatorParticipationId(person.getId(), 456L));
        elp.setNbsEntity(person.getNbsEntity());
        elp.setUseCd("DTH");
        elp.setVersionCtrlNbr((short) 1);

        // Create PostalLocator
        var postalLocator = new PostalLocator();
        postalLocator.setId(456L);

        elp.setLocator(postalLocator);
        person.getNbsEntity().setEntityLocatorParticipations(Collections.singletonList(elp));

        patientUpdater.update(person, data);

        verify(personRepository).save(personCaptor.capture());
        var postalParticipation = (PostalEntityLocatorParticipation) personCaptor.getValue()
                .getNbsEntity()
                .getEntityLocatorParticipations()
                .get(0);

        // validate entityLocatorParticipation
        var now = Instant.now();
        assertEquals(Long.valueOf(123L), postalParticipation.getId().getEntityUid());
        assertEquals(Long.valueOf(456L), postalParticipation.getId().getLocatorUid());
        assertTrue(postalParticipation.getLastChgTime().until(now, ChronoUnit.SECONDS) < 5);
        assertEquals(Long.valueOf(data.updatedBy()), postalParticipation.getLastChgUserId());
        assertEquals(data.asOf().getEpochSecond(), postalParticipation.getAsOfDate().getEpochSecond());
        assertEquals(Short.valueOf((short) 2), postalParticipation.getVersionCtrlNbr());

        // validate locator
        var locator = postalParticipation.getLocator();
        assertEquals(Long.valueOf(456L), locator.getId());
        assertEquals(data.cityOfDeath(), locator.getCityDescTxt());
        assertEquals(data.countryOfDeath(), locator.getCntryCd());
        assertEquals(data.countyOfDeath(), locator.getCntyCd());
        assertEquals(data.stateOfDeath(), locator.getStateCd());
        assertTrue(locator.getLastChgTime().until(now, ChronoUnit.SECONDS) < 5);
        assertEquals(Long.valueOf(data.updatedBy()), locator.getLastChgUserId());
    }

    private UpdateMortalityData getMortalityData() {
        return new UpdateMortalityData(123L,
                "RequestId",
                321L,
                Instant.now(),
                Deceased.UNK,
                Instant.now(),
                "CityOfDeath",
                "State of Death",
                "County of death",
                "Country of death");
    }

    @Test
    void should_delete_mortality_info() {
        var data = getDeleteMortalityData();
        var person = new Person(123L, "localId");
        // Create EntityLocatorParticipation
        var elp = new PostalEntityLocatorParticipation();
        elp.setId(new EntityLocatorParticipationId(person.getId(), 456L));
        elp.setNbsEntity(person.getNbsEntity());
        elp.setUseCd("DTH");
        elp.setVersionCtrlNbr((short) 1);

        // Create PostalLocator
        var postalLocator = new PostalLocator();
        postalLocator.setId(456L);

        elp.setLocator(postalLocator);
        person.getNbsEntity().setEntityLocatorParticipations(Collections.singletonList(elp));
        assertEquals(1, person.getNbsEntity().getEntityLocatorParticipations().size());
        patientUpdater.update(person, data);

        verify(personRepository).save(personCaptor.capture());
        assertEquals(0, personCaptor.getValue().getNbsEntity().getEntityLocatorParticipations().size());
    }

    private DeleteMortalityData getDeleteMortalityData() {
        return new DeleteMortalityData(123L,
                (short) 456,
                "RequestId",
                321L,
                Instant.now());
    }

    @Test
    void should_update_sex_and_birth_info() {
        var data = getSexAndBirthData();
        var person = new Person(123L, "localId");
        person.setVersionCtrlNbr((short) 2);
        patientUpdater.update(person, data);

        verify(personRepository).save(personCaptor.capture());
        var savedPerson = personCaptor.getValue();

        var now = Instant.now();

        assertEquals(data.asOf().getEpochSecond(), savedPerson.getAsOfDateSex().getEpochSecond());
        assertEquals(data.birthGender(), savedPerson.getBirthGenderCd());
        assertEquals(data.currentGender(), savedPerson.getCurrSexCd());
        assertEquals(data.dateOfBirth(), LocalDate.ofInstant(savedPerson.getBirthTime(), ZoneId.systemDefault()));
        assertEquals(data.currentAge(), savedPerson.getAgeReported());
        assertEquals(data.ageReportedTime().getEpochSecond(), savedPerson.getAgeReportedTime().getEpochSecond());
        assertEquals(data.birthCity(), savedPerson.getBirthCityCd());
        assertEquals(data.birthCntry(), savedPerson.getBirthCntryCd());
        assertEquals(data.birthState(), savedPerson.getBirthStateCd());
        assertEquals(data.birthOrderNbr(), savedPerson.getBirthOrderNbr());
        assertEquals(data.multipleBirth(), savedPerson.getMultipleBirthInd());
        assertEquals(data.sexUnknown(), savedPerson.getSexUnkReasonCd());
        assertEquals(data.additionalGender(), savedPerson.getAdditionalGenderCd());
        assertEquals(data.transGenderInfo(), savedPerson.getPreferredGenderCd());

        assertEquals(Long.valueOf(data.updatedBy()), savedPerson.getLastChgUserId());
        assertEquals(Short.valueOf((short) 3), savedPerson.getVersionCtrlNbr());
        assertEquals(Long.valueOf(data.updatedBy()), savedPerson.getLastChgUserId());
        assertTrue(savedPerson.getLastChgTime().until(now, ChronoUnit.SECONDS) < 5);
    }

    private UpdateSexAndBirthData getSexAndBirthData() {
        return new UpdateSexAndBirthData(
                "RequestId",
                123L,
                321L,
                Instant.now(),
                LocalDate.now(),
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
                Instant.now());
    }

    @Test
    void should_update_administrative_info() {
        var data = getAdministrativeData();
        var person = new Person(123L, "localId");
        person.setVersionCtrlNbr((short) 2);
        patientUpdater.update(person, data);

        verify(personRepository).save(personCaptor.capture());
        var savedPerson = personCaptor.getValue();

        var now = Instant.now();

        assertEquals(data.description(), savedPerson.getDescription());

        assertEquals(Long.valueOf(data.updatedBy()), savedPerson.getLastChgUserId());
        assertEquals(Short.valueOf((short) 3), savedPerson.getVersionCtrlNbr());
        assertEquals(Long.valueOf(data.updatedBy()), savedPerson.getLastChgUserId());
        assertTrue(savedPerson.getLastChgTime().until(now, ChronoUnit.SECONDS) < 5);
    }

    private UpdateAdministrativeData getAdministrativeData() {
        return new UpdateAdministrativeData(
                123L,
                "RequestId",
                321L,
                Instant.now(),
                "Administrative Data 1");
    }

    @Test
    void should_add_name_info() {
        var data = getAddNameData();
        var person = new Person(123L, "localId");
        patientUpdater.update(person, data);
        verify(personRepository).save(personCaptor.capture());
        PersonName personName = (PersonName) personCaptor.getValue()
                .getNames()
                .get(0);
        assertEquals(data.first(), personName.getFirstNm());
        assertEquals(data.middle(), personName.getMiddleNm());
        assertEquals(data.last(), personName.getLastNm());
        assertEquals(data.suffix(), personName.getNmSuffix());
        assertEquals(data.type().toString(), personName.getNmUseCd());
    }

    private AddNameData getAddNameData() {
        return new AddNameData(123L,
                "RequestId",
                321L,
                Instant.now(),
                "First Name",
                "Middle Name",
                "Last Name",
                Suffix.III,
                NameUseCd.L);
    }

    @Test
    void should_update_name_info() {
        var data = getUpdateNameData();
        var person = new Person(123L, "localId");
        // Create new name
        var pn = new PersonName();
        pn.setId(new PersonNameId(person.getId(), (short) 1));
        pn.setFirstNm("XXX");
        pn.setLastNm("XXX");
        pn.setMiddleNm("XXX");
        pn.setNmSuffix(Suffix.ESQ);

        person.setNames(Collections.singletonList(pn));

        patientUpdater.update(person, data);

        verify(personRepository).save(personCaptor.capture());
        PersonName personName = (PersonName) personCaptor.getValue()
                .getNames()
                .get(0);

        assertEquals(data.first(), personName.getFirstNm());
        assertEquals(data.middle(), personName.getMiddleNm());
        assertEquals(data.last(), personName.getLastNm());
        assertEquals(data.suffix(), personName.getNmSuffix());
        assertEquals(data.type(), personName.getNmUseCd().toString());
    }

    private UpdateNameData getUpdateNameData() {
        return new UpdateNameData(123L,
                (short) 1,
                "RequestId",
                321L,
                Instant.now(),
                "First Name",
                "Middle Name",
                "Last Name",
                Suffix.III,
                NameUseCd.L.toString());
    }

    @Test
    void should_delete_name_info() {
        var data = getDeleteNameData();
        var person = new Person(123L, "localId");
        // Create new name
        var pn = new PersonName();
        pn.setId(new PersonNameId(person.getId(), (short) 1));
        pn.setFirstNm("XXX");
        pn.setLastNm("XXX");
        pn.setMiddleNm("XXX");
        pn.setNmSuffix(Suffix.ESQ);
        person.setNames(Collections.singletonList(pn));

        patientUpdater.update(person, data);

        verify(personRepository).save(personCaptor.capture());
        assertEquals(0, personCaptor.getValue().getNames().size());
    }

    private DeleteNameData getDeleteNameData() {
        return new DeleteNameData(123L,
                (short) 1,
                "RequestId",
                321L,
                Instant.now());
    }

    @Test
    void should_update_ethnicity_info() {
        var data = getEthnicityData();
        var person = new Person(123L, "localId");
        person.setVersionCtrlNbr((short) 2);
        patientUpdater.update(person, data);

        verify(personRepository).save(personCaptor.capture());
        var savedPerson = personCaptor.getValue();

        var now = Instant.now();

        assertEquals(data.ethnicityGroupInd(), savedPerson.getEthnicGroupInd());

        assertEquals(Long.valueOf(data.updatedBy()), savedPerson.getLastChgUserId());
        assertEquals(Short.valueOf((short) 3), savedPerson.getVersionCtrlNbr());
        assertEquals(Long.valueOf(data.updatedBy()), savedPerson.getLastChgUserId());
        assertTrue(savedPerson.getLastChgTime().until(now, ChronoUnit.SECONDS) < 5);
    }

    private UpdateEthnicityData getEthnicityData() {
        return new UpdateEthnicityData(123L,
                "RequestId",
                321L,
                Instant.now(),
                "ethnicityCode",
                "ethnicUnkReasonCd");
    }

    @Test
    void should_add_address_info() {
        var data = getAddAddressData();
        var person = new Person(123L, "localId");
        patientUpdater.update(person, data);
        verify(personRepository).save(personCaptor.capture());
        var postalParticipation = (PostalEntityLocatorParticipation) personCaptor.getValue()
                .getNbsEntity()
                .getEntityLocatorParticipations()
                .get(0);
        var locator = postalParticipation.getLocator();
        assertEquals(data.streetAddress1(), locator.getStreetAddr1());
        assertEquals(data.streetAddress2(), locator.getStreetAddr2());
    }

    private AddAddressData getAddAddressData() {
        return new AddAddressData(123L,
                456L,
                "RequestId",
                321L,
                Instant.now(),
                "ST1",
                "ST2",
                "City",
                "State",
                "County",
                "Country",
                "zip",
                "census");
    }

    @Test
    void should_update_address_info() {
        var data = getUpdateAddressData();
        var person = new Person(123L, "localId");

        var elp = new PostalEntityLocatorParticipation();
        elp.setId(new EntityLocatorParticipationId(person.getId(), 456L));
        elp.setNbsEntity(person.getNbsEntity());
        elp.setVersionCtrlNbr((short) 321);

        var postalLocator = new PostalLocator();
        postalLocator.setId(456L);
        postalLocator.setStreetAddr1("ST1");
        postalLocator.setStreetAddr2("ST2");

        elp.setLocator(postalLocator);
        person.getNbsEntity().setEntityLocatorParticipations(Collections.singletonList(elp));
        patientUpdater.update(person, data);

        verify(personRepository).save(personCaptor.capture());
        var postalParticipation = (PostalEntityLocatorParticipation) personCaptor.getValue()
                .getNbsEntity()
                .getEntityLocatorParticipations()
                .get(0);
        var locator = postalParticipation.getLocator();
        assertEquals(data.streetAddress1(), locator.getStreetAddr1());
        assertEquals(data.streetAddress2(), locator.getStreetAddr2());
    }

    private UpdateAddressData getUpdateAddressData() {
        return new UpdateAddressData(123L,
                (short) 456,
                "RequestId",
                321L,
                Instant.now(),
                "ST1",
                "ST2",
                "City",
                "State",
                "County",
                "Country",
                "zip",
                "census");
    }

    @Test
    void should_delete_address_info() {
        var data = getDeleteAddressData();
        var person = new Person(123L, "localId");

        var elp = new PostalEntityLocatorParticipation();
        elp.setId(new EntityLocatorParticipationId(person.getId(), 456L));
        elp.setNbsEntity(person.getNbsEntity());
        elp.setVersionCtrlNbr((short) 1);

        var postalLocator = new PostalLocator();
        postalLocator.setId(456L);
        postalLocator.setStreetAddr1("ST1");
        postalLocator.setStreetAddr2("ST2");

        elp.setLocator(postalLocator);
        person.getNbsEntity().setEntityLocatorParticipations(Collections.singletonList(elp));

        patientUpdater.update(person, data);

        verify(personRepository).save(personCaptor.capture());

        assertEquals(0, personCaptor.getValue().getNbsEntity().getEntityLocatorParticipations().size());
    }

    private DeleteAddressData getDeleteAddressData() {
        return new DeleteAddressData(123L,
                (short) 456,
                "RequestId",
                321L,
                Instant.now());
    }

    @Test
    void should_add_phone_info() {
        var data = getAddPhoneData();
        var person = new Person(123L, "localId");
        patientUpdater.update(person, data);
        verify(personRepository).save(personCaptor.capture());
        var elp = (TeleEntityLocatorParticipation) personCaptor.getValue()
                .getNbsEntity()
                .getEntityLocatorParticipations()
                .get(0);
        var locator = elp.getLocator();
        assertEquals(data.number(), locator.getPhoneNbrTxt());
        assertEquals(data.extension(), locator.getExtensionTxt());
    }

    private AddPhoneData getAddPhoneData() {
        return new AddPhoneData(123L,
                "RequestId",
                321L,
                Instant.now(),
                "3145551212",
                "123",
                PhoneType.CELL);
    }

    @Test
    void should_update_phone_info() {
        var data = getUpdatePhoneData();
        var person = new Person(123L, "localId");

        var elp = new TeleEntityLocatorParticipation();
        elp.setId(new EntityLocatorParticipationId(person.getId(), 456L));
        elp.setNbsEntity(person.getNbsEntity());
        elp.setVersionCtrlNbr((short) 321);

        var teleLocator = new TeleLocator();
        teleLocator.setId(456L);
        teleLocator.setPhoneNbrTxt("3145551212");
        teleLocator.setExtensionTxt("123");
        elp.setLocator(teleLocator);

        person.getNbsEntity().setEntityLocatorParticipations(Collections.singletonList(elp));

        patientUpdater.update(person, data);

        verify(personRepository).save(personCaptor.capture());
        var telp = (TeleEntityLocatorParticipation) personCaptor.getValue()
                .getNbsEntity()
                .getEntityLocatorParticipations()
                .get(0);
        var locator = telp.getLocator();
        assertEquals(data.number(), locator.getPhoneNbrTxt());
        assertEquals(data.extension(), locator.getExtensionTxt());
    }

    private UpdatePhoneData getUpdatePhoneData() {
        return new UpdatePhoneData(123L,
                (short) 456,
                "RequestId",
                321L,
                Instant.now(),
                "3145551212",
                "123",
                PhoneType.CELL);
    }

    @Test
    void should_delete_phone_info() {
        var data = getDeletePhoneData();
        var person = new Person(123L, "localId");

        var elp = new TeleEntityLocatorParticipation();
        elp.setId(new EntityLocatorParticipationId(person.getId(), 456L));
        elp.setNbsEntity(person.getNbsEntity());
        elp.setVersionCtrlNbr((short) 321);

        var teleLocator = new TeleLocator();
        teleLocator.setId(456L);
        teleLocator.setPhoneNbrTxt("3145551212");
        teleLocator.setExtensionTxt("123");
        elp.setLocator(teleLocator);

        person.getNbsEntity().setEntityLocatorParticipations(Collections.singletonList(elp));

        patientUpdater.update(person, data);

        verify(personRepository).save(personCaptor.capture());

        assertEquals(0, personCaptor.getValue().getNbsEntity().getEntityLocatorParticipations().size());
    }

    private DeletePhoneData getDeletePhoneData() {
        return new DeletePhoneData(123L,
                (short) 456,
                "RequestId",
                321L,
                Instant.now());
    }

    @Test
    void should_add_email_info() {
        var data = getAddEmailData();
        var person = new Person(123L, "localId");
        patientUpdater.update(person, data);
        verify(personRepository).save(personCaptor.capture());
        var elp = (TeleEntityLocatorParticipation) personCaptor.getValue()
                .getNbsEntity()
                .getEntityLocatorParticipations()
                .get(0);
        var locator = elp.getLocator();
        assertEquals(data.emailAddress(), locator.getEmailAddress());
    }

    private AddEmailData getAddEmailData() {
        return new AddEmailData(123L,
                321L,
                "RequestId",
                321L,
                Instant.now(),
                "email@test.com");
    }

    @Test
    void should_update_email_info() {
        var data = getUpdateEmailData();
        var person = new Person(123L, "localId");

        var elp = new TeleEntityLocatorParticipation();
        elp.setId(new EntityLocatorParticipationId(person.getId(), 456L));
        elp.setNbsEntity(person.getNbsEntity());
        elp.setVersionCtrlNbr((short) 321);

        var teleLocator = new TeleLocator();
        teleLocator.setId(456L);
        teleLocator.setEmailAddress("email@test.com");
        elp.setLocator(teleLocator);

        person.getNbsEntity().setEntityLocatorParticipations(Collections.singletonList(elp));

        patientUpdater.update(person, data);

        verify(personRepository).save(personCaptor.capture());
        var telp = (TeleEntityLocatorParticipation) personCaptor.getValue()
                .getNbsEntity()
                .getEntityLocatorParticipations()
                .get(0);
        var locator = telp.getLocator();
        assertEquals(data.emailAddress(), locator.getEmailAddress());
    }

    private UpdateEmailData getUpdateEmailData() {
        return new UpdateEmailData(123L,
                321L,
                "RequestId",
                321L,
                Instant.now(),
                "email@test.com");
    }

    @Test
    void should_delete_email_info() {
        var data = getDeleteEmailData();
        var person = new Person(123L, "localId");

        var elp = new TeleEntityLocatorParticipation();
        elp.setId(new EntityLocatorParticipationId(person.getId(), 456L));
        elp.setNbsEntity(person.getNbsEntity());
        elp.setVersionCtrlNbr((short) 321);

        var teleLocator = new TeleLocator();
        teleLocator.setId(456L);
        teleLocator.setEmailAddress("email@test.com");
        elp.setLocator(teleLocator);

        person.getNbsEntity().setEntityLocatorParticipations(Collections.singletonList(elp));

        patientUpdater.update(person, data);

        verify(personRepository).save(personCaptor.capture());

        assertEquals(0, personCaptor.getValue().getNbsEntity().getEntityLocatorParticipations().size());
    }

    private DeleteEmailData getDeleteEmailData() {
        return new DeleteEmailData(123L,
                (short) 456,
                "RequestId",
                321L,
                Instant.now());
    }

    @Test
    void should_add_identification_info() {
        var data = getAddIdentificationData();
        var person = new Person(123L, "localId");
        patientUpdater.update(person, data);
        verify(personRepository).save(personCaptor.capture());
        var entityId = (EntityId) personCaptor.getValue()
                .getEntityIds()
                .get(0);
        assertEquals(data.identificationNumber(), entityId.getRootExtensionTxt());
        assertEquals(data.identificationType(), entityId.getTypeCd());
    }

    private AddIdentificationData getAddIdentificationData() {
        return new AddIdentificationData(123L,
                "RequestId",
                321L,
                Instant.now(),
                "123456789",
                "assign",
                "ssn");
    }

    @Test
    void should_update_identification_info() {
        var data = getUpdateIdentificationData();
        var person = new Person(123L, "localId");

        var entityId = new EntityId();
        entityId.setId(new EntityIdId(123L, (short) 456));
        entityId.setRootExtensionTxt("123456789");
        entityId.setTypeCd("ssn");
        person.setEntityIds(List.of(entityId));

        patientUpdater.update(person, data);

        verify(personRepository).save(personCaptor.capture());
        entityId = (EntityId) personCaptor.getValue()
                .getEntityIds()
                .get(0);
        assertEquals(data.identificationNumber(), entityId.getRootExtensionTxt());
        assertEquals(data.identificationType(), entityId.getTypeCd());
    }

    private UpdateIdentificationData getUpdateIdentificationData() {
        return new UpdateIdentificationData(123L,
                (short) 456,
                "RequestId",
                321L,
                Instant.now(),
                "123456789",
                "assign",
                "ssn");
    }

    @Test
    void should_delete_identification_info() {
        var data = getDeleteIdentificationData();
        var person = new Person(123L, "localId");

        var entityId = new EntityId();
        entityId.setId(new EntityIdId(123L, (short) 456));
        entityId.setRootExtensionTxt("123456789");
        entityId.setTypeCd("ssn");
        person.setEntityIds(List.of(entityId));
        assertEquals(1, person.getEntityIds().size());

        patientUpdater.update(person, data);

        verify(personRepository).save(personCaptor.capture());

        assertEquals(0, personCaptor.getValue().getEntityIds().size());
    }

    private DeleteIdentificationData getDeleteIdentificationData() {
        return new DeleteIdentificationData(123L,
                (short) 456,
                "RequestId",
                321L,
                Instant.now());
    }

    @Test
    void should_add_race_info() {
        var data = getAddRaceData();
        var person = new Person(123L, "localId");
        patientUpdater.update(person, data);
        verify(personRepository).save(personCaptor.capture());
        var personRace = (PersonRace) personCaptor.getValue()
                .getRaces()
                .get(0);
        assertEquals(data.raceCd(), personRace.getRaceCd());
    }

    private AddRaceData getAddRaceData() {
        return new AddRaceData(123L,
                "RequestId",
                321L,
                Instant.now(),
                "race",
                "race category");
    }

    @Test
    void should_update_race_info() {
        var data = getUpdateRaceData();
        var person = new Person(123L, "localId");

        var personRace = new PersonRace();
        personRace.setPersonUid(person);
        personRace.setRaceCd("race");
        personRace.setRaceCategoryCd("race category");
        person.setRaces(List.of(personRace));

        patientUpdater.update(person, data);

        verify(personRepository).save(personCaptor.capture());
        personRace = (PersonRace) personCaptor.getValue()
                .getRaces()
                .get(0);
        assertEquals(data.raceCd(), personRace.getRaceCd());
    }

    private UpdateRaceData getUpdateRaceData() {
        return new UpdateRaceData(123L,
                "RequestId",
                321L,
                Instant.now(),
                "race",
                "race category");
    }

    @Test
    void should_delete_race_info() {
        var data = getDeleteRaceData();
        var person = new Person(123L, "localId");

        var personRace = new PersonRace();
        personRace.setPersonUid(person);
        personRace.setRaceCd("race");
        personRace.setRaceCategoryCd("race category");
        person.setRaces(List.of(personRace));

        patientUpdater.update(person, data);

        verify(personRepository).save(personCaptor.capture());

        assertEquals(0, personCaptor.getValue().getRaces().size());
    }

    private DeleteRaceData getDeleteRaceData() {
        return new DeleteRaceData(123L,
                "RequestId",
                321L,
                Instant.now(),
                "race");
    }
}
