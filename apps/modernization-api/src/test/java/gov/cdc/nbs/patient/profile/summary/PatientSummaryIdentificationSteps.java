package gov.cdc.nbs.patient.profile.summary;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.entity.odse.EntityId;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.patient.profile.summary.PatientSummary.PatientSummaryIdentification;
import gov.cdc.nbs.support.TestActive;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@Transactional
public class PatientSummaryIdentificationSteps {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private TestActive<PatientSummary> summary;

    @Autowired
    private PatientSummaryIdentificationResolver summaryIdentificationResolver;

    private List<PatientSummaryIdentification> summaryIdentifications;
    private Exception exception;

    @When("patient summary identifications are requested by patient summary")
    public void a_patient_summary_identification_is_requested_by_patient_identifier() {
        try {
            summaryIdentifications = summaryIdentificationResolver.resolve(summary.maybeActive().orElse(null));
        } catch (Exception e) {
            this.exception = e;
        }
    }

    @Then("the patient summary identifications are found")
    public void the_summary_identifications_are_found() {
        assertNull(exception);
        assertNotNull(summaryIdentifications);
        assertFalse(summaryIdentifications.isEmpty());
    }

    @Then("the patient summary identifications are not accessible")
    public void the_summary_identification_are_not_accessible() {
        assertThat(summaryIdentifications).isNull();
        assertThat(this.exception).isInstanceOf(AccessDeniedException.class);
    }

    @Then("the summary identifications are in the correct order")
    public void the_summary_identifications_are_in_the_correct_order() {
        Person person = this.entityManager.find(Person.class, summary.active().patient());
        List<EntityId> sortedList = new ArrayList<>();
        sortedList.addAll(person.identifications());
        sortedList.sort((a, b) -> a.getAsOfDate().isAfter(b.getAsOfDate()) ? -1 : 1);
        assertEquals(sortedList.get(0).getRootExtensionTxt(), summaryIdentifications.get(0).value());
        assertEquals(sortedList.get(1).getRootExtensionTxt(), summaryIdentifications.get(1).value());
    }

    @Then("only two summary identifications are returned")
    public void only_two_summary_identifications_are_returned() {
        assertEquals(2, summaryIdentifications.size());
    }

}
