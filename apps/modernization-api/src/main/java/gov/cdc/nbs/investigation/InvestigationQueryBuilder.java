package gov.cdc.nbs.investigation;

import java.util.ArrayList;
import java.util.Collection;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.NestedSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.config.security.SecurityUtil;
import gov.cdc.nbs.entity.elasticsearch.ElasticsearchActId;
import gov.cdc.nbs.entity.elasticsearch.ElasticsearchOrganizationParticipation;
import gov.cdc.nbs.entity.elasticsearch.ElasticsearchPersonParticipation;
import gov.cdc.nbs.entity.elasticsearch.Investigation;
import gov.cdc.nbs.exception.QueryException;
import gov.cdc.nbs.investigation.InvestigationFilter.CaseStatus;
import gov.cdc.nbs.investigation.InvestigationFilter.NotificationStatus;
import gov.cdc.nbs.service.SecurityService;
import gov.cdc.nbs.time.FlexibleInstantConverter;

@Component
public class InvestigationQueryBuilder {
    private static final String SUBJ_OF_PHC = "SubjOfPHC";

    private final SecurityService securityService;

    public InvestigationQueryBuilder(SecurityService securityService) {
        this.securityService = securityService;
    }

    @SuppressWarnings("squid:S3776")
    // ignore high cognitive complexity as the method is simply going through the
    // passed in parameters, checking if null, and if not appending to the query
    public NativeSearchQuery buildInvestigationQuery(InvestigationFilter filter, Pageable pageable) {
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
                    .withSorts(buildSort(pageable))
                    .withPageable(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()))
                    .build();
        }

        // conditions
        if (filter.getConditions() != null && !filter.getConditions().isEmpty()) {
            builder.must(QueryBuilders.termsQuery(Investigation.CONDITION_ID, filter.getConditions()));
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
        if (filter.getEventId() != null) {
            switch (filter.getEventId().getInvestigationEventType()) {
                case ABCS_CASE_ID:
                    var abcsCaseIdQuery = QueryBuilders.boolQuery()
                            .must(QueryBuilders.matchQuery(Investigation.ACT_IDS + "." + ElasticsearchActId.ACT_ID_SEQ,
                                    2))
                            .must(QueryBuilders.matchQuery(
                                    Investigation.ACT_IDS + "." + ElasticsearchActId.ROOT_EXTENSION_TXT,
                                    filter.getEventId().getId()));
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
                                    filter.getEventId().getId()));
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
                                    filter.getEventId().getId()));
                    var nestedStateCountyQuery = QueryBuilders.nestedQuery(Investigation.ACT_IDS, stateCountryCaseId,
                            ScoreMode.None);
                    builder.must(nestedStateCountyQuery);
                    break;
                case INVESTIGATION_ID:
                    builder.must(QueryBuilders.matchQuery(Investigation.LOCAL_ID,
                            filter.getEventId().getId()));
                    break;
                case NOTIFICATION_ID:
                    builder.must(
                            QueryBuilders.matchQuery(Investigation.NOTIFICATION_LOCAL_ID,
                                    filter.getEventId().getId()));
                    break;

                default:
                    throw new QueryException("Invalid event id type: " +
                            filter.getEventId().getInvestigationEventType());
            }
        }
        // Event date
        var eds = filter.getEventDate();
        if (eds != null) {
            if (eds.getFrom() == null || eds.getTo() == null || eds.getType() == null) {
                throw new QueryException(
                        "From, To, and EventDateType are required when querying by event date");
            }
            String field;
            switch (eds.getType()) {
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
                            eds.getType());
            }
            var from = FlexibleInstantConverter.toString(eds.getFrom());
            var to = FlexibleInstantConverter.toString(eds.getTo());
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

        // case status
        if (filter.getCaseStatuses() != null && !filter.getCaseStatuses().isEmpty()) {
            // UNASSIGNED is included in status list but is not an actual status, it represents a null value
            var statusStrings = filter.getCaseStatuses()
                    .stream()
                    .filter(s -> !s.equals(CaseStatus.UNASSIGNED))
                    .map(status -> status.toString().toUpperCase())
                    .toList();
            var includeUnassigned = filter.getCaseStatuses().contains(CaseStatus.UNASSIGNED);
            if (includeUnassigned) {
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
        // notification status
        if (filter.getNotificationStatuses() != null && !filter.getNotificationStatuses().isEmpty()) {
            // UNASSIGNED is included in status list but is not an actual status, it represents a null value
            var statusStrings = filter.getNotificationStatuses()
                    .stream()
                    .filter(s -> !s.equals(NotificationStatus.UNASSIGNED))
                    .map(status -> status.toString().toUpperCase())
                    .toList();
            var includeUnassigned = filter.getNotificationStatuses().contains(NotificationStatus.UNASSIGNED);
            if (includeUnassigned) {
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
        // processing status
        if (filter.getProcessingStatuses() != null) {
            // UNASSIGNED is included in status list but is not an actual status, it represents a null value
            var statusStrings = filter.getProcessingStatuses()
                    .stream()
                    .filter(s -> !s.equals(InvestigationFilter.ProcessingStatus.UNASSIGNED))
                    .map(status -> status.toString().toUpperCase())
                    .toList();
            var includeUnassigned = filter.getProcessingStatuses()
                    .contains(InvestigationFilter.ProcessingStatus.UNASSIGNED);
            if (includeUnassigned) {
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
                .withSorts(buildSort(pageable))
                .withPageable(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()))
                .build();
    }


    private Collection<SortBuilder<?>> buildSort(Pageable pageable) {

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


    /**
     * Adds a query to only return documents that the user has access to based on the users program area and
     * jurisdiction access
     */
    private void addProgramAreaJurisdictionQuery(BoolQueryBuilder builder, String programJurisdictionField) {
        var userDetails = SecurityUtil.getUserDetails();
        var validOids = securityService.getProgramAreaJurisdictionOids(userDetails);
        builder.must(QueryBuilders.termsQuery(programJurisdictionField, validOids));
    }

    /**
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
     * ]
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
}
