package gov.cdc.nbs.patient.identifier;

import com.querydsl.core.Tuple;
import gov.cdc.nbs.entity.odse.QPerson;

import java.util.Objects;

class PatientIdentifierTupleMapper {

    record Tables(
        QPerson patient
    ) {
        Tables() {
            this(QPerson.person);
        }
    }


    private final Tables tables;
    private final PatientShortIdentifierResolver resolver;

    PatientIdentifierTupleMapper(
        final Tables tables,
        final PatientShortIdentifierResolver resolver
    ) {
        this.tables = tables;
        this.resolver = resolver;
    }

    PatientIdentifier map(final Tuple tuple) {
        long identifier = Objects.requireNonNull(
            tuple.get(this.tables.patient().id),
            "A patient id is required"
        );

        String local = Objects.requireNonNull(
            tuple.get(this.tables.patient().localId),
            "A patient local id is required"
        );

        long shortId = resolver.resolve(local).orElse(identifier);

        return new PatientIdentifier(
            identifier,
            shortId,
            local
        );
    }
}
