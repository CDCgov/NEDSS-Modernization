package gov.cdc.nbs.search.redirect.simple;

import gov.cdc.nbs.event.search.LabReportFilter;
import gov.cdc.nbs.option.Option;

import java.util.Map;
import java.util.Optional;

class SimpleLaboratoryReportSearchCriteriaResolver {

  private static final String TYPE = "patientSearchVO.actType";
  private static final String IDENTIFIER = "patientSearchVO.actId";

  private static final String ACCESSION_NUMBER_ID = "P10009";
  private static final String LAB_ID = "P10002";

  Optional<SimpleLaboratoryReportSearchCriteria> resolve(final Map<String, String> criteria) {
    return resolveType(criteria.get(TYPE))
        .map(type -> new Option(type.value(), type.display()))
        .map(
            type -> new SimpleLaboratoryReportSearchCriteria(
                new SimpleSearchIdentification(type, criteria.get(IDENTIFIER))
            )
        );
  }

  private Optional<LabReportFilter.LaboratoryEventIdType> resolveType(final String type) {
    return switch (type) {
      case ACCESSION_NUMBER_ID -> Optional.of(LabReportFilter.LaboratoryEventIdType.ACCESSION_NUMBER);
      case LAB_ID -> Optional.of(LabReportFilter.LaboratoryEventIdType.LAB_ID);
      default -> Optional.empty();
    };
  }
}
