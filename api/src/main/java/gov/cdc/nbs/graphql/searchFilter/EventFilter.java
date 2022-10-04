package gov.cdc.nbs.graphql.searchFilter;

import java.util.Set;

import gov.cdc.nbs.entity.enums.PregnancyStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventFilter {
    private EventType eventType;
    private InvestigationFilter investigationFilter;
    private LaboratoryReportFilter laboratoryReportFilter;
}

enum EventType {
    INVESTIGATION,
    LABORATORY_REPORT
}

@Getter
@Setter
class InvestigationFilter {
    private Set<String> conditions;
    private Set<String> programAreas;
    private Set<String> jurisdictions;
    private PregnancyStatus pregnancyStatus;

}

@Getter
@Setter
class LaboratoryReportFilter {

}
