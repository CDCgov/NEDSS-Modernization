package gov.cdc.nbs.patient.profile.phone;

import com.github.javafaker.Faker;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.TeleEntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.TeleLocator;
import gov.cdc.nbs.message.patient.input.PatientInput;
import gov.cdc.nbs.patient.PatientCreateAssertions;
import gov.cdc.nbs.patient.PatientMother;
import gov.cdc.nbs.patient.PatientService;
import gov.cdc.nbs.patient.TestPatient;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.support.TestActive;
import gov.cdc.nbs.support.TestAvailable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PatientProfilePhoneSteps {

    private final Faker faker = new Faker();

    @Autowired
    PatientMother mother;

    @Autowired
    TestAvailable<PatientIdentifier> patients;

    @Autowired
    TestActive<PatientInput> input;

    @Autowired
    TestPatient patient;
    
    
    @Autowired
    PatientService patientService;
    
    List<Person> persons;

    @Given("the patient has a phone")
    public void the_patient_has_a_phone() {
        mother.withPhone(patients.one());
       
    }
    
	@Given("the patient has multiple phones")
	public void the_patient_has_multiple_phones() {
		persons = new ArrayList<>();
		mother.withPhone(patients.one());

		PatientInput.PhoneNumber phoneNumber = new PatientInput.PhoneNumber();
		phoneNumber.setType("PH");
		phoneNumber.setUse("H");
		phoneNumber.setNumber(faker.phoneNumber().cellPhone());
		phoneNumber.setExtension(faker.phoneNumber().extension());

		this.input.active().getPhoneNumbers().add(phoneNumber);

	}

    @Given("the new patient's phone number is entered")
    public void the_new_patient_phone_number_is_entered() {

        PatientInput.PhoneNumber phoneNumber = new PatientInput.PhoneNumber();
        phoneNumber.setType("PH");
        phoneNumber.setUse("H");
        phoneNumber.setNumber(faker.phoneNumber().cellPhone());
        phoneNumber.setExtension(faker.phoneNumber().extension());

        this.input.active().getPhoneNumbers().add(phoneNumber);
        
        
    }
    
	@When("a patient phone list is retrieved")
	@Transactional
	public void a_patient_phone_list_is_retrieved() {
		PatientInput.PhoneNumber duplicate = this.input.active().getPhoneNumbers().get(0);
		duplicate.setUse("C");
		this.input.active().getPhoneNumbers().add(duplicate);

		Person actual = patient.managed();
		persons = patientService.distinctNumbers(List.of(actual));

	}

    @Then("the new patient has the entered phone number")
    @Transactional
    public void the_new_patient_has_the_entered_phone_number() {
        Person actual = patient.managed();

        Collection<TeleEntityLocatorParticipation> phoneNumbers = actual.phoneNumbers();

        if(!phoneNumbers.isEmpty()) {

            assertThat(phoneNumbers)
                .satisfiesExactlyInAnyOrder(PatientCreateAssertions.containsPhoneNumbers(input.active().getPhoneNumbers()));


        }

    }
    
    @Then("the patient phone list is distinct")
    @Transactional
    public void the_patient_phone_list_is_distinct() {
    	Person actual = patient.managed();
    	int duplicates = 0;
    	Map<String, TeleEntityLocatorParticipation> mapping = new HashMap<String, TeleEntityLocatorParticipation>();
    	Collection<TeleEntityLocatorParticipation> phoneNumbers = actual.phoneNumbers();

    	for (TeleEntityLocatorParticipation numb : phoneNumbers) {
    		TeleLocator pl = ((TeleEntityLocatorParticipation) numb).getLocator();
    		if (!mapping.containsKey(pl.getPhoneNbrTxt())) {
    			mapping.put(pl.getPhoneNbrTxt(), numb);
    		} else {
    			duplicates++;

    		}
    	}
    	assertThat(duplicates).isEqualTo(0);

    }
}
