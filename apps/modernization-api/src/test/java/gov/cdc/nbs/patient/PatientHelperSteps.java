package gov.cdc.nbs.patient;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.github.javafaker.Faker;

import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.TeleEntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.TeleLocator;
import gov.cdc.nbs.message.enums.Deceased;
import gov.cdc.nbs.message.enums.Gender;
import gov.cdc.nbs.patient.util.PatientHelper;
import gov.cdc.nbs.support.EthnicityMother;
import gov.cdc.nbs.support.util.RandomUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class PatientHelperSteps {

	private List<Person> persons;

	private List<Person> distinctPersons;
	
	private static final Long CREATED_BY_ID = 999999L;

	@Given("the patient has multiple phones")
	public void the_patient_has_multiple_phones() {

		persons = new ArrayList<>();
		distinctPersons = new ArrayList<>();
		persons = getRandomPersons(3);

		Person one = persons.get(0);

		one = addNumber(one);

		one = addNumber(one);

		persons.set(0, one);

	}

	@When("a patient phone list is retrieved")
	public void a_patient_phone_list_is_retrieved() {

		distinctPersons = PatientHelper.distinctNumbers(persons);

	}

	@Then("the patient phone list is distinct")
	public void the_patient_phone_list_is_distinct() {

		int duplicates = 0;
		Map<String, TeleEntityLocatorParticipation> mapping = new HashMap<String, TeleEntityLocatorParticipation>();

		for (Person person : distinctPersons) {
			Collection<TeleEntityLocatorParticipation> phoneNumbers = person.phoneNumbers();
			for (TeleEntityLocatorParticipation numb : phoneNumbers) {
				TeleLocator pl =  numb.getLocator();
				if (!mapping.containsKey(pl.getPhoneNbrTxt())) {
					mapping.put(pl.getPhoneNbrTxt(), numb);
				} else {
					duplicates++;

				}
			}
		}

		assertThat(duplicates).isZero();

	}
	
	private List<Person> getRandomPersons(int count) {
		List<Person> persons = new ArrayList<>();
		for (long i = 0; i < count; i++) {
			long id = 20000000L + i;
			persons.add(generateRandomPerson(id, "US"));
		}
		return persons;
	}

	private Person generateRandomPerson(long id, final String local) {
		Instant now = Instant.now();

		Faker faker = new Faker();
		final String firstName = faker.name().firstName();
		final String middleName = faker.name().firstName();
		final String lastName = faker.name().lastName();

		Person person = new Person(new PatientCommand.AddPatient(id, local, RandomUtil.dateInPast(),
				RandomUtil.getRandomFromArray(Gender.values()), RandomUtil.getRandomFromArray(Gender.values()),
				RandomUtil.getRandomFromArray(Deceased.values()), null, null,
				RandomUtil.getRandomFromArray(EthnicityMother.ETHNICITY_LIST), now,
				"Test Patient Created by PersonMother", null, CREATED_BY_ID, now));

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

		// Tele locator entry
		person.add(new PatientCommand.AddPhoneNumber(id, id + 40000L, RandomUtil.getRandomDateInPast(), "PH", "H",
				randomPhoneNumber(), null, CREATED_BY_ID, now));

		return person;
	}

	private Person addNumber(Person aPerson) {
		Instant now = Instant.now();

		aPerson.add(new PatientCommand.AddPhoneNumber(aPerson.getId(), aPerson.getId() + 40000L,
				RandomUtil.getRandomDateInPast(), "PH", "H", "222-555-3333", null, CREATED_BY_ID, now));

		return aPerson;
	}

	private String randomPhoneNumber() {
		StringBuilder number = new StringBuilder();
		Random ran = new Random();
		number.append("222-");
		for (int i = 0; i < 8; i++) {
			if (i == 3) {
				number.append("-");
			} else {
				int digit = ran.nextInt(1, 9);
				number.append(digit);
			}

		}

		return number.toString();
	}

}
