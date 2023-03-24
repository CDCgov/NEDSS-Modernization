package gov.cdc.nbs.labreport;

import java.time.Instant;
import java.util.Arrays;
import gov.cdc.nbs.graphql.filter.LabReportFilter;
import gov.cdc.nbs.graphql.filter.LabReportFilter.EntryMethod;
import gov.cdc.nbs.graphql.filter.LabReportFilter.EventStatus;
import gov.cdc.nbs.graphql.filter.LabReportFilter.LabReportDateType;
import gov.cdc.nbs.graphql.filter.LabReportFilter.LabReportProviderSearch;
import gov.cdc.nbs.graphql.filter.LabReportFilter.LaboratoryEventDateSearch;
import gov.cdc.nbs.graphql.filter.LabReportFilter.LaboratoryEventIdType;
import gov.cdc.nbs.graphql.filter.LabReportFilter.ProcessingStatus;
import gov.cdc.nbs.graphql.filter.LabReportFilter.ProviderType;
import gov.cdc.nbs.graphql.filter.LabReportFilter.UserType;
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
        filter.setEventIdType(LaboratoryEventIdType.ACCESSION_NUMBER);
        filter.setEventId("eventId");
        filter.setEventDateSearch(new LaboratoryEventDateSearch(LabReportDateType.DATE_OF_REPORT,
                RandomUtil.getRandomDateInPast(), Instant.now()));
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
