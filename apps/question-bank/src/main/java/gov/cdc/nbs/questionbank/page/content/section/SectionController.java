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
import gov.cdc.nbs.questionbank.page.content.section.response.UpdateSectionResponse;


@RestController
@RequestMapping("/api/v1/pages/{page}/sections/")
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
public class SectionController {

    private final SectionCreator creator;
    private final SectionDeleter deleter;

    private final UserDetailsProvider userDetailsProvider;

    public SectionController(
            final SectionCreator createSectionService,
            final UserDetailsProvider userDetailsProvider,
            final SectionDeleter deleter) {
        this.userDetailsProvider = userDetailsProvider;
        this.creator = createSectionService;
        this.deleter = deleter;
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

    @PutMapping("{sectionId}")
    public UpdateSectionResponse updateSection(@PathVariable("sectionId") Long sectionId,
            @RequestBody UpdateSectionRequest request) {
        return creator.updateSection(sectionId, request);
    }

}
