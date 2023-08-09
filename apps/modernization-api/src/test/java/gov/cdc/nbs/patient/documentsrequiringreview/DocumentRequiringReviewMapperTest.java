package gov.cdc.nbs.patient.documentsrequiringreview;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import gov.cdc.nbs.entity.odse.QNbsDocument;
import gov.cdc.nbs.entity.odse.QObsValueCoded;
import gov.cdc.nbs.entity.odse.QObservation;
import gov.cdc.nbs.entity.odse.QOrganization;
import gov.cdc.nbs.entity.odse.QPersonName;
import gov.cdc.nbs.entity.srte.QConditionCode;
import gov.cdc.nbs.message.enums.Suffix;
import gov.cdc.nbs.patient.documentsrequiringreview.DocumentRequiringReview.Description;
import gov.cdc.nbs.patient.documentsrequiringreview.DocumentRequiringReview.OrderingProvider;
import gov.cdc.nbs.patient.documentsrequiringreview.DocumentRequiringReview.ReportingFacility;

class DocumentRequiringReviewMapperTest {
    private static final String ID = "id";
    private static final String TYPE = "type";
    private static final String DATE_RECEIVED = "dateReceived";
    private static final String EVENT_DATE = "eventDate";
    private static final String LOCAL_ID = "localId";
    private static final QNbsDocument DOCUMENT = QNbsDocument.nbsDocument;
    private static final QObservation OBSERVATION = QObservation.observation;
    private static final QConditionCode CONDITION = QConditionCode.conditionCode;
    private static final QObservation OBSERVATION_2 = new QObservation("obs2");
    private static final QObsValueCoded OBS_VALUE_CODED = QObsValueCoded.obsValueCoded;
    private static final QPersonName PERSON_NAME = QPersonName.personName;
    private static final QOrganization ORGANIZATION = QOrganization.organization;

    private DocumentRequiringReviewMapper mapper = new DocumentRequiringReviewMapper();

    @Test
    void should_map_document() {
        // Given a row of results for a Document
        Instant now = Instant.now();
        Instant fiveMinutesAgo = now.minus(5, ChronoUnit.MINUTES);
        Tuple tuple = Mockito.mock(Tuple.class);
        when(tuple.get(Expressions.path(Long.class, ID))).thenReturn(123L);
        when(tuple.get(Expressions.path(String.class, TYPE))).thenReturn("Document");
        when(tuple.get(Expressions.path(String.class, LOCAL_ID))).thenReturn("localId");
        when(tuple.get(Expressions.path(Instant.class, EVENT_DATE))).thenReturn(now);
        when(tuple.get(Expressions.path(Instant.class, DATE_RECEIVED))).thenReturn(fiveMinutesAgo);
        when(tuple.get(DOCUMENT.externalVersionCtrlNbr)).thenReturn((short) 2); // is an update
        when(tuple.get(DOCUMENT.sendingFacilityNm)).thenReturn("SendingFacilityName");
        when(tuple.get(CONDITION.conditionDescTxt)).thenReturn("ConditionName");

        // When an object is mapped from the row
        DocumentRequiringReview actual = mapper.toDocumentRequiringReview(tuple);

        // Then the appropriate values are set
        assertEquals(123L, actual.id().longValue());
        assertEquals("Document", actual.type());
        assertEquals("localId", actual.localId());
        assertEquals(now, actual.eventDate());
        assertEquals(fiveMinutesAgo, actual.dateReceived());
        assertEquals(true, actual.isUpdate());
        assertEquals(false, actual.isElectronic());
        assertEquals("SendingFacilityName", actual.facilityProviders().getReportingFacility().name());
        assertEquals("ConditionName", actual.descriptions().get(0).title());
        assertEquals("", actual.descriptions().get(0).value());

    }

    @Test
    void should_map_lab_report() {
        // Given a row of results for a Lab Report
        Instant now = Instant.now();
        Instant fiveMinutesAgo = now.minus(5, ChronoUnit.MINUTES);
        Tuple tuple = Mockito.mock(Tuple.class);
        when(tuple.get(Expressions.path(Long.class, ID))).thenReturn(124L);
        when(tuple.get(Expressions.path(String.class, TYPE))).thenReturn("LabReport");
        when(tuple.get(Expressions.path(String.class, LOCAL_ID))).thenReturn("labLocalId");
        when(tuple.get(Expressions.path(Instant.class, EVENT_DATE))).thenReturn(now);
        when(tuple.get(Expressions.path(Instant.class, DATE_RECEIVED))).thenReturn(fiveMinutesAgo);
        when(tuple.get(OBSERVATION.electronicInd)).thenReturn('N');
        when(tuple.get(DOCUMENT.sendingFacilityNm)).thenReturn("SendingFacilityName");

        // When an object is mapped from the row
        DocumentRequiringReview actual = mapper.toDocumentRequiringReview(tuple);

        // Then the appropriate values are set
        assertEquals(124L, actual.id().longValue());
        assertEquals("LabReport", actual.type());
        assertEquals("labLocalId", actual.localId());
        assertEquals(now, actual.eventDate());
        assertEquals(fiveMinutesAgo, actual.dateReceived());
        assertEquals(false, actual.isElectronic());
        assertEquals(false, actual.isUpdate());
        assertTrue(actual.descriptions().isEmpty());
        assertNull(actual.facilityProviders().getOrderingProvider());
        assertNull(actual.facilityProviders().getReportingFacility());
    }

