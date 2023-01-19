package gov.cdc.nbs.support;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gov.cdc.nbs.entity.enums.Deceased;
import gov.cdc.nbs.entity.enums.Ethnicity;
import gov.cdc.nbs.entity.enums.Gender;
import gov.cdc.nbs.entity.enums.IdentificationType;
import gov.cdc.nbs.entity.enums.Race;
import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.odse.EntityId;
import gov.cdc.nbs.entity.odse.EntityIdId;
import gov.cdc.nbs.entity.odse.EntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.EntityLocatorParticipationId;
import gov.cdc.nbs.entity.odse.NBSEntity;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PersonName;
import gov.cdc.nbs.entity.odse.PersonNameId;
import gov.cdc.nbs.entity.odse.PersonRace;
import gov.cdc.nbs.entity.odse.PersonRaceId;
import gov.cdc.nbs.entity.odse.PostalLocator;
import gov.cdc.nbs.entity.odse.TeleLocator;
import gov.cdc.nbs.graphql.input.PatientInput.PhoneType;
import gov.cdc.nbs.graphql.input.PatientInput.PostalAddress;
import gov.cdc.nbs.support.util.CountryCodeUtil;
import gov.cdc.nbs.support.util.RandomUtil;
import gov.cdc.nbs.support.util.StateCodeUtil;
import com.github.javafaker.Faker;

public class PersonMother {

    private static final Long CREATED_BY_ID = 999999L;

    // generates random person data. always starts from the same id
    public static List<Person> getRandomPersons(int count) {
        List<Person> persons = new ArrayList<>();
        for (long i = 0; i < count; i++) {
            long id = 20000000L + i;
            persons.add(generateRandomPerson(id));
        }
        return persons;
    }

