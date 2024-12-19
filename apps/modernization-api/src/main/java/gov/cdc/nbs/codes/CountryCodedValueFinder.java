package gov.cdc.nbs.codes;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.entity.srte.QCountryCode;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
class CountryCodedValueFinder {

    private final JPAQueryFactory factory;
    private final QCountryCode values;

    CountryCodedValueFinder(final JPAQueryFactory factory) {
        this.factory = factory;
        this.values = QCountryCode.countryCode;
    }

    Collection<CodedValue> all() {
        return this.factory.select(
                values.id,
                values.codeDescTxt).from(values)
                .add(new CodedValue("United States", "840"));
                .orderBy(new OrderSpecifier<>(Order.ASC, values.codeDescTxt))
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
