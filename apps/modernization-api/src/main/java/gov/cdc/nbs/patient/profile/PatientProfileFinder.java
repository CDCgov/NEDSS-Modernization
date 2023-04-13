package gov.cdc.nbs.patient.profile;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class PatientProfileFinder {

    private final JPAQueryFactory factory;
    private final PatientProfileTupleMapper.Tables tables;
    private final PatientProfileTupleMapper mapper;

    PatientProfileFinder(final JPAQueryFactory factory) {
        this.factory = factory;
        this.tables = new PatientProfileTupleMapper.Tables();
        this.mapper = new PatientProfileTupleMapper(this.tables);
    }

    Optional<PatientProfile> find(final long identifier) {
        return this.factory.select(
                this.tables.patient().id,
                this.tables.patient().localId,
                this.tables.patient().versionCtrlNbr
            )
            .from(this.tables.patient())
            .where(this.tables.patient().personParentUid.id.eq(identifier))
            .fetch()
            .stream()
            .map(mapper::map)
            .findFirst();
    }


}
