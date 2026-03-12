package gov.cdc.nbs.questionbank.page.content.section;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.questionbank.page.content.section.model.Section;
import gov.cdc.nbs.questionbank.page.content.section.request.CreateSectionRequest;
import gov.cdc.nbs.questionbank.page.content.section.request.UpdateSectionRequest;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pages/{page}/sections/")
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
class SectionController {

  private final SectionCreator creator;
  private final SectionDeleter deleter;
  private final SectionUpdater updater;

  SectionController(
      final SectionCreator creator, final SectionDeleter deleter, final SectionUpdater updater) {
    this.creator = creator;
    this.deleter = deleter;
    this.updater = updater;
  }

  @PostMapping
  Section createSection(
      @PathVariable Long page,
      @RequestBody CreateSectionRequest request,
      @Parameter(hidden = true) @AuthenticationPrincipal final NbsUserDetails details) {
    return creator.create(page, request, details.getId());
  }

  @DeleteMapping("{sectionId}")
  void deleteSection(
      @PathVariable Long page,
      @PathVariable Long sectionId,
      @Parameter(hidden = true) @AuthenticationPrincipal final NbsUserDetails details) {
    deleter.deleteSection(page, sectionId, details.getId());
  }

  @PutMapping("{section}")
  Section updateSection(
      @PathVariable Long page,
      @PathVariable Long section,
      @RequestBody UpdateSectionRequest request,
      @Parameter(hidden = true) @AuthenticationPrincipal final NbsUserDetails details) {
    return updater.update(page, section, request, details.getId());
  }
}
