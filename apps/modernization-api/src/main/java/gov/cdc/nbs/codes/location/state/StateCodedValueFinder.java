package gov.cdc.nbs.codes.location.state;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.entity.srte.QStateCode;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class StateCodedValueFinder {

  private final JPAQueryFactory factory;
  private final QStateCode values;

  StateCodedValueFinder(final JPAQueryFactory factory) {
    this.factory = factory;
    this.values = QStateCode.stateCode;
  }

  public Collection<StateCodedValue> all() {
    return this.factory.select(
        values.id,
        values.codeDescTxt,
        values.stateNm).from(values)
        .orderBy(new OrderSpecifier<>(Order.ASC, values.id))
        .fetch()
        .stream()
        .map(this::map)
        .toList();
  }

  private StateCodedValue map(final Tuple tuple) {
    String value = tuple.get(values.id);
    String name = tuple.get(values.codeDescTxt);
    String abbreviation = tuple.get(values.stateNm);
    return new StateCodedValue(value, name, abbreviation);
  }
}
