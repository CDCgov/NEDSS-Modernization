package gov.cdc.nbs.redirect.search;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import gov.cdc.nbs.investigation.InvestigationFilter;
import gov.cdc.nbs.investigation.InvestigationFilter.IdType;
import gov.cdc.nbs.labreport.LabReportFilter;
import gov.cdc.nbs.labreport.LabReportFilter.LaboratoryEventIdType;

class EventFilterResolverTest {
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

    private final EventFilterResolver resolver = new EventFilterResolver();

    @Test
    void should_resolve_investigation_abc_case() {
        // Given params for act type ABCS_CASE_ID
        Map<String, String> map = new HashMap<>();
        map.put(ACT_TYPE, ABCS_CASE_ID);
        map.put(ID_FIELD, "someId");

        // When resolving the filter
        Object filter = resolver.resolve(map);

        // Then an investigation filter with ABCS_CASE_ID is returned
        assertTrue(filter instanceof InvestigationFilter);
        assertEquals("someId", ((InvestigationFilter) filter).getEventId().getId());
        assertEquals(IdType.ABCS_CASE_ID, ((InvestigationFilter) filter).getEventId().getInvestigationEventType());
    }

    @Test
    void should_resolve_investigation_city() {
        // Given params for act type CITY_COUNTY_CASE_ID
        Map<String, String> map = new HashMap<>();
        map.put(ACT_TYPE, CITY_COUNTY_CASE_ID);
        map.put(ID_FIELD, "someId");

        // When resolving the filter
        Object filter = resolver.resolve(map);

        // Then an investigation filter with CITY_COUNTY_CASE_ID is returned
        assertTrue(filter instanceof InvestigationFilter);
        assertEquals("someId", ((InvestigationFilter) filter).getEventId().getId());
        assertEquals(IdType.CITY_COUNTY_CASE_ID,
                ((InvestigationFilter) filter).getEventId().getInvestigationEventType());
    }

    @Test
    void should_resolve_investigation_investigation() {
        // Given params for act type NOTIFICATION_ID
        Map<String, String> map = new HashMap<>();
        map.put(ACT_TYPE, INVESTIGATION_ID);
        map.put(ID_FIELD, "someId");

        // When resolving the filter
        Object filter = resolver.resolve(map);

        // Then an investigation filter with INVESTIGATION_ID is returned
        assertTrue(filter instanceof InvestigationFilter);
        assertEquals("someId", ((InvestigationFilter) filter).getEventId().getId());
        assertEquals(IdType.INVESTIGATION_ID,
                ((InvestigationFilter) filter).getEventId().getInvestigationEventType());
    }

    @Test
    void should_resolve_investigation_notification() {
        // Given params for act type NOTIFICATION_ID
        Map<String, String> map = new HashMap<>();
        map.put(ACT_TYPE, NOTIFICATION_ID);
        map.put(ID_FIELD, "someId");

        // When resolving the filter
        Object filter = resolver.resolve(map);

        // Then an investigation filter with NOTIFICATION_ID is returned
        assertTrue(filter instanceof InvestigationFilter);
        assertEquals("someId", ((InvestigationFilter) filter).getEventId().getId());
        assertEquals(IdType.NOTIFICATION_ID,
                ((InvestigationFilter) filter).getEventId().getInvestigationEventType());
    }

    @Test
    void should_resolve_investigation_state() {
        // Given params for act type STATE_CASE_ID
        Map<String, String> map = new HashMap<>();
        map.put(ACT_TYPE, STATE_CASE_ID);
        map.put(ID_FIELD, "someId");

        // When resolving the filter
        Object filter = resolver.resolve(map);

        // Then an investigation filter with STATE_CASE_ID is returned
        assertTrue(filter instanceof InvestigationFilter);
        assertEquals("someId", ((InvestigationFilter) filter).getEventId().getId());
        assertEquals(IdType.STATE_CASE_ID,
                ((InvestigationFilter) filter).getEventId().getInvestigationEventType());
    }

    @Test
    void should_resolve_labreport_accession() {
        // Given params for act type ACCESSION_NUMBER_ID
        Map<String, String> map = new HashMap<>();
        map.put(ACT_TYPE, ACCESSION_NUMBER_ID);
        map.put(ID_FIELD, "someId");

        // When resolving the filter
        Object filter = resolver.resolve(map);

        // Then a lab report filter with ACCESSION_NUMBER_ID is returned
        assertTrue(filter instanceof LabReportFilter);
        assertEquals("someId", ((LabReportFilter) filter).getEventId().getLabEventId());
        assertEquals(LaboratoryEventIdType.ACCESSION_NUMBER,
                ((LabReportFilter) filter).getEventId().getLabEventType());
    }

    @Test
    void should_resolve_labreport_id() {
        // Given params for act type LAB_ID
        Map<String, String> map = new HashMap<>();
        map.put(ACT_TYPE, LAB_ID);
        map.put(ID_FIELD, "someId");

        // When resolving the filter
        Object filter = resolver.resolve(map);

        // Then a lab report filter with LAB_ID is returned
        assertTrue(filter instanceof LabReportFilter);
        assertEquals("someId", ((LabReportFilter) filter).getEventId().getLabEventId());
        assertEquals(LaboratoryEventIdType.LAB_ID,
                ((LabReportFilter) filter).getEventId().getLabEventType());
    }

    @Test
    void should_return_null_bad_type() {
        // Given params for act type badType
        Map<String, String> map = new HashMap<>();
        map.put(ACT_TYPE, "badType");
        map.put(ID_FIELD, "someId");

        // When resolving the filter
        Object filter = resolver.resolve(map);

        // Then a null is returned
        assertNull(filter);
    }

    @Test
    void should_return_null_bad_no_params() {
        // Given empty parameters
        Map<String, String> map = new HashMap<>();

        // When resolving the filter
        Object filter = resolver.resolve(map);

        // Then a null is returned
        assertNull(filter);
    }
}
