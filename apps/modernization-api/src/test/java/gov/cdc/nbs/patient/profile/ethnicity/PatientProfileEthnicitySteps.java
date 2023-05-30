package gov.cdc.nbs.patient.profile.ethnicity;

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
public class PatientProfileEthnicitySteps {

    @Autowired
    TestActive<PatientInput> input;

    @Autowired
    TestActive<Person> patient;

    @Given("the new patient's ethnicity is entered")
    public void the_new_patient_ethnicity_is_entered() {
        PatientInput active = this.input.active();

        active.setEthnicity(RandomUtil.getRandomString());
    }

    @Then("the new patient has the entered ethnicity")
    public void the_new_patient_has_the_entered_ethnicity() {
        Person actual = this.patient.active();
        PatientInput expected = this.input.active();

        assertThat(actual)
            .returns(expected.getAsOf(), Person::getAsOfDateEthnicity)
            .returns(expected.getEthnicity(), Person::getEthnicGroupInd);


    }
}
