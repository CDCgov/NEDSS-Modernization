package gov.cdc.nbs.patient.profile.investigation;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.authorization.permission.scope.PermissionScope;
import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.odse.QActRelationship;
import gov.cdc.nbs.entity.odse.QNotification;
import gov.cdc.nbs.entity.odse.QParticipation;
import gov.cdc.nbs.entity.odse.QPerson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
class PatientInvestigationFinder {

  private static final QPerson PATIENT = QPerson.person;
  private static final QParticipation INVESTIGATED = new QParticipation("investigated");
  private static final QActRelationship NOTIFIED = new QActRelationship("notified");
  private static final QNotification NOTIFICATION = QNotification.notification;
  private static final QParticipation INVESTIGATED_BY = new QParticipation("investigated_by");
  private static final String DELETED = "LOG_DEL";
  private static final String PATIENT_CODE = "PAT";
  private static final String ACTIVE_STATUS = "ACTIVE";
  private static final String PERSON_CODE = "PSN";
  private static final String SUBJECT_OF_INVESTIGATION = "SubjOfPHC";
  private static final String INVESTIGATION_STATUS_CODE_SET = "PHC_IN_STS";
  private static final String NOTIFICATION_TYPE = "Notification";
  private static final String CASE_CLASS = "CASE";
  private static final String NOTIFICATION_CLASS = "NOTF";
  private static final String RECORD_STATUS_CODE_SET = "REC_STAT";
  private static final String INVESTIGATOR_OF_CASE = "InvestgrOfPHC";
  private static final String CASE_STATUS_CODE_SET = "PHC_CLASS";


  record Criteria(
      long patient,
      PermissionScope scope,
      Status... status) {

    enum Status {
      OPEN("O"),
      CLOSED("C");

      private final String code;

      Status(final String code) {
        this.code = code;
      }

      public String code() {
        return code;
      }
    }

    Criteria(long patient, PermissionScope scope) {
      this(patient, scope, Status.values());
    }

    @Override
    public boolean equals(final Object o) {
      if (this == o)
        return true;
      if (o == null || getClass() != o.getClass())
        return false;
      Criteria criteria = (Criteria) o;
      return patient == criteria.patient && Arrays.equals(status, criteria.status);
    }

    @Override
    public int hashCode() {
      int result = Objects.hash(patient);
      result = 31 * result + Arrays.hashCode(status);
      return result;
    }

    @Override
    public String toString() {
      return "Criteria{" +
          "patient=" + patient +
          ", status=" + Arrays.toString(status) +
          '}';
    }
  }


  private final JPAQueryFactory factory;
  private final PatientInvestigationTupleMapper.Tables tables;
  private final PatientInvestigationTupleMapper mapper;

  PatientInvestigationFinder(final JPAQueryFactory factory) {
    this.factory = factory;
    this.tables = new PatientInvestigationTupleMapper.Tables();
    this.mapper = new PatientInvestigationTupleMapper(this.tables);
  }

  Page<PatientInvestigation> find(
      final Criteria criteria,
      final Pageable pageable) {
    long total = resolveTotal(criteria);

    return total > 0
        ? new PageImpl<>(resolvePage(criteria, pageable), pageable, total)
        : Page.empty(pageable);
  }

  private long resolveTotal(final Criteria criteria) {
    Long total = applyCriteria(
        factory.select(this.tables.investigation().countDistinct()),
        criteria)
        .fetchOne();
    return total == null ? 0L : total;
  }

