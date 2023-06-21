package gov.cdc.nbs.codes;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.entity.srte.QCodeValueGeneral;

import java.util.stream.Stream;

class GeneralCodedValueQuery {

    private final JPAQueryFactory factory;
    private final QCodeValueGeneral values;

    GeneralCodedValueQuery(
        final JPAQueryFactory factory,
        final QCodeValueGeneral values
    ) {
        this.factory = factory;
        this.values = values;
    }

    Stream<Tuple> all(final String set) {
        return this.factory.select(
                values.id.code,
                values.codeShortDescTxt
            ).from(values)
            .where(values.id.codeSetNm.eq(set))
            .orderBy(new OrderSpecifier<>(Order.ASC, values.indentLevelNbr))
            .fetch()
            .stream();
    }

}
