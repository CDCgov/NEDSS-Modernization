package gov.cdc.nbs.search.redirect.simple;

import gov.cdc.nbs.event.search.InvestigationFilter;
import gov.cdc.nbs.option.Option;

import java.util.Map;
import java.util.Optional;

class SimpleInvestigationSearchCriteriaResolver {

  private static final String TYPE = "patientSearchVO.actType";
  private static final String IDENTIFIER = "patientSearchVO.actId";

  private static final String ABCS_CASE_ID = "P10000";
  private static final String CITY_COUNTY_CASE_ID = "P10008";
  private static final String INVESTIGATION_ID = "P10001";
  private static final String NOTIFICATION_ID = "P10013";
  private static final String STATE_CASE_ID = "P10004";

  Optional<SimpleInvestigationSearchCriteria> resolve(final Map<String, String> criteria) {
    return resolveType(criteria.get(TYPE))
        .map(type -> new Option(type.value(), type.display()))
        .map(
            type -> new SimpleInvestigationSearchCriteria(
                new SimpleSearchIdentification(type, criteria.get(IDENTIFIER))
            )
        );
  }

  private Optional<InvestigationFilter.IdType> resolveType(final String type) {
    return switch (type) {
      case ABCS_CASE_ID -> Optional.of(InvestigationFilter.IdType.ABCS_CASE_ID);
      case CITY_COUNTY_CASE_ID -> Optional.of(InvestigationFilter.IdType.CITY_COUNTY_CASE_ID);
      case INVESTIGATION_ID -> Optional.of(InvestigationFilter.IdType.INVESTIGATION_ID);
      case NOTIFICATION_ID -> Optional.of(InvestigationFilter.IdType.NOTIFICATION_ID);
      case STATE_CASE_ID -> Optional.of(InvestigationFilter.IdType.STATE_CASE_ID);
      default -> Optional.empty();
    };
  }
}
