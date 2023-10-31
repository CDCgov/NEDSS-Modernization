package gov.cdc.nbs.questionbank.page.content.section;


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
import gov.cdc.nbs.questionbank.page.content.section.model.Section;
import gov.cdc.nbs.questionbank.page.content.section.request.CreateSectionRequest;
import gov.cdc.nbs.questionbank.page.content.section.request.UpdateSectionRequest;
import springfox.documentation.annotations.ApiIgnore;


@RestController
@RequestMapping("/api/v1/pages/{page}/sections/")
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
public class SectionController {

    private final SectionCreator creator;
    private final SectionDeleter deleter;
    private final SectionUpdater updater;

    public SectionController(
            final SectionCreator creator,
            final SectionDeleter deleter,
            final SectionUpdater updater) {
        this.creator = creator;
        this.deleter = deleter;
        this.updater = updater;
    }

    @PostMapping
    public Section createSection(
            @PathVariable("page") Long page,
            @RequestBody CreateSectionRequest request,
            @ApiIgnore @AuthenticationPrincipal final NbsUserDetails details) {
        return creator.create(page, request, details.getId());
    }

    @DeleteMapping("{sectionId}")
    public void deleteSection(
            @PathVariable("page") Long page,
            @PathVariable("sectionId") Long sectionId,
            @ApiIgnore @AuthenticationPrincipal final NbsUserDetails details) {
        deleter.deleteSection(page, sectionId, details.getId());
    }

    @PutMapping("{section}")
    public Section updateSection(
            @PathVariable("page") Long page,
            @PathVariable("section") Long section,
            @RequestBody UpdateSectionRequest request,
            @ApiIgnore @AuthenticationPrincipal final NbsUserDetails details) {
        return updater.update(page, section, request, details.getId());
    }

}
