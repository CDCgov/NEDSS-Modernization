package gov.cdc.nbs.questionbank.page.content.reorder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.page.content.reorder.models.ReorderRequest;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.support.PageMother;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@Transactional
public class ReorderSteps {

    @Autowired
    private PageMother pageMother;

    @Autowired
    private ReorderController controller;

    @Autowired
    private WaTemplateRepository templateRepository;

    @Autowired
    private ExceptionHolder exceptionHolder;

    @When("I send a reorder request for a tab")
    public void i_send_a_tab_reorder_request() {
        WaTemplate page = pageMother.brucellosis();
        List<WaUiMetadata> tabs = page.getUiMetadata().stream()
                .filter(w -> w.getNbsUiComponentUid() == 1010L)
                .toList();
        try {
            // Reorder first tab to after second tab
            controller.reorderItem(
                    page.getId(),
                    new ReorderRequest(
                            tabs.get(0).getId(),
                            tabs.get(1).getId()));
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("the tab is reordered")
    public void tab_is_reordered() {
        assertNull(exceptionHolder.getException());
        List<WaUiMetadata> content = templateRepository.findById(pageMother.brucellosis().getId())
                .orElseThrow()
                .getUiMetadata();
        content.sort((a, b) -> a.getOrderNbr() < b.getOrderNbr() ? -1 : 1);
        assertEquals("Second tab", content.get(1).getQuestionLabel());
        assertEquals("Second section", content.get(2).getQuestionLabel());
        assertEquals("Second subsection", content.get(3).getQuestionLabel());
        assertEquals("Second question", content.get(4).getQuestionLabel());
        assertEquals("First tab", content.get(5).getQuestionLabel());
        assertEquals("First section", content.get(6).getQuestionLabel());
        assertEquals("First subsection", content.get(7).getQuestionLabel());
        assertEquals("First question", content.get(8).getQuestionLabel());
    }

    @When("I send a reorder request for a section")
    public void i_send_a_section_reorder_request() {
        WaTemplate page = pageMother.brucellosis();
        List<WaUiMetadata> sections = page.getUiMetadata().stream()
                .filter(w -> w.getNbsUiComponentUid() == 1015L)
                .toList();

        try {
            // Reorder first section to after second section
            controller.reorderItem(
                    page.getId(),
                    new ReorderRequest(
                            sections.get(0).getId(),
                            sections.get(1).getId()));
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("the section is reordered")
    public void section_is_reordered() {
        assertNull(exceptionHolder.getException());
        List<WaUiMetadata> content = templateRepository.findById(pageMother.brucellosis().getId())
                .orElseThrow()
                .getUiMetadata();
        content.sort((a, b) -> a.getOrderNbr() < b.getOrderNbr() ? -1 : 1);
        assertEquals("First tab", content.get(1).getQuestionLabel());
        assertEquals("Second tab", content.get(2).getQuestionLabel());
        assertEquals("Second section", content.get(3).getQuestionLabel());
        assertEquals("Second subsection", content.get(4).getQuestionLabel());
        assertEquals("Second question", content.get(5).getQuestionLabel());
        assertEquals("First section", content.get(6).getQuestionLabel());
        assertEquals("First subsection", content.get(7).getQuestionLabel());
        assertEquals("First question", content.get(8).getQuestionLabel());
    }

    @When("I send a reorder request for a subsection")
    public void i_send_a_subsection_reorder_request() {
        WaTemplate page = pageMother.brucellosis();
        List<WaUiMetadata> subsections = page.getUiMetadata().stream()
                .filter(w -> w.getNbsUiComponentUid() == 1016L)
                .toList();
        try {
            // Reorder first section to after second section
            controller.reorderItem(
                    page.getId(),
                    new ReorderRequest(
                            subsections.get(0).getId(),
                            subsections.get(1).getId()));
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("the subsection is reordered")
    public void subsection_is_reordered() {
        assertNull(exceptionHolder.getException());
        List<WaUiMetadata> content = templateRepository.findById(pageMother.brucellosis().getId())
                .orElseThrow()
                .getUiMetadata();
        content.sort((a, b) -> a.getOrderNbr() < b.getOrderNbr() ? -1 : 1);
        assertEquals("First tab", content.get(1).getQuestionLabel());
        assertEquals("First section", content.get(2).getQuestionLabel());
        assertEquals("Second tab", content.get(3).getQuestionLabel());
        assertEquals("Second section", content.get(4).getQuestionLabel());
        assertEquals("Second subsection", content.get(5).getQuestionLabel());
        assertEquals("Second question", content.get(6).getQuestionLabel());
        assertEquals("First subsection", content.get(7).getQuestionLabel());
        assertEquals("First question", content.get(8).getQuestionLabel());
    }

    @When("I send a reorder request for a question")
    public void i_send_a_question_reorder_request() {
        WaTemplate page = pageMother.brucellosis();
        List<WaUiMetadata> questions = page.getUiMetadata().stream()
                .filter(w -> w.getNbsUiComponentUid() == 1009L)
                .toList();
        try {
            // Reorder first section to after second section
            controller.reorderItem(
                    page.getId(),
                    new ReorderRequest(
                            questions.get(0).getId(),
                            questions.get(1).getId()));
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("the question is reordered")
    public void question_is_reordered() {
        assertNull(exceptionHolder.getException());
        List<WaUiMetadata> content = templateRepository.findById(pageMother.brucellosis().getId())
                .orElseThrow()
                .getUiMetadata();
        content.sort((a, b) -> a.getOrderNbr() < b.getOrderNbr() ? -1 : 1);
        assertEquals("First tab", content.get(1).getQuestionLabel());
        assertEquals("First section", content.get(2).getQuestionLabel());
        assertEquals("First subsection", content.get(3).getQuestionLabel());
        assertEquals("Second tab", content.get(4).getQuestionLabel());
        assertEquals("Second section", content.get(5).getQuestionLabel());
        assertEquals("Second subsection", content.get(6).getQuestionLabel());
        assertEquals("Second question", content.get(7).getQuestionLabel());
        assertEquals("First question", content.get(8).getQuestionLabel());
    }

}
