package gov.cdc.nbs.labreport;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import gov.cdc.nbs.config.security.NbsAuthority;
import gov.cdc.nbs.config.security.NbsUserDetails;
import gov.cdc.nbs.entity.elasticsearch.ElasticsearchActId;
import gov.cdc.nbs.entity.elasticsearch.ElasticsearchObservation;
import gov.cdc.nbs.entity.elasticsearch.ElasticsearchOrganizationParticipation;
import gov.cdc.nbs.entity.elasticsearch.ElasticsearchPersonParticipation;
import gov.cdc.nbs.entity.elasticsearch.LabReport;
import gov.cdc.nbs.exception.QueryException;
import gov.cdc.nbs.labreport.LabReportFilter.EntryMethod;
import gov.cdc.nbs.labreport.LabReportFilter.LabReportEventId;
import gov.cdc.nbs.labreport.LabReportFilter.EventStatus;
import gov.cdc.nbs.labreport.LabReportFilter.LabReportDateType;
import gov.cdc.nbs.labreport.LabReportFilter.LaboratoryEventIdType;
import gov.cdc.nbs.labreport.LabReportFilter.ProcessingStatus;
import gov.cdc.nbs.labreport.LabReportFilter.ProviderType;
import gov.cdc.nbs.labreport.LabReportFilter.UserType;
import gov.cdc.nbs.message.enums.PregnancyStatus;
import gov.cdc.nbs.service.SecurityService;
import gov.cdc.nbs.support.util.RandomUtil;
import gov.cdc.nbs.time.FlexibleInstantConverter;

class LabReportQueryBuilderTest {
    @Mock
    private SecurityService securityService;

