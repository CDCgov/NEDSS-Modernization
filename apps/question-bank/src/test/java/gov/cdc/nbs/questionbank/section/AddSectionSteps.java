package gov.cdc.nbs.questionbank.section;

import gov.cdc.nbs.questionbank.section.model.CreateSectionRequest;
import gov.cdc.nbs.questionbank.tab.repository.WaUiMetaDataRepository;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.addtab.WaUiMetadata;
import gov.cdc.nbs.questionbank.section.model.CreateSectionResponse;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.support.PageMother;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import static org.junit.Assert.assertEquals;

public class AddSectionSteps {

    @Autowired
    private gov.cdc.nbs.questionbank.section.controller.SectionController SectionController;

    @Autowired
    private WaUiMetaDataRepository waUiMetadataRepository;

    @Autowired
    private PageMother pageMother;

    private CreateSectionResponse response;

    @Autowired
    private ExceptionHolder exceptionHolder;

    @Given("I send an add section request with {string}")
    public void i_send_an_add_section_request(String visibility) {
        WaTemplate template = pageMother.one();
        CreateSectionRequest createSectionRequest = new CreateSectionRequest(1L, template.getId(), "Local SubSection", visibility);
        try {
            response = SectionController.createSection(createSectionRequest);
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("the section is created with {string}")
    public void the_section_created_successfully(String visibility) {
        WaUiMetadata metadata = waUiMetadataRepository.findById(1L).orElseThrow();
        assertEquals(1016L, metadata.getNbsUiComponentUid().longValue());
        assertEquals("Local Section", metadata.getQuestionLabel());
        assertEquals(visibility, metadata.getDisplayInd());
    }

}
