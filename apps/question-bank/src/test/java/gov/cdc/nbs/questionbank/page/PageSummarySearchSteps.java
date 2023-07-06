package gov.cdc.nbs.questionbank.page;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import gov.cdc.nbs.questionbank.exception.QueryException;
import gov.cdc.nbs.questionbank.page.model.PageSummary;
import gov.cdc.nbs.questionbank.page.request.PageSummaryRequest;
import gov.cdc.nbs.questionbank.page.util.PageSummarySearchHolder;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.support.PageMother;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class PageSummarySearchSteps {

    @Autowired
    private PageController controller;

    @Autowired
    private PageMother pageMother;

    @Autowired
    private PageSummarySearchHolder holder;

    @Autowired
    private ExceptionHolder exceptionHolder;

    @Given("pages exist")
    public void pages_exist() {
        pageMother.brucellosis();
        pageMother.asepticMeningitis();
    }


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
        assertNotNull(holder.getResults());
        assertTrue(!holder.getResults().isEmpty());
        boolean correctlySorted = holder.getResults()
                .stream()
                .sorted((a, b) -> createComparator(a, b, field, direction))
                .map(a -> a.id())
                .toList()
                .equals(holder.getResults().map(r -> r.id()).toList());
        assertTrue(correctlySorted);
    }

    private int createComparator(PageSummary a, PageSummary b, String field, String direction) {
        return switch (field) {
            case "id" -> Long.valueOf(a.id()).compareTo(Long.valueOf(b.id())) * (direction.equals("ASC") ? 1 : -1);
            case "name" -> a.name().compareTo(b.name()) * (direction.equals("ASC") ? 1 : -1);
            case "eventType" -> a.eventType().type().compareTo(b.eventType().type())
                    * (direction.equals("ASC") ? 1 : -1);
            case "status" -> a.status().compareTo(b.status()) * (direction.equals("ASC") ? 1 : -1);
            case "lastUpdate" -> a.lastUpdate().compareTo(b.lastUpdate()) * (direction.equals("ASC") ? 1 : -1);
            case "lastUpdateBy" -> a.lastUpdateBy().compareTo(b.lastUpdateBy()) * (direction.equals("ASC") ? 1 : -1);
            default -> throw new IllegalArgumentException();
        };
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
        // current page name and condition are searched
        assertNotNull(holder.getResults());
        assertTrue(!holder.getResults().isEmpty());
        holder.getResults().forEach(r -> {
            boolean conditionMatch = r.conditions().stream().anyMatch(c -> c.name().contains(searchText));
            boolean nameMatch = r.name().contains(searchText);
            assertTrue(conditionMatch || nameMatch);
        });
    }

    @Then("a query exception is thrown")
    public void a_query_exception_is_thrown() {
        assertNotNull(exceptionHolder.getException());
        assertTrue(exceptionHolder.getException() instanceof QueryException);
    }
}
