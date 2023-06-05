package gov.cdc.nbs.support;

import com.github.javafaker.Faker;
import gov.cdc.nbs.address.City;
import gov.cdc.nbs.address.Country;
import gov.cdc.nbs.address.County;
import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.message.enums.Deceased;
import gov.cdc.nbs.message.enums.Gender;
import gov.cdc.nbs.message.patient.input.PatientInput;
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

        Person person = new Person(id, local);
        person.setCd("PAT");
        person.setRecordStatusCd(RecordStatus.ACTIVE);
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
        person.setAgeReportedTime(RandomUtil.getRandomDateInPast());
        person.setAsOfDateGeneral(RandomUtil.getRandomDateInPast());
        // Identification
        person.add(
            new PatientCommand.AddIdentification(
                id,
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
                firstName,
                middleName,
                lastName,
                null,
                PatientInput.NameUseCd.L,
                id,
                now));

        // ethnic group
        person.setEthnicGroupInd(RandomUtil.getRandomFromArray(EthnicityMother.ETHNICITY_LIST));

        // race
        person.add(
            new PatientCommand.AddRace(
                id,
                RandomUtil.getRandomFromArray(RaceMother.RACE_LIST),
                RandomUtil.getRandomFromArray(RaceMother.RACE_LIST),
                id,
                now));

        // Tele locator entry
        person.add(
            new PatientCommand.AddPhoneNumber(
                id,
                id + 40000L,
                RandomUtil.getRandomPhoneNumber(),
                null,
                "PH",
                "H",
                CREATED_BY_ID,
                now
            )
        );

        String city = faker.address().city();

        // Postal locator entry
        person.add(
            new PatientCommand.AddAddress(
                id,
                id + 80000L,
                faker.address().streetAddress(),
                null,
                new City(city),
                RandomUtil.getRandomStateCode(),
                faker.address().zipCode(),
                null,
                RandomUtil.country(),
                null,
                CREATED_BY_ID,
                now));

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
                "Jane",
                "S",
                "Doe",
                null,
                PatientInput.NameUseCd.L,
                CREATED_BY_ID,
                now));

        // phone numbers
        person.add(
            new PatientCommand.AddPhoneNumber(
                id,
                id + 40000L,
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
                "123 Main St",
                null,
                new City("Atlanta"),
                StateCodeUtil.stateCodeMap.get("Georgia"),
                "30301",
                new County("13089"),
                new Country("840", "United States"),
                null,
                CREATED_BY_ID,
                now
            )
        );

        person.setEthnicGroupInd(EthnicityMother.HISPANIC_OR_LATINO_CODE);

        // race
        person.add(
            new PatientCommand.AddRace(
                id,
                RaceMother.WHITE_CODE,
                RaceMother.WHITE_CODE,
                id,
                now));

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

}
