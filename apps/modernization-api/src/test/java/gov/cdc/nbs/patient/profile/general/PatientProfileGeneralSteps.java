package gov.cdc.nbs.patient.profile.general;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.message.patient.input.PatientInput;
import gov.cdc.nbs.support.TestActive;
import gov.cdc.nbs.support.util.RandomUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
public class PatientProfileGeneralSteps {

    @Autowired
    TestActive<PatientInput> input;

    @Autowired
    TestActive<Person> patient;

    @Autowired
    PatientGeneralResolver resolver;

    @Given("the new patient's marital status is entered")
    public void the_new_patient_marital_status_is_entered() {
        PatientInput active = this.input.active();

        active.setAsOf(RandomUtil.getRandomDateInPast());
        active.setMaritalStatus(RandomUtil.getRandomString());
    }

    @Then("the new patient has the entered marital status")
    public void the_new_patient_has_the_entered_martial_status() {
        Person actual = patient.active();
        PatientInput expected = this.input.active();

        assertThat(actual)
            .returns(expected.getMaritalStatus(), Person::getMaritalStatusCd);
    }
}
