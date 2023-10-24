package gov.cdc.nbs.questionbank.page.content.section;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.page.content.section.request.CreateSectionRequest;
import gov.cdc.nbs.questionbank.page.content.section.request.UpdateSectionRequest;
import gov.cdc.nbs.questionbank.page.content.section.response.CreateSectionResponse;
import gov.cdc.nbs.questionbank.page.content.section.model.Section;


@RestController
@RequestMapping("/api/v1/pages/{page}/sections/")
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
public class SectionController {

    private final SectionCreator creator;
    private final SectionDeleter deleter;
    private final SectionUpdater updater;

    private final UserDetailsProvider userDetailsProvider;

    public SectionController(
            final SectionCreator creator,
            final UserDetailsProvider userDetailsProvider,
            final SectionDeleter deleter,
            final SectionUpdater updater) {
        this.userDetailsProvider = userDetailsProvider;
        this.creator = creator;
        this.deleter = deleter;
        this.updater = updater;
    }

    @PostMapping
    public CreateSectionResponse createSection(
            @PathVariable("page") Long page,
            @RequestBody CreateSectionRequest request) {
        Long userId = userDetailsProvider.getCurrentUserDetails().getId();
        return creator.createSection(page, userId, request);
    }

    @DeleteMapping("{sectionId}")
    public void deleteSection(
            @PathVariable("page") Long page,
            @PathVariable("sectionId") Long sectionId) {
        deleter.deleteSection(page, sectionId);
    }

    @PutMapping("{section}")
    public Section updateSection(
            @PathVariable("page") Long page,
            @PathVariable("section") Long section,
            @RequestBody UpdateSectionRequest request) {
        Long userId = userDetailsProvider.getCurrentUserDetails().getId();
        return updater.update(page, section, userId, request);
    }

}
