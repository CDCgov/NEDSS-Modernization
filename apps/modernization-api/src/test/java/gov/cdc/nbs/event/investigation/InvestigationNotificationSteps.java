package gov.cdc.nbs.event.investigation;

import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.concept.ConceptParameterResolver;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.time.Instant;
import org.springframework.test.web.servlet.ResultActions;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


public class InvestigationNotificationSteps {

  private final Active<InvestigationIdentifier> activeInvestigation;
  private final Active<NotificationIdentifier> activeNotification;
  private final Active<ResultActions> response;
  private final InvestigationNotificationMother mother;
  private final ConceptParameterResolver resolver;
  private final NotificationTransportStatusRequester requester;

  InvestigationNotificationSteps(
      final Active<InvestigationIdentifier> activeInvestigation,
      final Active<NotificationIdentifier> activeNotification,
      final Active<ResultActions> response,
      final InvestigationNotificationMother mother,
      final ConceptParameterResolver resolver,
      final NotificationTransportStatusRequester requester) {
    this.activeInvestigation = activeInvestigation;
    this.activeNotification = activeNotification;
    this.response = response;
    this.mother = mother;
    this.resolver = resolver;
    this.requester = requester;
  }

  @ParameterType(name = "notificationStatus", value = ".*")
  public String notificationStatus(final String value) {
    return resolver.resolve("REC_STAT_NOT_UI", value)
        .orElse(null);
  }

  @ParameterType(name = "notificationTransportStatus", value = ".*")
  public String notificationTransportStatus(final String value) {
    return "null".equals(value) ? null : value;
  }

  @Given("the investigation has a notification {notificationStatus} as of {date}")
  public void the_investigation_was_notified_of(final String value, final Instant on) {
    if (value != null) {
      this.activeInvestigation.maybeActive().ifPresent(
          investigation -> mother.create(
              investigation,
              value,
              on));
    }
  }

  @Given("the investigation has a notification status of {notificationStatus}")
  public void the_investigation_has_a_notification_status_of(final String value) {
    if (value != null) {
      this.activeInvestigation.maybeActive().ifPresent(
          investigation -> mother.create(
              investigation,
              value,
              Instant.now()));
    }
  }

  @Given("the notification exists in the TransportQ_out table with status of {notificationTransportStatus}")
  public void the_notification_exists_in_the_transportq_out_table_with_status_of(final String status) {
    this.activeInvestigation.maybeActive().ifPresent(
        investigation -> mother.createTransportStatus(status));
  }

  @When("I query for a notifications transport status")
  public void i_query_for_a_notifications_transport_status() throws Exception {
    assertThat(activeNotification.active()).isNotNull();
    response.active(requester.request(activeNotification.active().local()));
  }

  @Then("I receive a notification transport status of {notificationTransportStatus}")
  public void i_receive_a_notification_transport_status(final String status) throws Exception {
    response.active()
        .andExpect(jsonPath("$.status").value(equalTo(status)));

  }

}
