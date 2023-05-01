package gov.cdc.nbs.labreport;

import java.time.LocalDate;
import java.util.Arrays;
import gov.cdc.nbs.labreport.LabReportFilter.EntryMethod;
import gov.cdc.nbs.labreport.LabReportFilter.EventId;
import gov.cdc.nbs.labreport.LabReportFilter.EventStatus;
import gov.cdc.nbs.labreport.LabReportFilter.LabReportDateType;
import gov.cdc.nbs.labreport.LabReportFilter.LabReportProviderSearch;
import gov.cdc.nbs.labreport.LabReportFilter.LaboratoryEventDateSearch;
import gov.cdc.nbs.labreport.LabReportFilter.LaboratoryEventIdType;
import gov.cdc.nbs.labreport.LabReportFilter.ProcessingStatus;
import gov.cdc.nbs.labreport.LabReportFilter.ProviderType;
import gov.cdc.nbs.labreport.LabReportFilter.UserType;
import gov.cdc.nbs.message.enums.PregnancyStatus;
import gov.cdc.nbs.support.util.RandomUtil;

public class LabReportTestUtil {
    private LabReportTestUtil() {

    }

    public static LabReportFilter createFilter() {
        var filter = new LabReportFilter();
        filter.setPatientId(411L);
        filter.setProgramAreas(Arrays.asList("PA1", "PA2"));
        filter.setJurisdictions(Arrays.asList(190L, 191L));
        filter.setPregnancyStatus(PregnancyStatus.YES);
        filter.setEventId(new EventId(LaboratoryEventIdType.ACCESSION_NUMBER, "eventId"));
        filter.setEventDate(new LaboratoryEventDateSearch(LabReportDateType.DATE_OF_REPORT,
                RandomUtil.dateInPast(), LocalDate.now()));
        filter.setEntryMethods(Arrays.asList(EntryMethod.ELECTRONIC));
        filter.setEnteredBy(Arrays.asList(UserType.EXTERNAL));
        filter.setEventStatus(Arrays.asList(EventStatus.UPDATE));
        filter.setProcessingStatus(Arrays.asList(ProcessingStatus.PROCESSED));
        filter.setCreatedBy(111L);
        filter.setLastUpdatedBy(112L);
        filter.setProviderSearch(new LabReportProviderSearch(ProviderType.ORDERING_FACILITY, 561L));
        filter.setResultedTest("resultedTest");
        filter.setCodedResult("codedResult");
        return filter;

    }
}
