package gov.cdc.nbs.questionbank.page.content.subsection;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.PageMother;
import gov.cdc.nbs.questionbank.page.content.subsection.request.GroupSubSectionRequest;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.ArrayList;
import java.util.List;
import org.springframework.test.web.servlet.ResultActions;

public class GroupSubsectionSteps {

  private final PageMother pageMother;
  private final SubsectionRequester requester;
  private final Active<ResultActions> groupResponse;

  GroupSubsectionSteps(PageMother pageMother, SubsectionRequester requester) {
    this.pageMother = pageMother;
    this.requester = requester;
    groupResponse = new Active<>();
  }

  @When("I send a group subsection request")
  public void i_send_a_group_subsection_request() throws Exception {
    WaTemplate temp = pageMother.one();
    WaUiMetadata subsection =
        pageMother.pageContent().stream()
            .filter(m -> m.getNbsUiComponentUid().equals(1016L)) // find subsection
            .findFirst()
            .orElseThrow();

    groupResponse.active(
        requester.subsectionGroup(
            temp.getId(),
            subsection.getId(),
            new GroupSubSectionRequest("BLOCK_NAME", getBatchList(), 2)));
  }

  @Then("the subsection is grouped")
  public void the_subsection_is_grouped() throws Exception {
    groupResponse.active().andExpect(status().isOk());
  }

  @Then("a bad request exception is thrown")
  public void the_subsection_is_not_grouped() throws Exception {
    groupResponse.active().andExpect(status().isBadRequest());
  }

  @Then("a redirect is initiated")
  public void a_redirect_is_initiated() throws Exception {
    groupResponse.active().andExpect(status().isFound());
  }

  List<GroupSubSectionRequest.Batch> getBatchList() {
    WaUiMetadata question =
        pageMother.pageContent().stream()
            .filter(ui -> ui.getNbsUiComponentUid().equals(1008L))
            .findFirst()
            .orElse(null);
    List<GroupSubSectionRequest.Batch> batchList = new ArrayList<>();

    if (question != null) {
      batchList.add(new GroupSubSectionRequest.Batch(question.getId(), true, "header_", 100));
    }
    return batchList;
  }
}
