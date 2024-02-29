package gov.cdc.nbs.questionbank.page.content.subsection;

import gov.cdc.nbs.questionbank.page.content.subsection.request.GroupSubSectionRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.questionbank.page.content.subsection.model.SubSection;
import gov.cdc.nbs.questionbank.page.content.subsection.request.CreateSubSectionRequest;
import gov.cdc.nbs.questionbank.page.content.subsection.request.UpdateSubSectionRequest;
import springfox.documentation.annotations.ApiIgnore;


@RestController
@RequestMapping("/api/v1/pages/{page}/subsections/")
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
public class SubSectionController {

    private final SubSectionCreator creator;
    private final SubSectionUpdater updater;
    private final SubSectionDeleter deleter;
    private final SubSectionGrouper grouper;
    private final SubSectionValidator validator;

    public SubSectionController(
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
    public SubSection createSubsection(
        @PathVariable("page") Long page,
        @RequestBody CreateSubSectionRequest request,
        @ApiIgnore @AuthenticationPrincipal final NbsUserDetails details) {
        return creator.create(page, request, details.getId());
    }


    @DeleteMapping("{subSectionId}")
    public void deleteSubSection(
        @PathVariable("page") Long page,
        @PathVariable Long subSectionId,
        @ApiIgnore @AuthenticationPrincipal final NbsUserDetails details) {
        deleter.delete(page, subSectionId, details.getId());
    }

    @PutMapping("{subSectionId}")
    public SubSection updateSubSection(
        @PathVariable("page") Long page,
        @PathVariable("subSectionId") Long subSectionId,
        @RequestBody UpdateSubSectionRequest request,
        @ApiIgnore @AuthenticationPrincipal final NbsUserDetails details) {
        return updater.update(page, subSectionId, request, details.getId());
    }

    @PostMapping("group")
    public void groupSubSection(
        @PathVariable("page") Long page,
        @RequestBody GroupSubSectionRequest request,
        @ApiIgnore @AuthenticationPrincipal final NbsUserDetails details) {
        grouper.group(page, request, details.getId());
    }

    @GetMapping("{subSectionId}/un-group")
    public void unGroupSubSection(
        @PathVariable("page") Long page,
        @PathVariable Long subSectionId,
        @ApiIgnore @AuthenticationPrincipal final NbsUserDetails details) {
        grouper.unGroup(page, subSectionId, details.getId());
    }

    @GetMapping("{subSectionId}/validate")
    public void validateSubSection(
        @PathVariable("page") Long page,
        @PathVariable Long subSectionId,
        @ApiIgnore @AuthenticationPrincipal final NbsUserDetails details) {
        validator.validateIfCanBeGrouped(page, subSectionId);
    }


}
