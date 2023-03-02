package gov.cdc.nbs.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.NestedSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import gov.cdc.nbs.config.security.SecurityUtil;
import gov.cdc.nbs.config.security.SecurityUtil.BusinessObjects;
import gov.cdc.nbs.config.security.SecurityUtil.Operations;
import gov.cdc.nbs.entity.elasticsearch.ElasticsearchActId;
import gov.cdc.nbs.entity.elasticsearch.ElasticsearchObservation;
import gov.cdc.nbs.entity.elasticsearch.ElasticsearchOrganizationParticipation;
import gov.cdc.nbs.entity.elasticsearch.ElasticsearchPersonParticipation;
import gov.cdc.nbs.entity.elasticsearch.Investigation;
import gov.cdc.nbs.entity.elasticsearch.LabReport;
import gov.cdc.nbs.entity.enums.converter.InstantConverter;
import gov.cdc.nbs.entity.odse.Observation;
import gov.cdc.nbs.exception.QueryException;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.graphql.filter.InvestigationFilter;
import gov.cdc.nbs.graphql.filter.LabReportFilter;
import gov.cdc.nbs.repository.ObservationRepository;
import gov.cdc.nbs.repository.ParticipationRepository;
import gov.cdc.nbs.repository.PersonRepository;
import gov.cdc.nbs.graphql.filter.LabReportFilter.EntryMethod;
import gov.cdc.nbs.graphql.filter.LabReportFilter.EventStatus;
import gov.cdc.nbs.graphql.filter.LabReportFilter.ProcessingStatus;
import gov.cdc.nbs.graphql.filter.LabReportFilter.UserType;
import lombok.RequiredArgsConstructor;
import gov.cdc.nbs.util.Constants;



@Service
@RequiredArgsConstructor
public class EventService {
	private static final String HAS_AUTHORITY = "hasAuthority('";
    private static final String VIEW_INVESTIGATION = HAS_AUTHORITY + Operations.VIEW + "-"
            + BusinessObjects.INVESTIGATION + "')";
    private static final String VIEW_LAB_REPORT = HAS_AUTHORITY + Operations.VIEW + "-"
            + BusinessObjects.OBSERVATIONLABREPORT
            + "')";
    private static final String VIEW_MORBIDITY_REPORT = HAS_AUTHORITY + Operations.VIEW + "-"
            + BusinessObjects.OBSERVATIONMORBIDITYREPORT
            + "')";
    private static final String SUBJ_OF_PHC = "SubjOfPHC";
    private static final String PATSBJ = "PATSBJ";

    @Value("${nbs.max-page-size: 50}")
    private Integer maxPageSize;

    private final InstantConverter instantConverter = new InstantConverter();
    private final ElasticsearchOperations operations;
    private final SecurityService securityService;

    @PersistenceContext
    private final EntityManager entityManager;
    
    
    @Autowired
    PersonRepository personReposity;
    
    @Autowired
    ParticipationRepository participationRepository;
    
    @Autowired
    ObservationRepository oboservationRepository;

    @PreAuthorize(VIEW_INVESTIGATION)
    public Page<Investigation> findInvestigationsByFilter(InvestigationFilter filter, GraphQLPage page) {
        var pageable = GraphQLPage.toPageable(page, maxPageSize);
        var query = buildInvestigationQuery(filter, pageable);
        return performSearch(query, Investigation.class);
    }

    @PreAuthorize(VIEW_INVESTIGATION)
    public Page<Investigation> findInvestigationsByFilterForExport(InvestigationFilter filter) {
        var query = buildInvestigationQuery(filter, Pageable.ofSize(1000));
        return performSearch(query, Investigation.class);
    }

    @PreAuthorize(VIEW_LAB_REPORT)
    public Page<LabReport> findLabReportsByFilter(LabReportFilter filter, GraphQLPage page) {
        var pageable = GraphQLPage.toPageable(page, maxPageSize);
        var query = buildLabReportQuery(filter, pageable);
        return performSearch(query, LabReport.class);
    }

    @PreAuthorize(VIEW_LAB_REPORT)
    public Page<LabReport> findLabReportsByFilterForExport(LabReportFilter filter) {
        var query = buildLabReportQuery(filter, Pageable.ofSize(1000));
        return performSearch(query, LabReport.class);
    }
    
    @PreAuthorize(VIEW_MORBIDITY_REPORT)
    public Page<Observation> findMorbidtyReportForPatient(Long patientId, GraphQLPage page) {
        var pageable = GraphQLPage.toPageable(page, maxPageSize);
        List<Observation> reports = findMorbidityReportsForPatient(patientId);
        return new PageImpl<>(reports, pageable, reports.size());
    }
    

