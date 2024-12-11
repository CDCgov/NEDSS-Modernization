package gov.cdc.nbs.questionbank.programarea;

import gov.cdc.nbs.questionbank.programarea.model.ProgramArea;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.support.ProgramAreaHolder;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProgramAreaFinderSteps {

    private final ProgramAreaHolder programAreaHolder;

    private final ExceptionHolder exceptionHolder;

    private final ProgramAreaController controller;

    ProgramAreaFinderSteps(
        final ProgramAreaHolder programAreaHolder,
        final ExceptionHolder exceptionHolder,
        final ProgramAreaController controller
    ) {
        this.programAreaHolder = programAreaHolder;
        this.exceptionHolder = exceptionHolder;
        this.controller = controller;
    }

    @When("I list all program areas")
    public void i_list_all_program_areas() {
        try {
            programAreaHolder.setProgramAreas(controller.getProgramAreas());
        } catch (AccessDeniedException | AuthenticationCredentialsNotFoundException e) {
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
