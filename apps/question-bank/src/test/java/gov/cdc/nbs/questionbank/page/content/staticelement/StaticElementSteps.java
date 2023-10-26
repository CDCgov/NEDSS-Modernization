package gov.cdc.nbs.questionbank.page.content.staticelement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.springframework.beans.factory.annotation.Autowired;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.content.staticelement.request.AddStaticElementDefaultRequest;
import gov.cdc.nbs.questionbank.page.content.tab.repository.WaUiMetaDataRepository;
import gov.cdc.nbs.questionbank.support.PageMother;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StaticElementSteps {

    @Autowired
    private PageStaticController pageStaticController;

    @Autowired
    private WaUiMetaDataRepository waUiMetaDataRepository;

    @Autowired
    private PageMother mother;

    private Long lineSeparatorId;

    @When("I send an add line separator request")
    public void i_send_an_add_tab_request() {
        // create request
        WaTemplate temp = mother.one();
        WaUiMetadata subsection = temp.getUiMetadata().stream()
                .filter(ui -> ui.getNbsUiComponentUid() == 1016L)
                .findFirst()
                .orElseThrow();

        lineSeparatorId = pageStaticController.addStaticLineSeparator(
                temp.getId(),
                new AddStaticElementDefaultRequest(null, subsection.getId()))
                .componentId();
    }

    @Then("a line separator is created")
    public void a_line_separator_is_created() {
        assertNotNull(lineSeparatorId);
        WaUiMetadata lineSeparatorEnt = waUiMetaDataRepository.findById(lineSeparatorId).orElseThrow();
        assertEquals(lineSeparatorId, lineSeparatorEnt.getId());
        assertEquals(1012L, lineSeparatorEnt.getNbsUiComponentUid().longValue());
    }
}