    private <T> Page<T> performSearch(NativeSearchQuery query, Class<T> clazz) {
        var hits = operations.search(query, clazz);
        var list = hits.getSearchHits().stream().map(SearchHit::getContent).toList();
        return new PageImpl<>(list, query.getPageable(), hits.getTotalHits());
    }

    @SuppressWarnings("squid:S3776")
    // ignore high cognitive complexity as the method is simply going through the
    // passed in parameters, checking if null, and if not appending to the query
    private NativeSearchQuery buildLabReportQuery(LabReportFilter filter, Pageable pageable) {
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        // Lab reports are secured by Program Area and Jurisdiction
        addProgramAreaJurisdictionQuery(builder, LabReport.PROGRAM_JURISDICTION_OID);

        // OBS only for lab reports
        builder.must(QueryBuilders.matchQuery(LabReport.CLASS_CD, "OBS"));

        // act must be EVN
        builder.must(QueryBuilders.matchQuery(LabReport.MOOD_CD, "EVN"));

        if (filter == null) {
            return new NativeSearchQueryBuilder()
                    .withQuery(builder)
                    .withSorts(buildLabReportSort(pageable))
                    .withPageable(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()))
                    .build();
        }

        // program area
        if (filter.getProgramAreas() != null && !filter.getProgramAreas().isEmpty()) {
            builder.must(QueryBuilders.termsQuery(LabReport.PROGRAM_AREA_CD, filter.getProgramAreas()));
        }
        // jurisdictions
        if (filter.getJurisdictions() != null && !filter.getJurisdictions().isEmpty()) {
            builder.must(QueryBuilders.termsQuery(LabReport.JURISDICTION_CD, filter.getJurisdictions()));
        }
        // pregnancy status
        if (filter.getPregnancyStatus() != null) {
            var status = filter.getPregnancyStatus().toString().substring(0, 1);
            builder.must(QueryBuilders.matchQuery(LabReport.PREGNANT_IND_CD, status));
        }
        // event Id
        if (filter.getEventIdType() != null && filter.getEventId() != null) {
            switch (filter.getEventIdType()) {
                case ACCESSION_NUMBER:
                    var accessionNumberQuery = QueryBuilders.boolQuery()
                            .must(QueryBuilders.matchQuery(
                                    LabReport.ACT_IDS + "."
                                            + ElasticsearchActId.TYPE_CD,
                                    "Filler Number"))
                            .must(QueryBuilders.matchQuery(
                                    LabReport.ACT_IDS + "."
                                            + ElasticsearchActId.ROOT_EXTENSION_TXT,
                                    filter.getEventId()));
                    var nestedAccessionNumberQuery = QueryBuilders.nestedQuery(Investigation.ACT_IDS,
                            accessionNumberQuery,
                            ScoreMode.None);
                    builder.must(nestedAccessionNumberQuery);
                    break;
                case LAB_ID:
                    builder.must(QueryBuilders.matchQuery(LabReport.LOCAL_ID, filter.getEventId()));
                    break;
                default:
                    throw new QueryException("Invalid event type: " + filter.getEventIdType());
            }
        }
        // event date search
        if (filter.getEventDateSearch() != null) {
            var eds = filter.getEventDateSearch();
            if (eds.getFrom() == null || eds.getTo() == null) {
                throw new QueryException("'From' and 'To' required when performing event date search");
            }
            String field;
            switch (eds.getEventDateType()) {
                case DATE_OF_REPORT:
                    field = LabReport.ACTIVITY_TO_TIME;
                    break;
                case DATE_OF_SPECIMEN_COLLECTION:
                    field = LabReport.EFFECTIVE_FROM_TIME;
                    break;
                case DATE_RECEIVED_BY_PUBLIC_HEALTH:
                    field = LabReport.RPT_TO_STATE_TIME;
                    break;
                case LAB_REPORT_CREATE_DATE:
                    field = LabReport.ADD_TIME;
                    break;
                case LAST_UPDATE_DATE:
                    field = LabReport.OBSERVATION_LAST_CHG_TIME;
                    break;
                default:
                    throw new QueryException(
                            "Invalid event date type specified: " + eds.getEventDateType());
            }
            var from = instantConverter.write(eds.getFrom());
            var to = instantConverter.write(eds.getTo());
            builder.must(QueryBuilders.rangeQuery(field).from(from).to(to));
        }
        // entry methods / entered by
        /**
         * Entry Method Electronic = electronicInd: 'Y'
         * Entry Method Manual = electronicInd: 'N'
         * Entered by External = electronicInd: 'E
         */
        if ((filter.getEntryMethods() != null && !filter.getEntryMethods().isEmpty())
                || (filter.getEnteredBy() != null && !filter.getEnteredBy().isEmpty())) {
            var electronicIndCodes = new ArrayList<String>();
            if (filter.getEntryMethods() != null) {
                filter.getEntryMethods().forEach(em -> {
                    if (em.equals(EntryMethod.ELECTRONIC)) {
                        electronicIndCodes.add("Y");
                    } else if (em.equals(EntryMethod.MANUAL)) {
                        electronicIndCodes.add("N");
                    }
                });
            }
            if (filter.getEnteredBy() != null && filter.getEnteredBy().contains(UserType.EXTERNAL)) {
                electronicIndCodes.add("E");
            }
            builder.must(QueryBuilders.termsQuery(LabReport.ELECTRONIC_IND, electronicIndCodes));
        }
        // event status
        if (filter.getEventStatus() != null && !filter.getEventStatus().isEmpty()) {
            if (filter.getEventStatus().contains(EventStatus.NEW)) {
                if (filter.getEventStatus().contains(EventStatus.UPDATE)) {
                    builder.must(QueryBuilders.rangeQuery(LabReport.VERSION_CTRL_NBR).gte(1));
                } else {
                    builder.must(QueryBuilders.matchQuery(LabReport.VERSION_CTRL_NBR, 1));
                }
            } else if (filter.getEventStatus().contains(EventStatus.UPDATE)) {
                builder.must(QueryBuilders.rangeQuery(LabReport.VERSION_CTRL_NBR).gt(1));
            }
        }

