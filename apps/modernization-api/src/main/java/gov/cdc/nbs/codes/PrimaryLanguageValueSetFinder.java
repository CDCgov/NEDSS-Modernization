package gov.cdc.nbs.codes;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.entity.srte.QLanguageCode;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
class PrimaryLanguageValueSetFinder {

    private final JPAQueryFactory factory;
    private final QLanguageCode values;

    PrimaryLanguageValueSetFinder(final JPAQueryFactory factory) {
        this.factory = factory;
        this.values = QLanguageCode.languageCode;
    }

    Collection<CodedValue> all() {
        return this.factory.select(
                values.id,
                values.codeShortDescTxt
            ).from(values)
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
