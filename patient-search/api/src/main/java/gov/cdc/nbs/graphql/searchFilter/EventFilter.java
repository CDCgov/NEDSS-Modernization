package gov.cdc.nbs.graphql.searchFilter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventFilter {
    private EventType eventType;
    private InvestigationFilter investigationFilter;
    private LabReportFilter laboratoryReportFilter;

    public enum EventType {
        INVESTIGATION,
        LABORATORY_REPORT
    }
}
