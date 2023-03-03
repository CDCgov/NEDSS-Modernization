package gov.cdc.nbs.patient.treatment;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.odse.QActRelationship;
import gov.cdc.nbs.entity.odse.QParticipation;
import gov.cdc.nbs.entity.odse.QPerson;
import gov.cdc.nbs.entity.odse.QPersonName;
import gov.cdc.nbs.entity.odse.QPublicHealthCase;
import gov.cdc.nbs.entity.odse.QTreatment;
import gov.cdc.nbs.entity.odse.QTreatmentAdministered;
import gov.cdc.nbs.entity.srte.QConditionCode;
import gov.cdc.nbs.message.enums.Suffix;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
class PatientTreatmentFinder {

    private static final String NAME_SEPARATOR = " ";
    private static final String SUBJECT_OF_TREATMENT = "SubjOfTrmt";
    private static final String TREATMENT = "TRMT";
    private static final String PERSON = "PSN";
    private static final String TREATMENT_TO_PUBLIC_HEALTH_CASE = "TreatmentToPHC";
    private static final String CASE = "CASE";
    private static final String PROVIDER_OF_TREATMENT = "ProviderOfTrmt";
    private static final String ACTIVE = "ACTIVE";
    private static final String OPEN = "O";
    private static final String CLOSED = "C";
    private static final String DELETED = "LOG_DEL";

    private final JPAQueryFactory factory;

    PatientTreatmentFinder(final JPAQueryFactory factory) {
        this.factory = factory;
    }

    List<PatientTreatment> find(long patient) {

        QPerson patients = QPerson.person;
        QParticipation subjectOfTreatment = QParticipation.participation;
        QTreatment treatment = QTreatment.treatment;
        QTreatmentAdministered administered = QTreatmentAdministered.treatmentAdministered;
        QActRelationship relationship = QActRelationship.actRelationship;
        QParticipation treatmentProvider = QParticipation.participation;
        QPersonName provider = QPersonName.personName;
        QPublicHealthCase investigation = QPublicHealthCase.publicHealthCase;
        QConditionCode condition = QConditionCode.conditionCode;

        JPAQuery<Tuple> query = factory.selectDistinct(
                        treatment.id,
                        relationship.addTime,
                        treatment.cdDescTxt,
                        treatment.localId,
                        administered.effectiveFromTime,
                        provider.nmPrefix,
                        provider.firstNm,
                        provider.lastNm,
                        provider.nmSuffix,
                        investigation.id,
                        investigation.localId,
                        condition.conditionShortNm
                ).from(patients)
                .join(subjectOfTreatment).on(
                        subjectOfTreatment.id.subjectEntityUid.eq(patients.id),
                        subjectOfTreatment.id.typeCd.eq(SUBJECT_OF_TREATMENT),
                        subjectOfTreatment.actClassCd.eq(TREATMENT),
                        subjectOfTreatment.subjectClassCd.eq(PERSON),
                        subjectOfTreatment.recordStatusCd.eq(RecordStatus.ACTIVE)
                )
                .join(treatment).on(
                        treatment.id.eq(subjectOfTreatment.actUid.id),
                        treatment.recordStatusCd.eq(ACTIVE)
                )
                .join(administered).on(
                        administered.treatmentUid.id.eq(treatment.id)
                )
                .join(relationship).on(
                        relationship.id.typeCd.eq(TREATMENT_TO_PUBLIC_HEALTH_CASE),
                        relationship.sourceActUid.id.eq(treatment.id),
                        relationship.sourceClassCd.eq(TREATMENT),
                        relationship.targetClassCd.eq(CASE)
                )
                .leftJoin(treatmentProvider).on(
                        treatmentProvider.actUid.id.eq(treatment.id),
                        treatmentProvider.id.typeCd.eq(PROVIDER_OF_TREATMENT),
                        treatmentProvider.subjectClassCd.eq(PERSON)
                )
                .leftJoin(provider).on(
                        provider.id.personUid.eq(treatmentProvider.id.subjectEntityUid)
                )
                .join(investigation).on(
                        investigation.id.eq(relationship.targetActUid.id),
                        investigation.investigationStatusCd.in(OPEN, CLOSED),
                        investigation.recordStatusCd.ne(DELETED)
                )
                .join(condition).on(
                        condition.id.eq(investigation.cd)
                )
                .where(patients.personParentUid.id.eq(patient));


        return query.fetch()
                .stream()
                .map(this::mapTreatment)
                .toList();
    }

    private PatientTreatment mapTreatment(final Tuple tuple) {
        Long treatment = Objects.requireNonNull(tuple.get(QTreatment.treatment.id), "A treatment is required.");
        Instant createdOn = tuple.get(QActRelationship.actRelationship.addTime);
        String description = tuple.get(QTreatment.treatment.cdDescTxt);
        String event = tuple.get(QTreatment.treatment.localId);
        Instant treatedOn = tuple.get(QTreatmentAdministered.treatmentAdministered.effectiveFromTime);
        String providerPrefix = tuple.get(QPersonName.personName.nmPrefix);
        String providerFirstName = tuple.get(QPersonName.personName.firstNm);
        String providerLastName = tuple.get(QPersonName.personName.lastNm);
        Suffix providerSuffix = tuple.get(QPersonName.personName.nmSuffix);

        PatientTreatment.Investigation investigation = mapInvestigation(tuple);

        String provider = combineName(
                providerPrefix,
                providerFirstName,
                providerLastName,
                providerSuffix
        );

        return new PatientTreatment(
                treatment,
                createdOn,
                provider,
                treatedOn,
                description,
                event,
                investigation
        );
    }

    private PatientTreatment.Investigation mapInvestigation(final Tuple tuple) {
        Long identifier = Objects.requireNonNull(tuple.get(QPublicHealthCase.publicHealthCase.id), "An investigation is required.");
        String local = tuple.get(QPublicHealthCase.publicHealthCase.localId);
        String condition = tuple.get(QConditionCode.conditionCode.conditionShortNm);

        return new PatientTreatment.Investigation(
                identifier,
                local,
                condition
        );
    }

    private String combineName(
            String prefix,
            String first,
            String last,
            Suffix suffix
    ) {
        String suffixDisplay = suffix == null ? null : suffix.name();
        return Stream.of(
                        prefix,
                        first,
                        last,
                        suffixDisplay
                ).filter(Objects::nonNull)
                .collect(Collectors.joining(NAME_SEPARATOR));
    }
}
