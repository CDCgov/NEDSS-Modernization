package gov.cdc.nbs.graphql.filter;

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
