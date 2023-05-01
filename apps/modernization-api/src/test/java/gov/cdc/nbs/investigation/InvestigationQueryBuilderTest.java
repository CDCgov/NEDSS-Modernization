package gov.cdc.nbs.investigation;

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
import org.elasticsearch.index.query.ExistsQueryBuilder;
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
import gov.cdc.nbs.entity.elasticsearch.ElasticsearchPersonParticipation;
import gov.cdc.nbs.entity.elasticsearch.Investigation;
import gov.cdc.nbs.exception.QueryException;
import gov.cdc.nbs.investigation.InvestigationFilter.CaseStatus;
import gov.cdc.nbs.investigation.InvestigationFilter.EventDate;
import gov.cdc.nbs.investigation.InvestigationFilter.EventDateType;
import gov.cdc.nbs.investigation.InvestigationFilter.EventId;
import gov.cdc.nbs.investigation.InvestigationFilter.IdType;
import gov.cdc.nbs.investigation.InvestigationFilter.InvestigationStatus;
import gov.cdc.nbs.investigation.InvestigationFilter.NotificationStatus;
import gov.cdc.nbs.investigation.InvestigationFilter.ProcessingStatus;
import gov.cdc.nbs.investigation.InvestigationFilter.ReportingEntityType;
import gov.cdc.nbs.message.enums.PregnancyStatus;
import gov.cdc.nbs.service.SecurityService;
import gov.cdc.nbs.support.util.RandomUtil;
import gov.cdc.nbs.time.FlexibleInstantConverter;

class InvestigationQueryBuilderTest {

    @Mock
    private SecurityService securityService;

