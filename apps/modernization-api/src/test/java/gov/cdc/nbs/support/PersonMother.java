package gov.cdc.nbs.support;

import com.github.javafaker.Faker;
import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.message.enums.Deceased;
import gov.cdc.nbs.message.enums.Gender;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.support.util.RandomUtil;
import gov.cdc.nbs.support.util.StateCodeUtil;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class PersonMother {
    private static String patientIdSuffix = "GA01";
    private static long patientIdSeed = 10000000;
    private static String patientIdPrefix = "PSN";

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
        return generateRandomPerson(id, patientIdPrefix + (patientIdSeed + id) + patientIdSuffix);
    }

    public static Person generateRandomPerson(long id, final String local) {
        Instant now = Instant.now();

        Faker faker = new Faker();
        final String firstName = faker.name().firstName();
        final String middleName = faker.name().firstName();
        final String lastName = faker.name().lastName();

        Person person = new Person(
            new PatientCommand.AddPatient(
                id,
                local,
                RandomUtil.dateInPast(),
                RandomUtil.getRandomFromArray(Gender.values()),
                RandomUtil.getRandomFromArray(Gender.values()),
                RandomUtil.getRandomFromArray(Deceased.values()),
                null,
                null,
                RandomUtil.getRandomFromArray(EthnicityMother.ETHNICITY_LIST),
                now,
                "Test Patient Created by PersonMother",
                null,
                CREATED_BY_ID,
                now
            )
        );

        person.setCd("PAT");
        person.setRecordStatusCd(RecordStatus.ACTIVE);
        person.setFirstNm(firstName);
        person.setMiddleNm(middleName);
        person.setLastNm(lastName);
        person.setSsn(RandomUtil.getRandomSsn());


        person.setBirthCityCd(RandomUtil.getRandomString());
        person.setBirthStateCd(RandomUtil.getRandomStateCode());
        person.setBirthCntryCd("United States");
        person.setAgeReportedTime(RandomUtil.getRandomDateInPast());

        // Identification
        person.add(
            new PatientCommand.AddIdentification(
                id,
                RandomUtil.getRandomDateInPast(),
                RandomUtil.getRandomNumericString(8),
                "GA",
                RandomUtil.getRandomFromArray(IdentificationMother.IDENTIFICATION_CODE_LIST),
                id,
                now
            )
        );

        // name
        person.add(
            new PatientCommand.AddName(
                id,
                RandomUtil.getRandomDateInPast(),
                firstName,
                middleName,
                lastName,
                null,
                "L",
                id,
                now));


        // race
        person.add(
            new PatientCommand.AddRace(
                id,
                RandomUtil.getRandomDateInPast(),
                RandomUtil.getRandomFromArray(RaceMother.RACE_LIST),
                id,
                now
            )
        );

        // Tele locator entry
        person.add(
            new PatientCommand.AddPhoneNumber(
                id,
                id + 40000L,
                RandomUtil.getRandomDateInPast(),
                "PH",
                "H",
                RandomUtil.getRandomPhoneNumber(),
                null,
                CREATED_BY_ID,
                now
            )
        );

        // Postal locator entry
        person.add(
            new PatientCommand.AddAddress(
                id,
                id + 80000L,
                RandomUtil.getRandomDateInPast(),
                faker.address().streetAddress(),
                null,
                faker.address().city(),
                RandomUtil.getRandomStateCode(),
                faker.address().zipCode(),
                null,
                RandomUtil.country(),
                null,
                CREATED_BY_ID,
                now
            )
        );

        return person;
    }

    public static Person janeDoe_deleted() {
        final long id = 19000001L;
        Instant now = Instant.now();
        Person person = new Person(id, "PSN" + 19000001L + "GA01");

        person.setBirthTime(Instant.parse("1980-11-30T18:35:24.00Z"));
        person.setBirthGenderCd(Gender.F);
        person.setCurrSexCd(Gender.F);
        person.setDeceasedIndCd(Deceased.N);
        person.setSsn("666-777-8888");
        person.setCd("PAT");

        // name
        person.add(
            new PatientCommand.AddName(
                id,
                RandomUtil.getRandomDateInPast(),
                "Jane",
                "S",
                "Doe",
                null,
                "L",
                CREATED_BY_ID,
                now));

        // phone numbers
        person.add(
            new PatientCommand.AddPhoneNumber(
                id,
                id + 40000L,
                RandomUtil.getRandomDateInPast(),
                "111-222-3333",
                null,
                "PH",
                "H",
                CREATED_BY_ID,
                now
            )
        );

        // addresses
        person.add(
            new PatientCommand.AddAddress(
                id,
                id + 80000L,
                RandomUtil.getRandomDateInPast(),
                "123 Main St",
                null,
                "Atlanta",
                StateCodeUtil.stateCodeMap.get("Georgia"),
                "30301",
                "13089",
                "840",
                null,
                CREATED_BY_ID,
                now
            )
        );

        // race
        person.add(
            new PatientCommand.AddRace(
                id,
                RandomUtil.getRandomDateInPast(),
                RaceMother.WHITE_CODE,
                id,
                now
            )
        );

        person.delete(
            new PatientCommand.Delete(
                id,
                id,
                now
            ),
            patient -> 0
        );

        return person;
    }
    
    public static Person addNumber(Person aPerson) {
    	Instant now = Instant.now();
    	
    	aPerson.add(
    	            new PatientCommand.AddPhoneNumber(
    	                aPerson.getId(),
    	                aPerson.getId() + 40000L,
    	                RandomUtil.getRandomDateInPast(),
    	                "PH",
    	                "H",
    	                "222-555-3333",
    	                null,
    	                CREATED_BY_ID,
    	                now
    	            )
    	        );	
    	
    	return aPerson;
    }
    
    

}
