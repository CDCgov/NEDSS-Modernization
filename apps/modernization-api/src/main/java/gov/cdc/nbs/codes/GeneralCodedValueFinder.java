package gov.cdc.nbs.codes;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.entity.srte.QCodeValueGeneral;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
class GeneralCodedValueFinder {

    private final JPAQueryFactory factory;
    private final QCodeValueGeneral values;

    GeneralCodedValueFinder(final JPAQueryFactory factory) {
        this.factory = factory;
        this.values = QCodeValueGeneral.codeValueGeneral;
    }

    Collection<CodedValue> all(final String set) {
        return this.factory.select(
                values.id.code,
                values.codeShortDescTxt
            ).from(values)
            .where(values.id.codeSetNm.eq(set))
            .fetch()
            .stream()
            .map(this::map)
            .toList();
    }

    private CodedValue map(final Tuple tuple) {
        String value = tuple.get(values.id.code);
        String name = StandardNameFormatter.formatted(tuple.get(values.codeShortDescTxt));
        return new CodedValue(value, name);
    }
}
