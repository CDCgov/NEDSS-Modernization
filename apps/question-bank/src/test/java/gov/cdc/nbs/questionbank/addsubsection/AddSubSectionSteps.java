package gov.cdc.nbs.questionbank.addsubsection;

import gov.cdc.nbs.questionbank.addsubsection.controller.AddSubSectionController;
import gov.cdc.nbs.questionbank.addsubsection.model.CreateSubSectionRequest;
import gov.cdc.nbs.questionbank.addtab.repository.WaUiMetaDataRepository;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.addtab.WaUiMetadata;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.support.PageMother;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import static org.junit.Assert.assertEquals;

public class AddSubSectionSteps {

    @Autowired
    private AddSubSectionController subSectionController;

    @Autowired
    private WaUiMetaDataRepository waUiMetadataRepository;

    @Autowired
    private PageMother pageMother;

    private CreateSubSectionResponse response;

    @Autowired
    private ExceptionHolder exceptionHolder;

    @Given("I send an add subsection request with {string}")
    public void i_send_an_add_subsection_request(String visibility) {
        WaTemplate template = pageMother.one();
        CreateSubSectionRequest createSubSectionRequest = new CreateSubSectionRequest(1L, template.getId(), "Local SubSection", visibility);
        try {
            response = subSectionController.createSubSection(createSubSectionRequest);
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("the section is created with {string}")
    public void the_subsection_created_successfully(String visibility) {
        WaUiMetadata metadata = waUiMetadataRepository.findById(1L).orElseThrow();
        assertEquals(1016L, metadata.getNbsUiComponentUid().longValue());
        assertEquals("Local SubSection", metadata.getQuestionLabel());
        assertEquals(visibility, metadata.getDisplayInd());
    }

}
