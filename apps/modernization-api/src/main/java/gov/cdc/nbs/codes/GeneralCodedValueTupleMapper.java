package gov.cdc.nbs.codes;

import com.querydsl.core.Tuple;
import gov.cdc.nbs.entity.srte.QCodeValueGeneral;

class GeneralCodedValueTupleMapper {

    private final QCodeValueGeneral values;

    GeneralCodedValueTupleMapper(final QCodeValueGeneral values) {
        this.values = values;
    }

    CodedValue map(final Tuple tuple) {
        String value = tuple.get(values.id.code);
        String name = tuple.get(values.codeShortDescTxt);
        return new CodedValue(value, name);
    }
}
