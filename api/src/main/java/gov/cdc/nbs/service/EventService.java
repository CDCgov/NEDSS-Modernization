package gov.cdc.nbs.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import gov.cdc.nbs.entity.odse.Act;
import gov.cdc.nbs.entity.odse.QAct;
import gov.cdc.nbs.entity.odse.QActId;
import gov.cdc.nbs.entity.odse.QActRelationship;
import gov.cdc.nbs.entity.odse.QNotification;
import gov.cdc.nbs.entity.odse.QObsValueCoded;
import gov.cdc.nbs.entity.odse.QObservation;
import gov.cdc.nbs.entity.odse.QParticipation;
import gov.cdc.nbs.entity.odse.QPerson;
import gov.cdc.nbs.entity.odse.QPublicHealthCase;
import gov.cdc.nbs.entity.srte.QJurisdictionCode;
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

    @PersistenceContext
    private final EntityManager entityManager;

    public List<Act> findInvestigationsByFilter(InvestigationFilter filter, Pageable pageable) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        var publicHealthCase = QPublicHealthCase.publicHealthCase;
        var jurisdictionCode = QJurisdictionCode.jurisdictionCode;
        var notification = QNotification.notification;
        var participation = QParticipation.participation;
        var person = QPerson.person;
        var actId = QActId.actId;
        var query = queryFactory.selectDistinct(publicHealthCase.act).from(publicHealthCase)
                .leftJoin(jurisdictionCode)
                .on(publicHealthCase.jurisdictionCd.eq(jurisdictionCode.id))
                .leftJoin(notification)
                .on(publicHealthCase.act.id.eq(notification.id))
                .leftJoin(actId)
                .on(publicHealthCase.act.id.eq(actId.id.actUid))
                .leftJoin(participation)
                .on(publicHealthCase.act.id.eq(participation.id.actUid))
                .leftJoin(person)
                .on(participation.id.subjectEntityUid.eq(person.id));
        if (filter == null) {
            return query.limit(pageable.getPageSize()).offset(pageable.getOffset()).fetch();
        }
        // investigation type only
        query = query.where(publicHealthCase.caseTypeCd.eq('I'));
        // conditions
        query = addParameter(query, publicHealthCase.cdDescTxt::in, filter.getConditions());
        // program areas
        query = addParameter(query, publicHealthCase.progAreaCd::in, filter.getProgramAreas());
        // jurisdictions
        if (filter.getJurisdictions() != null && !filter.getJurisdictions().isEmpty()) {
            // jurisdictions come in as List<Long> need to cast to List<String> for query
            var jurisdictionStrings = filter.getJurisdictions().stream().map(Object::toString)
                    .collect(Collectors.toList());
            query = query.where(jurisdictionCode.id.in(jurisdictionStrings));
        }
        // pregnancy status
        if (filter.getPregnancyStatus() != null) {
            var status = filter.getPregnancyStatus().toString().substring(0, 1);
            query = query.where(publicHealthCase.pregnantIndCd.containsIgnoreCase(status));
        }
        // Event Id / Type
        if (filter.getEventIdType() != null && filter.getEventId() != null) {
            switch (filter.getEventIdType()) {
                case ABCS_CASE_ID:
                    query = addParameter(query,
                            (x) -> actId.rootExtensionTxt.eq(x).and(actId.id.actIdSeq.eq((short) 2)),
                            filter.getEventId());
                    break;
                case CITY_COUNTY_CASE_ID:
                    query = addParameter(query,
                            (x) -> actId.rootExtensionTxt.eq(x)
                                    .and(actId.id.actIdSeq.eq((short) 2).and(actId.typeCd.eq("CITY"))),
                            filter.getEventId());
                    break;
                case INVESTIGATION_ID:
                    query = addParameter(query, publicHealthCase.localId::eq, filter.getEventId());
                    break;
                case NOTIFICATION_ID:
                    query = addParameter(query, notification.localId::eq, filter.getEventId());
                    break;
                case STATE_CASE_ID:
                    query = addParameter(query,
                            (x) -> actId.rootExtensionTxt.eq(x)
                                    .and(actId.id.actIdSeq.eq((short) 1).and(actId.typeCd.eq("STATE"))),
                            filter.getEventId());
                    break;
                default:
                    throw new QueryException("Invalid event id type: " + filter.getEventIdType());
            }
        }
        // Event date
        var eds = filter.getEventDateSearch();
        if (eds != null) {
            if (eds.getFrom() == null || eds.getTo() == null || eds.getEventDateType() == null) {
                throw new QueryException("From, To, and EventDateType are required when querying by event date");
            }
            switch (eds.getEventDateType()) {
                case DATE_OF_REPORT:
                    query = query.where(publicHealthCase.rptFormCmpltTime.between(eds.getFrom(), eds.getTo()));
                    break;
                case INVESTIGATION_CLOSED_DATE:
                    query = query.where(publicHealthCase.activityToTime.between(eds.getFrom(), eds.getTo()));
                    break;
                case INVESTIGATION_CREATE_DATE:
                    query = query.where(publicHealthCase.addTime.between(eds.getFrom(), eds.getTo()));
                    break;
                case INVESTIGATION_START_DATE:
                    query = query.where(publicHealthCase.activityFromTime.between(eds.getFrom(), eds.getTo()));
                    break;
                case LAST_UPDATE_DATE:
                    query = query.where(publicHealthCase.lastChgTime.between(eds.getFrom(), eds.getTo()));
                    break;
                case NOTIFICATION_CREATE_DATE:
                    query = query.where(notification.addTime.between(eds.getFrom(), eds.getTo()));
                    break;
                default:
                    throw new QueryException("Invalid event date type " + eds.getEventDateType());
            }
        }
        // Created By
        query = addParameter(query, publicHealthCase.addUserId::eq, filter.getCreatedBy());
        // Updated By
        query = addParameter(query, publicHealthCase.lastChgUserId::eq, filter.getLastUpdatedBy());
        // provider facility + investigator Id

        var pfSearch = filter.getProviderFacilitySearch();
        if (pfSearch != null) {
            if (pfSearch.getEntityType() == null || pfSearch.getId() == null) {
                throw new QueryException("Entity type and entity Id required when querying provider/facility");
            }
            // provider/facility and investigator both check for entity uid's. We need to
            // make sure it does an OR instead of two ANDs
            var doInvestigatorQuery = filter.getInvestigatorId() != null;
            switch (pfSearch.getEntityType()) {
                case PROVIDER:
                    if (doInvestigatorQuery) {
                        query = query.where(participation.id.typeCd.eq("PerAsReporterOfPHC")
                                .and(participation.id.subjectEntityUid.eq(pfSearch.getId()))
                                .or(person.id.eq(filter.getInvestigatorId())));
                    } else {
                        query = query.where(participation.id.typeCd.eq("PerAsReporterOfPHC")
                                .and(participation.id.subjectEntityUid.eq(pfSearch.getId())));
                    }
                    break;
                case FACILITY:
                    if (doInvestigatorQuery) {
                        query = query.where(participation.id.typeCd.eq("OrgAsReporterOfPHC")
                                .and(participation.id.subjectEntityUid.eq(pfSearch.getId()))
                                .or(person.id.eq(filter.getInvestigatorId())));
                    } else {
                        query = query.where(participation.id.typeCd.eq("OrgAsReporterOfPHC")
                                .and(participation.id.subjectEntityUid.eq(pfSearch.getId())));
                    }
                    break;
                default:
                    throw new QueryException("Invalid entity type: " + pfSearch.getEntityType());
            }
        } else {
            // investigator id
            query = addParameter(query, person.id::eq, filter.getInvestigatorId());
        }

        // investigation status
        if (filter.getInvestigationStatus() != null) {
            var status = filter.getInvestigationStatus().toString().substring(0, 1);
            query = query.where(publicHealthCase.investigationStatusCd
                    .equalsIgnoreCase(status));
        }

        // outbreak name
        query = addParameter(query, publicHealthCase.outbreakName.lower()::in, filter.getOutbreakNames());
        // case status / include unassigned
        if (filter.getCaseStatuses() != null) {
            var cs = filter.getCaseStatuses();
            if (cs.getStatusList() == null || cs.getStatusList().isEmpty() || cs.getIncludeUnassigned() == null) {
                throw new QueryException("statusList and includeUnassigned are required when specifying caseStatuses");
            }
            var statusStrings = filter.getCaseStatuses().getStatusList().stream().map(s -> s.toString().toUpperCase())
                    .collect(Collectors.toList());
            if (cs.getIncludeUnassigned()) {
                query = addParameter(query,
                        (x) -> publicHealthCase.caseClassCd.upper().in(x).or(publicHealthCase.caseClassCd.isEmpty()),
                        statusStrings);
            } else {
                query = addParameter(query, publicHealthCase.caseClassCd.upper()::in, statusStrings);
            }
        }
        // notification status / include unassigned
        if (filter.getNotificationStatuses() != null) {
            var cs = filter.getCaseStatuses();
            if (cs.getStatusList() == null || cs.getStatusList().isEmpty() || cs.getIncludeUnassigned() == null) {
                throw new QueryException(
                        "statusList and includeUnassigned are required when specifying notificationStatuses");
            }
            var statusStrings = cs.getStatusList().stream().map(s -> s.toString().toUpperCase())
                    .collect(Collectors.toList());
            if (cs.getIncludeUnassigned()) {
                query = addParameter(query,
                        (x) -> notification.recordStatusCd.upper().in(x)
                                .or(publicHealthCase.currProcessStateCd.isEmpty()
                                        .or(publicHealthCase.currProcessStateCd.eq("NF"))),
                        statusStrings);
            } else {
                query = addParameter(query, notification.recordStatusCd.upper()::in, statusStrings);
            }
        }
        // processing status / include unassigned
        if (filter.getProcessingStatuses() != null) {
            var cs = filter.getProcessingStatuses();
            if (cs.getStatusList() == null || cs.getStatusList().isEmpty() || cs.getIncludeUnassigned() == null) {
                throw new QueryException(
                        "statusList and includeUnassigned are required when specifying processingStatuses");
            }
            var statusStrings = cs.getStatusList().stream().map(s -> s.toString().toUpperCase())
                    .collect(Collectors.toList());
            if (cs.getIncludeUnassigned()) {
                query = addParameter(query,
                        (x) -> publicHealthCase.currProcessStateCd.upper().in(x)
                                .or(publicHealthCase.currProcessStateCd.isEmpty()
                                        .or(publicHealthCase.currProcessStateCd.eq("NF"))),
                        statusStrings);
            } else {
                query = addParameter(query, publicHealthCase.currProcessStateCd.upper()::in, statusStrings);
            }
        }

        return query.limit(pageable.getPageSize()).offset(pageable.getOffset()).fetch();
    }

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
