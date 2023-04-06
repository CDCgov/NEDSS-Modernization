package gov.cdc.nbs.patient.profile;

import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.entity.odse.QPerson;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
class PatientProfileFinder {

    private static final QPerson PERSON = QPerson.person;
    private final JPAQueryFactory factory;
    private final PatientProfileTupleMapper mapper;

    PatientProfileFinder(final JPAQueryFactory factory) {
        this.factory = factory;
        this.mapper = new PatientProfileTupleMapper(PERSON);
    }

    Optional<PatientProfile> find(final long identifier, final Instant asOf) {
        return this.factory.select(
                PERSON.id,
                PERSON.localId
            )
            .from(PERSON)
            .where(PERSON.personParentUid.id.eq(identifier))
            .fetch()
            .stream()
            .map(tuple -> mapper.map(asOf, tuple))
            .findFirst();
    }


}