  private <R> JPAQuery<R> applyCriteria(
      final JPAQuery<R> query,
      final Criteria criteria) {


    return query.from(PATIENT)
        .join(INVESTIGATED).on(
            INVESTIGATED.recordStatusCd.eq(RecordStatus.ACTIVE),
            INVESTIGATED.actClassCd.eq(CASE_CLASS),
            INVESTIGATED.id.typeCd.eq(SUBJECT_OF_INVESTIGATION),
            INVESTIGATED.id.subjectEntityUid.eq(PATIENT.id),
            INVESTIGATED.subjectClassCd.eq(PERSON_CODE))
        .join(this.tables.investigation()).on(
            this.tables.investigation().id.eq(INVESTIGATED.id.actUid),
            this.tables.investigation().recordStatusCd.ne(DELETED),
            this.tables.investigation().investigationStatusCd
                .in(resolveInvestigationStatus(criteria.status())))
        .where(
            PATIENT.personParentUid.id.eq(criteria.patient()),
            PATIENT.cd.eq(PATIENT_CODE),
            PATIENT.recordStatus.status.ne(DELETED),
            // only return investigations where the user has access to program area / jurisdiction
            this.tables.investigation().programJurisdictionOid.in(criteria.scope().any()));
  }

  private String[] resolveInvestigationStatus(final Criteria.Status... status) {

    String[] codes = new String[status.length];

    for (int i = 0; i < status.length; i++) {
      codes[i] = status[i].code();
    }

    return codes;
  }

  private List<PatientInvestigation> resolvePage(
      final Criteria criteria,
      final Pageable pageable) {
    return applyCriteria(
        this.factory.selectDistinct(
            this.tables.investigation().id,
            this.tables.investigation().activityFromTime,
            this.tables.condition().conditionShortNm,
            this.tables.condition().investigationFormCd,
            this.tables.status().codeShortDescTxt,
            this.tables.caseStatus().codeShortDescTxt,
            this.tables.jurisdiction().codeShortDescTxt,
            this.tables.investigation().localId,
            this.tables.notificationStatus().codeShortDescTxt,
            this.tables.investigation().coinfectionId,
            this.tables.investigator().firstNm,
            this.tables.investigator().lastNm
        ),
        criteria)
        .join(this.tables.status()).on(
            this.tables.status().id.codeSetNm.eq(INVESTIGATION_STATUS_CODE_SET),
            this.tables.status().id.code.eq(this.tables.investigation().investigationStatusCd))
        .join(this.tables.condition()).on(
            this.tables.condition().id.eq(this.tables.investigation().cd))
        .join(this.tables.jurisdiction()).on(
            this.tables.jurisdiction().id.eq(this.tables.investigation().jurisdictionCd))
        .leftJoin(this.tables.caseStatus()).on(
            this.tables.caseStatus().id.codeSetNm.eq(CASE_STATUS_CODE_SET),
            this.tables.caseStatus().id.code.eq(this.tables.investigation().caseClassCd))
        .leftJoin(NOTIFIED).on(
            NOTIFIED.id.typeCd.eq(NOTIFICATION_TYPE),
            NOTIFIED.id.targetActUid.eq(this.tables.investigation().id),
            NOTIFIED.targetClassCd.eq(CASE_CLASS),
            NOTIFIED.recordStatusCd.eq(ACTIVE_STATUS),
            NOTIFIED.sourceClassCd.eq(NOTIFICATION_CLASS))
        .leftJoin(NOTIFICATION).on(
            NOTIFICATION.id.eq(NOTIFIED.id.sourceActUid))
        .leftJoin(this.tables.notificationStatus()).on(
            this.tables.notificationStatus().id.codeSetNm.eq(RECORD_STATUS_CODE_SET),
            this.tables.notificationStatus().id.code.eq(NOTIFICATION.recordStatusCd))
        .leftJoin(INVESTIGATED_BY).on(
            INVESTIGATED_BY.id.typeCd.eq(INVESTIGATOR_OF_CASE),
            INVESTIGATED_BY.id.actUid.eq(this.tables.investigation().id),
            INVESTIGATED_BY.actClassCd.eq(CASE_CLASS),
            INVESTIGATED_BY.subjectClassCd.eq(PERSON_CODE))
        .leftJoin(this.tables.investigator()).on(
            this.tables.investigator().id.eq(INVESTIGATED_BY.id.subjectEntityUid))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch()
        .stream()
        .map(mapper::map)
        .toList();
  }
}
