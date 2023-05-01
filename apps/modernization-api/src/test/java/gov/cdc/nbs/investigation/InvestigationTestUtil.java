package gov.cdc.nbs.investigation;

import java.time.LocalDate;
import java.util.Collections;
import gov.cdc.nbs.investigation.InvestigationFilter.CaseStatus;
import gov.cdc.nbs.investigation.InvestigationFilter.EventDate;
import gov.cdc.nbs.investigation.InvestigationFilter.EventDateType;
import gov.cdc.nbs.investigation.InvestigationFilter.EventId;
import gov.cdc.nbs.investigation.InvestigationFilter.IdType;
import gov.cdc.nbs.investigation.InvestigationFilter.InvestigationStatus;
import gov.cdc.nbs.investigation.InvestigationFilter.NotificationStatus;
import gov.cdc.nbs.investigation.InvestigationFilter.ProcessingStatus;
import gov.cdc.nbs.investigation.InvestigationFilter.ProviderFacilitySearch;
import gov.cdc.nbs.investigation.InvestigationFilter.ReportingEntityType;
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
        filter.setEventId(new EventId(IdType.ABCS_CASE_ID, "eventId"));

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
