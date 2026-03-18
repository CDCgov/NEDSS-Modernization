package gov.cdc.nbs.questionbank.page.content.section;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.PageMother;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class DeleteSectionSteps {

  private final SectionController sectionController;

  private final WaUiMetadataRepository repository;

  private final PageMother pageMother;

  private final ExceptionHolder exceptionHolder;

  private final UserDetailsProvider user;

  private WaUiMetadata sectionToDelete;

  DeleteSectionSteps(
      final SectionController sectionController,
      final WaUiMetadataRepository repository,
      final PageMother pageMother,
      final ExceptionHolder exceptionHolder,
      final UserDetailsProvider user) {
    this.sectionController = sectionController;
    this.repository = repository;
    this.pageMother = pageMother;
    this.exceptionHolder = exceptionHolder;
    this.user = user;
  }

  @Given("I send a delete section request")
  public void i_send_a_delete_section_request() {
    WaTemplate page = pageMother.one();

    sectionToDelete =
        page.getUiMetadata().stream()
            .filter(u -> u.getNbsUiComponentUid() == 1015L)
            .findFirst()
            .orElseThrow();

    try {
      sectionController.deleteSection(
          page.getId(), sectionToDelete.getId(), user.getCurrentUserDetails());
    } catch (AccessDeniedException | AuthenticationCredentialsNotFoundException e) {
      exceptionHolder.setException(e);
    }
  }

  @Then("the section is deleted")
  public void the_section_is_deleted() {
    assertNotNull(sectionToDelete);
    assertTrue(repository.findById(sectionToDelete.getId()).isEmpty());
  }
}
