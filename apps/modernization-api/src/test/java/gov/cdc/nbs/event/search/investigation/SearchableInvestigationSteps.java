package gov.cdc.nbs.event.search.investigation;

import gov.cdc.nbs.event.investigation.InvestigationIdentifier;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.java.en.Given;
import java.util.Objects;

public class SearchableInvestigationSteps {

  private final Active<SearchableInvestigation> active;
  private final Available<SearchableInvestigation> available;
  private final Active<InvestigationIdentifier> investigation;
  private final Available<InvestigationIdentifier> investigations;
  private final SearchableInvestigationMother mother;

  SearchableInvestigationSteps(
      final Active<SearchableInvestigation> active,
      final Available<SearchableInvestigation> available,
      final Active<InvestigationIdentifier> investigation,
      final Available<InvestigationIdentifier> investigations,
      final SearchableInvestigationMother mother) {
    this.active = active;
    this.available = available;
    this.investigation = investigation;
    this.investigations = investigations;
    this.mother = mother;
  }

  @Given("the investigation is available for search")
  public void investigation_is_available_for_search() {
    this.investigation.maybeActive().ifPresent(mother::searchable);
  }

  @Given("investigations are available for search")
  public void investigation_are_available_for_search() {
    this.mother.searchable(this.investigations.all());
  }

  @Given("I am searching for the Investigation")
  public void i_am_searching_the_investigation() {
    this.investigation.maybeActive().stream()
        .flatMap(
            current ->
                this.available
                    .all()
                    .filter(
                        searchable ->
                            Objects.equals(current.identifier(), searchable.identifier())))
        .findFirst()
        .ifPresent(this.active::active);
  }
}
