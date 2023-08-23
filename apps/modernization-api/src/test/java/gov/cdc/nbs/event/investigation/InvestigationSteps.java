package gov.cdc.nbs.event.investigation;

import gov.cdc.nbs.entity.odse.PublicHealthCase;
import gov.cdc.nbs.patient.PatientMother;
import gov.cdc.nbs.patient.TestPatientIdentifier;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Transactional
public class InvestigationSteps {

    @Autowired
    TestPatientIdentifier patients;

    @Autowired
    PatientMother patientMother;

    @Autowired
    TestInvestigations investigations;

    @Autowired
    InvestigationMother mother;

    @Autowired
    EntityManager entityManager;

    @Before
    public void clean() {
        mother.reset();
    }

    @Given("the patient is a subject of an investigation")
    public void the_patient_is_a_subject_of_an_investigation() {
        PatientIdentifier revision = patientMother.revise(patients.one());

        mother.investigation(revision.id());
    }

    @Given("the patient is a subject of {int} investigations")
    public void the_patient_is_a_subject_N_investigation(final int n) {
        PatientIdentifier patient = patients.one();

        for (int i = 0; i < n; i++) {

            PatientIdentifier revision = patientMother.revise(patient);

            mother.investigation(revision.id());
        }
    }

    @Given("the investigation has been closed")
    public void the_patient_is_a_subject_of_closed_investigation() {
        PublicHealthCase investigation = this.entityManager.find(PublicHealthCase.class, investigations.one());
        investigation.setInvestigationStatusCd("C");
    }
}
