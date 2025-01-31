package gov.cdc.nbs.patient.profile;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class PatientProfileFinder {

    private static final String PATIENT_CODE = "PAT";

    private final JPAQueryFactory factory;
    private final PatientProfileTupleMapper.Tables tables;
    private final PatientProfileTupleMapper mapper;

    PatientProfileFinder(final JPAQueryFactory factory) {
        this.factory = factory;
        this.tables = new PatientProfileTupleMapper.Tables();
        this.mapper = new PatientProfileTupleMapper(this.tables);
    }

    Optional<PatientProfile> findById(final long identifier) {
        return this.factory.selectDistinct(
                this.tables.patient().personParentUid.id,
                this.tables.patient().personParentUid.localId,
                this.tables.patient().personParentUid.versionCtrlNbr,
                this.tables.patient().personParentUid.recordStatus.status
            )
            .from(this.tables.patient())
            .where(
                this.tables.patient().cd.eq(PATIENT_CODE),
                this.tables.patient().id.eq(identifier)
            )
            .fetch()
            .stream()
            .map(mapper::map)
            .findFirst();
    }


    Optional<PatientProfile> findByLocalId(final String local) {
        return this.factory.selectDistinct(
                this.tables.patient().personParentUid.id,
                this.tables.patient().personParentUid.localId,
                this.tables.patient().personParentUid.versionCtrlNbr,
                this.tables.patient().personParentUid.recordStatus.status
            )
            .from(this.tables.patient())
            .where(
                this.tables.patient().cd.eq(PATIENT_CODE),
                this.tables.patient().localId.eq(local)
            )
            .fetch()
            .stream()
            .map(mapper::map)
            .findFirst();
    }
}
