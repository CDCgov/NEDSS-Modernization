package gov.cdc.nbs.questionbank.page.content.addsubsection;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.content.subsection.SubSectionController;
import gov.cdc.nbs.questionbank.page.content.subsection.request.CreateSubSectionRequest;
import gov.cdc.nbs.questionbank.page.content.subsection.response.CreateSubSectionResponse;
import gov.cdc.nbs.questionbank.page.content.tab.repository.WaUiMetaDataRepository;
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
    private SubSectionController subSectionController;

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
        Long section = template.getUiMetadata().get(1).getId();
        CreateSubSectionRequest createSubSectionRequest =
                new CreateSubSectionRequest(section, "Local SubSection", visibility.equals("T"));
        try {
            response = subSectionController.createSubSection(template.getId(), createSubSectionRequest);
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("the subsection is created with {string}")
    public void the_subsection_created_successfully(String visibility) {
        WaUiMetadata metadata = waUiMetadataRepository.findById(response.uid()).orElseThrow();
        assertEquals(1016L, metadata.getNbsUiComponentUid().longValue());
        assertEquals("Local SubSection", metadata.getQuestionLabel());
        assertEquals(visibility, metadata.getDisplayInd());
    }

}
