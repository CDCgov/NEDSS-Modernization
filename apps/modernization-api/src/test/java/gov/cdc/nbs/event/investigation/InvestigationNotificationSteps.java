package gov.cdc.nbs.event.investigation;

import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.concept.ConceptParameterResolver;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;

import java.time.Instant;

public class InvestigationNotificationSteps {

  private final Active<InvestigationIdentifier> activeInvestigation;
  private final InvestigationNotificationMother mother;
  private final ConceptParameterResolver resolver;

  InvestigationNotificationSteps(
      final Active<InvestigationIdentifier> activeInvestigation,
      final InvestigationNotificationMother mother,
      final ConceptParameterResolver resolver
  ) {
    this.activeInvestigation = activeInvestigation;
    this.mother = mother;
    this.resolver = resolver;
  }

  @ParameterType(name = "notificationStatus", value = ".*")
  public String notificationStatus(final String value) {
    return resolver.resolve("REC_STAT_NOT_UI", value)
        .orElse(null);
  }

  @Given("the investigation has a notification {notificationStatus} as of {date}")
  public void the_investigation_was_notified_of(final String value, final Instant on) {
    if (value != null) {
      this.activeInvestigation.maybeActive().ifPresent(
          investigation -> mother.create(
              investigation,
              value,
              on
          )
      );
    }
  }

  @Given("the investigation has a notification status of {notificationStatus}")
  public void the_investigation_has_a_notification_status_of(final String value) {
    if (value != null) {
      this.activeInvestigation.maybeActive().ifPresent(
          investigation -> mother.create(
              investigation,
              value,
              Instant.now()
          )
      );
    }
  }

}
