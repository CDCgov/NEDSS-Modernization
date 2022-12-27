package gov.cdc.nbs.service;

import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import gov.cdc.nbs.config.security.SecurityUtil;
import gov.cdc.nbs.config.security.SecurityUtil.BusinessObjects;
import gov.cdc.nbs.config.security.SecurityUtil.Operations;
import gov.cdc.nbs.entity.elasticsearch.ElasticsearchActId;
import gov.cdc.nbs.entity.elasticsearch.ElasticsearchParticipation;
import gov.cdc.nbs.entity.elasticsearch.Investigation;
import gov.cdc.nbs.entity.elasticsearch.LabReport;
import gov.cdc.nbs.entity.enums.converter.InstantConverter;
import gov.cdc.nbs.exception.QueryException;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.graphql.searchFilter.InvestigationFilter;
import gov.cdc.nbs.graphql.searchFilter.LaboratoryReportFilter;
import gov.cdc.nbs.graphql.searchFilter.LaboratoryReportFilter.EventStatus;
import gov.cdc.nbs.graphql.searchFilter.LaboratoryReportFilter.ProcessingStatus;
import gov.cdc.nbs.graphql.searchFilter.LaboratoryReportFilter.UserType;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {
    @Value("${nbs.max-page-size: 50}")
    private Integer MAX_PAGE_SIZE;

    private final InstantConverter instantConverter = new InstantConverter();
    private final ElasticsearchOperations operations;
    private final SecurityService securityService;
    private final String VIEW_INVESTIGATION = "hasAuthority('" + Operations.VIEW + "-"
            + BusinessObjects.INVESTIGATION + "')";
    private final String VIEW_LAB_REPORT = "hasAuthority('" + Operations.VIEW + "-"
            + BusinessObjects.OBSERVATIONLABREPORT
            + "')";

    @PersistenceContext
    private final EntityManager entityManager;

    @PreAuthorize(VIEW_INVESTIGATION)
    public Page<Investigation> findInvestigationsByFilter(InvestigationFilter filter, GraphQLPage page) {
        var pageable = GraphQLPage.toPageable(page, MAX_PAGE_SIZE);
        if (filter == null) {
            var query = new NativeSearchQueryBuilder().withQuery(QueryBuilders.boolQuery()).withPageable(pageable)
                    .build();
            return performSearch(query, Investigation.class);
        }
        var query = buildInvestigationQuery(filter, pageable);
        return performSearch(query, Investigation.class);
    }

    private <T> void addListQuery(BoolQueryBuilder builder, String field, Iterable<T> searchItems) {
        var shouldQuery = QueryBuilders.boolQuery();
        searchItems.forEach(i -> shouldQuery.should(QueryBuilders.matchQuery(field, i)));
        builder.must(shouldQuery);
    }

    @PreAuthorize(VIEW_LAB_REPORT)
    public SearchHits<LabReport> findLabReportsByFilter(LaboratoryReportFilter filter) {
        BoolQueryBuilder builder = QueryBuilders.boolQuery();

        // OBS only for lab reports ?
        builder.must(QueryBuilders.matchQuery("class_cd", "OBS"));

        // act must be EVN
        builder.must(QueryBuilders.matchQuery("mood_cd", "EVN"));

        // Lab reports are secured by Program Area and Jurisdiction
        var userDetails = SecurityUtil.getUserDetails();
        var validOids = securityService.getProgramAreaJurisdictionOids(userDetails);
        addListQuery(builder, "program_jurisdiction_oid", validOids);

        if (filter == null) {
            var query = new NativeSearchQueryBuilder().withQuery(builder).withMaxResults(MAX_PAGE_SIZE).build();
            return operations.search(query, LabReport.class);
        }
        // program area
        if (filter.getProgramAreas() != null && !filter.getProgramAreas().isEmpty()) {
            addListQuery(builder, "program_area_cd", filter.getProgramAreas());
        }
        // jurisdictions
        if (filter.getJurisdictions() != null && !filter.getJurisdictions().isEmpty()) {
            addListQuery(builder, "jurisdiction_cd", filter.getJurisdictions());
        }
        // pregnancy status
        if (filter.getPregnancyStatus() != null) {
            var status = filter.getPregnancyStatus().toString().substring(0, 1);
            builder.must(QueryBuilders.matchQuery("pregnant_ind_cd", status));
        }
        // event Id
        if (filter.getEventIdType() != null && filter.getEventId() != null) {
            switch (filter.getEventIdType()) {
                case ACCESSION_NUMBER:
                    builder.must(QueryBuilders.matchQuery("type_desc_txt", "Filler Number"));
                    builder.must(QueryBuilders.matchQuery("root_extension_txt", filter.getEventId()));
                    break;
                case LAB_ID:
                    builder.must(QueryBuilders.matchQuery("local_id", filter.getEventId()));
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
                    field = "activity_to_time";
                    break;
                case DATE_OF_SPECIMEN_COLLECTION:
                    field = "effective_from_time";
                    break;
                case DATE_RECEIVED_BY_PUBLIC_HEALTH:
                    field = "rpt_to_state_time";
                    break;
                case LAB_REPORT_CREATE_DATE:
                    field = "add_time";
                    break;
                case LAST_UPDATE_DATE:
                    field = "observation_last_chg_time";
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
                    switch (em) {
                        case ELECTRONIC:
                            electronicIndCodes.add("Y");
                            break;
                        case MANUAL:
                            electronicIndCodes.add("N");
                            break;
                    }
                });
            }
            if (filter.getEnteredBy() != null) {
                if (filter.getEnteredBy().contains(UserType.EXTERNAL)) {
                    electronicIndCodes.add("E");
                }
            }
            addListQuery(builder, "electronic_ind", electronicIndCodes);
        }
        // event status
        if (filter.getEventStatus() != null && !filter.getEventStatus().isEmpty()) {
            if (filter.getEventStatus().contains(EventStatus.NEW)) {
                if (filter.getEventStatus().contains(EventStatus.UPDATE)) {
                    builder.must(QueryBuilders.rangeQuery("version_ctrl_nbr").gte(1));
                } else {
                    builder.must(QueryBuilders.matchQuery("version_ctrl_nbr", 1));
                }
            } else if (filter.getEventStatus().contains(EventStatus.UPDATE)) {
                builder.must(QueryBuilders.rangeQuery("version_ctrl_nbr").gt(1));
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

            addListQuery(builder, "observation_record_status_cd", validStatuses);
        }
        // created by
        if (filter.getCreatedBy() != null) {
            builder.must(QueryBuilders.matchQuery("add_user_id", filter.getCreatedBy()));
        }

        // last update
        if (filter.getLastUpdatedBy() != null) {
            builder.must(QueryBuilders.matchQuery("last_chg_user_id", filter.getLastUpdatedBy()));
        }

        // event provider/facility
        if (filter.getProviderSearch() != null) {
            var pSearch = filter.getProviderSearch();
            switch (pSearch.getProviderType()) {
                case ORDERING_FACILITY:
                    builder.must(QueryBuilders.matchQuery("type_cd", "ORD"));
                    builder.must(QueryBuilders.matchQuery("subject_class_cd", "ORG"));
                    builder.must(QueryBuilders.matchQuery("subject_entity_uid", pSearch.getProviderId()));
                    break;
                case ORDERING_PROVIDER:
                    builder.must(QueryBuilders.matchQuery("type_cd", "ORD"));
                    builder.must(QueryBuilders.matchQuery("subject_class_cd", "PSN"));
                    builder.must(QueryBuilders.matchQuery("subject_entity_uid", pSearch.getProviderId()));
                    break;
                case REPORTING_FACILITY:
                    builder.must(QueryBuilders.matchQuery("subject_class_cd", "AUT"));
                    builder.must(QueryBuilders.matchQuery("subject_entity_uid", pSearch.getProviderId()));
                    break;
            }
        }
        // resulted test
        if (filter.getResultedTest() != null) {
            builder.must(QueryBuilders.matchQuery("cd_desc_txt", filter.getResultedTest()));
        }

        // coded result
        if (filter.getCodedResult() != null) {
            builder.must(QueryBuilders.matchQuery("display_name", filter.getCodedResult()));
        }

        var query = new NativeSearchQueryBuilder().withQuery(builder).withMaxResults(MAX_PAGE_SIZE).build();
        return operations.search(query, LabReport.class);
    }

    private <T> Page<T> performSearch(NativeSearchQuery query, Class<T> clazz) {
        var hits = operations.search(query, clazz);
        var list = hits.getSearchHits().stream().map(h -> h.getContent()).collect(Collectors.toList());
        return new PageImpl<>(list, query.getPageable(), hits.getTotalHits());
    }

    private NativeSearchQuery buildInvestigationQuery(InvestigationFilter filter, Pageable pageable) {
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        // Investigations are secured by Program Area and Jurisdiction
        var userDetails = SecurityUtil.getUserDetails();
        var validOids = securityService.getProgramAreaJurisdictionOids(userDetails);
        addListQuery(builder, Investigation.PROGRAM_JURISDICTION_OID, validOids);

        // investigation type only
        builder.must(QueryBuilders.matchQuery(Investigation.CASE_TYPE_CD, "I"));

        // act must be EVN
        builder.must(QueryBuilders.matchQuery(Investigation.MOOD_CD, "EVN"));

        // conditions
        if (filter.getConditions() != null && !filter.getConditions().isEmpty()) {
            addListQuery(builder, Investigation.CD_DESC_TXT, filter.getConditions());
        }
        // program areas
        if (filter.getProgramAreas() != null && !filter.getProgramAreas().isEmpty()) {
            addListQuery(builder, Investigation.PROGRAM_AREA_CD, filter.getProgramAreas());
        }
        // jurisdictions
        if (filter.getJurisdictions() != null && !filter.getJurisdictions().isEmpty()) {
            addListQuery(builder, Investigation.JURISDICTION_CD, filter.getJurisdictions());
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
        // investigator id
        if (filter.getInvestigatorId() != null) {
            var investigatorQuery = QueryBuilders.boolQuery();
            investigatorQuery
                    .must(QueryBuilders.matchQuery(ElasticsearchParticipation.ENTITY_ID, filter.getInvestigatorId()));
            builder.must(QueryBuilders.nestedQuery(Investigation.PARTICIPATIONS, investigatorQuery, ScoreMode.None));
        }
        // provider/facility
        var pfSearch = filter.getProviderFacilitySearch();
        if (pfSearch != null) {
            if (pfSearch.getEntityType() == null || pfSearch.getId() == null) {
                throw new QueryException(
                        "Entity type and entity Id required when querying provider/facility");
            }

            String typeCd;
            switch (pfSearch.getEntityType()) {
                case PROVIDER:
                    typeCd = "PerAsReporterOfPHC";
                    break;
                case FACILITY:
                    typeCd = "OrgAsReporterOfPHC";
                    break;
                default:
                    throw new QueryException("Invalid entity type: " + pfSearch.getEntityType());
            }
            var providerQuery = QueryBuilders.boolQuery()
                    .must(QueryBuilders.matchQuery(ElasticsearchParticipation.ENTITY_ID, pfSearch.getId()))
                    .must(QueryBuilders.matchQuery(ElasticsearchParticipation.TYPE_CD, typeCd));

            var nestedProviderQuery = QueryBuilders.nestedQuery(
                    Investigation.PARTICIPATIONS,
                    providerQuery,
                    ScoreMode.None);

            builder.must(nestedProviderQuery);
        }

        // investigation status
        if (filter.getInvestigationStatus() != null) {
            var status = filter.getInvestigationStatus().toString().substring(0, 1);
            builder.must(QueryBuilders.matchQuery(Investigation.INVESTIGATION_STATUS_CD, status));
        }

        // outbreak name
        if (filter.getOutbreakNames() != null && !filter.getOutbreakNames().isEmpty()) {
            addListQuery(builder, Investigation.OUTBREAK_NAME, filter.getOutbreakNames());
        }

        // case status / include unassigned
        if (filter.getCaseStatuses() != null) {
            var cs = filter.getCaseStatuses();
            if (cs.getStatusList() == null || cs.getStatusList().isEmpty()
                    || cs.getIncludeUnassigned() == null) {
                throw new QueryException(
                        "statusList and includeUnassigned are required when specifying caseStatuses");
            }
            var statusStrings = filter.getCaseStatuses().getStatusList().stream()
                    .map(status -> status.toString().toUpperCase())
                    .collect(Collectors.toList());
            if (cs.getIncludeUnassigned()) {
                // value is in list, or null
                var caseStatusQuery = QueryBuilders.boolQuery();
                statusStrings
                        .forEach(s -> caseStatusQuery.should(QueryBuilders.matchQuery(Investigation.CASE_CLASS_CD, s)));
                caseStatusQuery.mustNot(QueryBuilders.existsQuery(Investigation.CASE_CLASS_CD));
                builder.should(caseStatusQuery);
            } else {
                addListQuery(builder, Investigation.CASE_CLASS_CD, statusStrings);
            }
        }
        // notification status / include unassigned
        if (filter.getNotificationStatuses() != null) {
            var cs = filter.getCaseStatuses();
            if (cs.getStatusList() == null || cs.getStatusList().isEmpty()
                    || cs.getIncludeUnassigned() == null) {
                throw new QueryException(
                        "statusList and includeUnassigned are required when specifying notificationStatuses");
            }
            var statusStrings = cs.getStatusList().stream()
                    .map(status -> status.toString().toUpperCase())
                    .collect(Collectors.toList());
            if (cs.getIncludeUnassigned()) {
                // value is in list, or null
                var notificationStatusQuery = QueryBuilders.boolQuery();
                statusStrings.forEach(s -> notificationStatusQuery
                        .should(QueryBuilders.matchQuery(Investigation.NOTIFICATION_RECORD_STATUS_CD, s)));
                notificationStatusQuery.mustNot(QueryBuilders.existsQuery(Investigation.NOTIFICATION_RECORD_STATUS_CD));
                builder.should(notificationStatusQuery);
            } else {
                addListQuery(builder, Investigation.NOTIFICATION_RECORD_STATUS_CD, statusStrings);
            }
        }
        // processing status / include unassigned
        if (filter.getProcessingStatuses() != null) {
            var cs = filter.getProcessingStatuses();
            if (cs.getStatusList() == null || cs.getStatusList().isEmpty()
                    || cs.getIncludeUnassigned() == null) {
                throw new QueryException(
                        "statusList and includeUnassigned are required when specifying processingStatuses");
            }
            var statusStrings = cs.getStatusList().stream()
                    .map(status -> status.toString().toUpperCase())
                    .collect(Collectors.toList());
            if (cs.getIncludeUnassigned()) {
                // value is in list, or null
                var notificationStatusQuery = QueryBuilders.boolQuery();
                statusStrings.forEach(s -> notificationStatusQuery
                        .should(QueryBuilders.matchQuery(Investigation.CURR_PROCESS_STATUS_CD, s)));
                notificationStatusQuery.mustNot(QueryBuilders.existsQuery(Investigation.CURR_PROCESS_STATUS_CD));
                builder.should(notificationStatusQuery);

            } else {
                addListQuery(builder, Investigation.CURR_PROCESS_STATUS_CD, statusStrings);
            }
        }

        return new NativeSearchQueryBuilder().withQuery(builder).withPageable(pageable).build();
    }

}