    @InjectMocks
    private LabReportQueryBuilder queryBuilder;


    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should_not_throw_exceptions() {
        setAuthentication();
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());
        var query = queryBuilder.buildLabReportQuery(LabReportTestUtil.createFilter(), Pageable.ofSize(10));
        assertNotNull(query);
    }

    @Test
    void should_include_lastNm_sort() {
        setAuthentication();
        var pageable = PageRequest.of(0, 20, Sort.by(Direction.ASC, "lastNm"));
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());
        var query = queryBuilder.buildLabReportQuery(null, pageable);
        assertNotNull(query);
        var sorts = query.getElasticsearchSorts();
        assertNotNull(sorts);
        var sort = sorts.get(0);
        assertEquals(Direction.ASC.toString(), sort.order().name());
    }

    @Test
    void should_include_birth_sort() {
        setAuthentication();
        var pageable = PageRequest.of(0, 20, Sort.by(Direction.ASC, "birthTime"));
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());
        var query = queryBuilder.buildLabReportQuery(null, pageable);
        assertNotNull(query);
        var sorts = query.getElasticsearchSorts();
        assertNotNull(sorts);
        var sort = sorts.get(0);
        assertEquals(Direction.ASC.toString(), sort.order().name());
    }

    @Test
    void should_throw_sort_exception() {
        setAuthentication();
        var pageable = PageRequest.of(0, 20, Sort.by(Direction.ASC, "notSupported"));
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());
        assertThrows(IllegalArgumentException.class, () -> queryBuilder.buildLabReportQuery(null, pageable));
    }

    @Test
    void should_add_program_area_jurisdictions() {
        // mock
        setAuthentication();
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());

        // method call
        var query = queryBuilder.buildLabReportQuery(null, Pageable.ofSize(10));

        // assertions
        assertNotNull(query);

        var must = ((BoolQueryBuilder) query.getQuery()).must();

        // program_jurisdiction_oid clause was added
        var clause = must.stream()
                .filter(m -> {
                    if (m instanceof TermsQueryBuilder mq)
                        return mq.fieldName().equals(LabReport.PROGRAM_JURISDICTION_OID);
                    return false;
                })
                .findFirst()
                .map(m -> (TermsQueryBuilder) m);
        assertTrue(clause.isPresent());
        var oids = programAreaJurisdictionOids();
        oids.forEach(oid -> assertTrue(clause.get().values().contains(oid)));
    }

    @Test
    void should_add_class_cd() {
        // mock
        setAuthentication();
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());

        // method call
        var query = queryBuilder.buildLabReportQuery(null, Pageable.ofSize(10));

        // assertions
        assertNotNull(query);

        var must = ((BoolQueryBuilder) query.getQuery()).must();

        // Case type clause added
        var clause = findMatchQueryBuilder(LabReport.CLASS_CD, must);
        assertEquals("OBS", clause.value());
    }

    @Test
    void should_add_mood_cd() {
        // mock
        setAuthentication();
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());

        // method call
        var query = queryBuilder.buildLabReportQuery(null, Pageable.ofSize(10));

        // assertions
        assertNotNull(query);

        var must = ((BoolQueryBuilder) query.getQuery()).must();

        // Case type clause added
        var clause = findMatchQueryBuilder(LabReport.MOOD_CD, must);
        assertEquals("EVN", clause.value());
    }

    @Test
    void should_add_program_area() {
        // mock
        setAuthentication();
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());

        // method call
        var filter = new LabReportFilter();
        filter.setProgramAreas(Arrays.asList("PA1", "PA2"));
        var query = queryBuilder.buildLabReportQuery(filter, Pageable.ofSize(10));

        // assertions
        assertNotNull(query);

        var must = ((BoolQueryBuilder) query.getQuery()).must();

        // Case type clause added
        var clause = findTermsQueryBuilder(LabReport.PROGRAM_AREA_CD, must);
        filter.getProgramAreas().forEach(pa -> assertTrue(clause.values().contains(pa)));
    }

    @Test
    void should_add_jurisdiction() {
        // mock
        setAuthentication();
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());

        // method call
        var filter = new LabReportFilter();
        filter.setJurisdictions(Arrays.asList(11L, 12L, 13L));
        var query = queryBuilder.buildLabReportQuery(filter, Pageable.ofSize(10));

        // assertions
        assertNotNull(query);

        var must = ((BoolQueryBuilder) query.getQuery()).must();

        // Case type clause added
        var clause = findTermsQueryBuilder(LabReport.JURISDICTION_CD, must);
        filter.getJurisdictions().forEach(pa -> assertTrue(clause.values().contains(pa)));
    }

    @Test
    void should_add_pregnancy_status() {
        // mock
        setAuthentication();
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());

        // method call
        var filter = new LabReportFilter();
        filter.setPregnancyStatus(PregnancyStatus.YES);
        var query = queryBuilder.buildLabReportQuery(filter, Pageable.ofSize(10));

        // assertions
        assertNotNull(query);

        var must = ((BoolQueryBuilder) query.getQuery()).must();

        // Case type clause added
        var clause = findMatchQueryBuilder(LabReport.PREGNANT_IND_CD, must);
        assertEquals(filter.getPregnancyStatus().toString().substring(0, 1), clause.value());
    }

    @Test
    void should_add_accession_number() {
        // mock
        setAuthentication();
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());

        // method call
        var filter = new LabReportFilter();
        filter.setEventId(new LabReportEventId(LaboratoryEventIdType.ACCESSION_NUMBER, "eventId"));
        var query = queryBuilder.buildLabReportQuery(filter, Pageable.ofSize(10));

        // assertions
        assertNotNull(query);

        var must = ((BoolQueryBuilder) query.getQuery()).must();

        // Case type clause added
        var nested = findNestedQueryBuilders(must);
        var clause1 = findMatchQueryBuilder(LabReport.ACT_IDS + "." + ElasticsearchActId.TYPE_CD, nested);
        assertEquals("Filler Number", clause1.value());
        var clause2 = findMatchQueryBuilder(LabReport.ACT_IDS + "." + ElasticsearchActId.ROOT_EXTENSION_TXT, nested);
        assertEquals(filter.getEventId().getLabEventId(), clause2.value());
    }

    @Test
    void should_add_lab_id() {
        // mock
        setAuthentication();
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());

        // method call
        var filter = new LabReportFilter();
        filter.setEventId(new LabReportEventId(LaboratoryEventIdType.LAB_ID, "labId"));
        var query = queryBuilder.buildLabReportQuery(filter, Pageable.ofSize(10));

        // assertions
        assertNotNull(query);

        var must = ((BoolQueryBuilder) query.getQuery()).must();

        // Case type clause added
        var clause = findMatchQueryBuilder(LabReport.LOCAL_ID, must);
        assertEquals(filter.getEventId().getLabEventId(), clause.value());
    }

    @ParameterizedTest
    @EnumSource(LabReportDateType.class)
    void should_include_event_date(LabReportDateType dateType) {
        // mock
        setAuthentication();
        var pageable = Pageable.ofSize(13);
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());

        // method call
        var eds = new LabReportFilter.LaboratoryEventDateSearch(dateType, RandomUtil.dateInPast(),
                LocalDate.now());
        var filter = new LabReportFilter();
        filter.setEventDate(eds);
        var query = queryBuilder.buildLabReportQuery(filter, pageable);

        // assertions
        assertNotNull(query);
        var must = ((BoolQueryBuilder) query.getQuery()).must();

        String path = getPathForDateType(dateType);

        var clause = findRangeQueryBuilder(path, must);
        assertEquals(FlexibleInstantConverter.toString(eds.getFrom()), clause.from());
        assertEquals(FlexibleInstantConverter.toString(eds.getTo()), clause.to());
    }

    private String getPathForDateType(LabReportDateType dateType) {
        return switch (dateType) {
            case DATE_OF_REPORT -> LabReport.ACTIVITY_TO_TIME;
            case DATE_OF_SPECIMEN_COLLECTION -> LabReport.EFFECTIVE_FROM_TIME;
            case DATE_RECEIVED_BY_PUBLIC_HEALTH -> LabReport.RPT_TO_STATE_TIME;
            case LAB_REPORT_CREATE_DATE -> LabReport.ADD_TIME;
            case LAST_UPDATE_DATE -> LabReport.OBSERVATION_LAST_CHG_TIME;
        };
    }

    @Test
    void should_throw_query_exception_due_to_missing_date() {
        // mock
        setAuthentication();
        var pageable = Pageable.ofSize(13);
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());

        // method call
        var eds =
                new LabReportFilter.LaboratoryEventDateSearch(LabReportDateType.DATE_OF_REPORT, null, LocalDate.now());
        var filter = new LabReportFilter();
        filter.setEventDate(eds);
        assertThrows(QueryException.class, () -> queryBuilder.buildLabReportQuery(filter, pageable));
    }

    @Test
    void should_add_entry_method_Electronic() {
        // mock
        setAuthentication();
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());

        // method call
        var filter = new LabReportFilter();
        filter.setEntryMethods(Arrays.asList(EntryMethod.ELECTRONIC));
        var query = queryBuilder.buildLabReportQuery(filter, Pageable.ofSize(10));

        // assertions
        assertNotNull(query);

        var must = ((BoolQueryBuilder) query.getQuery()).must();

        // Case type clause added
        var clause = findTermsQueryBuilder(LabReport.ELECTRONIC_IND, must);
        assertTrue(clause.values().contains("Y"));
        assertEquals(1, clause.values().size());
    }

    @Test
    void should_add_entry_method_Manual() {
        // mock
        setAuthentication();
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());

        // method call
        var filter = new LabReportFilter();
        filter.setEntryMethods(Arrays.asList(EntryMethod.MANUAL));
        var query = queryBuilder.buildLabReportQuery(filter, Pageable.ofSize(10));

        // assertions
        assertNotNull(query);

        var must = ((BoolQueryBuilder) query.getQuery()).must();

        // Case type clause added
        var clause = findTermsQueryBuilder(LabReport.ELECTRONIC_IND, must);
        assertTrue(clause.values().contains("N"));
        assertEquals(1, clause.values().size());
    }

    @Test
    void should_add_entry_method_external() {
        // mock
        setAuthentication();
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());

        // method call
        var filter = new LabReportFilter();
        filter.setEnteredBy(Arrays.asList(UserType.EXTERNAL));
        var query = queryBuilder.buildLabReportQuery(filter, Pageable.ofSize(10));

        // assertions
        assertNotNull(query);

        var must = ((BoolQueryBuilder) query.getQuery()).must();

        // Case type clause added
        var clause = findTermsQueryBuilder(LabReport.ELECTRONIC_IND, must);
        assertTrue(clause.values().contains("E"));
        assertEquals(1, clause.values().size());
    }

    @Test
    void should_add_event_status_New() {
        // mock
        setAuthentication();
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());

        // method call
        var filter = new LabReportFilter();
        filter.setEventStatus(Arrays.asList(EventStatus.NEW));
        var query = queryBuilder.buildLabReportQuery(filter, Pageable.ofSize(10));

        // assertions
        assertNotNull(query);

        var must = ((BoolQueryBuilder) query.getQuery()).must();

        // Case type clause added
        var clause = findMatchQueryBuilder(LabReport.VERSION_CTRL_NBR, must);
        assertEquals(1, clause.value());
    }

    @Test
    void should_add_event_status_Update() {
        // mock
        setAuthentication();
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());

        // method call
        var filter = new LabReportFilter();
        filter.setEventStatus(Arrays.asList(EventStatus.UPDATE));
        var query = queryBuilder.buildLabReportQuery(filter, Pageable.ofSize(10));

        // assertions
        assertNotNull(query);

        var must = ((BoolQueryBuilder) query.getQuery()).must();

        // Case type clause added
        var clause = findRangeQueryBuilder(LabReport.VERSION_CTRL_NBR, must);
        assertEquals(1, clause.from());
        assertNull(clause.to());
        assertFalse(clause.includeLower());
        assertTrue(clause.includeUpper());
    }

    @Test
    void should_add_event_status_New_and_Update() {
        // mock
        setAuthentication();
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());

        // method call
        var filter = new LabReportFilter();
        filter.setEventStatus(Arrays.asList(EventStatus.NEW, EventStatus.UPDATE));
        var query = queryBuilder.buildLabReportQuery(filter, Pageable.ofSize(10));

        // assertions
        assertNotNull(query);

        var must = ((BoolQueryBuilder) query.getQuery()).must();

        // Case type clause added
        var clause = findRangeQueryBuilder(LabReport.VERSION_CTRL_NBR, must);
        assertEquals(1, clause.from());
        assertNull(clause.to());
        assertTrue(clause.includeLower());
        assertTrue(clause.includeUpper());
    }

    @Test
    void should_add_processing_status() {
        // mock
        setAuthentication();
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());

        // method call
        var filter = new LabReportFilter();
        filter.setProcessingStatus(Arrays.asList(ProcessingStatus.values()));
        var query = queryBuilder.buildLabReportQuery(filter, Pageable.ofSize(10));

        // assertions
        assertNotNull(query);

        var must = ((BoolQueryBuilder) query.getQuery()).must();

        // Case type clause added
        var clause = findTermsQueryBuilder(LabReport.RECORD_STATUS_CD, must);
        Arrays.asList(ProcessingStatus.values()).stream()
                .forEach(status -> assertTrue(clause.values().contains(status.toString())));
    }

    @Test
    void should_add_created_by() {
        // mock
        setAuthentication();
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());

        // method call
        var filter = new LabReportFilter();
        filter.setCreatedBy(119L);
        var query = queryBuilder.buildLabReportQuery(filter, Pageable.ofSize(10));

        // assertions
        assertNotNull(query);

        var must = ((BoolQueryBuilder) query.getQuery()).must();

        // Case type clause added
        var clause = findMatchQueryBuilder(LabReport.ADD_USER_ID, must);
        assertEquals(filter.getCreatedBy(), clause.value());
    }

    @Test
    void should_add_last_updated_by() {
        // mock
        setAuthentication();
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());

        // method call
        var filter = new LabReportFilter();
        filter.setLastUpdatedBy(129L);
        var query = queryBuilder.buildLabReportQuery(filter, Pageable.ofSize(10));

        // assertions
        assertNotNull(query);

        var must = ((BoolQueryBuilder) query.getQuery()).must();

        // Case type clause added
        var clause = findMatchQueryBuilder(LabReport.LAST_CHG_USER_ID, must);
        assertEquals(filter.getLastUpdatedBy(), clause.value());
    }

    @Test
    void should_add_patient_id() {
        // mock
        setAuthentication();
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());

        // method call
        var filter = new LabReportFilter();
        filter.setPatientId(100L);
        var query = queryBuilder.buildLabReportQuery(filter, Pageable.ofSize(10));

        // assertions
        assertNotNull(query);

        var must = ((BoolQueryBuilder) query.getQuery()).must();

        // Case type clause added
        var nested = findNestedQueryBuilders(must);
        var clause1 = findMatchQueryBuilder(
                LabReport.PERSON_PARTICIPATIONS + "." + ElasticsearchPersonParticipation.TYPE_CD, nested);
        assertEquals("PATSBJ", clause1.value());
        var clause2 = findMatchQueryBuilder(
                LabReport.PERSON_PARTICIPATIONS + "." + ElasticsearchPersonParticipation.PERSON_PARENT_UID, nested);
        assertEquals(filter.getPatientId(), clause2.value());
    }

    @Test
    void should_add_ordering_facility() {
        // mock
        setAuthentication();
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());

        // method call
        var ps = new LabReportFilter.LabReportProviderSearch(ProviderType.ORDERING_FACILITY, 1L);
        var filter = new LabReportFilter();
        filter.setProviderSearch(ps);
        var query = queryBuilder.buildLabReportQuery(filter, Pageable.ofSize(10));

        // assertions
        assertNotNull(query);

        var must = ((BoolQueryBuilder) query.getQuery()).must();

        // Case type clause added
        var nested = findNestedQueryBuilders(must);
        var clause1 = findMatchQueryBuilder(
                LabReport.ORGANIZATION_PARTICIPATIONS + "." + ElasticsearchOrganizationParticipation.TYPE_CD,
                nested);
        assertEquals("ORD", clause1.value());
        var clause2 = findMatchQueryBuilder(
                LabReport.ORGANIZATION_PARTICIPATIONS + "." + ElasticsearchOrganizationParticipation.SUBJECT_CLASS_CD,
                nested);
        assertEquals("ORG", clause2.value());
        var clause3 = findMatchQueryBuilder(
                LabReport.ORGANIZATION_PARTICIPATIONS + "." + ElasticsearchOrganizationParticipation.ENTITY_ID,
                nested);
        assertEquals(filter.getProviderSearch().getProviderId(), clause3.value());
    }

    @Test
    void should_add_ordering_provider() {
        // mock
        setAuthentication();
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());

        // method call
        var ps = new LabReportFilter.LabReportProviderSearch(ProviderType.ORDERING_PROVIDER, 2L);
        var filter = new LabReportFilter();
        filter.setProviderSearch(ps);
        var query = queryBuilder.buildLabReportQuery(filter, Pageable.ofSize(10));

        // assertions
        assertNotNull(query);

        var must = ((BoolQueryBuilder) query.getQuery()).must();

        // Case type clause added
        var nested = findNestedQueryBuilders(must);
        var clause1 = findMatchQueryBuilder(
                LabReport.PERSON_PARTICIPATIONS + "." + ElasticsearchOrganizationParticipation.TYPE_CD,
                nested);
        assertEquals("ORD", clause1.value());
        var clause2 = findMatchQueryBuilder(
                LabReport.PERSON_PARTICIPATIONS + "." + ElasticsearchOrganizationParticipation.SUBJECT_CLASS_CD,
                nested);
        assertEquals("PSN", clause2.value());
        var clause3 = findMatchQueryBuilder(
                LabReport.PERSON_PARTICIPATIONS + "." + ElasticsearchOrganizationParticipation.ENTITY_ID,
                nested);
        assertEquals(filter.getProviderSearch().getProviderId(), clause3.value());
    }

    @Test
    void should_add_reporting_facility() {
        // mock
        setAuthentication();
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());

        // method call
        var ps = new LabReportFilter.LabReportProviderSearch(ProviderType.REPORTING_FACILITY, 3L);
        var filter = new LabReportFilter();
        filter.setProviderSearch(ps);
        var query = queryBuilder.buildLabReportQuery(filter, Pageable.ofSize(10));

        // assertions
        assertNotNull(query);

        var must = ((BoolQueryBuilder) query.getQuery()).must();

        // Case type clause added
        var nested = findNestedQueryBuilders(must);
        var clause1 = findMatchQueryBuilder(
                LabReport.ORGANIZATION_PARTICIPATIONS + "." + ElasticsearchOrganizationParticipation.SUBJECT_CLASS_CD,
                nested);
        assertEquals("AUT", clause1.value());
        var clause3 = findMatchQueryBuilder(
                LabReport.ORGANIZATION_PARTICIPATIONS + "." + ElasticsearchOrganizationParticipation.ENTITY_ID,
                nested);
        assertEquals(filter.getProviderSearch().getProviderId(), clause3.value());
    }

    @Test
    void should_add_resulted_test() {
        // mock
        setAuthentication();
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());

        // method call
        var filter = new LabReportFilter();
        filter.setResultedTest("ResultedTest");
        var query = queryBuilder.buildLabReportQuery(filter, Pageable.ofSize(10));

        // assertions
        assertNotNull(query);

        var must = ((BoolQueryBuilder) query.getQuery()).must();

        // Case type clause added
        var nested = findNestedQueryBuilders(must);
        var clause = findMatchQueryBuilder(LabReport.OBSERVATIONS_FIELD + "." + ElasticsearchObservation.CD_DESC_TXT,
                nested);
        assertEquals(filter.getResultedTest(), clause.value());
    }

    @Test
    void should_add_coded_result() {
        // mock
        setAuthentication();
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());

        // method call
        var filter = new LabReportFilter();
        filter.setCodedResult("CodedResult");
        var query = queryBuilder.buildLabReportQuery(filter, Pageable.ofSize(10));

        // assertions
        assertNotNull(query);

        var must = ((BoolQueryBuilder) query.getQuery()).must();

        // Case type clause added
        var nested = findNestedQueryBuilders(must);
        var clause = findMatchQueryBuilder(LabReport.OBSERVATIONS_FIELD + "." + ElasticsearchObservation.DISPLAY_NAME,
                nested);
        assertEquals(filter.getCodedResult(), clause.value());
    }

    private RangeQueryBuilder findRangeQueryBuilder(String path, List<QueryBuilder> builders) {
        var optional = builders.stream()
                .filter(m -> {
                    if (m instanceof RangeQueryBuilder mq)
                        return mq.fieldName().equals(path);
                    return false;
                })
                .findFirst()
                .map(m -> (RangeQueryBuilder) m);
        assertNotNull(optional);
        return optional.get();
    }

    private MatchQueryBuilder findMatchQueryBuilder(String path, List<QueryBuilder> builders) {
        var optional = builders.stream()
                .filter(m -> {
                    if (m instanceof MatchQueryBuilder mq)
                        return mq.fieldName().equals(path);
                    return false;
                })
                .findFirst()
                .map(m -> (MatchQueryBuilder) m);
        assertNotNull(optional);
        return optional.get();
    }

    private TermsQueryBuilder findTermsQueryBuilder(String path, List<QueryBuilder> builders) {
        var optional = builders.stream()
                .filter(m -> {
                    if (m instanceof TermsQueryBuilder mq)
                        return mq.fieldName().equals(path);
                    return false;
                })
                .findFirst()
                .map(m -> (TermsQueryBuilder) m);
        assertNotNull(optional);
        return optional.get();
    }

    private List<QueryBuilder> findNestedQueryBuilders(List<QueryBuilder> builders) {
        var optional = builders.stream().filter(b -> b instanceof NestedQueryBuilder).findFirst();
        assertTrue(optional.isPresent());
        var nested = (NestedQueryBuilder) optional.get();
        return ((BoolQueryBuilder) nested.query()).must();
    }



    private Set<Long> programAreaJurisdictionOids() {
        return new HashSet<Long>(Arrays.asList(600L, 626L));
    }

    private Set<NbsAuthority> authorities() {
        var authorities = new HashSet<NbsAuthority>();
        authorities.add(NbsAuthority.builder()
                .authority("FIND-PATIENT")
                .businessObject("PATIENT")
                .businessOperation("FIND")
                .programArea("STD")
                .programAreaUid(1)
                .jurisdiction("130001")
                .build());
        authorities.add(NbsAuthority.builder()
                .authority("VIEW-OBSERVATIONLABREPORT")
                .businessObject("OBSERVATIONLABREPORT")
                .businessOperation("VIEW")
                .programArea("STD")
                .programAreaUid(2)
                .jurisdiction("130002")
                .build());
        return authorities;
    }

    private void setAuthentication() {
        var userDetails = userDetails();
        var authentication = new PreAuthenticatedAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private NbsUserDetails userDetails() {
        return NbsUserDetails.builder()
                .id(1L)
                .username("MOCK-USER")
                .token("token")
                .authorities(authorities())
                .isEnabled(true)
                .build();
    }
}
