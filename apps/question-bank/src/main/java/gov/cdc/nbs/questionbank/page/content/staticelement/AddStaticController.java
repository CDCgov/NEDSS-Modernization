package gov.cdc.nbs.questionbank.page.content.staticelement;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.page.content.staticelement.request.AddStaticLineSeparatorRequest;
import gov.cdc.nbs.questionbank.page.content.staticelement.response.AddStaticResponse;
import lombok.extern.slf4j.Slf4j;



@Slf4j
@RestController
@RequestMapping("/api/v1/pages/{page}/static-element/")
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
public class AddStaticController {
    private final UserDetailsProvider userDetailsProvider;
    private final PageStaticCreator pageStaticCreator;

    public AddStaticController(
        final UserDetailsProvider userDetailsProvider,
        final PageStaticCreator pageStaticCreator
    ) {
        this.userDetailsProvider = userDetailsProvider;
        this.pageStaticCreator = pageStaticCreator;
    }


    @PostMapping("/line-separator")
    public AddStaticResponse addStaticLineSeparator(
        @PathVariable("page") Long pageId,
        @RequestBody AddStaticLineSeparatorRequest request
    ) {
        log.debug("Received request to add line separator in page");
        Long userId = userDetailsProvider.getCurrentUserDetails().getId();
        Long componentId = pageStaticCreator.addLineSeparator(pageId, request, userId);
        log.debug("Completed adding line separator");
        log.debug("component ID = " + componentId);
        return new AddStaticResponse(componentId);
    }



}