    public static Person generateRandomPerson(long id) {
        Faker faker = new Faker();
        final String firstName = faker.name().firstName();
        final String middleName = RandomUtil.getRandomString();
        final String lastName = faker.name().lastName();
        var entity = new NBSEntity(id, "PSN");
        var person = new Person();
        person.setId(id);
        person.setCd("PAT");
        person.setFirstNm(firstName);
        person.setMiddleNm(middleName);
        person.setLastNm(lastName);
        person.setSsn(RandomUtil.getRandomSsn());
        person.setBirthTime(RandomUtil.getRandomDateInPast());
        person.setBirthGenderCd(RandomUtil.getRandomFromArray(Gender.values()));
        person.setDeceasedIndCd(RandomUtil.getRandomFromArray(Deceased.values()));
        person.setCurrSexCd(RandomUtil.getRandomFromArray(Gender.values()));
        person.setBirthCityCd(RandomUtil.getRandomString());
        person.setBirthStateCd(RandomUtil.getRandomStateCode());
        person.setBirthCntryCd("United States");
        person.setRecordStatusCd(RecordStatus.ACTIVE);
        person.setNbsEntity(entity);
        person.setVersionCtrlNbr((short) 1);

        // Identification
        var entityId = new EntityId();
        entityId.setId(new EntityIdId(id, (short) 1));
        entityId.setNbsEntityUid(entity);
        entityId.setAddTime(Instant.now());
        entityId.setAssigningAuthorityCd("GA");
        entityId.setAssigningAuthorityDescTxt("GA");
        entityId.setRecordStatusCd("ACTIVE");
        entityId.setRecordStatusTime(Instant.now());
        entityId.setStatusTime(Instant.now());
        entityId.setStatusCd('A');
        entityId.setRootExtensionTxt(RandomUtil.getRandomNumericString(8)); // the Id number
        entityId.setTypeCd(RandomUtil.getRandomFromArray(IdentificationType.values()));
        entityId.setTypeDescTxt("TEST GENERATED");
        person.setEntityIds(Arrays.asList(entityId));

        // name
        var name = new PersonName();
        name.setId(new PersonNameId(id, (short) 1));
        name.setPersonUid(person);
        name.setStatusCd('A');
        name.setStatusTime(Instant.now());
        name.setFirstNm(firstName);
        name.setMiddleNm(middleName);
        name.setLastNm(lastName);
        person.setNames(Arrays.asList(name));

        // ethnic group
        person.setEthnicGroupInd(RandomUtil.getRandomFromArray(Ethnicity.values()));

        // race
        var race = new PersonRace();
        race.setId(new PersonRaceId(id, RandomUtil.getRandomFromArray(Race.values())));
        race.setPersonUid(person);
        race.setRecordStatusCd("ACTIVE");
        person.setRaceDescTxt(race.getId().getRaceCd().toString());
        person.setRaces(Arrays.asList(race));

        // Tele locator entry
        var locatorId = id + 40000L;
        var teleLocator = new TeleLocator();
        teleLocator.setId(locatorId);
        teleLocator.setAddTime(Instant.now());
        teleLocator.setAddUserId(CREATED_BY_ID);
        teleLocator.setPhoneNbrTxt(RandomUtil.getRandomPhoneNumber());
        teleLocator.setRecordStatusCd("ACTIVE");

        EntityLocatorParticipation teleElp = new EntityLocatorParticipation();
        teleElp.setNbsEntity(entity);
        teleElp.setId(new EntityLocatorParticipationId(entity.getId(), locatorId));
        teleElp.setCd("PH");
        teleElp.setUseCd("H");
        teleElp.setClassCd("TELE");
        teleElp.setRecordStatusCd("ACTIVE");
        teleElp.setStatusCd('A');
        teleElp.setVersionCtrlNbr((short) 1);
        teleElp.setLocator(teleLocator);

        // Postal locator entry
        locatorId = id + 80000L;
        var postalLocator = new PostalLocator();
        postalLocator.setId(locatorId);
        postalLocator.setStreetAddr1(faker.address().streetAddress());
        postalLocator.setCntryCd(
                RandomUtil.getRandomFromArray(CountryCodeUtil.countryCodeMap.values().toArray(new String[0])));
        postalLocator.setCityDescTxt(faker.address().city());
        postalLocator.setStateCd(RandomUtil.getRandomStateCode());
        postalLocator.setZipCd(RandomUtil.getRandomNumericString(5));
        postalLocator.setRecordStatusCd("ACTIVE");

        var postalElp = new EntityLocatorParticipation();
        postalElp.setNbsEntity(entity);
        postalElp.setId(new EntityLocatorParticipationId(entity.getId(), locatorId));
        postalElp.setCd("H"); // Home
        postalElp.setUseCd("H");
        postalElp.setClassCd("PST"); // Postal
        postalElp.setRecordStatusCd("ACTIVE");
        postalElp.setStatusCd('A');
        postalElp.setVersionCtrlNbr((short) 1);
        postalElp.setLocator(postalLocator);

        entity.setEntityLocatorParticipations(Arrays.asList(teleElp, postalElp));

        return person;
    }

    public static Person johnDoe() {
        final long id = 19000000L;
        var person = new Person();
        person.setId(id);
        person.setNbsEntity(new NBSEntity(id, "PSN"));
        person.setCd("PAT");
        person.setFirstNm("John");
        person.setMiddleNm("Bob");
        person.setLastNm("Doe");
        person.setBirthTime(Instant.parse("1982-11-30T18:35:24.00Z"));
        person.setBirthGenderCd(Gender.M);
        person.setCurrSexCd(Gender.M);
        person.setDeceasedIndCd(Deceased.N);
        person.setSsn("999-888-7777");

        // name
        person.setFirstNm("John");
        person.setMiddleNm("Bob");
        person.setLastNm("Doe");
        var name = new PersonName();
        name.setId(new PersonNameId(id, (short) 1));
        name.setPersonUid(person);
        name.setStatusCd('A');
        name.setStatusTime(Instant.now());
        name.setFirstNm(person.getFirstNm());
        name.setMiddleNm(person.getMiddleNm());
        name.setLastNm(person.getLastNm());
        person.setNames(Arrays.asList(name));

        // phone numbers
        createTeleLocatorEntry("111-222-3333", id + 40000L, person.getNbsEntity(), PhoneType.HOME);
        createTeleLocatorEntry("222-333-4444", id + 40001L, person.getNbsEntity(), PhoneType.WORK);
        createTeleLocatorEntry("444-555-6666", id + 40002L, person.getNbsEntity(), PhoneType.CELL);

        // addresses

        var homeAddress = new PostalAddress(
                "123 Main St",
                null,
                "Atlanta",
                StateCodeUtil.stateCodeMap.get("Georgia"),
                "13089",
                CountryCodeUtil.countryCodeMap.get("United States"),
                "30301",
                null);
        createPostalLocatorEntry(id + 80000L, person.getNbsEntity(), homeAddress, "H");

        person.setEthnicGroupInd(Ethnicity.NOT_HISPANIC_OR_LATINO);
        person.setRecordStatusCd(RecordStatus.ACTIVE);
        person.setVersionCtrlNbr((short) 1);

        // race
        var race = new PersonRace();
        race.setId(new PersonRaceId(id, Race.WHITE));
        race.setPersonUid(person);
        race.setRecordStatusCd("ACTIVE");
        person.setRaces(Arrays.asList(race));
        return person;
    }