        // processing status
        if (filter.getProcessingStatus() != null && !filter.getProcessingStatus().isEmpty()) {
            var validStatuses = new ArrayList<String>();
            if (filter.getProcessingStatus().contains(ProcessingStatus.PROCESSED)) {
                validStatuses.add("PROCESSED");
            }
            if (filter.getProcessingStatus().contains(ProcessingStatus.UNPROCESSED)) {
                validStatuses.add("UNPROCESSED");
            }
            builder.must(QueryBuilders.termsQuery(LabReport.RECORD_STATUS_CD, validStatuses));
        }
        // created by
        if (filter.getCreatedBy() != null) {
            builder.must(QueryBuilders.matchQuery(LabReport.ADD_USER_ID, filter.getCreatedBy()));
        }

        // last update
        if (filter.getLastUpdatedBy() != null) {
            builder.must(QueryBuilders.matchQuery(LabReport.LAST_CHG_USER_ID, filter.getLastUpdatedBy()));
        }

        if (filter.getPatientId() != null) {
            var patientIdQuery = QueryBuilders.boolQuery();
            patientIdQuery
                    .must(QueryBuilders.matchQuery(
                            LabReport.PERSON_PARTICIPATIONS + "." + ElasticsearchPersonParticipation.TYPE_CD,
                            PATSBJ));
            patientIdQuery
                    .must(QueryBuilders.matchQuery(
                            LabReport.PERSON_PARTICIPATIONS + "." + ElasticsearchPersonParticipation.PERSON_PARENT_UID,
                            filter.getPatientId()));
            builder.must(
                    QueryBuilders.nestedQuery(LabReport.PERSON_PARTICIPATIONS, patientIdQuery, ScoreMode.None));
        }

