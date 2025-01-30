package gov.cdc.nbs.patient.profile.vaccination;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.odse.QActRelationship;
import gov.cdc.nbs.entity.odse.QParticipation;
import gov.cdc.nbs.entity.odse.QPerson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class PatientVaccinationFinder {

  private static final String PATIENT_CODE = "PAT";

  private static final QPerson PATIENT = QPerson.person;

  private static final QParticipation VACCINATED = new QParticipation("vaccinated");

  private static final QParticipation PROVIDED_BY = new QParticipation("provided_by");

  private static final QActRelationship RELATIONSHIP = QActRelationship.actRelationship;


  private static final String SUBJECT_OF_VACCINATION = "SubOfVacc";
  private static final String VACCINATION_GIVEN = "VaccGiven";
  private static final String VACCINE_CODE_SET = "VAC_NM";
  private static final String PERFORMER_OF_VACCINATION_TYPE = "PerformerOfVacc";
  private static final String PERSON_CLASS = "PSN";
  private static final String INVESTIGATION_CLASS = "CASE";
  private static final String VACCINATION_CLASS = "INTV";
  private static final String DELETED = "LOG_DEL";
  private static final String ACTIVE_STATUS = "ACTIVE";

  private final JPAQueryFactory factory;
  private final PatientVaccinationTupleMapper.Tables tables;
  private final PatientVaccinationTupleMapper mapper;

  PatientVaccinationFinder(final JPAQueryFactory factory) {
    this.factory = factory;
    this.tables = new PatientVaccinationTupleMapper.Tables();
    mapper = new PatientVaccinationTupleMapper(tables);
  }

  private <R> JPAQuery<R> applyCriteria(final JPAQuery<R> query, final long patient) {
    return query.from(PATIENT)
        .join(VACCINATED).on(
            VACCINATED.recordStatusCd.eq(RecordStatus.ACTIVE),
            VACCINATED.id.typeCd.in(SUBJECT_OF_VACCINATION, VACCINATION_GIVEN),
            VACCINATED.id.subjectEntityUid.eq(PATIENT.id),
            VACCINATED.subjectClassCd.eq(PATIENT_CODE)
        )
        .join(this.tables.vaccination()).on(
            this.tables.vaccination().id.eq(VACCINATED.id.actUid),
            this.tables.vaccination().recordStatusCd.eq(ACTIVE_STATUS)
        )
        .where(
            PATIENT.personParentUid.id.eq(patient),
            PATIENT.cd.eq(PATIENT_CODE),
            PATIENT.recordStatus.status.eq(ACTIVE_STATUS)
        );

  }

  private long resolveTotal(final long patient) {
    Long total = applyCriteria(
        factory.select(PATIENT.countDistinct()),
        patient
    )
        .fetchOne();
    return total == null ? 0L : total;
  }

  private List<PatientVaccination> resolvePage(final long patient, final Pageable pageable) {
    return applyCriteria(
        this.factory.selectDistinct(
            this.tables.vaccination().id,
            this.tables.vaccination().addTime,
            this.tables.provider().name().nmPrefix,
            this.tables.provider().name().firstNm,
            this.tables.provider().name().lastNm,
            this.tables.provider().name().nmSuffix,
            this.tables.vaccination().activityFromTime,
            this.tables.vaccine().codeShortDescTxt,
            this.tables.associatedWith().investigation().id,
            this.tables.associatedWith().investigation().localId,
            this.tables.associatedWith().condition().conditionShortNm,
            this.tables.vaccination().localId
        ),
        patient
    )
        .join(this.tables.vaccine()).on(
            this.tables.vaccine().id.codeSetNm.eq(VACCINE_CODE_SET),
            this.tables.vaccine().id.code.eq(this.tables.vaccination().materialCd)
        )
        .leftJoin(PROVIDED_BY).on(
            PROVIDED_BY.actUid.id.eq(this.tables.vaccination().id),
            PROVIDED_BY.id.typeCd.eq(PERFORMER_OF_VACCINATION_TYPE),
            PROVIDED_BY.subjectClassCd.eq(PERSON_CLASS)
        )
        .leftJoin(this.tables.provider().name()).on(
            this.tables.provider().name().id.personUid.eq(PROVIDED_BY.id.subjectEntityUid)
        )
        .leftJoin(RELATIONSHIP).on(
            RELATIONSHIP.targetClassCd.eq(INVESTIGATION_CLASS),
            RELATIONSHIP.sourceClassCd.eq(VACCINATION_CLASS),
            RELATIONSHIP.id.sourceActUid.eq(this.tables.vaccination().id)
        )
        .leftJoin(this.tables.associatedWith().investigation()).on(
            this.tables.associatedWith().investigation().id.eq(RELATIONSHIP.id.targetActUid),
            this.tables.associatedWith().investigation().recordStatusCd.ne(DELETED)
        )
        .leftJoin(this.tables.associatedWith().condition()).on(
            this.tables.associatedWith().condition().id.eq(this.tables.associatedWith().investigation().cd)
        )
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch()
        .stream()
        .map(mapper::map)
        .toList()
        ;

  }

  Page<PatientVaccination> find(final long patient, final Pageable pageable) {
    long total = resolveTotal(patient);

    return total > 0
        ? new PageImpl<>(resolvePage(patient, pageable), pageable, total)
        : Page.empty(pageable);
  }
}
