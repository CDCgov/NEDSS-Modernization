package gov.cdc.nbs.codes;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.entity.srte.QNaicsIndustryCode;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
class PrimaryOccupationValueSetFinder {

    private final JPAQueryFactory factory;
    private final QNaicsIndustryCode values;

    PrimaryOccupationValueSetFinder(final JPAQueryFactory factory) {
        this.factory = factory;
        this.values = QNaicsIndustryCode.naicsIndustryCode;
    }

    Collection<CodedValue> all() {
        return this.factory.select(
                values.id,
                values.codeShortDescTxt).from(values)
                .where(values.indentLevelNbr.eq((short) 1))
                .orderBy(new OrderSpecifier<>(Order.ASC, values.codeShortDescTxt))
                .fetch()
                .stream()
                .map(this::map)
                .toList();
    }

    private CodedValue map(final Tuple tuple) {
        String value = tuple.get(values.id);
        String name = StandardNameFormatter.formatted(tuple.get(values.codeShortDescTxt));
        return new CodedValue(value, name);
    }
}