        // event provider/facility
        if (filter.getProviderSearch() != null) {
            var pSearch = filter.getProviderSearch();
            switch (pSearch.getProviderType()) {
                case ORDERING_FACILITY:
                    var orderingFacilityQuery = QueryBuilders.boolQuery()
                            .must(QueryBuilders.matchQuery(
                                    LabReport.ORGANIZATION_PARTICIPATIONS + "."
                                            + ElasticsearchOrganizationParticipation.TYPE_CD,
                                    "ORD"))
                            .must(QueryBuilders.matchQuery(
                                    LabReport.ORGANIZATION_PARTICIPATIONS + "."
                                            + ElasticsearchOrganizationParticipation.SUBJECT_CLASS_CD,
                                    "ORG"))
                            .must(QueryBuilders.matchQuery(
                                    LabReport.ORGANIZATION_PARTICIPATIONS + "."
                                            + ElasticsearchOrganizationParticipation.ENTITY_ID,
                                    pSearch.getProviderId()));
                    var nestedOrderingFacilityQuery = QueryBuilders.nestedQuery(Investigation.ACT_IDS,
                            orderingFacilityQuery,
                            ScoreMode.None);
                    builder.must(nestedOrderingFacilityQuery);
                    break;
                case ORDERING_PROVIDER:
                    var orderingProviderQuery = QueryBuilders.boolQuery()
                            .must(QueryBuilders.matchQuery(
                                    LabReport.PERSON_PARTICIPATIONS + "."
                                            + ElasticsearchPersonParticipation.TYPE_CD,
                                    "ORD"))
                            .must(QueryBuilders.matchQuery(
                                    LabReport.PERSON_PARTICIPATIONS + "."
                                            + ElasticsearchPersonParticipation.SUBJECT_CLASS_CD,
                                    "PSN"))
                            .must(QueryBuilders.matchQuery(
                                    LabReport.PERSON_PARTICIPATIONS + "."
                                            + ElasticsearchPersonParticipation.ENTITY_ID,
                                    pSearch.getProviderId()));
                    var nestedOrderingProviderQuery = QueryBuilders.nestedQuery(Investigation.ACT_IDS,
                            orderingProviderQuery,
                            ScoreMode.None);
                    builder.must(nestedOrderingProviderQuery);
                    break;
                case REPORTING_FACILITY:
                    var reportingFacilityQuery = QueryBuilders.boolQuery()
                            .must(QueryBuilders.matchQuery(
                                    LabReport.ORGANIZATION_PARTICIPATIONS + "."
                                            + ElasticsearchOrganizationParticipation.SUBJECT_CLASS_CD,
                                    "AUT"))
                            .must(QueryBuilders.matchQuery(
                                    LabReport.ORGANIZATION_PARTICIPATIONS + "."
                                            + ElasticsearchOrganizationParticipation.ENTITY_ID,
                                    pSearch.getProviderId()));
                    var nestedReportingFacilityQuery = QueryBuilders.nestedQuery(Investigation.ACT_IDS,
                            reportingFacilityQuery,
                            ScoreMode.None);
                    builder.must(nestedReportingFacilityQuery);
                    break;
            }
        }
        // resulted test
        if (filter.getResultedTest() != null) {
            var resultedTestQuery = QueryBuilders.boolQuery()
                    .must(QueryBuilders.matchQuery(
                            LabReport.OBSERVATIONS_FIELD + "." + ElasticsearchObservation.CD_DESC_TXT,
                            filter.getResultedTest()));
            var nestedResultedTestQuery = QueryBuilders.nestedQuery(LabReport.OBSERVATIONS_FIELD, resultedTestQuery,
                    ScoreMode.None);
            builder.must(nestedResultedTestQuery);
        }

        // coded result
        if (filter.getCodedResult() != null) {
            var codedResultQuery = QueryBuilders.boolQuery()
                    .must(QueryBuilders.matchQuery(
                            LabReport.OBSERVATIONS_FIELD + "." + ElasticsearchObservation.DISPLAY_NAME,
                            filter.getCodedResult()));
            var nestedCodedResultQuery = QueryBuilders.nestedQuery(LabReport.OBSERVATIONS_FIELD, codedResultQuery,
                    ScoreMode.None);
            builder.must(nestedCodedResultQuery);
        }

