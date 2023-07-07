package gov.cdc.nbs.questionbank.programarea;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import gov.cdc.nbs.questionbank.programarea.model.ProgramArea;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.support.ProgramAreaHolder;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ProgramAreaFinderSteps {

    @Autowired
    private ProgramAreaHolder programAreaHolder;

    @Autowired
    private ExceptionHolder exceptionHolder;

    @Autowired
    private ProgramAreaController controller;

    @When("I list all program areas")
    public void i_list_all_program_areas() {
        try {
            programAreaHolder.setProgramAreas(controller.getProgramAreas());
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }


    @Then("program areas are returned")
    public void program_areas_are_returned() {
        List<ProgramArea> programAreas = programAreaHolder.getProgramAreas();
        assertNotNull(programAreas);
        assertFalse(programAreas.isEmpty());

        // test-db contains STD program area, find and validate
        ProgramArea std = programAreas.stream().filter(pa -> pa.value().equals("STD")).findFirst().orElseThrow();

        assertEquals("STD", std.value());
        assertEquals("STD", std.display());
        assertEquals(15, std.nbsId().intValue());
        assertEquals("Active", std.status());
    }
}
