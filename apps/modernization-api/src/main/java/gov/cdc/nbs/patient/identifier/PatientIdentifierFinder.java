package gov.cdc.nbs.patient.identifier;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PatientIdentifierFinder {

    private static final String PATIENT_CODE = "PAT";

    private final JPAQueryFactory factory;
    private final PatientIdentifierTupleMapper.Tables tables;
    private final PatientIdentifierTupleMapper mapper;


    public PatientIdentifierFinder(
        final JPAQueryFactory factory,
        final PatientShortIdentifierResolver resolver
    ) {
        this.factory = factory;

        this.tables = new PatientIdentifierTupleMapper.Tables();
        this.mapper = new PatientIdentifierTupleMapper(this.tables, resolver);
    }

    public Optional<PatientIdentifier> findById(final long identifier) {
        return this.factory.selectDistinct(
                this.tables.patient().id,
                this.tables.patient().localId
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
}
