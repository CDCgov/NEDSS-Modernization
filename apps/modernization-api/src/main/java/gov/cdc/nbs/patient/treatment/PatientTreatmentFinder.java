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

    private final JPAQueryFactory factory;
    private final QPerson patients;
    private final QParticipation subjectOfTreatment;
    private final QTreatment treatment;
    private final QTreatmentAdministered administered;
    private final QActRelationship relationship;
    private final QParticipation treatmentProvider;
    private final QPersonName provider;
    private final QPublicHealthCase investigation;
    private final QConditionCode condition;

    PatientTreatmentFinder(final JPAQueryFactory factory) {
        this.factory = factory;
        patients = QPerson.person;
        subjectOfTreatment = QParticipation.participation;
        treatment = QTreatment.treatment;
        administered = QTreatmentAdministered.treatmentAdministered;
        relationship = QActRelationship.actRelationship;
        treatmentProvider = QParticipation.participation;
        provider = QPersonName.personName;
        investigation = QPublicHealthCase.publicHealthCase;
        condition = QConditionCode.conditionCode;
    }

    List<PatientTreatment> find(long patient) {

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
                        subjectOfTreatment.id.typeCd.eq("SubjOfTrmt"),
                        subjectOfTreatment.actClassCd.eq("TRMT"),
                        subjectOfTreatment.subjectClassCd.eq("PSN"),
                        subjectOfTreatment.recordStatusCd.eq(RecordStatus.ACTIVE)
                )
                .join(treatment).on(
                        treatment.id.eq(subjectOfTreatment.actUid.id),
                        treatment.recordStatusCd.eq("ACTIVE")
                )
                .join(administered).on(
                        administered.treatmentUid.id.eq(treatment.id)
                )
                .join(relationship).on(
                        relationship.id.typeCd.eq("TreatmentToPHC"),
                        relationship.sourceActUid.id.eq(treatment.id),
                        relationship.sourceClassCd.eq("TRMT"),
                        relationship.targetClassCd.eq("CASE")
                )
                .leftJoin(treatmentProvider).on(
                        treatmentProvider.actUid.id.eq(treatment.id),
                        treatmentProvider.id.typeCd.eq("ProviderOfTrmt"),
                        treatmentProvider.subjectClassCd.eq("PSN")
                )
                .leftJoin(provider).on(
                        provider.id.personUid.eq(treatmentProvider.id.subjectEntityUid)
                )
                .join(investigation).on(
                        investigation.id.eq(relationship.targetActUid.id),
                        investigation.investigationStatusCd.in("O", "C"),
                        investigation.recordStatusCd.ne("LOG_DEL")
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
