package gov.cdc.nbs.patient.morbidity;

import com.querydsl.core.Tuple;

import java.math.BigDecimal;
import java.util.Optional;

class LabOrderResultTupleMapper {

    private final LabOrderResultTables tables;

    LabOrderResultTupleMapper(final LabOrderResultTables tables) {
        this.tables = tables;
    }

    Optional<PatientMorbidity.LabOrderResult> maybeMap(final Tuple tuple) {
        String labTest = tuple.get(tables.labTest().labTestDescTxt);

        return labTest == null
            ? Optional.empty()
            : Optional.of(map(tuple));
    }

    PatientMorbidity.LabOrderResult map(final Tuple tuple) {

        String labTest = tuple.get(tables.labTest().labTestDescTxt);
        String status = tuple.get(tables.status().codeShortDescTxt);

        String codedResult = tuple.get(tables.codedLabResult().labResultDescTxt);

        String numericResult = mapNumericValue(tuple);

        String textResult = tuple.get(tables.textResult().valueTxt);

        return new PatientMorbidity.LabOrderResult(
            labTest,
            status,
            codedResult,
            numericResult,
            textResult
        );
    }

    private String mapNumericValue(final Tuple tuple) {
        String numericComparator = tuple.get(tables.numericResult().comparatorCd1);
        BigDecimal numericValue = tuple.get(tables.numericResult().numericValue1);

        return numericComparator != null && numericValue != null
            ? numericComparator + numericValue
            : null;
    }
}
