package gov.cdc.nbs.event.search;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class InvestigationFilterTest {

  @Test
  void should_not_return_ABCS_without_identifier() {

    InvestigationFilter filter = new InvestigationFilter();

    filter.setEventId(
        new InvestigationFilter.InvestigationEventId(
            InvestigationFilter.IdType.ABCS_CASE_ID, null));

    assertThat(filter.abcsCase()).isEmpty();
  }

  @Test
  void should_not_return_state_case_without_identifier() {

    InvestigationFilter filter = new InvestigationFilter();

    filter.setEventId(
        new InvestigationFilter.InvestigationEventId(
            InvestigationFilter.IdType.STATE_CASE_ID, null));

    assertThat(filter.stateCase()).isEmpty();
  }

  @Test
  void should_not_return_county_case_without_identifier() {

    InvestigationFilter filter = new InvestigationFilter();

    filter.setEventId(
        new InvestigationFilter.InvestigationEventId(
            InvestigationFilter.IdType.CITY_COUNTY_CASE_ID, null));

    assertThat(filter.countyCase()).isEmpty();
  }

  @Test
  void should_not_return_notification_without_identifier() {

    InvestigationFilter filter = new InvestigationFilter();

    filter.setEventId(
        new InvestigationFilter.InvestigationEventId(
            InvestigationFilter.IdType.NOTIFICATION_ID, null));

    assertThat(filter.notification()).isEmpty();
  }

  @Test
  void should_not_return_case_id_without_identifier() {

    InvestigationFilter filter = new InvestigationFilter();

    filter.setEventId(
        new InvestigationFilter.InvestigationEventId(
            InvestigationFilter.IdType.INVESTIGATION_ID, null));

    assertThat(filter.caseId()).isEmpty();
  }
}
