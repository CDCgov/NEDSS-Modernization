package gov.cdc.nbs.questionbank.page.content.staticelement;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.questionbank.page.content.staticelement.request.StaticContentRequests;
import gov.cdc.nbs.questionbank.page.content.staticelement.response.AddStaticResponse;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;



@Slf4j
@RestController
@RequestMapping("/api/v1/pages/{page}/content/static")
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
public class PageStaticController {
    private final PageStaticCreator pageStaticCreator;

    public PageStaticController(
            final PageStaticCreator pageStaticCreator) {
        this.pageStaticCreator = pageStaticCreator;
    }


    @PostMapping("/line-separator")
    public AddStaticResponse addStaticLineSeparator(
            @PathVariable("page") Long pageId,
            @RequestBody StaticContentRequests.AddDefault request,
            @ApiIgnore @AuthenticationPrincipal final NbsUserDetails details) {
        log.debug("Received request to add line separator in page");
        Long componentId = pageStaticCreator.addLineSeparator(pageId, request, details.getId());
        log.debug("Completed adding line separator");
        log.debug("component ID = " + componentId);
        return new AddStaticResponse(componentId);
    }

    @PostMapping("/hyperlink")
    public AddStaticResponse addStaticHyperLink(
            @PathVariable("page") Long pageId,
            @RequestBody StaticContentRequests.AddHyperlink request,
            @ApiIgnore @AuthenticationPrincipal final NbsUserDetails details) {

        Long componentId = pageStaticCreator.addHyperLink(pageId, request, details.getId());
        return new AddStaticResponse(componentId);
    }

    @PostMapping("/read-only-comments")
    public AddStaticResponse addStaticReadOnlyComments(
            @PathVariable("page") Long pageId,
            @RequestBody StaticContentRequests.AddReadOnlyComments request,
            @ApiIgnore @AuthenticationPrincipal final NbsUserDetails details) {
        Long componentId = pageStaticCreator.addReadOnlyComments(pageId, request, details.getId());

        return new AddStaticResponse(componentId);
    }

    @PostMapping("/read-only-participants-list")
    public AddStaticResponse addStaticReadOnlyParticipantsList(
            @PathVariable("page") Long pageId,
            @RequestBody StaticContentRequests.AddDefault request,
            @ApiIgnore @AuthenticationPrincipal final NbsUserDetails details) {
        Long componentId = pageStaticCreator.addReadOnlyParticipantsList(pageId, request, details.getId());

        return new AddStaticResponse(componentId);
    }

    @PostMapping("/original_elec_doc_list")
    public AddStaticResponse addStaticOriginalElectronicDocList(
            @PathVariable("page") Long pageId,
            @RequestBody StaticContentRequests.AddDefault request,
            @ApiIgnore @AuthenticationPrincipal final NbsUserDetails details) {
        Long componentId = pageStaticCreator.addOriginalElectronicDocList(pageId, request, details.getId());
        return new AddStaticResponse(componentId);
    }

}
