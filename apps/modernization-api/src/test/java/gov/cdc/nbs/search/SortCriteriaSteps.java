package gov.cdc.nbs.search;

import gov.cdc.nbs.search.support.SortCriteria;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import org.springframework.data.domain.Sort;

public class SortCriteriaSteps {

  private final Active<SortCriteria> sorting;

  SortCriteriaSteps(final Active<SortCriteria> sorting) {
    this.sorting = sorting;
  }

  @Given("I want search results sorted by {string} {string}")
  public void i_want_search_results_sorted_by(final String sortBy, final String direction) {
    Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;

    String field = switch (sortBy.toLowerCase()) {
      case "birthday" -> "birthTime";
      case "legal name" -> "legalName";
      case "first name" -> "firstNm";
      case "last name" -> "lastNm";
      case "phone" -> "phoneNumber";
      case "startdate" -> "startDate";
      case "investigationid" -> "investigationId";
      default -> sortBy.toLowerCase();
    };

    this.sorting.active(new SortCriteria(sortDirection, field));
  }
}
