package gov.cdc.nbs.questionbank.page.content.section;

import static org.junit.jupiter.api.Assertions.*;

import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.PageMother;
import gov.cdc.nbs.questionbank.page.content.section.model.Section;
import gov.cdc.nbs.questionbank.page.content.section.request.CreateSectionRequest;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class CreateSectionSteps {

  private final SectionController sectionController;

  private final WaUiMetadataRepository repository;

  private final PageMother pageMother;

  private final ExceptionHolder exceptionHolder;

  private final UserDetailsProvider user;

  private Section section;

  CreateSectionSteps(
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

  @Given("I send a create section request")
  public void i_send_a_create_section_request() {
    WaTemplate page = pageMother.one();

    WaUiMetadata tab =
        page.getUiMetadata().stream()
            .filter(u -> u.getNbsUiComponentUid() == 1010L)
            .findFirst()
            .orElseThrow();
    try {
      section =
          sectionController.createSection(
              page.getId(),
              new CreateSectionRequest(tab.getId(), "new section", true),
              user.getCurrentUserDetails());
    } catch (AccessDeniedException | AuthenticationCredentialsNotFoundException e) {
      exceptionHolder.setException(e);
    }
  }

  @Then("the section is created")
  public void the_section_is_created() {
    assertNotNull(section);
    assertEquals("new section", section.name());
    assertTrue(section.visible());

    WaUiMetadata sectionMetadata = repository.findById(section.id()).orElseThrow();
    assertEquals("new section", sectionMetadata.getQuestionLabel());
    assertEquals("T", sectionMetadata.getDisplayInd());
  }
}
