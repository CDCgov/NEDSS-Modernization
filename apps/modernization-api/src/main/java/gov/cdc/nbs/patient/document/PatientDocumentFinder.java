package gov.cdc.nbs.patient.document;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.odse.QActRelationship;
import gov.cdc.nbs.entity.odse.QNbsDocument;
import gov.cdc.nbs.entity.odse.QParticipation;
import gov.cdc.nbs.entity.odse.QPerson;
import gov.cdc.nbs.entity.odse.QPublicHealthCase;
import gov.cdc.nbs.entity.srte.QCodeValueGeneral;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Component
class PatientDocumentFinder {

    private static final QPublicHealthCase INVESTIGATION = new QPublicHealthCase("investigation");
    private static final QPerson PATIENT = QPerson.person;
    private static final QParticipation PARTICIPATION = QParticipation.participation;
    private static final QNbsDocument DOCUMENT = QNbsDocument.nbsDocument;
    private static final QCodeValueGeneral DOCUMENT_TYPE = QCodeValueGeneral.codeValueGeneral;
    private static final QActRelationship RELATIONSHIP = QActRelationship.actRelationship;
    private static final String DELETED = "LOG_DEL";
    private static final String SUBJECT_OF_DOCUMENT = "SubjOfDoc";
    private static final String DOCUMENT_TYPE_CODE_NAME_SET = "PUBLIC_HEALTH_EVENT";
    private static final String PERSON_CLASS = "PSN";
    private static final String DOCUMENT_CLASS = "DOC";
    private static final String INVESTIGATION_CLASS = "CASE";
    public static final String UPDATED_MODIFIER = "(Updated)";

    private final JPAQueryFactory factory;


    PatientDocumentFinder(final JPAQueryFactory factory) {
        this.factory = factory;
    }

    List<PatientDocument> find(final long patient, final Set<Long> programJurisdictionOids) {
        JPAQuery<Tuple> query = applyCriteria(
                selection(),
                patient);
        return applySecurity(query, programJurisdictionOids)
                .fetch()
                .stream()
                .map(this::map)
                .toList();
    }

    Page<PatientDocument> find(
            final long patient,
            final Set<Long> programJurisdictionOids,
            final Pageable pageable) {
        long total = resolveTotal(patient, programJurisdictionOids);
        return total > 0
                ? new PageImpl<>(resolvePage(patient, pageable, programJurisdictionOids), pageable, total)
                : Page.empty(pageable);
    }

    private JPAQuery<Tuple> selection() {
        return this.factory.selectDistinct(
                DOCUMENT.id,
                DOCUMENT.addTime,
                DOCUMENT_TYPE.codeShortDescTxt,
                DOCUMENT.sendingFacilityNm,
                DOCUMENT.cdDescTxt,
                DOCUMENT.localId,
                DOCUMENT.externalVersionCtrlNbr,
                INVESTIGATION.id,
                INVESTIGATION.localId);
    }

    private <R> JPAQuery<R> applyBaseCriteria(
            final JPAQuery<R> query,
            final long patient) {
        return query.from(PATIENT)
                .join(PARTICIPATION).on(
                        PARTICIPATION.id.subjectEntityUid.eq(PATIENT.id),
                        PARTICIPATION.id.typeCd.eq(SUBJECT_OF_DOCUMENT),
                        PARTICIPATION.actClassCd.eq(DOCUMENT_CLASS),
                        PARTICIPATION.subjectClassCd.eq(PERSON_CLASS),
                        PARTICIPATION.recordStatusCd.eq(RecordStatus.ACTIVE))
                .join(DOCUMENT).on(
                        DOCUMENT.id.eq(PARTICIPATION.id.actUid),
                        DOCUMENT.recordStatusCd.ne(DELETED))
                .join(DOCUMENT_TYPE).on(
                        DOCUMENT_TYPE.id.codeSetNm.eq(DOCUMENT_TYPE_CODE_NAME_SET),
                        DOCUMENT_TYPE.id.code.eq(DOCUMENT.docTypeCd))
                .where(PATIENT.personParentUid.id.eq(patient));
    }

    private <R> JPAQuery<R> applyCriteria(
            final JPAQuery<R> query,
            final long patient) {
        return applyBaseCriteria(query, patient)
                .leftJoin(RELATIONSHIP).on(
                        RELATIONSHIP.targetClassCd.eq(INVESTIGATION_CLASS),
                        RELATIONSHIP.sourceClassCd.eq(DOCUMENT_CLASS))
                .leftJoin(INVESTIGATION).on(
                        INVESTIGATION.id.eq(RELATIONSHIP.id.targetActUid),
                        INVESTIGATION.recordStatusCd.ne(DELETED))
                .orderBy(new OrderSpecifier<>(Order.DESC, DOCUMENT.addTime));
    }

    private PatientDocument map(final Tuple tuple) {
        Long identifier = Objects.requireNonNull(tuple.get(DOCUMENT.id), "A document id is required.");
        Instant added = tuple.get(DOCUMENT.addTime);
        String type = tuple.get(DOCUMENT_TYPE.codeShortDescTxt);
        String sendingFacility = tuple.get(DOCUMENT.sendingFacilityNm);
        String condition = tuple.get(DOCUMENT.cdDescTxt);
        String localId = tuple.get(DOCUMENT.localId);
        Short version = tuple.get(DOCUMENT.externalVersionCtrlNbr);

        String event = resolveEvent(localId, version);

        PatientDocument.Investigation investigation = maybeMapInvestigation(tuple);

        return new PatientDocument(
                identifier,
                added,
                type,
                sendingFacility,
                added,
                condition,
                event,
                investigation);
    }

    private String resolveEvent(final String localId, final Short version) {
        return version != null && version > 1
                ? localId + UPDATED_MODIFIER
                : localId;
    }

    private PatientDocument.Investigation maybeMapInvestigation(final Tuple tuple) {
        Long identifier = tuple.get(INVESTIGATION.id);
        String local = tuple.get(INVESTIGATION.localId);

        return identifier == null
                ? null
                : new PatientDocument.Investigation(
                        identifier,
                        local);
    }

    private long resolveTotal(final long patient, final Set<Long> programJurisdictionOids) {
        JPAQuery<Long> query = applyBaseCriteria(
                factory.selectDistinct(DOCUMENT.countDistinct()),
                patient);
        Long total = applySecurity(query, programJurisdictionOids)
                .fetchOne();
        return total == null ? 0L : total;
    }

    private List<PatientDocument> resolvePage(
            final long patient,
            final Pageable pageable,
            final Set<Long> programJurisdictionOids) {
        JPAQuery<Tuple> query = applyCriteria(
                selection(),
                patient)
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize());
        return applySecurity(query, programJurisdictionOids)
                .fetch()
                .stream()
                .map(this::map)
                .toList();
    }

    /**
     * Appends a programJurisdictionOid WHERE clause to a query
     * 
     * @param query
     * @param programJurisdictionOids
     * @return
     */
    private <R> JPAQuery<R> applySecurity(final JPAQuery<R> query, final Set<Long> programJurisdictionOids) {
        return query.where(DOCUMENT.programJurisdictionOid.in(programJurisdictionOids));
    }
}
