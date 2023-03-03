package gov.cdc.nbs.patient.contact;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.entity.odse.QPublicHealthCase;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
class PatientNamedByContactFinder {

    private static final QPublicHealthCase INVESTIGATION = new QPublicHealthCase("investigation");

    private final JPAQueryFactory queryFactory;

    PatientNamedByContactFinder(final JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    List<PatientContacts.NamedByContact> find(final long patient) {
        return List.of();
    }

    private PatientContacts.NamedByContact map(final Tuple tuple) {

        PatientContacts.Investigation investigation = mapInvestigation(tuple);

        return null;
    }

    private PatientContacts.Investigation mapInvestigation(final Tuple tuple) {
        Long identifier = Objects.requireNonNull(tuple.get(INVESTIGATION.id), "An investigation identifier is required.");
        String local = tuple.get(INVESTIGATION.localId);
        String condition = tuple.get(INVESTIGATION.cdDescTxt);

        return new PatientContacts.Investigation(
                identifier,
                local,
                condition
        );
    }
}
