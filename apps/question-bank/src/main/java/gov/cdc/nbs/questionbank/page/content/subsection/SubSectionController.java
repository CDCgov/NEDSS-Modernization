package gov.cdc.nbs.questionbank.page.content.subsection;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.page.content.subsection.model.Subsection;
import gov.cdc.nbs.questionbank.page.content.subsection.request.CreateSubsectionRequest;
import gov.cdc.nbs.questionbank.page.content.subsection.request.UpdateSubsectionRequest;


@RestController
@RequestMapping("/api/v1/pages/{page}/subsections/")
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
public class SubsectionController {

    private final SubsectionCreator creator;
    private final SubsectionUpdater updater;
    private final SubsectionDeleter deleter;
    private final UserDetailsProvider userDetailsProvider;

    public SubsectionController(
            final SubsectionCreator createSubSectionService,
            final SubsectionDeleter deleter,
            final SubsectionUpdater updater,
            final UserDetailsProvider userDetailsProvider) {
        this.userDetailsProvider = userDetailsProvider;
        this.deleter = deleter;
        this.updater = updater;
        this.creator = createSubSectionService;
    }

    @PostMapping
    public Subsection createSubsection(
            @PathVariable("page") Long page,
            @RequestBody CreateSubsectionRequest request) {
        Long userId = userDetailsProvider.getCurrentUserDetails().getId();
        return creator.create(page, userId, request);
    }


    @DeleteMapping("{subSectionId}")
    public void deleteSubSection(
            @PathVariable("page") Long page,
            @PathVariable Long subSectionId) {
        deleter.delete(page, subSectionId);
    }

    @PutMapping("{subSectionId}")
    public Subsection updateSubSection(
            @PathVariable("page") Long page,
            @PathVariable("subSectionId") Long subSectionId,
            @RequestBody UpdateSubsectionRequest request) {
        Long userId = userDetailsProvider.getCurrentUserDetails().getId();
        return updater.update(page, subSectionId, userId, request);
    }

}
