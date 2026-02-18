package gov.cdc.nbs.data.pagination;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;

public class PaginationSteps {

  private final int maxPageSize;
  private final Active<Pageable> activePageable;

  PaginationSteps(
      @Value("${nbs.max-page-size}") final int maxPageSize, final Active<Pageable> activePageable) {
    this.maxPageSize = maxPageSize;
    this.activePageable = activePageable;
  }

  @Before("@web-interaction")
  public void reset() {
    this.activePageable.reset();
  }

  @Given("I want at most {int} results")
  public void i_want_at_least_x_results(final int x) {
    this.activePageable.active(Pageable.ofSize(x));
  }

  @Given("I want the {nth} page")
  public void i_want_the_nth_page(final int nth) {
    this.activePageable.active(next -> next.withPage(nth), this::defaultPage);
  }

  private Pageable defaultPage() {
    return Pageable.ofSize(this.maxPageSize);
  }
}
