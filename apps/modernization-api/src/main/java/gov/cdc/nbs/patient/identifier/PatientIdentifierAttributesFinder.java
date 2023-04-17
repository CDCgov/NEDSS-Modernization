package gov.cdc.nbs.patient.identifier;

import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.entity.odse.QLocalUidGenerator;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class PatientIdentifierAttributesFinder {

    private static final String PERSON_CLASS = "PERSON";
    private static final String LOCAL_TYPE = "LOCAL";
    private final JPAQueryFactory factory;
    private final PatientIdentifierAttributesTupleMapper.Tables tables;

    private final PatientIdentifierAttributesTupleMapper mapper;

    PatientIdentifierAttributesFinder(final JPAQueryFactory factory) {
        this.factory = factory;
        this.tables = new PatientIdentifierAttributesTupleMapper.Tables(QLocalUidGenerator.localUidGenerator);
        this.mapper = new PatientIdentifierAttributesTupleMapper(this.tables);
    }

    Optional<PatientIdentifierAttributes> find() {
        return this.factory.selectDistinct(
                this.tables.localUniqueIdentifier().uidPrefixCd,
                this.tables.localUniqueIdentifier().uidSuffixCd
            ).from(this.tables.localUniqueIdentifier())
            .where(
                this.tables.localUniqueIdentifier().id.eq(PERSON_CLASS),
                this.tables.localUniqueIdentifier().typeCd.eq(LOCAL_TYPE)
            )
            .fetch()
            .stream()
            .map(mapper::map)
            .findFirst();
    }


}
