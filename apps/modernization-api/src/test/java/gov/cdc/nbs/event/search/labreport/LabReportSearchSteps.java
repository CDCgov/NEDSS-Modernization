package gov.cdc.nbs.event.search.labreport;

import gov.cdc.nbs.event.search.LabReportFilter;
import gov.cdc.nbs.search.support.SortCriteria;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.ResultActions;

public class LabReportSearchSteps {

  private final LabReportSearchRequester requester;

  private final Active<LabReportFilter> criteria;
  private final Active<Pageable> paging;
  private final Active<SortCriteria> sorting;
  private final Active<ResultActions> response;

  LabReportSearchSteps(
      final LabReportSearchRequester requester,
      final Active<LabReportFilter> criteria,
      final Active<Pageable> paging,
      final Active<SortCriteria> sorting,
      final Active<ResultActions> response) {
    this.requester = requester;
    this.criteria = criteria;
    this.paging = paging;
    this.sorting = sorting;
    this.response = response;
  }

  @When("I search for lab reports")
  public void i_search_for_lab_reports() {
    this.response.active(requester.search(criteria.active(), paging.active(), sorting.active()));
  }
}
