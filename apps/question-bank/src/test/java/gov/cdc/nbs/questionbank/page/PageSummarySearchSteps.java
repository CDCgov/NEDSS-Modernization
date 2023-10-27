package gov.cdc.nbs.questionbank.page;

import gov.cdc.nbs.questionbank.exception.QueryException;
import gov.cdc.nbs.questionbank.page.model.PageSummary;
import gov.cdc.nbs.questionbank.page.request.PageSummaryRequest;
import gov.cdc.nbs.questionbank.page.util.PageSummarySearchHolder;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class PageSummarySearchSteps {

  @Autowired
  private PageController controller;

  @Autowired
  private PageSummarySearchHolder holder;

  @Autowired
  private ExceptionHolder exceptionHolder;

  @When("I get all page summaries")
  public void i_get_all_page_summaries() {
    try {
      holder.setResults(controller.getAllPageSummary(PageRequest.ofSize(25)));
    } catch (AccessDeniedException e) {
      exceptionHolder.setException(e);
    } catch (AuthenticationCredentialsNotFoundException e) {
      exceptionHolder.setException(e);
    }
  }

  @Then("page summaries are returned")
  public void page_summaries_are_returned() {
    assertNotNull(holder.getResults());
    assertTrue(!holder.getResults().isEmpty());
  }

  @When("I get all page summaries and sort by {string} {string}")
  public void i_get_all_page_summaries_sorted(String field, String direction) {
    Direction dir = direction.equals("ASC") ? Direction.ASC : Direction.DESC;
    try {
      holder.setResults(controller.getAllPageSummary(PageRequest.of(0, 5, dir, field)));
    } catch (AccessDeniedException e) {
      exceptionHolder.setException(e);
    } catch (AuthenticationCredentialsNotFoundException e) {
      exceptionHolder.setException(e);
    } catch (QueryException e) {
      exceptionHolder.setException(e);
    }
  }

  @Then("page summaries are returned sorted by {string} {string}")
  public void page_summaries_are_returned_sorted(String field, String direction) {
    assertThat(holder.getResults().stream())
        .isNotEmpty()
        .isSortedAccordingTo(sorted(field, direction));
  }

  private Comparator<PageSummary> sorted(final String field, final String direction) {
    Comparator<PageSummary> fieldComparator = switch (field) {
      case "id" -> Comparator.comparing(PageSummary::id);
      case "name" -> Comparator.comparing(PageSummary::name, String.CASE_INSENSITIVE_ORDER);
      case "eventType" -> Comparator.comparing(
          PageSummary::eventType,
          Comparator.comparing(
              PageSummary.EventType::name,
              String.CASE_INSENSITIVE_ORDER
          )
      );
      case "status" -> Comparator.comparing(PageSummary::status);
      case "lastUpdate" -> Comparator.comparing(PageSummary::lastUpdate);
      case "lastUpdateBy" -> Comparator.comparing(PageSummary::lastUpdateBy);
      default -> throw new IllegalArgumentException("Unexpected Page Summary field " + field);
    };

    return direction.equalsIgnoreCase("desc")
        ? fieldComparator.reversed()
        : fieldComparator;

  }


  @When("I search for summaries by {string}")
  public void search_by_text(String searchText) {
    try {
      holder.setResults(controller.search(new PageSummaryRequest(searchText), PageRequest.ofSize(25)));
    } catch (AccessDeniedException e) {
      exceptionHolder.setException(e);
    } catch (AuthenticationCredentialsNotFoundException e) {
      exceptionHolder.setException(e);
    }
  }

  @When("I search for summaries by {string} and sort by {string} {string}")
  public void search_by_text_and_sort(String searchText, String field, String direction) {
    Direction dir = direction.equals("ASC") ? Direction.ASC : Direction.DESC;
    try {
      holder.setResults(
          controller.search(new PageSummaryRequest(searchText), PageRequest.of(0, 25, dir, field)));
    } catch (AccessDeniedException e) {
      exceptionHolder.setException(e);
    } catch (AuthenticationCredentialsNotFoundException e) {
      exceptionHolder.setException(e);
    }
  }

  @Then("page summaries are returned that match {string}")
  public void results_match_search_text(String searchText) {

    assertThat(holder.getResults())
        .anySatisfy(result -> assertThat(result.name()).containsIgnoringCase(searchText))
        .anySatisfy(result -> assertThat(result.conditions())
            .anySatisfy(summary -> assertThat(summary.name()).containsIgnoringCase(searchText)
            )
        );

  }

  @Then("a query exception is thrown")
  public void a_query_exception_is_thrown() {
    assertNotNull(exceptionHolder.getException());
    assertTrue(exceptionHolder.getException() instanceof QueryException);
  }
}
