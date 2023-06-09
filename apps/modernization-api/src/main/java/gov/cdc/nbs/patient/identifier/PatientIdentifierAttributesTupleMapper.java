package gov.cdc.nbs.patient.identifier;

import com.querydsl.core.Tuple;
import gov.cdc.nbs.id.QLocalUidGenerator;

class PatientIdentifierAttributesTupleMapper {

    record Tables(QLocalUidGenerator localUniqueIdentifier) {
        Tables() {
            this(QLocalUidGenerator.localUidGenerator);
        }
    }


    private final Tables tables;

    PatientIdentifierAttributesTupleMapper(final Tables tables) {
        this.tables = tables;
    }

    PatientIdentifierAttributes map(final Tuple tuple) {
        String prefix = tuple.get(tables.localUniqueIdentifier().uidPrefixCd);
        String suffix = tuple.get(tables.localUniqueIdentifier().uidSuffixCd);
        return new PatientIdentifierAttributes(
                prefix,
                suffix);
    }
}
