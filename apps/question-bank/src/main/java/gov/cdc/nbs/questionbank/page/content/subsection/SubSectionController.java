package gov.cdc.nbs.questionbank.page.content.subsection;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.questionbank.page.content.subsection.model.SubSection;
import gov.cdc.nbs.questionbank.page.content.subsection.request.CreateSubSectionRequest;
import gov.cdc.nbs.questionbank.page.content.subsection.request.GroupSubSectionRequest;
import gov.cdc.nbs.questionbank.page.content.subsection.request.UpdateSubSectionRequest;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pages/{page}/subsections/")
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
class SubSectionController {

  private final SubSectionCreator creator;
  private final SubSectionUpdater updater;
  private final SubSectionDeleter deleter;
  private final SubSectionGrouper grouper;
  private final SubSectionValidator validator;

  SubSectionController(
      final SubSectionCreator createSubSectionService,
      final SubSectionDeleter deleter,
      final SubSectionUpdater updater,
      final SubSectionGrouper grouper,
      final SubSectionValidator validator) {

    this.deleter = deleter;
    this.updater = updater;
    this.creator = createSubSectionService;
    this.grouper = grouper;
    this.validator = validator;
  }

  @PostMapping
  SubSection createSubsection(
      @PathVariable Long page,
      @RequestBody CreateSubSectionRequest request,
      @Parameter(hidden = true) @AuthenticationPrincipal final NbsUserDetails details) {
    return creator.create(page, request, details.getId());
  }

  @DeleteMapping("{subSectionId}")
  void deleteSubSection(
      @PathVariable Long page,
      @PathVariable Long subSectionId,
      @Parameter(hidden = true) @AuthenticationPrincipal final NbsUserDetails details) {
    deleter.delete(page, subSectionId, details.getId());
  }

  @PutMapping("{subSectionId}")
  SubSection updateSubSection(
      @PathVariable Long page,
      @PathVariable Long subSectionId,
      @RequestBody UpdateSubSectionRequest request,
      @Parameter(hidden = true) @AuthenticationPrincipal final NbsUserDetails details) {
    return updater.update(page, subSectionId, request, details.getId());
  }

  @PostMapping("{subsection}/group")
  public void groupSubSection(
      @PathVariable Long page,
      @PathVariable Long subsection,
      @RequestBody GroupSubSectionRequest request,
      @Parameter(hidden = true) @AuthenticationPrincipal final NbsUserDetails details) {
    grouper.group(page, subsection, request, details.getId());
  }

  @GetMapping("{subSectionId}/un-group")
  void unGroupSubSection(
      @PathVariable Long page,
      @PathVariable Long subSectionId,
      @Parameter(hidden = true) @AuthenticationPrincipal final NbsUserDetails details) {
    grouper.unGroup(page, subSectionId, details.getId());
  }

  @GetMapping("{subSectionId}/validate")
  void validateSubSection(
      @PathVariable Long page,
      @PathVariable Long subSectionId,
      @Parameter(hidden = true) @AuthenticationPrincipal final NbsUserDetails details) {
    validator.validateIfCanBeGrouped(page, subSectionId);
  }
}
