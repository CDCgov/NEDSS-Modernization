package gov.cdc.nbs.questionbank.page;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.page.request.PageCreateRequest;
import gov.cdc.nbs.questionbank.page.response.PageCreateResponse;
import gov.cdc.nbs.questionbank.page.response.PageDeleteResponse;
import gov.cdc.nbs.questionbank.page.response.PageStateResponse;

@RestController
@RequestMapping("/api/v1/pages")
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
public class PageController {

  private final PageCreator creator;
  private final PageStateChanger stateChange;
  private final PageDeletor pageDeletor;
  private final UserDetailsProvider userDetailsProvider;

  public PageController(
      final PageCreator creator,
      final PageStateChanger stateChange,
      final PageDeletor pageDeletor,
      final UserDetailsProvider userDetailsProvider) {
    this.creator = creator;
    this.stateChange = stateChange;
    this.pageDeletor = pageDeletor;
    this.userDetailsProvider = userDetailsProvider;
  }

  @PostMapping
  public PageCreateResponse createPage(
      @RequestBody PageCreateRequest request) {
    Long userId = userDetailsProvider.getCurrentUserDetails().getId();
    return creator.createPage(request, userId);
  }


  @PutMapping("{id}/draft")
  public PageStateResponse savePageDraft(@PathVariable("id") Long pageId) {
    return stateChange.savePageAsDraft(pageId);
  }

  @DeleteMapping("{id}/delete-draft")
  public PageDeleteResponse deletePageDraft(@PathVariable("id") Long pageId) {
    return pageDeletor.deletePageDraft(pageId);
  }

}
