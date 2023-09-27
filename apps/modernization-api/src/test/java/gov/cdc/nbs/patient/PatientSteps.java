package gov.cdc.nbs.patient;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.support.TestActive;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Transactional
public class PatientSteps {

    @Autowired
    TestActive<PatientIdentifier> patient;

    @Autowired
    PatientMother mother;

    //  Make sure that patients are cleaned up after everything else
    @Before( order = 15000)
    public void clean() {
        mother.reset();
    }

    @Given("I have a(nother) patient")
    public void i_have_a_patient() {
        mother.create();
    }

    @Given("the patient is inactive")
    public void the_patient_is_inactive() {
        mother.deleted(patient.active());
    }

    @Given("the patient has a {string} of {string}")
    public void the_patient_has_a_field_with_a_value_of(
        final String field,
        final String value
    ) {

        PatientIdentifier identifier = this.patient.active();

        switch (field.toLowerCase()) {
            case "first name" -> mother.withName(
                identifier,
                "L",
                value,
                null
            );

            case "last name" -> mother.withName(
                identifier,
                "L",
                null,
                value
            );

            case "birthday" -> mother.withBirthday(
                identifier,
                LocalDate.parse(value)
            );

            case "phone number" -> mother.withPhone(
                identifier,
                value
            );

            case "address" -> mother.withAddress(
                identifier,
                value,
                null,
                null,
                null
            );

            case "city" -> mother.withAddress(
                identifier,
                null,
                value,
                null,
                null
            );
        }

    }
}
