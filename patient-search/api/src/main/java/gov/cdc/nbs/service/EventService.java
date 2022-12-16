package gov.cdc.nbs.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import gov.cdc.nbs.config.security.SecurityUtil;
import gov.cdc.nbs.config.security.SecurityUtil.BusinessObjects;
import gov.cdc.nbs.config.security.SecurityUtil.Operations;
import gov.cdc.nbs.entity.elasticsearch.Investigation;
import gov.cdc.nbs.entity.elasticsearch.LabReport;
import gov.cdc.nbs.entity.enums.converter.InstantConverter;
import gov.cdc.nbs.exception.QueryException;
import gov.cdc.nbs.graphql.searchFilter.InvestigationFilter;
import gov.cdc.nbs.graphql.searchFilter.LaboratoryReportFilter;
import gov.cdc.nbs.graphql.searchFilter.LaboratoryReportFilter.EventStatus;
import gov.cdc.nbs.graphql.searchFilter.LaboratoryReportFilter.ProcessingStatus;
import gov.cdc.nbs.graphql.searchFilter.LaboratoryReportFilter.UserType;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {
    private final InstantConverter instantConverter = new InstantConverter();
    private final ElasticsearchOperations operations;
    private final SecurityService securityService;
    private final String VIEW_INVESTIGATION = "hasAuthority('" + Operations.VIEW + "-"
            + BusinessObjects.INVESTIGATION + "')";
    private final String VIEW_LAB_REPORT = "hasAuthority('" + Operations.VIEW + "-"
            + BusinessObjects.OBSERVATIONLABREPORT
            + "')";
    private final int MAX_RESULTS = 500;

    @PersistenceContext
    private final EntityManager entityManager;

    @PreAuthorize(VIEW_INVESTIGATION)
    public SearchHits<Investigation> findInvestigationsByFilter(InvestigationFilter filter) {
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        // Investigations are secured by Program Area and Jurisdiction
        var userDetails = SecurityUtil.getUserDetails();
        var validOids = securityService.getProgramAreaJurisdictionOids(userDetails);
        addListQuery(builder, "program_jurisdiction_oid", validOids);

        // investigation type only
        builder.must(QueryBuilders.matchQuery("case_type_cd", "I"));

        // act must be EVN
        builder.must(QueryBuilders.matchQuery("mood_cd", "EVN"));

        if (filter == null) {
            var query = new NativeSearchQueryBuilder().withQuery(builder).withMaxResults(MAX_RESULTS).build();
            return operations.search(query, Investigation.class);
        }
        // conditions
        if (filter.getConditions() != null && !filter.getConditions().isEmpty()) {
            addListQuery(builder, "cd_desc_txt", filter.getConditions());
        }
        // program areas
        if (filter.getProgramAreas() != null && !filter.getProgramAreas().isEmpty()) {
            addListQuery(builder, "prog_area_cd", filter.getProgramAreas());
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
        // Event Id / Type
        if (filter.getEventIdType() != null && filter.getEventId() != null) {
            switch (filter.getEventIdType()) {
                case ABCS_CASE_ID:
                    builder.must(QueryBuilders.matchQuery("act_id_seq", 2));
                    builder.must(QueryBuilders.matchQuery("root_extension_txt", filter.getEventId()));
                    break;
                case CITY_COUNTY_CASE_ID:
                    builder.must(QueryBuilders.matchQuery("act_id_seq", 2));
                    builder.must(QueryBuilders.matchQuery("act_id_type_cd", "CITY"));
                    builder.must(QueryBuilders.matchQuery("root_extension_txt", filter.getEventId()));
                    break;
                case STATE_CASE_ID:
                    builder.must(QueryBuilders.matchQuery("act_id_seq", 2));
                    builder.must(QueryBuilders.matchQuery("act_id_type_cd", "STATE"));
                    builder.must(QueryBuilders.matchQuery("root_extension_txt", filter.getEventId()));
                    break;
                case INVESTIGATION_ID:
                    builder.must(QueryBuilders.matchQuery("public_health_case_local_id", filter.getEventId()));
                    break;
                case NOTIFICATION_ID:
                    builder.must(QueryBuilders.matchQuery("notification_local_id", filter.getEventId()));
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
                    field = "rpt_form_cmplt_time";
                    break;
                case INVESTIGATION_CLOSED_DATE:
                    field = "activity_to_time";
                    break;
                case INVESTIGATION_CREATE_DATE:
                    field = "add_time";
                    break;
                case INVESTIGATION_START_DATE:
                    field = "activity_from_time";
                    break;
                case LAST_UPDATE_DATE:
                    field = "public_health_case_last_chg_time";
                    break;
                case NOTIFICATION_CREATE_DATE:
                    field = "notification_add_time";
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
            builder.must(QueryBuilders.matchQuery("add_user_id", filter.getCreatedBy()));
        }
        // Updated By
        if (filter.getLastUpdatedBy() != null) {
            builder.must(QueryBuilders.matchQuery("last_chg_user_id", filter.getLastUpdatedBy()));
        }
        // provider facility + investigator Id
        var pfSearch = filter.getProviderFacilitySearch();
        if (pfSearch != null) {
            if (pfSearch.getEntityType() == null || pfSearch.getId() == null) {
                throw new QueryException(
                        "Entity type and entity Id required when querying provider/facility");
            }

            var doInvestigatorQuery = filter.getInvestigatorId() != null;
            switch (pfSearch.getEntityType()) {
                case PROVIDER:
                    if (doInvestigatorQuery) {
                        builder.must(QueryBuilders.matchQuery("participation_type_cd", "PerAsReporterOfPHC"));
                        addListQuery(builder, "subject_entity_uid",
                                Arrays.asList(pfSearch.getId(), filter.getInvestigatorId()));
                    } else {
                        builder.must(QueryBuilders.matchQuery("participation_type_cd", "PerAsReporterOfPHC"));
                        builder.must(QueryBuilders.matchQuery("subject_entity_uid", pfSearch.getId()));
                    }
                    break;
                case FACILITY:
                    if (doInvestigatorQuery) {
                        builder.must(QueryBuilders.matchQuery("participation_type_cd", "OrgAsReporterOfPHC"));
                        addListQuery(builder, "subject_entity_uid",
                                Arrays.asList(pfSearch.getId(), filter.getInvestigatorId()));
                    } else {
                        builder.must(QueryBuilders.matchQuery("participation_type_cd", "OrgAsReporterOfPHC"));
                        builder.must(QueryBuilders.matchQuery("subject_entity_uid", pfSearch.getId()));
                    }
                    break;
                default:
                    throw new QueryException("Invalid entity type: " + pfSearch.getEntityType());
            }
        } else {
            // investigator id
            if (filter.getInvestigatorId() != null) {
                builder.must(QueryBuilders.matchQuery("subject_entity_uid", filter.getInvestigatorId()));
            }
        }

        // investigation status
        if (filter.getInvestigationStatus() != null) {
            var status = filter.getInvestigationStatus().toString().substring(0, 1);
            builder.must(QueryBuilders.matchQuery("investigation_status_cd", status));
        }

        // outbreak name
        if (filter.getOutbreakNames() != null && !filter.getOutbreakNames().isEmpty()) {
            addListQuery(builder, "outbreak_name", filter.getOutbreakNames());
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
                statusStrings.forEach(s -> caseStatusQuery.should(QueryBuilders.matchQuery("class_cd", s)));
                caseStatusQuery.mustNot(QueryBuilders.existsQuery("class_cd"));
                builder.should(caseStatusQuery);
            } else {
                addListQuery(builder, "class_cd", statusStrings);
            }
        }
        // notification status / include unassigned
        if (filter.getNotificationStatuses() != null) {
            var cs = filter.getCaseStatuses();
            if (cs.getStatusList() == null || cs.getStatusList().isEmpty()
                    || cs.getIncludeUnassigned() == null) {
                throw new QueryException(
                        "statusList and includeUnassigned are required when specifying        notificationStatuses");
            }
            var statusStrings = cs.getStatusList().stream()
                    .map(status -> status.toString().toUpperCase())
                    .collect(Collectors.toList());
            if (cs.getIncludeUnassigned()) {
                // value is in list, or null
                var notificationStatusQuery = QueryBuilders.boolQuery();
                statusStrings.forEach(s -> notificationStatusQuery
                        .should(QueryBuilders.matchQuery("notification_record_status_cd", s)));
                notificationStatusQuery.mustNot(QueryBuilders.existsQuery("notification_record_status_cd"));
                builder.should(notificationStatusQuery);
            } else {
                addListQuery(builder, "notification_record_status_cd", statusStrings);
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
                        .should(QueryBuilders.matchQuery("curr_process_state_cd", s)));
                notificationStatusQuery.mustNot(QueryBuilders.existsQuery("curr_process_state_cd"));
                builder.should(notificationStatusQuery);

            } else {
                addListQuery(builder, "curr_process_state_cd", statusStrings);
            }
        }

        var query = new NativeSearchQueryBuilder().withQuery(builder).withMaxResults(MAX_RESULTS).build();
        return operations.search(query, Investigation.class);
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
            var query = new NativeSearchQueryBuilder().withQuery(builder).withMaxResults(MAX_RESULTS).build();
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

        var query = new NativeSearchQueryBuilder().withQuery(builder).withMaxResults(MAX_RESULTS).build();
        return operations.search(query, LabReport.class);
    }

}
