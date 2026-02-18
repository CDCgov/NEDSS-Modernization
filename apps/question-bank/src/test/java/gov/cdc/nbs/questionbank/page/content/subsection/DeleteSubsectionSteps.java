package gov.cdc.nbs.questionbank.page.content.subsection;

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
import java.util.List;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class DeleteSubsectionSteps {
  private final SubSectionController subsectionController;

  private final WaUiMetadataRepository waUiMetadataRepository;

  private final PageMother pageMother;

  private final ExceptionHolder exceptionHolder;

  private final UserDetailsProvider user;

  private WaUiMetadata subsectionToDelete;

  DeleteSubsectionSteps(
      final SubSectionController subsectionController,
      final WaUiMetadataRepository waUiMetadataRepository,
      final PageMother pageMother,
      final ExceptionHolder exceptionHolder,
      final UserDetailsProvider user) {
    this.subsectionController = subsectionController;
    this.waUiMetadataRepository = waUiMetadataRepository;
    this.pageMother = pageMother;
    this.exceptionHolder = exceptionHolder;
    this.user = user;
  }

  @Given("I send a delete subsection request")
  public void i_send_a_delete_subsection_request() {
    WaTemplate page = pageMother.one();

    List<WaUiMetadata> subsections =
        page.getUiMetadata().stream().filter(u -> u.getNbsUiComponentUid() == 1016L).toList();

    subsectionToDelete = subsections.getLast();

    try {
      subsectionController.deleteSubSection(
          page.getId(), subsectionToDelete.getId(), user.getCurrentUserDetails());
    } catch (AccessDeniedException | AuthenticationCredentialsNotFoundException e) {
      exceptionHolder.setException(e);
    }
  }

  @Then("the subsection is deleted")
  public void the_subsection_is_deleted() {
    assertNotNull(subsectionToDelete);
    assertTrue(waUiMetadataRepository.findById(subsectionToDelete.getId()).isEmpty());
  }
}
