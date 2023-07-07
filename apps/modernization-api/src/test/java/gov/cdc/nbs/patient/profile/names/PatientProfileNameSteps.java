package gov.cdc.nbs.patient.profile.names;

import com.github.javafaker.Faker;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.message.patient.input.PatientInput;
import gov.cdc.nbs.patient.PatientCreateAssertions;
import gov.cdc.nbs.patient.PatientMother;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.support.TestActive;
import gov.cdc.nbs.support.TestAvailable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class PatientProfileNameSteps {

    private final Faker faker = new Faker();

    @Autowired
    PatientMother mother;

    @Autowired
    TestAvailable<PatientIdentifier> patients;

    @Autowired
    TestActive<PatientInput> input;

    @Autowired
    TestActive<Person> patient;

    @Given("the patient has a name")
    public void the_patient_has_a_name() {
        mother.withName(patients.one());
    }

    @Given("the new patient's name is entered")
    public void the_new_patient_name_is_entered() {

        PatientInput.Name name = new PatientInput.Name();
        name.setUse(PatientInput.NameUseCd.L);
        name.setFirst(faker.name().firstName());
        name.setLast(faker.name().lastName());

        this.input.active().getNames().add(name);
    }

    @Then("the new patient has the entered name")
    public void the_new_patient_has_the_entered_name() {
        Person actual = patient.active();

        if (!actual.getNames().isEmpty()) {
            assertThat(actual.getNames())
                .satisfiesExactly(PatientCreateAssertions.containsNames(this.input.active().getNames()));
        }
    }
}
