package gov.cdc.nbs.questionbank.page.content.subsection;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    public SubSectionController(
            final SubSectionCreator createSubSectionService,
            final SubSectionDeleter deleter,
            final SubSectionUpdater updater) {
        this.deleter = deleter;
        this.updater = updater;
        this.creator = createSubSectionService;
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

}