    @InjectMocks
    private InvestigationQueryBuilder queryBuilder;


    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should_not_throw_exceptions() {
        setAuthentication();
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());
        var query = queryBuilder.buildInvestigationQuery(InvestigationTestUtil.createFilter(), Pageable.ofSize(10));
        assertNotNull(query);
    }

    @Test
    void should_include_lastNm_sort() {
        setAuthentication();
        var pageable = PageRequest.of(0, 20, Sort.by(Direction.ASC, "lastNm"));
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());
        var query = queryBuilder.buildInvestigationQuery(null, pageable);
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
        var query = queryBuilder.buildInvestigationQuery(null, pageable);
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
        assertThrows(IllegalArgumentException.class, () -> queryBuilder.buildInvestigationQuery(null, pageable));
    }

    @Test
    void should_add_program_area_jurisdictions() {
        // mock
        setAuthentication();
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());

        // method call
        var query = queryBuilder.buildInvestigationQuery(null, Pageable.ofSize(10));

        // assertions
        assertNotNull(query);

        var must = ((BoolQueryBuilder) query.getQuery()).must();

        // program_jurisdiction_oid clause was added
        var clause = must.stream()
                .filter(m -> {
                    if (m instanceof TermsQueryBuilder mq)
                        return mq.fieldName().equals(Investigation.PROGRAM_JURISDICTION_OID);
                    return false;
                })
                .findFirst()
                .map(m -> (TermsQueryBuilder) m);
        assertTrue(clause.isPresent());
        assertTrue(clause.get().values().contains(123L));
        assertTrue(clause.get().values().contains(321L));
    }

    @Test
    void should_add_case_type() {
        // mock
        setAuthentication();
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());

        // method call
        var query = queryBuilder.buildInvestigationQuery(null, Pageable.ofSize(10));

        // assertions
        assertNotNull(query);

        var must = ((BoolQueryBuilder) query.getQuery()).must();

        // Case type clause added
        var clause = must.stream()
                .filter(m -> {
                    if (m instanceof MatchQueryBuilder mq)
                        return mq.fieldName().equals(Investigation.CASE_TYPE_CD);
                    return false;
                })
                .findFirst()
                .map(m -> (MatchQueryBuilder) m);
        assertTrue(clause.isPresent());
        assertEquals("I", clause.get().value());
    }

    @Test
    void should_add_event_type() {
        // mock
        setAuthentication();
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());

        // method call
        var query = queryBuilder.buildInvestigationQuery(null, Pageable.ofSize(10));

        // assertions
        assertNotNull(query);
        var must = ((BoolQueryBuilder) query.getQuery()).must();

        // Event type clause was added
        var clause = findMatchQueryBuilder(Investigation.MOOD_CD, must);
        assertEquals("EVN", clause.value());
    }

    @Test
    void should_include_paging() {
        // mock
        setAuthentication();
        var pageable = Pageable.ofSize(13);
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());

        // method call
        var query = queryBuilder.buildInvestigationQuery(null, pageable);

        // assertions
        assertNotNull(query);
        assertEquals(pageable, query.getPageable());
    }

    @Test
    void should_include_conditions() {
        // mock
        setAuthentication();
        var pageable = Pageable.ofSize(13);
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());

        // method call
        var filter = new InvestigationFilter();
        filter.setConditions(Arrays.asList("Condition1", "Condition2"));
        var query = queryBuilder.buildInvestigationQuery(filter, pageable);

        // assertions
        assertNotNull(query);
        var must = ((BoolQueryBuilder) query.getQuery()).must();

        // Condition clause was added
        var clause = findTermsQueryBuilder(Investigation.CD_DESC_TXT, must);

        assertTrue(clause.values().contains("Condition1"));
        assertTrue(clause.values().contains("Condition2"));
    }

    @Test
    void should_include_program_areas() {
        // mock
        setAuthentication();
        var pageable = Pageable.ofSize(13);
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());

        // method call
        var filter = new InvestigationFilter();
        filter.setProgramAreas(Arrays.asList("PA1", "PA2"));
        var query = queryBuilder.buildInvestigationQuery(filter, pageable);

        // assertions
        assertNotNull(query);
        var must = ((BoolQueryBuilder) query.getQuery()).must();

        // program area clause was added
        var clause = findTermsQueryBuilder(Investigation.PROGRAM_AREA_CD, must);

        assertTrue(clause.values().contains("PA1"));
        assertTrue(clause.values().contains("PA2"));
    }

    @Test
    void should_include_jurisdictions() {
        // mock
        setAuthentication();
        var pageable = Pageable.ofSize(13);
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());

        // method call
        var filter = new InvestigationFilter();
        filter.setJurisdictions(Arrays.asList(321L, 123L));
        var query = queryBuilder.buildInvestigationQuery(filter, pageable);

        // assertions
        assertNotNull(query);
        var must = ((BoolQueryBuilder) query.getQuery()).must();

        // jurisdiction clause was added
        var clause = findTermsQueryBuilder(Investigation.JURISDICTION_CD, must);

        assertTrue(clause.values().contains(123L));
        assertTrue(clause.values().contains(321L));
    }

    @Test
    void should_include_pregnancy_status() {
        // mock
        setAuthentication();
        var pageable = Pageable.ofSize(13);
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());

        // method call
        var filter = new InvestigationFilter();
        filter.setPregnancyStatus(PregnancyStatus.NO);
        var query = queryBuilder.buildInvestigationQuery(filter, pageable);

        // assertions
        assertNotNull(query);
        var must = ((BoolQueryBuilder) query.getQuery()).must();

        // pregnancy status clause was added
        var clause = findMatchQueryBuilder(Investigation.PREGNANT_IND_CD, must);

        assertEquals(filter.getPregnancyStatus().toString().substring(0, 1), clause.value());
    }

    @Test
    void should_include_event_type_ABCS() {
        // mock
        setAuthentication();
        var pageable = Pageable.ofSize(13);
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());

        // method call
        var filter = new InvestigationFilter();
        filter.setEventId(new EventId(IdType.ABCS_CASE_ID, "eventId"));
        var query = queryBuilder.buildInvestigationQuery(filter, pageable);

        // assertions
        assertNotNull(query);
        var must = ((BoolQueryBuilder) query.getQuery()).must();

        // nested clause was added
        var nestedBuilders = findNestedQueryBuilders(must);

        var clause1 =
                findMatchQueryBuilder(Investigation.ACT_IDS + "." + ElasticsearchActId.ACT_ID_SEQ, nestedBuilders);
        assertEquals(2, clause1.value());

        var clause2 =
                findMatchQueryBuilder(Investigation.ACT_IDS + "." + ElasticsearchActId.ROOT_EXTENSION_TXT,
                        nestedBuilders);
        assertEquals(filter.getEventId().getId(), clause2.value());
    }

    @Test
    void should_include_event_type_city_county() {
        // mock
        setAuthentication();
        var pageable = Pageable.ofSize(13);
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());

        // method call
        var filter = new InvestigationFilter();
        filter.setEventId(new EventId(IdType.CITY_COUNTY_CASE_ID, "eventId"));
        var query = queryBuilder.buildInvestigationQuery(filter, pageable);

        // assertions
        assertNotNull(query);
        var must = ((BoolQueryBuilder) query.getQuery()).must();

        // nested clause was added
        var nestedBuilders = findNestedQueryBuilders(must);

        var clause1 =
                findMatchQueryBuilder(Investigation.ACT_IDS + "." + ElasticsearchActId.ACT_ID_SEQ, nestedBuilders);
        assertEquals(2, clause1.value());

        var clause2 =
                findMatchQueryBuilder(Investigation.ACT_IDS + "." + ElasticsearchActId.TYPE_CD,
                        nestedBuilders);
        assertEquals("CITY", clause2.value());

        var clause3 =
                findMatchQueryBuilder(Investigation.ACT_IDS + "." + ElasticsearchActId.ROOT_EXTENSION_TXT,
                        nestedBuilders);
        assertEquals(filter.getEventId().getId(), clause3.value());
    }


    @Test
    void should_include_event_type_state() {
        // mock
        setAuthentication();
        var pageable = Pageable.ofSize(13);
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());

        // method call
        var filter = new InvestigationFilter();
        filter.setEventId(new EventId(IdType.STATE_CASE_ID, "eventId"));
        var query = queryBuilder.buildInvestigationQuery(filter, pageable);

        // assertions
        assertNotNull(query);
        var must = ((BoolQueryBuilder) query.getQuery()).must();

        // nested clause was added
        var nestedBuilders = findNestedQueryBuilders(must);

        var clause1 =
                findMatchQueryBuilder(Investigation.ACT_IDS + "." + ElasticsearchActId.ACT_ID_SEQ, nestedBuilders);
        assertEquals(2, clause1.value());

        var clause2 =
                findMatchQueryBuilder(Investigation.ACT_IDS + "." + ElasticsearchActId.TYPE_CD,
                        nestedBuilders);
        assertEquals("STATE", clause2.value());

        var clause3 =
                findMatchQueryBuilder(Investigation.ACT_IDS + "." + ElasticsearchActId.ROOT_EXTENSION_TXT,
                        nestedBuilders);
        assertEquals(filter.getEventId().getId(), clause3.value());
    }

    @Test
    void should_include_event_type_investigation() {
        // mock
        setAuthentication();
        var pageable = Pageable.ofSize(13);
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());

        // method call
        var filter = new InvestigationFilter();
        filter.setEventId(new EventId(IdType.INVESTIGATION_ID, "eventId"));
        var query = queryBuilder.buildInvestigationQuery(filter, pageable);

        // assertions
        assertNotNull(query);
        var must = ((BoolQueryBuilder) query.getQuery()).must();

        var clause = findMatchQueryBuilder(Investigation.LOCAL_ID, must);
        assertEquals(filter.getEventId().getId(), clause.value());
    }

    @Test
    void should_include_event_type_notification() {
        // mock
        setAuthentication();
        var pageable = Pageable.ofSize(13);
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());

        // method call
        var filter = new InvestigationFilter();
        filter.setEventId(new EventId(IdType.NOTIFICATION_ID, "eventId"));
        var query = queryBuilder.buildInvestigationQuery(filter, pageable);

        // assertions
        assertNotNull(query);
        var must = ((BoolQueryBuilder) query.getQuery()).must();

        var clause = findMatchQueryBuilder(Investigation.NOTIFICATION_LOCAL_ID, must);
        assertEquals(filter.getEventId().getId(), clause.value());
    }

    @ParameterizedTest
    @EnumSource(EventDateType.class)
    void should_include_event_date(EventDateType dateType) {
        // mock
        setAuthentication();
        var pageable = Pageable.ofSize(13);
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());

        // method call
        var eds = new EventDate(dateType, RandomUtil.dateInPast(),
                LocalDate.now());
        var filter = new InvestigationFilter();
        filter.setEventDate(eds);
        var query = queryBuilder.buildInvestigationQuery(filter, pageable);

        // assertions
        assertNotNull(query);
        var must = ((BoolQueryBuilder) query.getQuery()).must();

        String path = getPathForDateType(dateType);

        var clause = findRangeQueryBuilder(path, must);
        assertEquals(FlexibleInstantConverter.toString(eds.getFrom()), clause.from());
        assertEquals(FlexibleInstantConverter.toString(eds.getTo()), clause.to());
    }

    @Test
    void should_throw_query_exception_for_missing_date() {
        // mock
        setAuthentication();
        var pageable = Pageable.ofSize(13);
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());

        // method call
        var eds = new EventDate(EventDateType.DATE_OF_REPORT, null,
                LocalDate.now());
        var filter = new InvestigationFilter();
        filter.setEventDate(eds);
        QueryException ex = null;
        try {
            queryBuilder.buildInvestigationQuery(filter, pageable);
        } catch (QueryException e) {
            ex = e;
        }

        // assertions
        assertNotNull(ex);
    }

    private String getPathForDateType(EventDateType dateType) {
        return switch (dateType) {
            case DATE_OF_REPORT -> Investigation.RPT_FORM_COMPLETE_TIME;
            case INVESTIGATION_CLOSED_DATE -> Investigation.ACTIVITY_TO_TIME;
            case INVESTIGATION_CREATE_DATE -> Investigation.ADD_TIME;
            case INVESTIGATION_START_DATE -> Investigation.ACTIVITY_FROM_TIME;
            case LAST_UPDATE_DATE -> Investigation.PUBLIC_HEALTH_CASE_LAST_CHANGE_TIME;
            case NOTIFICATION_CREATE_DATE -> Investigation.NOTIFICATION_ADD_TIME;
        };
    }

    @Test
    void should_include_created_by() {
        // mock
        setAuthentication();
        var pageable = Pageable.ofSize(13);
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());

        // method call
        var filter = new InvestigationFilter();
        filter.setCreatedBy(999L);
        var query = queryBuilder.buildInvestigationQuery(filter, pageable);

        // assertions
        assertNotNull(query);
        var must = ((BoolQueryBuilder) query.getQuery()).must();
        var clause = findMatchQueryBuilder(Investigation.ADD_USER_ID, must);
        assertEquals(999L, clause.value());
    }

    @Test
    void should_include_updated_by() {
        // mock
        setAuthentication();
        var pageable = Pageable.ofSize(13);
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());

        // method call
        var filter = new InvestigationFilter();
        filter.setLastUpdatedBy(888L);
        var query = queryBuilder.buildInvestigationQuery(filter, pageable);

        // assertions
        assertNotNull(query);
        var must = ((BoolQueryBuilder) query.getQuery()).must();
        var clause = findMatchQueryBuilder(Investigation.LAST_CHANGE_USER_ID, must);
        assertEquals(888L, clause.value());
    }

    @Test
    void should_include_patient_id() {
        // mock
        setAuthentication();
        var pageable = Pageable.ofSize(13);
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());

        // method call
        var filter = new InvestigationFilter();
        filter.setPatientId(123L);
        var query = queryBuilder.buildInvestigationQuery(filter, pageable);

        // assertions
        assertNotNull(query);
        var must = ((BoolQueryBuilder) query.getQuery()).must();
        var nested = findNestedQueryBuilders(must);

        var clause = findMatchQueryBuilder(
                Investigation.PERSON_PARTICIPATIONS + "." + ElasticsearchPersonParticipation.TYPE_CD, nested);
        assertEquals("SubjOfPHC", clause.value());

        var clause2 = findMatchQueryBuilder(Investigation.PERSON_PARTICIPATIONS + "."
                + ElasticsearchPersonParticipation.PERSON_PARENT_UID, nested);
        assertEquals(123L, clause2.value());
    }

    @Test
    void should_include_investigator_id() {
        // mock
        setAuthentication();
        var pageable = Pageable.ofSize(13);
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());

        // method call
        var filter = new InvestigationFilter();
        filter.setInvestigatorId(123L);
        var query = queryBuilder.buildInvestigationQuery(filter, pageable);

        // assertions
        assertNotNull(query);
        var must = ((BoolQueryBuilder) query.getQuery()).must();
        var nested = findNestedQueryBuilders(must);

        var clause = findMatchQueryBuilder(
                Investigation.PERSON_PARTICIPATIONS + "." + ElasticsearchPersonParticipation.ENTITY_ID, nested);
        assertEquals(123L, clause.value());
    }

    @Test
    void should_include_provider() {
        // mock
        setAuthentication();
        var pageable = Pageable.ofSize(13);
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());

        // method call
        var pfsearch = new InvestigationFilter.ProviderFacilitySearch(ReportingEntityType.PROVIDER, 123L);
        var filter = new InvestigationFilter();
        filter.setProviderFacilitySearch(pfsearch);

        var query = queryBuilder.buildInvestigationQuery(filter, pageable);

        // assertions
        assertNotNull(query);
        var must = ((BoolQueryBuilder) query.getQuery()).must();
        var nested = findNestedQueryBuilders(must);
        var clause = findMatchQueryBuilder(ElasticsearchPersonParticipation.ENTITY_ID, nested);
        assertEquals(filter.getProviderFacilitySearch().getId(), clause.value());
        var clause2 = findMatchQueryBuilder(ElasticsearchPersonParticipation.TYPE_CD, nested);
        assertEquals("PerAsReporterOfPHC", clause2.value());
    }

    @Test
    void should_include_investigatiion_status() {
        // mock
        setAuthentication();
        var pageable = Pageable.ofSize(13);
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());

        // method call
        var filter = new InvestigationFilter();
        filter.setInvestigationStatus(InvestigationStatus.CLOSED);
        var query = queryBuilder.buildInvestigationQuery(filter, pageable);

        // assertions
        assertNotNull(query);
        var must = ((BoolQueryBuilder) query.getQuery()).must();
        var clause = findMatchQueryBuilder(Investigation.INVESTIGATION_STATUS_CD, must);
        assertEquals(filter.getInvestigationStatus().toString().substring(0, 1), clause.value());
    }

    @Test
    void should_include_outbreak_name() {
        // mock
        setAuthentication();
        var pageable = Pageable.ofSize(13);
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());

        // method call
        var filter = new InvestigationFilter();
        filter.setOutbreakNames(Arrays.asList("outbreak1", "outbreak2"));
        var query = queryBuilder.buildInvestigationQuery(filter, pageable);

        // assertions
        assertNotNull(query);
        var must = ((BoolQueryBuilder) query.getQuery()).must();
        var clause = findTermsQueryBuilder(Investigation.OUTBREAK_NAME, must);
        assertTrue(clause.values().contains("outbreak1"));
        assertTrue(clause.values().contains("outbreak2"));
    }

    @Test
    void should_include_case_status() {
        // mock
        setAuthentication();
        var pageable = Pageable.ofSize(13);
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());

        // method call
        var filter = new InvestigationFilter();
        filter.setCaseStatuses(Arrays.asList(CaseStatus.UNASSIGNED, CaseStatus.CONFIRMED));
        var query = queryBuilder.buildInvestigationQuery(filter, pageable);

        // assertions
        assertNotNull(query);
        var should = ((BoolQueryBuilder) query.getQuery()).should();
        var nestedShould = ((BoolQueryBuilder) should.get(0)).should();
        var mustNot = ((BoolQueryBuilder) should.get(0)).mustNot();
        var clause = findMatchQueryBuilder(Investigation.CASE_CLASS_CD, nestedShould);
        findExistsQueryBuilder(Investigation.CASE_CLASS_CD, mustNot);

        assertEquals(CaseStatus.CONFIRMED.toString(), clause.value());
    }

    @Test
    void should_include_notification_status() {
        // mock
        setAuthentication();
        var pageable = Pageable.ofSize(13);
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());

        // method call
        var filter = new InvestigationFilter();
        filter.setNotificationStatuses(Arrays.asList(NotificationStatus.UNASSIGNED, NotificationStatus.APPROVED));
        var query = queryBuilder.buildInvestigationQuery(filter, pageable);

        // assertions
        assertNotNull(query);
        var should = ((BoolQueryBuilder) query.getQuery()).should();
        var nestedShould = ((BoolQueryBuilder) should.get(0)).should();
        var mustNot = ((BoolQueryBuilder) should.get(0)).mustNot();
        var clause = findMatchQueryBuilder(Investigation.NOTIFICATION_RECORD_STATUS_CD, nestedShould);
        findExistsQueryBuilder(Investigation.NOTIFICATION_RECORD_STATUS_CD, mustNot);

        assertEquals(NotificationStatus.APPROVED.toString(), clause.value());
    }

    @Test
    void should_include_processing_status() {
        // mock
        setAuthentication();
        var pageable = Pageable.ofSize(13);
        when(securityService.getProgramAreaJurisdictionOids(Mockito.any())).thenReturn(programAreaJurisdictionOids());

        // method call
        var filter = new InvestigationFilter();
        filter.setProcessingStatuses(Arrays.asList(ProcessingStatus.UNASSIGNED, ProcessingStatus.CLOSED_CASE));
        var query = queryBuilder.buildInvestigationQuery(filter, pageable);

        // assertions
        assertNotNull(query);
        var should = ((BoolQueryBuilder) query.getQuery()).should();
        var nestedShould = ((BoolQueryBuilder) should.get(0)).should();
        var mustNot = ((BoolQueryBuilder) should.get(0)).mustNot();
        var clause = findMatchQueryBuilder(Investigation.CURR_PROCESS_STATUS_CD, nestedShould);
        findExistsQueryBuilder(Investigation.CURR_PROCESS_STATUS_CD, mustNot);

        assertEquals(ProcessingStatus.CLOSED_CASE.toString(), clause.value());
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

    private ExistsQueryBuilder findExistsQueryBuilder(String path, List<QueryBuilder> builders) {
        var optional = builders.stream()
                .filter(m -> {
                    if (m instanceof ExistsQueryBuilder mq)
                        return mq.fieldName().equals(path);
                    return false;
                })
                .findFirst()
                .map(m -> (ExistsQueryBuilder) m);
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
        return new HashSet<Long>(Arrays.asList(123L, 321L));
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
                .authority("VIEW-INVESTIGATION")
                .businessObject("INVESTIGATION")
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
}
