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
import gov.cdc.nbs.entity.odse.PersonEthnicGroup;
import gov.cdc.nbs.entity.odse.PersonEthnicGroupId;
import gov.cdc.nbs.entity.odse.PersonName;
import gov.cdc.nbs.entity.odse.PersonNameId;
import gov.cdc.nbs.entity.odse.PersonRace;
import gov.cdc.nbs.entity.odse.PersonRaceId;
import gov.cdc.nbs.entity.odse.PostalLocator;
import gov.cdc.nbs.entity.odse.TeleLocator;
import gov.cdc.nbs.support.util.CountryCodeUtil;
import gov.cdc.nbs.support.util.RandomUtil;

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
        var entity = new NBSEntity(id, "PSN");
        var person = new Person();
        person.setId(id);
        person.setCd("PAT");
        person.setSsn(RandomUtil.getRandomSsn());
        person.setBirthTime(RandomUtil.getRandomDateInPast());
        person.setBirthGenderCd(RandomUtil.getRandomFromArray(Gender.values()));
        person.setDeceasedIndCd(RandomUtil.getRandomFromArray(Deceased.values()));
        person.setCurrSexCd(RandomUtil.getRandomFromArray(new Character[] { 'F', 'M', 'U' }));
        person.setBirthCityCd(RandomUtil.getRandomString());
        person.setBirthStateCd(RandomUtil.getRandomStateCode());
        person.setBirthCntryCd("United States");
        person.setRecordStatusCd(
                RandomUtil.getRandomFromArray(RecordStatus.values()));
        person.setNBSEntity(entity);
        person.setVersionCtrlNbr((short) 1);

        // Identification
        var entityId = new EntityId();
        entityId.setId(new EntityIdId(id, (short) 1));
        entityId.setNBSEntityUid(entity);
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
        name.setFirstNm(RandomUtil.getRandomString());
        name.setLastNm(RandomUtil.getRandomString());
        person.setNames(Arrays.asList(name));

        // ethnic group
        var peg = new PersonEthnicGroup();
        peg.setId(new PersonEthnicGroupId(id, RandomUtil.getRandomFromArray(Ethnicity.values())));
        peg.setPersonUid(person);
        peg.setRecordStatusCd("ACTIVE");
        person.setEthnicGroups(Arrays.asList(peg));

        // race
        var race = new PersonRace();
        race.setId(new PersonRaceId(id, RandomUtil.getRandomFromArray(Race.values())));
        race.setPersonUid(person);
        race.setRecordStatusCd("ACTIVE");
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
        teleElp.setEntityUid(entity);
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
        postalLocator.setStreetAddr1(RandomUtil.getRandomString(8));
        postalLocator.setCntryCd(
                RandomUtil.getRandomFromArray(CountryCodeUtil.countryCodeMap.values().toArray(new String[0])));
        postalLocator.setCityCd(RandomUtil.getRandomString(8));
        postalLocator.setStateCd(RandomUtil.getRandomStateCode());
        postalLocator.setZipCd(RandomUtil.getRandomNumericString(5));
        postalLocator.setRecordStatusCd("ACTIVE");

        var postalElp = new EntityLocatorParticipation();
        postalElp.setEntityUid(entity);
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
        person.setCd("PAT");
        person.setFirstNm("John");
        person.setLastNm("Doe");
        person.setSsn("999-888-7777");
        person.setHmPhoneNbr("111-222-3333");
        person.setWkPhoneNbr("222-333-4444");
        person.setCellPhoneNbr("444-555-6666");
        person.setBirthTime(Instant.parse("1982-11-30T18:35:24.00Z"));
        person.setBirthGenderCd(Gender.M);
        person.setDeceasedIndCd(Deceased.N);
        person.setHmStreetAddr1("123 Main St");
        person.setHmCityCd("Atlanta");
        person.setHmStateCd("Georgia");
        person.setHmZipCd("30301");
        person.setHmCntryCd("United States");
        person.setWkStreetAddr1("345 Work St");
        person.setWkCityCd("Atlanta");
        person.setWkStateCd("Georgia");
        person.setWkZipCd("30302");
        person.setWkCntryCd("United States");
        person.setCurrSexCd('M');
        person.setBirthCityCd("Savannah");
        person.setBirthStateCd("Georgia");
        person.setBirthCntryCd("United States");
        person.setEthnicityGroupCd("2186-5");
        person.setRecordStatusCd(RecordStatus.ACTIVE);
        person.setNBSEntity(new NBSEntity(id, "PSN"));
        person.setVersionCtrlNbr((short) 1);
        return person;
    }

}