        return new NativeSearchQueryBuilder()
                .withQuery(builder)
                .withSorts(buildLabReportSort(pageable))
                .withPageable(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()))
                .build();
    }

    @SuppressWarnings("squid:S3776")
    // ignore high cognitive complexity as the method is simply going through the
    // passed in parameters, checking if null, and if not appending to the query
    private NativeSearchQuery buildInvestigationQuery(InvestigationFilter filter, Pageable pageable) {
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        // Investigations are secured by Program Area and Jurisdiction
        addProgramAreaJurisdictionQuery(builder, Investigation.PROGRAM_JURISDICTION_OID);

        // investigation type only
        builder.must(QueryBuilders.matchQuery(Investigation.CASE_TYPE_CD, "I"));

        // act must be EVN
        builder.must(QueryBuilders.matchQuery(Investigation.MOOD_CD, "EVN"));

        if (filter == null) {
            return new NativeSearchQueryBuilder()
                    .withQuery(builder)
                    .withSorts(buildInvestigationSort(pageable))
                    .withPageable(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()))
                    .build();
        }

        // conditions
        if (filter.getConditions() != null && !filter.getConditions().isEmpty()) {
            builder.must(QueryBuilders.termsQuery(Investigation.CD_DESC_TXT, filter.getConditions()));
        }
        // program areas
        if (filter.getProgramAreas() != null && !filter.getProgramAreas().isEmpty()) {
            builder.must(QueryBuilders.termsQuery(Investigation.PROGRAM_AREA_CD, filter.getProgramAreas()));
        }
        // jurisdictions
        if (filter.getJurisdictions() != null && !filter.getJurisdictions().isEmpty()) {
            builder.must(QueryBuilders.termsQuery(Investigation.JURISDICTION_CD, filter.getJurisdictions()));
        }
        // pregnancy status
        if (filter.getPregnancyStatus() != null) {
            var status = filter.getPregnancyStatus().toString().substring(0, 1);
            builder.must(QueryBuilders.matchQuery(Investigation.PREGNANT_IND_CD, status));
        }
        // Event Id / Type
        if (filter.getEventIdType() != null && filter.getEventId() != null) {
            switch (filter.getEventIdType()) {
                case ABCS_CASE_ID:
                    var abcsCaseIdQuery = QueryBuilders.boolQuery()
                            .must(QueryBuilders.matchQuery(Investigation.ACT_IDS + "." + ElasticsearchActId.ACT_ID_SEQ,
                                    2))
                            .must(QueryBuilders.matchQuery(
                                    Investigation.ACT_IDS + "." + ElasticsearchActId.ROOT_EXTENSION_TXT,
                                    filter.getEventId()));
                    var nestedAbcsCaseQuery = QueryBuilders.nestedQuery(Investigation.ACT_IDS, abcsCaseIdQuery,
                            ScoreMode.None);
                    builder.must(nestedAbcsCaseQuery);
                    break;
                case CITY_COUNTY_CASE_ID:
                    var cityCountryCaseId = QueryBuilders.boolQuery()
                            .must(QueryBuilders.matchQuery(Investigation.ACT_IDS + "." + ElasticsearchActId.ACT_ID_SEQ,
                                    2))
                            .must(QueryBuilders.matchQuery(Investigation.ACT_IDS + "." + ElasticsearchActId.TYPE_CD,
                                    "CITY"))
                            .must(QueryBuilders.matchQuery(
                                    Investigation.ACT_IDS + "." + ElasticsearchActId.ROOT_EXTENSION_TXT,
                                    filter.getEventId()));
                    var nestedCityCountyQuery = QueryBuilders.nestedQuery(Investigation.ACT_IDS, cityCountryCaseId,
                            ScoreMode.None);
                    builder.must(nestedCityCountyQuery);
                    break;
                case STATE_CASE_ID:
                    var stateCountryCaseId = QueryBuilders.boolQuery()
                            .must(QueryBuilders.matchQuery(Investigation.ACT_IDS + "." + ElasticsearchActId.ACT_ID_SEQ,
                                    2))
                            .must(QueryBuilders.matchQuery(Investigation.ACT_IDS + "." + ElasticsearchActId.TYPE_CD,
                                    "STATE"))
                            .must(QueryBuilders.matchQuery(
                                    Investigation.ACT_IDS + "." + ElasticsearchActId.ROOT_EXTENSION_TXT,
                                    filter.getEventId()));
                    var nestedStateCountyQuery = QueryBuilders.nestedQuery(Investigation.ACT_IDS, stateCountryCaseId,
                            ScoreMode.None);
                    builder.must(nestedStateCountyQuery);
                    break;
                case INVESTIGATION_ID:
                    builder.must(QueryBuilders.matchQuery(Investigation.LOCAL_ID, filter.getEventId()));
                    break;
                case NOTIFICATION_ID:
                    builder.must(QueryBuilders.matchQuery(Investigation.NOTIFICATION_LOCAL_ID, filter.getEventId()));
                    break;

                default:
                    throw new QueryException("Invalid event id type: " +
                            filter.getEventIdType());
            }
        }
        // Event date
        var eds = filter.getEventDateSearch();
        if (eds != null) {
            if (eds.getFrom() == null || eds.getTo() == null || eds.getEventDateType() == null) {
                throw new QueryException(
                        "From, To, and EventDateType are required when querying by event date");
            }
            String field;
            switch (eds.getEventDateType()) {
                case DATE_OF_REPORT:
                    field = Investigation.RPT_FORM_COMPLETE_TIME;
                    break;
                case INVESTIGATION_CLOSED_DATE:
                    field = Investigation.ACTIVITY_TO_TIME;
                    break;
                case INVESTIGATION_CREATE_DATE:
                    field = Investigation.ADD_TIME;
                    break;
                case INVESTIGATION_START_DATE:
                    field = Investigation.ACTIVITY_FROM_TIME;
                    break;
                case LAST_UPDATE_DATE:
                    field = Investigation.PUBLIC_HEALTH_CASE_LAST_CHANGE_TIME;
                    break;
                case NOTIFICATION_CREATE_DATE:
                    field = Investigation.NOTIFICATION_ADD_TIME;
                    break;
                default:
                    throw new QueryException("Invalid event date type " +
                            eds.getEventDateType());
            }
            var from = instantConverter.write(eds.getFrom());
            var to = instantConverter.write(eds.getTo());
            builder.must(QueryBuilders.rangeQuery(field).from(from).to(to));
        }
        // Created By
        if (filter.getCreatedBy() != null) {
            builder.must(QueryBuilders.matchQuery(Investigation.ADD_USER_ID, filter.getCreatedBy()));
        }
        // Updated By
        if (filter.getLastUpdatedBy() != null) {
            builder.must(QueryBuilders.matchQuery(Investigation.LAST_CHANGE_USER_ID, filter.getLastUpdatedBy()));
        }
        // Patient id
        if (filter.getPatientId() != null) {
            var patientIdQuery = QueryBuilders.boolQuery();
            patientIdQuery
                    .must(QueryBuilders.matchQuery(
                            Investigation.PERSON_PARTICIPATIONS + "." + ElasticsearchPersonParticipation.TYPE_CD,
                            SUBJ_OF_PHC));
            patientIdQuery
                    .must(QueryBuilders.matchQuery(
                            Investigation.PERSON_PARTICIPATIONS + "."
                                    + ElasticsearchPersonParticipation.PERSON_PARENT_UID,
                            filter.getPatientId()));
            builder.must(
                    QueryBuilders.nestedQuery(Investigation.PERSON_PARTICIPATIONS, patientIdQuery, ScoreMode.None));
        }
        // investigator id
        if (filter.getInvestigatorId() != null) {
            var investigatorQuery = QueryBuilders.boolQuery();
            investigatorQuery
                    .must(QueryBuilders.matchQuery(
                            Investigation.PERSON_PARTICIPATIONS + "." + ElasticsearchPersonParticipation.ENTITY_ID,
                            filter.getInvestigatorId()));
            builder.must(
                    QueryBuilders.nestedQuery(Investigation.PERSON_PARTICIPATIONS, investigatorQuery, ScoreMode.None));
        }
        // provider/facility
        var pfSearch = filter.getProviderFacilitySearch();
        if (pfSearch != null) {
            if (pfSearch.getEntityType() == null || pfSearch.getId() == null) {
                throw new QueryException(
                        "Entity type and entity Id required when querying provider/facility");
            }

            switch (pfSearch.getEntityType()) {
                case PROVIDER:
                    var providerQuery = QueryBuilders.boolQuery()
                            .must(QueryBuilders.matchQuery(ElasticsearchPersonParticipation.ENTITY_ID,
                                    pfSearch.getId()))
                            .must(QueryBuilders.matchQuery(ElasticsearchPersonParticipation.TYPE_CD,
                                    "PerAsReporterOfPHC"));

                    var nestedProviderQuery = QueryBuilders.nestedQuery(
                            Investigation.PERSON_PARTICIPATIONS,
                            providerQuery,
                            ScoreMode.None);

                    builder.must(nestedProviderQuery);
                    break;
                case FACILITY:
                    var facilityQuery = QueryBuilders.boolQuery()
                            .must(QueryBuilders.matchQuery(ElasticsearchOrganizationParticipation.ENTITY_ID,
                                    pfSearch.getId()))
                            .must(QueryBuilders.matchQuery(ElasticsearchOrganizationParticipation.TYPE_CD,
                                    "OrgAsReporterOfPHC"));

                    var nestedFacilityQuery = QueryBuilders.nestedQuery(
                            Investigation.PERSON_PARTICIPATIONS,
                            facilityQuery,
                            ScoreMode.None);

                    builder.must(nestedFacilityQuery);
                    break;
                default:
                    throw new QueryException("Invalid entity type: " + pfSearch.getEntityType());
            }

        }

        // investigation status
        if (filter.getInvestigationStatus() != null) {
            var status = filter.getInvestigationStatus().toString().substring(0, 1);
            builder.must(QueryBuilders.matchQuery(Investigation.INVESTIGATION_STATUS_CD, status));
        }

        // outbreak name
        if (filter.getOutbreakNames() != null && !filter.getOutbreakNames().isEmpty()) {
            builder.must(QueryBuilders.termsQuery(Investigation.OUTBREAK_NAME, filter.getOutbreakNames()));
        }

        // case status / include unassigned
        if (filter.getCaseStatuses() != null) {
            var cs = filter.getCaseStatuses();
            if (cs.getStatusList() == null || cs.getStatusList().isEmpty()
                    || !cs.isIncludeUnassigned()) {
                throw new QueryException(
                        "statusList and includeUnassigned are required when specifying caseStatuses");
            }
            var statusStrings = filter.getCaseStatuses().getStatusList().stream()
                    .map(status -> status.toString().toUpperCase())
                    .toList();
            if (cs.isIncludeUnassigned()) {
                // value is in list, or null
                var caseStatusQuery = QueryBuilders.boolQuery();
                statusStrings
                        .forEach(s -> caseStatusQuery.should(QueryBuilders.matchQuery(Investigation.CASE_CLASS_CD, s)));
                caseStatusQuery.mustNot(QueryBuilders.existsQuery(Investigation.CASE_CLASS_CD));
                builder.should(caseStatusQuery);
            } else {
                builder.must(QueryBuilders.termsQuery(Investigation.CASE_CLASS_CD, statusStrings));
            }
        }
        // notification status / include unassigned
        if (filter.getNotificationStatuses() != null) {
            var ns = filter.getNotificationStatuses();
            if (ns.getStatusList() == null || ns.getStatusList().isEmpty()
                    || !ns.isIncludeUnassigned()) {
                throw new QueryException(
                        "statusList and includeUnassigned are required when specifying notificationStatuses");
            }
            var statusStrings = ns.getStatusList().stream()
                    .map(status -> status.toString().toUpperCase())
                    .toList();
            if (ns.isIncludeUnassigned()) {
                // value is in list, or null
                var notificationStatusQuery = QueryBuilders.boolQuery();
                statusStrings.forEach(s -> notificationStatusQuery
                        .should(QueryBuilders.matchQuery(Investigation.NOTIFICATION_RECORD_STATUS_CD, s)));
                notificationStatusQuery.mustNot(QueryBuilders.existsQuery(Investigation.NOTIFICATION_RECORD_STATUS_CD));
                builder.should(notificationStatusQuery);
            } else {
                builder.must(QueryBuilders.termsQuery(Investigation.NOTIFICATION_RECORD_STATUS_CD, statusStrings));
            }
        }
        // processing status / include unassigned
        if (filter.getProcessingStatuses() != null) {
            var ps = filter.getProcessingStatuses();
            if (ps.getStatusList() == null || ps.getStatusList().isEmpty()
                    || !ps.isIncludeUnassigned()) {
                throw new QueryException(
                        "statusList and includeUnassigned are required when specifying processingStatuses");
            }
            var statusStrings = ps.getStatusList().stream()
                    .map(status -> status.toString().toUpperCase())
                    .toList();
            if (ps.isIncludeUnassigned()) {
                // value is in list, or null
                var notificationStatusQuery = QueryBuilders.boolQuery();
                statusStrings.forEach(s -> notificationStatusQuery
                        .should(QueryBuilders.matchQuery(Investigation.CURR_PROCESS_STATUS_CD, s)));
                notificationStatusQuery.mustNot(QueryBuilders.existsQuery(Investigation.CURR_PROCESS_STATUS_CD));
                builder.should(notificationStatusQuery);

            } else {
                builder.must(QueryBuilders.termsQuery(Investigation.CURR_PROCESS_STATUS_CD, statusStrings));
            }
        }

        return new NativeSearchQueryBuilder()
                .withQuery(builder)
                .withSorts(buildInvestigationSort(pageable))
                .withPageable(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()))
                .build();
    }
    

    private Collection<SortBuilder<?>> buildInvestigationSort(Pageable pageable) {

        if (pageable.getSort().isEmpty()) {
            return new ArrayList<>();
        }
        Collection<SortBuilder<?>> sorts = new ArrayList<>();
        pageable.getSort().stream().forEach(sort -> {
            switch (sort.getProperty()) {
                case "lastNm":
                    sorts.add(createNestedSortWithFilter(
                            Investigation.PERSON_PARTICIPATIONS,
                            ElasticsearchPersonParticipation.LAST_NAME + ".keyword",
                            ElasticsearchPersonParticipation.TYPE_CD,
                            SUBJ_OF_PHC,
                            sort.getDirection()));
                    break;
                case "birthTime":
                    sorts.add(createNestedSortWithFilter(
                            Investigation.PERSON_PARTICIPATIONS,
                            ElasticsearchPersonParticipation.BIRTH_TIME,
                            ElasticsearchPersonParticipation.TYPE_CD,
                            SUBJ_OF_PHC,
                            sort.getDirection()));
                    break;
                default:
                    throw new IllegalArgumentException("Invalid sort operator specified: " + sort.getProperty());
            }
        });
        return sorts;
    }

    private Collection<SortBuilder<?>> buildLabReportSort(Pageable pageable) {
        if (pageable.getSort().isEmpty()) {
            return new ArrayList<>();
        }
        Collection<SortBuilder<?>> sorts = new ArrayList<>();
        pageable.getSort().stream().forEach(sort -> {
            switch (sort.getProperty()) {
                case "lastNm":
                    sorts.add(createNestedSortWithFilter(
                            LabReport.PERSON_PARTICIPATIONS,
                            ElasticsearchPersonParticipation.LAST_NAME + ".keyword",
                            ElasticsearchPersonParticipation.TYPE_CD,
                            PATSBJ,
                            sort.getDirection()));
                    break;
                case "birthTime":
                    sorts.add(createNestedSortWithFilter(
                            LabReport.PERSON_PARTICIPATIONS,
                            ElasticsearchPersonParticipation.BIRTH_TIME,
                            ElasticsearchPersonParticipation.TYPE_CD,
                            PATSBJ,
                            sort.getDirection()));
                    break;
                default:
                    throw new IllegalArgumentException("Invalid sort operator specified: " + sort.getProperty());
            }
        });
        return sorts;
    }

    /**
     * 
     * Creates a sort that adhere to the following format
     * 
     * <pre>
     * "sort" : [
     *  {
     *    "nestedField.childField": {
     *      "order" : "asc",
     *      "nested": {
     *        "path": "nestedField",
     *        "filter": {
     *          "term" : {"nestedField.filterField" : "filterValue"}
     *        }
     *      }
     *   }
     *  }
     *]
     *
     * </pre>
     */
    private FieldSortBuilder createNestedSortWithFilter(String nestedField, String childField, String filterField,
            String filterValue, Direction direction) {
        return SortBuilders
                .fieldSort(nestedField + "." + childField)
                .order(direction == Direction.ASC ? SortOrder.ASC : SortOrder.DESC)
                .setNestedSort(new NestedSortBuilder(nestedField)
                        .setFilter(
                                QueryBuilders.termQuery(nestedField + "." + filterField, filterValue)));
    }

    /**
     * Return a list of lab reports that the user has access to, are associated
     * with a particular patient, and have the status of "UNPROCESSED"
     *
     * This currently only returns lab reports as those are the only documents
     * ingested in elasticsearch, but in the future will also include morbidity
     * reports and case reports
     */
    public Page<LabReport> findDocumentsRequiringReviewForPatient(Long patientId, GraphQLPage page) {
        var pageable = GraphQLPage.toPageable(page, maxPageSize);

        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        // Lab reports are secured by Program Area and Jurisdiction
        addProgramAreaJurisdictionQuery(builder, LabReport.PROGRAM_JURISDICTION_OID);

        // Patient id - must match parent uid as this is the Master Patient Record Id
        var patientIdQuery = QueryBuilders.boolQuery();
        patientIdQuery.must(
                QueryBuilders.matchQuery(
                        LabReport.PERSON_PARTICIPATIONS + "." + ElasticsearchPersonParticipation.PERSON_PARENT_UID,
                        patientId));

        builder.must(
                QueryBuilders.nestedQuery(LabReport.PERSON_PARTICIPATIONS, patientIdQuery, ScoreMode.None));

        // Status is Unprocessed
        builder.must(QueryBuilders.matchQuery(LabReport.RECORD_STATUS_CD, "UNPROCESSED"));

        var query = new NativeSearchQueryBuilder()
                .withQuery(builder)
                .withSorts(buildLabReportSort(pageable))
                .withPageable(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()))
                .build();
        return performSearch(query, LabReport.class);
    }

    public Page<Investigation> findOpenInvestigationsForPatient(Long patientId, GraphQLPage page) {
        var pageable = GraphQLPage.toPageable(page, maxPageSize);
        BoolQueryBuilder builder = QueryBuilders.boolQuery();

        // Investigations are secured by Program Area and Jurisdiction
        addProgramAreaJurisdictionQuery(builder, Investigation.PROGRAM_JURISDICTION_OID);

        // Patient id - must match parent uid as this is the Master Patient Record Id
        var patientIdQuery = QueryBuilders.boolQuery();
        patientIdQuery.must(
                QueryBuilders.matchQuery(
                        Investigation.PERSON_PARTICIPATIONS + "." + ElasticsearchPersonParticipation.PERSON_PARENT_UID,
                        patientId));

        builder.must(
                QueryBuilders.nestedQuery(Investigation.PERSON_PARTICIPATIONS, patientIdQuery, ScoreMode.None));

        // Status is 'O'
        builder.must(QueryBuilders.matchQuery(Investigation.INVESTIGATION_STATUS_CD, "O"));

        var query = new NativeSearchQueryBuilder()
                .withQuery(builder)
                .withSorts(buildInvestigationSort(pageable))
                .withPageable(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()))
                .build();
        return performSearch(query, Investigation.class);
    }
    
    public List<Observation> findMorbidityReportsForPatient( Long patientId) {
    	List<Long> personIds = personReposity.getPersonIdsByPersonParentId(patientId);
    	List<Long> actIds = participationRepository.getActIdsBySubjectEntityUids(personIds, Constants.REPORT_TYPE);
    	return oboservationRepository.findByIdIn(actIds)
    }

    /**
     * Adds a query to only return documents that the user has access to based on
     * the users program area and jurisdiction access
     */
    private void addProgramAreaJurisdictionQuery(BoolQueryBuilder builder, String programJurisdictionField) {
        var userDetails = SecurityUtil.getUserDetails();
        var validOids = securityService.getProgramAreaJurisdictionOids(userDetails);
        builder.must(QueryBuilders.termsQuery(programJurisdictionField, validOids));
    }
}
