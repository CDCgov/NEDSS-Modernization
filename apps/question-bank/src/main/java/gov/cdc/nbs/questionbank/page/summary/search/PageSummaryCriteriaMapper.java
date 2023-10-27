package gov.cdc.nbs.questionbank.page.summary.search;

class PageSummaryCriteriaMapper {

  static PageSummaryCriteria asCriteria(final PageSummaryRequest request) {
    return new PageSummaryCriteria(request.search());
  }

  private PageSummaryCriteriaMapper() {
  }

}
