package gov.cdc.nbs.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query.Builder;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import gov.cdc.nbs.config.security.SecurityUtil;
import gov.cdc.nbs.config.security.SecurityUtil.BusinessObjects;
import gov.cdc.nbs.config.security.SecurityUtil.Operations;
import gov.cdc.nbs.entity.odse.Act;
import gov.cdc.nbs.entity.odse.QAct;
import gov.cdc.nbs.entity.odse.QActId;
import gov.cdc.nbs.entity.odse.QActRelationship;
import gov.cdc.nbs.entity.odse.QObsValueCoded;
import gov.cdc.nbs.entity.odse.QObservation;
import gov.cdc.nbs.entity.odse.QParticipation;
import gov.cdc.nbs.exception.QueryException;
import gov.cdc.nbs.graphql.searchFilter.InvestigationFilter;
import gov.cdc.nbs.graphql.searchFilter.LaboratoryReportFilter;
import gov.cdc.nbs.graphql.searchFilter.LaboratoryReportFilter.EventStatus;
import gov.cdc.nbs.graphql.searchFilter.LaboratoryReportFilter.ProcessingStatus;
import gov.cdc.nbs.graphql.searchFilter.LaboratoryReportFilter.UserType;
import gov.cdc.nbs.model.InvestigationDocument;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {

    private final ElasticsearchClient client;
    private final SecurityService securityService;
    private final String VIEW_INVESTIGATION = "hasAuthority('" + Operations.VIEW + "-"
            + BusinessObjects.INVESTIGATION + "')";
    private final String VIEW_LAB_REPORT = "hasAuthority('" + Operations.VIEW + "-"
            + BusinessObjects.OBSERVATIONLABREPORT
            + "')";

    @PersistenceContext
    private final EntityManager entityManager;

    @PreAuthorize(VIEW_INVESTIGATION)
    public SearchResponse<InvestigationDocument> findInvestigationsByFilter(InvestigationFilter filter,
            Pageable pageable) {
        try {
            return client.search(s -> s
                    .index("investigation")
                    .collapse(t -> t.field("participation_subject_entity_uid"))
                    .size(pageable.getPageSize())
                    .from((int) pageable.getOffset())
                    .query(q -> {
                        // Investigations are secured by Program Area and Jurisdiction
                        var userDetails = SecurityUtil.getUserDetails();
                        var validOids = securityService.getProgramAreaJurisdictionOids(userDetails);
                        var oidQuery = validOids.stream()
                                .map(oid -> MatchQuery.of(m -> m
                                        .field("public_health_case_program_jurisdiction_oid")
                                        .query(oid))._toQuery())
                                .collect(Collectors.toList());
                        q.bool(b -> b.should(oidQuery));

                        // investigation type only
                        q.match(m -> m.field("public_health_case_case_type_cd").query("I"));
                        // conditions
                        addBoolQuery(q, "public_health_case_cd_desc_txt", filter.getConditions());
                        // program areas
                        addBoolQuery(q, "public_health_case_prog_area_cd", filter.getProgramAreas());
                        // jurisdictions
                        if (filter.getJurisdictions() != null && !filter.getJurisdictions().isEmpty()) {
                            // jurisdictions come in as List<Long> need to cast to List<String> for query
                            var jurisdictionStrings = filter.getJurisdictions().stream().map(Object::toString)
                                    .collect(Collectors.toList());
                            addBoolQuery(q, "public_health_case_jurisdiction_cd", jurisdictionStrings);
                        }
                        // pregnancy status
                        if (filter.getPregnancyStatus() != null) {
                            var status = filter.getPregnancyStatus().toString().substring(0, 1);
                            q.match(m -> m.field("public_health_case_pregnant_ind_cd").query(status));
                        }
                        // Event Id / Type
                        if (filter.getEventIdType() != null && filter.getEventId() != null) {
                            switch (filter.getEventIdType()) {
                                case ABCS_CASE_ID:
                                    q.match(m -> m.field("act_id_act_id_seq").query(2));
                                    q.match(m -> m.field("act_id_root_extension_txt").query(filter.getEventId()));
                                    break;
                                case CITY_COUNTY_CASE_ID:
                                    q.match(m -> m.field("act_id_act_id_seq").query(2));
                                    q.match(m -> m.field("act_id_type_cd").query("CITY"));
                                    q.match(m -> m.field("act_id_root_extension_txt").query(filter.getEventId()));
                                    break;
                                case STATE_CASE_ID:
                                    q.match(m -> m.field("act_id_act_id_seq").query(2));
                                    q.match(m -> m.field("act_id_type_cd").query("STATE"));
                                    q.match(m -> m.field("act_id_root_extension_txt").query(filter.getEventId()));
                                    break;
                                case INVESTIGATION_ID:
                                    q.match(m -> m.field("public_health_case_local_id").query(filter.getEventId()));
                                    break;
                                case NOTIFICATION_ID:
                                    q.match(m -> m.field("notification_local_id").query(filter.getEventId()));
                                    break;

                                default:
                                    throw new QueryException("Invalid event id type: " + filter.getEventIdType());
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
                                    field = "public_health_case_rpt_form_cmplt_time";
                                    break;
                                case INVESTIGATION_CLOSED_DATE:
                                    field = "public_health_case_activity_to_time";
                                    break;
                                case INVESTIGATION_CREATE_DATE:
                                    field = "public_health_case_add_time";
                                    break;
                                case INVESTIGATION_START_DATE:
                                    field = "public_health_case_activity_from_time";
                                    break;
                                case LAST_UPDATE_DATE:
                                    field = "public_health_case_last_chg_time";
                                    break;
                                case NOTIFICATION_CREATE_DATE:
                                    field = "notification_add_time";
                                    break;
                                default:
                                    throw new QueryException("Invalid event date type " + eds.getEventDateType());
                            }
                            q.bool(b -> b.filter(f -> f.range(r -> r
                                    .field(field)
                                    .from(eds.getFrom().toString())
                                    .to(eds.getTo().toString()))));
                        }
                        // Created By
                        q.match(m -> m.field("public_health_case_add_user_id").query(filter.getCreatedBy()));
                        // Updated By
                        q.match(m -> m.field("public_health_case_last_chg_user_id").query(filter.getCreatedBy()));
                        // provider facility + investigator Id
                        var pfSearch = filter.getProviderFacilitySearch();
                        if (pfSearch != null) {
                            if (pfSearch.getEntityType() == null || pfSearch.getId() == null) {
                                throw new QueryException(
                                        "Entity type and entity Id required when querying provider/facility");
                            }
                            // provider/facility and investigator both check for entity uid's. We need to
                            // make sure it does an OR instead of two ANDs
                            var doInvestigatorQuery = filter.getInvestigatorId() != null;
                            switch (pfSearch.getEntityType()) {
                                case PROVIDER:
                                    if (doInvestigatorQuery) {
                                        q.match(m -> m.field("participation_type_cd").query("PerAsReporterOfPHC"));
                                        q.bool(b -> b.should(Arrays.asList(
                                                MatchQuery.of(m -> m
                                                        .field("participation_subject_entity_uid")
                                                        .query(pfSearch.getId()))._toQuery(),
                                                MatchQuery.of(m -> m
                                                        .field("person_person_uid")
                                                        .query(filter.getInvestigatorId()))._toQuery())));
                                    } else {
                                        q.match(m -> m.field("participation_type_cd").query("PerAsReporterOfPHC"));
                                        q.match(m -> m.field("participation_subject_entity_uid")
                                                .query(pfSearch.getId()));
                                    }
                                    break;
                                case FACILITY:
                                    if (doInvestigatorQuery) {
                                        q.match(m -> m.field("participation_type_cd").query("OrgAsReporterOfPHC"));
                                        q.bool(b -> b.should(Arrays.asList(
                                                MatchQuery.of(m -> m
                                                        .field("participation_subject_entity_uid")
                                                        .query(pfSearch.getId()))._toQuery(),
                                                MatchQuery.of(m -> m
                                                        .field("person_person_uid")
                                                        .query(filter.getInvestigatorId()))._toQuery())));
                                    } else {
                                        q.match(m -> m.field("participation_type_cd").query("OrgAsReporterOfPHC"));
                                        q.match(m -> m.field("participation_subject_entity_uid")
                                                .query(pfSearch.getId()));
                                    }
                                    break;
                                default:
                                    throw new QueryException("Invalid entity type: " + pfSearch.getEntityType());
                            }
                        } else {
                            // investigator id
                            q.match(m -> m.field("person_person_uid").query(filter.getInvestigatorId()));
                        }

                        // investigation status
                        if (filter.getInvestigationStatus() != null) {
                            var status = filter.getInvestigationStatus().toString().substring(0, 1);
                            q.match(m -> m.field("public_health_case_investigation_status_cd").query(status));
                        }

                        // outbreak name
                        addBoolQuery(q, "public_health_case_outbreak_name", filter.getOutbreakNames());

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
                                var queries = statusStrings.stream()
                                        .map(search -> MatchQuery.of(m -> m
                                                .field("public_health_case_class_cd")
                                                .query(search))._toQuery())
                                        .collect(Collectors.toList());

                                queries.add(BoolQuery.of(
                                        b -> b.mustNot(mn -> mn.exists(e -> e.field("public_health_case_class_cd"))))
                                        ._toQuery());
                                q.bool(b -> b.should(queries));
                            } else {
                                addBoolQuery(q, "public_health_case_case_class_cd", statusStrings);
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
                                var queries = statusStrings.stream()
                                        .map(search -> MatchQuery.of(m -> m
                                                .field("notification_record_status_cd")
                                                .query(search))._toQuery())
                                        .collect(Collectors.toList());

                                queries.add(BoolQuery.of(
                                        b -> b.mustNot(mn -> mn
                                                .exists(e -> e.field("public_health_case_curr_process_state_cd"))))
                                        ._toQuery());
                                queries.add(MatchQuery.of(
                                        m -> m.field("public_health_case_curr_process_state_cd")
                                                .query("NF"))
                                        ._toQuery());
                                q.bool(b -> b.should(queries));
                            } else {
                                addBoolQuery(q, "notification_record_status_cd", statusStrings);
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
                                var queries = statusStrings.stream()
                                        .map(search -> MatchQuery.of(m -> m
                                                .field("public_health_case_curr_process_state_cd")
                                                .query(search))._toQuery())
                                        .collect(Collectors.toList());

                                queries.add(BoolQuery.of(
                                        b -> b.mustNot(mn -> mn
                                                .exists(e -> e.field("public_health_case_curr_process_state_cd"))))
                                        ._toQuery());
                                queries.add(MatchQuery.of(
                                        m -> m.field("public_health_case_curr_process_state_cd")
                                                .query("NF"))
                                        ._toQuery());
                                q.bool(b -> b.should(queries));
                            } else {
                                addBoolQuery(q, "public_health_case_curr_process_state_cd", statusStrings);
                            }
                        }

                        return q;
                    }),
                    InvestigationDocument.class);
        } catch (ElasticsearchException | IOException e) {
            throw new RuntimeException("Failed to query investigations");
        }

    }

    private void addBoolQuery(Builder q, String field, List<String> searchStrings) {
        if (searchStrings != null && searchStrings.size() > 0) {
            var queries = searchStrings.stream()
                    .map(search -> MatchQuery.of(m -> m
                            .field(field)
                            .query(search))._toQuery())
                    .collect(Collectors.toList());
            q.bool(b -> b.should(queries));
        }
    }

    @PreAuthorize(VIEW_LAB_REPORT)
    public List<Act> findLabReportsByFilter(LaboratoryReportFilter filter, Pageable pageable) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        var actRelationship = QActRelationship.actRelationship;
        var observation = QObservation.observation;
        var act = QAct.act;
        var participation = QParticipation.participation;
        var obsValueCoded = QObsValueCoded.obsValueCoded;
        var actId = QActId.actId;
        var query = queryFactory.selectDistinct(act).from(obsValueCoded)
                .leftJoin(actRelationship)
                .on(obsValueCoded.id.observationUid.eq(actRelationship.sourceActUid.id))
                .leftJoin(act)
                .on(act.id.eq(actRelationship.targetActUid.id))
                .leftJoin(participation)
                .on(participation.id.actUid.eq(act.id))
                .leftJoin(observation)
                .on(observation.id.eq(actRelationship.targetActUid.id))
                .leftJoin(actId)
                .on(actId.actUid.id.eq(observation.id));

        // OBS only for lab reports ?
        query = query.where(act.classCd.eq("OBS"));

        // Lab reports are secured by Program Area and Jurisdiction
        var userDetails = SecurityUtil.getUserDetails();
        var validOids = securityService.getProgramAreaJurisdictionOids(userDetails);
        query.where(observation.programJurisdictionOid.in(validOids));

        if (filter == null) {
            return query.limit(pageable.getPageSize()).offset(pageable.getOffset()).fetch();
        }
        // program area
        query = addParameter(query, observation.progAreaCd::in, filter.getProgramAreas());
        // jurisdictions
        if (filter.getJurisdictions() != null && !filter.getJurisdictions().isEmpty()) {
            // jurisdictions come in as List<Long> need to cast to List<String> for query
            var jurisdictionStrings = filter.getJurisdictions().stream().map(Object::toString)
                    .collect(Collectors.toList());
            query = query.where(observation.jurisdictionCd.in(jurisdictionStrings));
        }
        // pregnancy status
        if (filter.getPregnancyStatus() != null) {
            var status = filter.getPregnancyStatus().toString().substring(0, 1);
            query = query.where(observation.pregnantIndCd.containsIgnoreCase(status));
        }
        // event Id
        if (filter.getEventIdType() != null && filter.getEventId() != null) {
            switch (filter.getEventIdType()) {
                case ACCESSION_NUMBER:
                    query = query.where(
                            actId.typeDescTxt.eq("Filler Number")
                                    .and(actId.rootExtensionTxt.eq(filter.getEventId())));
                    break;
                case LAB_ID:
                    query = query.where(observation.localId.eq(filter.getEventId()));
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
            switch (eds.getEventDateType()) {
                case DATE_OF_REPORT:
                    query = query.where(observation.activityToTime.between(eds.getFrom(), eds.getTo()));
                    break;
                case DATE_OF_SPECIMEN_COLLECTION:
                    query = query.where(observation.effectiveFromTime.between(eds.getFrom(), eds.getTo()));
                    break;
                case DATE_RECEIVED_BY_PUBLIC_HEALTH:
                    query = query.where(observation.rptToStateTime.between(eds.getFrom(), eds.getTo()));
                    break;
                case LAB_REPORT_CREATE_DATE:
                    query = query.where(observation.addTime.between(eds.getFrom(), eds.getTo()));
                    break;
                case LAST_UPDATE_DATE:
                    query = query.where(observation.lastChgTime.between(eds.getFrom(), eds.getTo()));
                    break;
            }
        }
        // entry methods / entered by
        /**
         * Entry Method Electronic = electronicInd: 'Y'
         * Entry Method Manual = electronicInd: 'N'
         * Entered by External = electronicInd: 'E
         */
        if ((filter.getEntryMethods() != null && !filter.getEntryMethods().isEmpty())
                || (filter.getEnteredBy() != null && !filter.getEnteredBy().isEmpty())) {
            var electronicIndCodes = new ArrayList<Character>();
            if (filter.getEntryMethods() != null) {
                filter.getEntryMethods().forEach(em -> {
                    switch (em) {
                        case ELECTRONIC:
                            electronicIndCodes.add('Y');
                            break;
                        case MANUAL:
                            electronicIndCodes.add('N');
                            break;
                    }
                });
            }
            if (filter.getEnteredBy() != null) {
                if (filter.getEnteredBy().contains(UserType.EXTERNAL)) {
                    electronicIndCodes.add('E');
                }
            }
            query = query.where(observation.electronicInd.in(electronicIndCodes));
        }
        // event status
        if (filter.getEventStatus() != null && !filter.getEventStatus().isEmpty()) {
            if (filter.getEventStatus().contains(EventStatus.NEW)) {
                if (filter.getEventStatus().contains(EventStatus.UPDATE)) {
                    query = query.where(observation.versionCtrlNbr.goe(1));
                } else {
                    query = query.where(observation.versionCtrlNbr.eq((short) 1));
                }
            } else if (filter.getEventStatus().contains(EventStatus.UPDATE)) {
                query = query.where(observation.versionCtrlNbr.gt(1));
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
            query = query.where(observation.recordStatusCd.in(validStatuses));
        }
        // created by
        query = addParameter(query, observation.addUserId::eq, filter.getCreatedBy());

        // last update
        query = addParameter(query, observation.lastChgUserId::eq, filter.getLastUpdatedBy());
        // event provider/facility
        if (filter.getProviderSearch() != null) {
            var pSearch = filter.getProviderSearch();
            switch (pSearch.getProviderType()) {
                case ORDERING_FACILITY:
                    query = query.where(participation.id.typeCd.eq("ORD")
                            .and(participation.subjectClassCd.eq("ORG"))
                            .and(participation.id.subjectEntityUid.eq(pSearch.getProviderId())));
                    break;
                case ORDERING_PROVIDER:
                    query = query.where(participation.id.typeCd.eq("ORD")
                            .and(participation.subjectClassCd.eq("PSN"))
                            .and(participation.id.subjectEntityUid.eq(pSearch.getProviderId())));
                    break;
                case REPORTING_FACILITY:
                    query = query.where(participation.id.typeCd.eq("AUT")
                            .and(participation.id.subjectEntityUid.eq(pSearch.getProviderId())));
                    break;
            }
        }
        // resulted test
        query = addParameter(query, observation.cdDescTxt::like, filter.getResultedTest());

        // coded result
        query = addParameter(query, obsValueCoded.displayName::like, filter.getCodedResult());

        return query.limit(pageable.getPageSize()).offset(pageable.getOffset()).fetch();
    }

    // checks to see if the filter provided is null, if not add the filter to the
    // 'query.where' based on the expression supplied
    private <T, I> JPAQuery<I> addParameter(JPAQuery<I> query,
            Function<T, BooleanExpression> expression, T filter) {
        if (filter != null) {
            return query.where(expression.apply(filter));
        } else {
            return query;
        }
    }
}
