package gov.cdc.nbs.redirect.search;

import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import gov.cdc.nbs.event.search.EventFilter;
import gov.cdc.nbs.event.search.InvestigationFilter;
import gov.cdc.nbs.event.search.LabReportFilter;
import gov.cdc.nbs.event.search.InvestigationFilter.IdType;
import gov.cdc.nbs.event.search.LabReportFilter.LaboratoryEventIdType;

@Component
public class EventFilterResolver {
    private static final String ACT_TYPE = "patientSearchVO.actType";
    private static final String ID_FIELD = "patientSearchVO.actId";

    // Investigation Ids
    private static final String ABCS_CASE_ID = "P10000";
    private static final String CITY_COUNTY_CASE_ID = "P10008";
    private static final String INVESTIGATION_ID = "P10001";
    private static final String NOTIFICATION_ID = "P10013";
    private static final String STATE_CASE_ID = "P10004";

    // Lab report Ids
    private static final String ACCESSION_NUMBER_ID = "P10009";
    private static final String LAB_ID = "P10002";

    /**
     * Returns either an {@link InvestigationFilter} or {@link LabReportFilter} based on the content passed
     * 
     * @param map
     * @return
     */
    public EventFilter resolve(Map<String, String> map) {
        if (StringUtils.hasText(map.get(ACT_TYPE)) && StringUtils.hasText(map.get(ID_FIELD))) {
            String id = map.get(ID_FIELD);
            // Attempt to parse an investigation filter
            IdType type = resolveInvestigationIdType(map.get(ACT_TYPE));
            if (type != null) {
                InvestigationFilter filter = new InvestigationFilter();
                filter.setEventId(new InvestigationFilter.InvestigationEventId(type, id));
                return filter;
            }

            // Attempt to parse a lab report filter
            LaboratoryEventIdType labEventType = resolveLabReportIdType(map.get(ACT_TYPE));
            if (labEventType != null) {
                LabReportFilter filter = new LabReportFilter();
                filter.setEventId(new LabReportFilter.LabReportEventId(labEventType, id));
                return filter;
            }
        }
        return null;
    }



    private IdType resolveInvestigationIdType(String idType) {
        return switch (idType) {
            case ABCS_CASE_ID -> IdType.ABCS_CASE_ID;
            case CITY_COUNTY_CASE_ID -> IdType.CITY_COUNTY_CASE_ID;
            case INVESTIGATION_ID -> IdType.INVESTIGATION_ID;
            case NOTIFICATION_ID -> IdType.NOTIFICATION_ID;
            case STATE_CASE_ID -> IdType.STATE_CASE_ID;
            default -> null;
        };
    }

    private LaboratoryEventIdType resolveLabReportIdType(String idType) {
        return switch (idType) {
            case ACCESSION_NUMBER_ID -> LaboratoryEventIdType.ACCESSION_NUMBER;
            case LAB_ID -> LaboratoryEventIdType.LAB_ID;
            default -> null;
        };
    }
}
