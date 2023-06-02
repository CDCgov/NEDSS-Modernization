package gov.cdc.nbs.patient.profile.phone;

import com.github.javafaker.Faker;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.TeleEntityLocatorParticipation;
import gov.cdc.nbs.message.patient.input.PatientInput;
import gov.cdc.nbs.patient.PatientAssertions;
import gov.cdc.nbs.patient.TestPatient;
import gov.cdc.nbs.support.TestActive;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class PatientProfilePhoneSteps {

    private final Faker faker = new Faker();

    @Autowired
    TestActive<PatientInput> input;

    @Autowired
    TestPatient patient;

    @Given("the new patient's phone number is entered")
    public void the_new_patient_phone_number_is_entered() {

        PatientInput.PhoneNumber phoneNumber = new PatientInput.PhoneNumber();
        phoneNumber.setType("PH");
        phoneNumber.setUse("H");
        phoneNumber.setNumber(faker.phoneNumber().cellPhone());
        phoneNumber.setExtension(faker.phoneNumber().extension());

        this.input.active().getPhoneNumbers().add(phoneNumber);
    }

    @Then("the new patient has the entered phone number")
    @Transactional
    public void the_new_patient_has_the_entered_phone_number() {
        Person actual = patient.managed();

        Collection<TeleEntityLocatorParticipation> phoneNumbers = actual.phoneNumbers();

        if(!phoneNumbers.isEmpty()) {

            assertThat(phoneNumbers)
                .satisfiesExactlyInAnyOrder(PatientAssertions.containsPhoneNumbers(input.active().getPhoneNumbers()));


        }

    }
}
