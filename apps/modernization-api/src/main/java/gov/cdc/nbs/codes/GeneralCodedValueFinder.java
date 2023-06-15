package gov.cdc.nbs.codes;

import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.entity.srte.QCodeValueGeneral;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.function.UnaryOperator;

@Component
class GeneralCodedValueFinder {

    private final GeneralCodedValueQuery query;
    private final GeneralCodedValueTupleMapper mapper;

    GeneralCodedValueFinder(final JPAQueryFactory factory) {
        QCodeValueGeneral values = QCodeValueGeneral.codeValueGeneral;
        this.query = new GeneralCodedValueQuery(factory, values);
        this.mapper = new GeneralCodedValueTupleMapper(values);
    }

    Collection<CodedValue> all(final String set) {
        return this.query.all(set)
            .map(this.mapper::map)
            .toList();
    }

    Collection<CodedValue> all(final String set, final UnaryOperator<CodedValue> transformer) {
        return this.query.all(set)
            .map(transformer.compose(this.mapper::map))
            .toList();
    }

}