    private static void createPostalLocatorEntry(Long locatorId, NBSEntity entity, PostalAddress address, String cd) {
        // Postal locator entry
        var postalLocator = new PostalLocator();
        postalLocator.setId(locatorId);
        postalLocator.setStreetAddr1(address.getStreetAddress1());
        postalLocator.setStreetAddr2(address.getStreetAddress2());
        postalLocator.setCntryCd(address.getCountryCode());
        postalLocator.setCityDescTxt(address.getCity());
        postalLocator.setStateCd(address.getStateCode());
        postalLocator.setZipCd(address.getZip());
        postalLocator.setRecordStatusCd("ACTIVE");

        var elp = new EntityLocatorParticipation();
        elp.setNbsEntity(entity);
        elp.setId(new EntityLocatorParticipationId(entity.getId(), locatorId));
        elp.setCd(cd); // Home
        elp.setUseCd(cd);
        elp.setClassCd("PST"); // Postal
        elp.setRecordStatusCd("ACTIVE");
        elp.setStatusCd('A');
        elp.setVersionCtrlNbr((short) 1);
        elp.setLocator(postalLocator);

        var elpList = new ArrayList<EntityLocatorParticipation>();
        elpList.add(elp);
        if (entity.getEntityLocatorParticipations() != null && entity.getEntityLocatorParticipations().size() > 0) {
            elpList.addAll(entity.getEntityLocatorParticipations());
        }
        entity.setEntityLocatorParticipations(elpList);
    }

    private static void createTeleLocatorEntry(String phoneNumber, Long locatorId, NBSEntity entity,
            PhoneType phoneType) {
        // Tele locator entry
        var teleLocator = new TeleLocator();
        teleLocator.setId(locatorId);
        teleLocator.setAddTime(Instant.now());
        teleLocator.setAddUserId(CREATED_BY_ID);
        teleLocator.setPhoneNbrTxt(phoneNumber);
        teleLocator.setRecordStatusCd("ACTIVE");

        EntityLocatorParticipation elp = new EntityLocatorParticipation();
        elp.setNbsEntity(entity);
        elp.setId(new EntityLocatorParticipationId(entity.getId(), locatorId));
        setElpTypeFields(elp, phoneType);
        elp.setClassCd("TELE");
        elp.setRecordStatusCd("ACTIVE");
        elp.setStatusCd('A');
        elp.setVersionCtrlNbr((short) 1);
        elp.setLocator(teleLocator);

        var elpList = new ArrayList<EntityLocatorParticipation>();
        elpList.add(elp);
        if (entity.getEntityLocatorParticipations() != null && entity.getEntityLocatorParticipations().size() > 0) {
            elpList.addAll(entity.getEntityLocatorParticipations());
        }
        entity.setEntityLocatorParticipations(elpList);
    }

    private static void setElpTypeFields(EntityLocatorParticipation elp, PhoneType phoneType) {
        switch (phoneType) {
            case CELL:
                elp.setCd("CP");
                elp.setUseCd("MC");
                break;
            case HOME:
                elp.setCd("PH");
                elp.setUseCd("H");
                break;
            case WORK:
                elp.setCd("PH");
                elp.setUseCd("WP");
                break;
        }
    }

}