    @Test
    void should_map_lab_report_external() {
        // Given a row of results for a Lab Report with external Indicator
        Tuple tuple = Mockito.mock(Tuple.class);
        when(tuple.get(Expressions.path(String.class, TYPE))).thenReturn("LabReport");
        when(tuple.get(OBSERVATION.electronicInd)).thenReturn('Y');

        // When an object is mapped from the row
        DocumentRequiringReview actual = mapper.toDocumentRequiringReview(tuple);

        // Then the appropriate values are set
        assertEquals(true, actual.isElectronic());
    }

    @Test
    void should_map_lab_report_null_external() {
        // Given a row of results for a Lab Report with external Indicator
        Tuple tuple = Mockito.mock(Tuple.class);
        when(tuple.get(Expressions.path(String.class, TYPE))).thenReturn("LabReport");
        when(tuple.get(OBSERVATION.electronicInd)).thenReturn(null);

        // When an object is mapped from the row
        DocumentRequiringReview actual = mapper.toDocumentRequiringReview(tuple);

        // Then the appropriate values are set
        assertEquals(false, actual.isElectronic());
    }

    @Test
    void should_map_morb_report() {
        // Given a row of results for a Morbidity Report
        Instant now = Instant.now();
        Instant fiveMinutesAgo = now.minus(5, ChronoUnit.MINUTES);
        Tuple tuple = Mockito.mock(Tuple.class);
        when(tuple.get(Expressions.path(Long.class, ID))).thenReturn(124L);
        when(tuple.get(Expressions.path(String.class, TYPE))).thenReturn("MorbReport");
        when(tuple.get(Expressions.path(String.class, LOCAL_ID))).thenReturn("morbLocalId");
        when(tuple.get(Expressions.path(Instant.class, EVENT_DATE))).thenReturn(now);
        when(tuple.get(Expressions.path(Instant.class, DATE_RECEIVED))).thenReturn(fiveMinutesAgo);
        when(tuple.get(OBSERVATION.electronicInd)).thenReturn('Y');
        when(tuple.get(DOCUMENT.sendingFacilityNm)).thenReturn("SendingFacilityName");
        when(tuple.get(CONDITION.conditionDescTxt)).thenReturn("ConditionName");

        // When an object is mapped from the row
        DocumentRequiringReview actual = mapper.toDocumentRequiringReview(tuple);

        // Then the appropriate values are set
        assertEquals(124L, actual.id().longValue());
        assertEquals("MorbReport", actual.type());
        assertEquals("morbLocalId", actual.localId());
        assertEquals(now, actual.eventDate());
        assertEquals(fiveMinutesAgo, actual.dateReceived());
        assertEquals(true, actual.isElectronic());
        assertEquals(false, actual.isUpdate());
        assertEquals(1, actual.descriptions().size());
        assertEquals("ConditionName", actual.descriptions().get(0).title());
        assertEquals("", actual.descriptions().get(0).value());
        assertNull(actual.facilityProviders().getOrderingProvider());
        assertNull(actual.facilityProviders().getReportingFacility());
    }


    @Test
    void should_map_ordering_provider() {
        Tuple tuple = Mockito.mock(Tuple.class);
        when(tuple.get(PERSON_NAME.nmPrefix)).thenReturn("prefix");
        when(tuple.get(PERSON_NAME.firstNm)).thenReturn("firstName");
        when(tuple.get(PERSON_NAME.lastNm)).thenReturn("lastName");
        when(tuple.get(PERSON_NAME.nmSuffix)).thenReturn(Suffix.JR);

        OrderingProvider provider = mapper.toOrderingProvider(tuple);
        assertEquals("prefix firstName lastName " + Suffix.JR.name(), provider.name());
    }

    @Test
    void should_map_reporting_facility() {
        Tuple tuple = Mockito.mock(Tuple.class);
        when(tuple.get(ORGANIZATION.displayNm)).thenReturn("organization display name");
        ReportingFacility reportingFacility = mapper.toReportingFacility(tuple);
        assertEquals("organization display name", reportingFacility.name());
    }

    @Test
    void should_map_description() {
        Tuple tuple = Mockito.mock(Tuple.class);
        when(tuple.get(OBS_VALUE_CODED.displayName)).thenReturn("value coded display name");
        when(tuple.get(OBSERVATION_2.cdDescTxt)).thenReturn("desc text");
        Description description = mapper.toDescription(tuple);
        assertEquals("value coded display name", description.value());
        assertEquals("desc text", description.title());
    }

    @Test
    void should_return_null() {
        // Given a row of results for an unknown type
        Tuple tuple = Mockito.mock(Tuple.class);
        when(tuple.get(Expressions.path(String.class, TYPE))).thenReturn("badType");

        // When an object is mapped from the row
        DocumentRequiringReview actual = mapper.toDocumentRequiringReview(tuple);

        // Then null is returned
        assertNull(actual);
    }

}
