package gov.cdc.nbs.questionbank.page;

import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.page.model.PageHistory;
import gov.cdc.nbs.questionbank.page.request.PageCreateRequest;
import gov.cdc.nbs.questionbank.page.request.PageValidationRequest;
import gov.cdc.nbs.questionbank.page.response.PageCreateResponse;
import gov.cdc.nbs.questionbank.page.response.PageDeleteResponse;
import gov.cdc.nbs.questionbank.page.response.PageStateResponse;
import gov.cdc.nbs.questionbank.page.service.PageHistoryFinder;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pages")
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
public class PageController {

  private final PageCreator creator;
  private final PageStateChanger stateChange;
  private final PageDeletor pageDeletor;
  private final UserDetailsProvider userDetailsProvider;
  private final PageHistoryFinder pageHistoryFinder;
  private final PageValidator validator;

  public PageController(
      final PageCreator creator,
      final PageStateChanger stateChange,
      final PageDeletor pageDeletor,
      final UserDetailsProvider userDetailsProvider,
      final PageHistoryFinder pageHistoryFinder,
      final PageValidator validator) {
    this.creator = creator;
    this.stateChange = stateChange;
    this.pageDeletor = pageDeletor;
    this.userDetailsProvider = userDetailsProvider;
    this.pageHistoryFinder = pageHistoryFinder;
    this.validator = validator;
  }

  @PostMapping
  public PageCreateResponse createPage(@RequestBody PageCreateRequest request) {
    Long userId = userDetailsProvider.getCurrentUserDetails().getId();
    return creator.createPage(request, userId);
  }

  @PutMapping("{id}/draft")
  public PageStateResponse savePageDraft(@PathVariable("id") Long pageId) {
    return stateChange.savePageAsDraft(pageId);
  }

  @GetMapping("{id}/page-history")
  public Page<PageHistory> getPageHistory(
      @PathVariable("id") Long pageId,
      @ParameterObject @PageableDefault(size = 25) Pageable pageable) {
    return pageHistoryFinder.getPageHistory(pageId, pageable);
  }

  @DeleteMapping("{id}/delete-draft")
  public PageDeleteResponse deletePageDraft(@PathVariable("id") Long pageId) {
    return pageDeletor.deletePageDraft(pageId);
  }

  @PostMapping("/validate")
  public Boolean validatePageRequest(@RequestBody PageValidationRequest request) {
    return validator.validate(request);
  }
}
