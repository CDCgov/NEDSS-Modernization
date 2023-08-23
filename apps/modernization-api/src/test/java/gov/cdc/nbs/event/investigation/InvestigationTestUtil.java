package gov.cdc.nbs.event.investigation;

import java.time.LocalDate;
import java.util.Collections;
import gov.cdc.nbs.event.InvestigationFilter;
import gov.cdc.nbs.event.InvestigationFilter.CaseStatus;
import gov.cdc.nbs.event.InvestigationFilter.EventDate;
import gov.cdc.nbs.event.InvestigationFilter.EventDateType;
import gov.cdc.nbs.event.InvestigationFilter.IdType;
import gov.cdc.nbs.event.InvestigationFilter.InvestigationEventId;
import gov.cdc.nbs.event.InvestigationFilter.InvestigationStatus;
import gov.cdc.nbs.event.InvestigationFilter.NotificationStatus;
import gov.cdc.nbs.event.InvestigationFilter.ProcessingStatus;
import gov.cdc.nbs.event.InvestigationFilter.ProviderFacilitySearch;
import gov.cdc.nbs.event.InvestigationFilter.ReportingEntityType;
import gov.cdc.nbs.message.enums.PregnancyStatus;
import gov.cdc.nbs.support.util.RandomUtil;

public class InvestigationTestUtil {
    private InvestigationTestUtil() {

    }

    public static InvestigationFilter createFilter() {
        var filter = new InvestigationFilter();
        filter.setPatientId(123L);
        filter.setConditions(Collections.singletonList("Condition1"));
        filter.setProgramAreas(Collections.singletonList("ProgramArea1"));
        filter.setJurisdictions(Collections.singletonList(321L));
        filter.setPregnancyStatus(PregnancyStatus.NO);
        filter.setEventId(new InvestigationEventId(IdType.ABCS_CASE_ID, "eventId"));

        filter.setEventDate(new EventDate(
                EventDateType.DATE_OF_REPORT,
                RandomUtil.dateInPast(),
                LocalDate.now()));
        filter.setCreatedBy(999L);
        filter.setLastUpdatedBy(998L);
        filter.setProviderFacilitySearch(new ProviderFacilitySearch(ReportingEntityType.FACILITY, 777L));
        filter.setInvestigatorId(565L);
        filter.setInvestigationStatus(InvestigationStatus.CLOSED);
        filter.setOutbreakNames(Collections.singletonList("OutbreakName"));
        filter.setCaseStatuses(Collections.singletonList(CaseStatus.CONFIRMED));
        filter.setNotificationStatuses(Collections.singletonList(NotificationStatus.APPROVED));
        filter.setProcessingStatuses(Collections.singletonList(ProcessingStatus.FIELD_FOLLOW_UP));
        return filter;
    }
}
