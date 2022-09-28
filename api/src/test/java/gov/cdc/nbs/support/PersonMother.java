package gov.cdc.nbs.support;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import gov.cdc.nbs.entity.NBSEntity;
import gov.cdc.nbs.entity.Person;
import gov.cdc.nbs.entity.enums.Deceased;
import gov.cdc.nbs.entity.enums.RecordStatus;

public class PersonMother {
    private static List<Person> persons = new ArrayList<>();
    // create 10 random persons
    static {
        for (long i = 0; i < 10; i++) {
            long id = 20000000L + i;
            persons.add(generateRandomPerson(id));
        }
    }

    public static List<Person> getRandomPersons() {
        return persons;
    }

    public static Person person1() {
        final long id = 999001L;
        var person = new Person();
        person.setId(id);
        person.setFirstNm("John");
        person.setLastNm("Doe");
        person.setSsn("123-456-789");
        person.setHmPhoneNbr("111-222-3333");
        person.setWkPhoneNbr("222-333-4444");
        person.setCellPhoneNbr("333-444-5555");
        person.setBirthTime(Instant.parse("1980-09-22T14:40:46.137Z"));
        person.setDeceasedIndCd(Deceased.N);
        person.setHmStreetAddr1("123 Main St");
        person.setHmCityCd("Atlanta");
        person.setHmStateCd("Georgia");
        person.setHmZipCd("30301");
        person.setHmCntryCd("United States");
        person.setWkStreetAddr1("123 Work Address");
        person.setWkCityCd("Atlanta");
        person.setWkStateCd("Georgia");
        person.setWkZipCd("30305");
        person.setWkCntryCd("United States");
        person.setCurrSexCd('M');
        person.setBirthCityCd("Atlanta");
        person.setBirthStateCd("Georgia");
        person.setBirthCntryCd("United States");
        person.setEthnicGroupInd("2135-2");
        person.setRecordStatusCd(RecordStatus.ACTIVE);
        person.setNBSEntity(new NBSEntity(id, "PSN"));
        person.setVersionCtrlNbr((short) 1);
        return person;
    }

    public static Person generateRandomPerson(long id) {
        var person = new Person();
        person.setId(id);
        person.setFirstNm(TestUtil.getRandomString());
        person.setLastNm(TestUtil.getRandomString());
        person.setSsn(TestUtil.getRandomSsn());
        person.setHmPhoneNbr(TestUtil.getRandomPhoneNumber());
        person.setWkPhoneNbr(TestUtil.getRandomPhoneNumber());
        person.setCellPhoneNbr(TestUtil.getRandomPhoneNumber());
        person.setBirthTime(TestUtil.getRandomDateInPast());
        person.setDeceasedIndCd(TestUtil.getRandomDeceasedValue());
        person.setHmStreetAddr1(TestUtil.getRandomString());
        person.setHmCityCd(TestUtil.getRandomString(8));
        person.setHmStateCd(TestUtil.getRandomState());
        person.setHmZipCd(TestUtil.getRandomNumericString(5));
        person.setHmCntryCd("United States");
        person.setWkStreetAddr1(TestUtil.getRandomString());
        person.setWkCityCd(TestUtil.getRandomString());
        person.setWkStateCd(TestUtil.getRandomState());
        person.setWkZipCd(TestUtil.getRandomNumericString(5));
        person.setWkCntryCd("United States");
        person.setCurrSexCd(TestUtil.getRandomSexValue());
        person.setBirthCityCd(TestUtil.getRandomString());
        person.setBirthStateCd(TestUtil.getRandomState());
        person.setBirthCntryCd("United States");
        person.setEthnicGroupInd(TestUtil.getRandomNumericString(6));
        person.setRecordStatusCd(TestUtil.getRandomRecordStatus());
        person.setNBSEntity(new NBSEntity(id, "PSN"));
        person.setVersionCtrlNbr((short) 1);
        return person;
    }

}
