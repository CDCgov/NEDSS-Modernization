package gov.cdc.nbs.codes;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.entity.srte.QStateCode;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
class StateCodedValueFinder {

    private final JPAQueryFactory factory;
    private final QStateCode values;

    StateCodedValueFinder(final JPAQueryFactory factory) {
        this.factory = factory;
        this.values = QStateCode.stateCode;
    }

    Collection<CodedValue> all() {
        return this.factory.select(
                values.id,
                values.codeDescTxt
            ).from(values)
            .orderBy(new OrderSpecifier<>(Order.ASC, values.indentLevelNbr))
            .fetch()
            .stream()
            .map(this::map)
            .toList();
    }

    private CodedValue map(final Tuple tuple) {
        String value = tuple.get(values.id);
        String name = tuple.get(values.codeDescTxt);
        return new CodedValue(value, name);
    }
}
