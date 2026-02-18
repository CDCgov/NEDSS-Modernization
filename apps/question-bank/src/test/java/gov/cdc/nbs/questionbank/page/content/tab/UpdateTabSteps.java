package gov.cdc.nbs.questionbank.page.content.tab;

import static org.junit.jupiter.api.Assertions.assertEquals;

import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.PageMother;
import gov.cdc.nbs.questionbank.page.content.tab.request.UpdateTabRequest;
import gov.cdc.nbs.questionbank.page.content.tab.response.Tab;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomUtils;

@Transactional
public class UpdateTabSteps {
  private final TabController tabController;

  private final WaUiMetadataRepository waUiMetadataRepository;

  private final PageMother pageMother;

  private final ExceptionHolder exceptionHolder;

  private final UserDetailsProvider user;

  private final Boolean visibility = RandomUtils.nextBoolean();
  private Tab response;

  UpdateTabSteps(
      final TabController tabController,
      final WaUiMetadataRepository waUiMetadataRepository,
      final PageMother pageMother,
      final ExceptionHolder exceptionHolder,
      final UserDetailsProvider user) {
    this.tabController = tabController;
    this.waUiMetadataRepository = waUiMetadataRepository;
    this.pageMother = pageMother;
    this.exceptionHolder = exceptionHolder;
    this.user = user;
  }

  @Given("I send an update tab request")
  public void i_send_an_update_tab_request() {
    WaTemplate template = pageMother.one();
    WaUiMetadata tab =
        template.getUiMetadata().stream()
            .filter(t -> t.getNbsUiComponentUid() == 1010L)
            .findFirst()
            .orElseThrow();
    UpdateTabRequest request = new UpdateTabRequest("Updated tab name", visibility);
    try {
      response =
          tabController.updateTab(
              template.getId(), tab.getId(), request, user.getCurrentUserDetails());
    } catch (AccessDeniedException | AuthenticationCredentialsNotFoundException e) {
      exceptionHolder.setException(e);
    }
  }

  @Then("the tab is updated")
  public void the_tab_updated_successfully() {
    // response validation
    assertEquals("Updated tab name", response.name());
    assertEquals(visibility, response.visible());

    // database validation
    WaUiMetadata metadata = waUiMetadataRepository.findById(response.id()).orElseThrow();
    assertEquals(1010L, metadata.getNbsUiComponentUid().longValue());
    assertEquals("Updated tab name", metadata.getQuestionLabel());
    assertEquals(visibility ? "T" : "F", metadata.getDisplayInd());
    assertEquals(metadata.getId(), response.id());
  }
}
