package gov.cdc.nbs.patient;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.TeleEntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.TeleLocator;
import gov.cdc.nbs.patient.util.PatientHelper;
import gov.cdc.nbs.support.PersonMother;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class PatientHelperSteps {

	private List<Person> persons;

	private List<Person> distinctPersons;

	@Given("the patient has multiple phones")
	public void the_patient_has_multiple_phones() {

		persons = new ArrayList<>();
		distinctPersons = new ArrayList<>();
		persons = PersonMother.getRandomPersons(3);

		Person one = persons.get(0);

		one = PersonMother.addNumber(one);

		one = PersonMother.addNumber(one);

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
			System.out.println("MAPNUMBERLIST========: " + person.phoneNumbers());
			Collection<TeleEntityLocatorParticipation> phoneNumbers = person.phoneNumbers();
			for (TeleEntityLocatorParticipation numb : phoneNumbers) {
				TeleLocator pl =  numb.getLocator();
				System.out.println("CHECKINGNUMBER========: " + pl.getPhoneNbrTxt());
				if (!mapping.containsKey(pl.getPhoneNbrTxt())) {
					System.out.println("MAPNUMBER========: " + pl.getPhoneNbrTxt());
					mapping.put(pl.getPhoneNbrTxt(), numb);
				} else {
					duplicates++;

				}
			}
		}

		assertThat(duplicates).isZero();

	}

}
