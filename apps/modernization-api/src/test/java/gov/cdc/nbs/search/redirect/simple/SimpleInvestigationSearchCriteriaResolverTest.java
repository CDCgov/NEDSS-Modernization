package gov.cdc.nbs.search.redirect.simple;

import gov.cdc.nbs.event.search.InvestigationFilter;
import gov.cdc.nbs.option.Option;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.type;

class SimpleInvestigationSearchCriteriaResolverTest {

  @Test
  void should_resolve_abcs_case_id() {
    Map<String, String> parameters = Map.of(
        "patientSearchVO.actId", "abcs-case-number-value",
        "patientSearchVO.actType", "P10000"
    );


    Optional<SimpleInvestigationSearchCriteria> resolved =
        new SimpleInvestigationSearchCriteriaResolver().resolve(parameters);

    assertThat(resolved).hasValueSatisfying(
        actual -> assertThat(actual)
            .asInstanceOf(type(SimpleInvestigationSearchCriteria.class))
            .extracting(SimpleInvestigationSearchCriteria::identification, type(SimpleSearchIdentification.class))
            .returns("abcs-case-number-value", SimpleSearchIdentification::value)
            .extracting(SimpleSearchIdentification::type, type(Option.class))
            .returns(InvestigationFilter.IdType.ABCS_CASE_ID.value(), Option::value)
            .returns(InvestigationFilter.IdType.ABCS_CASE_ID.display(), Option::name)
            .returns(InvestigationFilter.IdType.ABCS_CASE_ID.display(), Option::label)
    );
  }

  @Test
  void should_resolve_city_county_case_id() {
    Map<String, String> parameters = Map.of(
        "patientSearchVO.actId", "city-county-case-number-value",
        "patientSearchVO.actType", "P10008"
    );


    Optional<SimpleInvestigationSearchCriteria> resolved =
        new SimpleInvestigationSearchCriteriaResolver().resolve(parameters);

    assertThat(resolved).hasValueSatisfying(
        actual -> assertThat(actual)
            .asInstanceOf(type(SimpleInvestigationSearchCriteria.class))
            .extracting(SimpleInvestigationSearchCriteria::identification, type(SimpleSearchIdentification.class))
            .returns("city-county-case-number-value", SimpleSearchIdentification::value)
            .extracting(SimpleSearchIdentification::type, type(Option.class))
            .returns(InvestigationFilter.IdType.CITY_COUNTY_CASE_ID.value(), Option::value)
            .returns(InvestigationFilter.IdType.CITY_COUNTY_CASE_ID.display(), Option::name)
            .returns(InvestigationFilter.IdType.CITY_COUNTY_CASE_ID.display(), Option::label)
    );
  }

  @Test
  void should_resolve_investigation_id() {
    Map<String, String> parameters = Map.of(
        "patientSearchVO.actId", "investigation-id-value",
        "patientSearchVO.actType", "P10001"
    );


    Optional<SimpleInvestigationSearchCriteria> resolved =
        new SimpleInvestigationSearchCriteriaResolver().resolve(parameters);

    assertThat(resolved).hasValueSatisfying(
        actual -> assertThat(actual)
            .asInstanceOf(type(SimpleInvestigationSearchCriteria.class))
            .extracting(SimpleInvestigationSearchCriteria::identification, type(SimpleSearchIdentification.class))
            .returns("investigation-id-value", SimpleSearchIdentification::value)
            .extracting(SimpleSearchIdentification::type, type(Option.class))
            .returns(InvestigationFilter.IdType.INVESTIGATION_ID.value(), Option::value)
            .returns(InvestigationFilter.IdType.INVESTIGATION_ID.display(), Option::name)
            .returns(InvestigationFilter.IdType.INVESTIGATION_ID.display(), Option::label)
    );
  }

  @Test
  void should_resolve_notification_id() {
    Map<String, String> parameters = Map.of(
        "patientSearchVO.actId", "notification-id-value",
        "patientSearchVO.actType", "P10013"
    );


    Optional<SimpleInvestigationSearchCriteria> resolved =
        new SimpleInvestigationSearchCriteriaResolver().resolve(parameters);

    assertThat(resolved).hasValueSatisfying(
        actual -> assertThat(actual)
            .asInstanceOf(type(SimpleInvestigationSearchCriteria.class))
            .extracting(SimpleInvestigationSearchCriteria::identification, type(SimpleSearchIdentification.class))
            .returns("notification-id-value", SimpleSearchIdentification::value)
            .extracting(SimpleSearchIdentification::type, type(Option.class))
            .returns(InvestigationFilter.IdType.NOTIFICATION_ID.value(), Option::value)
            .returns(InvestigationFilter.IdType.NOTIFICATION_ID.display(), Option::name)
            .returns(InvestigationFilter.IdType.NOTIFICATION_ID.display(), Option::label)
    );
  }

  @Test
  void should_resolve_state_case_id() {
    Map<String, String> parameters = Map.of(
        "patientSearchVO.actId", "state-case-id-value",
        "patientSearchVO.actType", "P10004"
    );


    Optional<SimpleInvestigationSearchCriteria> resolved =
        new SimpleInvestigationSearchCriteriaResolver().resolve(parameters);

    assertThat(resolved).hasValueSatisfying(
        actual -> assertThat(actual)
            .asInstanceOf(type(SimpleInvestigationSearchCriteria.class))
            .extracting(SimpleInvestigationSearchCriteria::identification, type(SimpleSearchIdentification.class))
            .returns("state-case-id-value", SimpleSearchIdentification::value)
            .extracting(SimpleSearchIdentification::type, type(Option.class))
            .returns(InvestigationFilter.IdType.STATE_CASE_ID.value(), Option::value)
            .returns(InvestigationFilter.IdType.STATE_CASE_ID.display(), Option::name)
            .returns(InvestigationFilter.IdType.STATE_CASE_ID.display(), Option::label)
    );
  }
}
