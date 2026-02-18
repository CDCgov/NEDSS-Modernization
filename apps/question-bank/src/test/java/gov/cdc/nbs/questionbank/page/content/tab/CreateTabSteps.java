package gov.cdc.nbs.questionbank.page.content.tab;

import static org.junit.jupiter.api.Assertions.assertEquals;

import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.PageMother;
import gov.cdc.nbs.questionbank.page.content.tab.request.CreateTabRequest;
import gov.cdc.nbs.questionbank.page.content.tab.response.Tab;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

public class CreateTabSteps {

  private final TabController controller;

  private final WaUiMetadataRepository waUiMetadataRepository;

  private final PageMother pageMother;

  private final ExceptionHolder exceptionHolder;

  private final UserDetailsProvider userDetailsProvider;

  private Tab response;

  CreateTabSteps(
      final TabController controller,
      final WaUiMetadataRepository waUiMetadataRepository,
      final PageMother pageMother,
      final ExceptionHolder exceptionHolder,
      final UserDetailsProvider userDetailsProvider) {
    this.controller = controller;
    this.waUiMetadataRepository = waUiMetadataRepository;
    this.pageMother = pageMother;
    this.exceptionHolder = exceptionHolder;
    this.userDetailsProvider = userDetailsProvider;
  }

  @Given("I send an add tab request with {string}")
  public void i_send_an_add_tab_request(String visibility) {
    WaTemplate template = pageMother.one();
    CreateTabRequest createTabRequest = new CreateTabRequest("Local Tab", visibility.equals("T"));
    try {
      response =
          controller.createTab(
              template.getId(), createTabRequest, userDetailsProvider.getCurrentUserDetails());
    } catch (AccessDeniedException | AuthenticationCredentialsNotFoundException e) {
      exceptionHolder.setException(e);
    }
  }

  @Then("the tab is created with {string}")
  public void the_tab_created_successfully(String visibility) {
    WaUiMetadata metadata = waUiMetadataRepository.findById(response.id()).orElseThrow();
    assertEquals(1010L, metadata.getNbsUiComponentUid().longValue());
    assertEquals("Local Tab", metadata.getQuestionLabel());
    assertEquals(visibility, metadata.getDisplayInd());
  }
}
