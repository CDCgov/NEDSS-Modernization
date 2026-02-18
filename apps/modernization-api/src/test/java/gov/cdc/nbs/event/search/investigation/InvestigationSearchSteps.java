package gov.cdc.nbs.event.search.investigation;

import gov.cdc.nbs.event.search.InvestigationFilter;
import gov.cdc.nbs.search.support.SortCriteria;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.ResultActions;

public class InvestigationSearchSteps {

  private final InvestigationSearchRequester requester;
  private final Active<InvestigationFilter> criteria;
  private final Active<Pageable> paging;
  private final Active<SortCriteria> sorting;
  private final Active<ResultActions> response;

  InvestigationSearchSteps(
      final InvestigationSearchRequester requester,
      final Active<InvestigationFilter> criteria,
      final Active<Pageable> paging,
      final Active<SortCriteria> sorting,
      final Active<ResultActions> response) {
    this.requester = requester;
    this.criteria = criteria;
    this.paging = paging;
    this.sorting = sorting;
    this.response = response;
  }

  @When("I search for investigations")
  public void i_search_for_investigations() {
    this.response.active(requester.search(criteria.active(), paging.active(), sorting.active()));
  }
}
