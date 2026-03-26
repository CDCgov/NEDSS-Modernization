package gov.cdc.nbs.questionbank.filter.querydsl;

import static org.assertj.core.api.Assertions.assertThat;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import gov.cdc.nbs.questionbank.filter.DateFilter;
import gov.cdc.nbs.questionbank.filter.DateRangeFilter;
import gov.cdc.nbs.questionbank.filter.Filter;
import gov.cdc.nbs.questionbank.filter.MultiValueFilter;
import gov.cdc.nbs.questionbank.filter.SingleValueFilter;
import gov.cdc.nbs.questionbank.filter.ValueFilter;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class DefaultQueryDSLCriteriaResolverTest {

  @Test
  void should_not_apply_single_value_filter_without_a_StringExpression() {

    Filter filter = new SingleValueFilter("property", ValueFilter.Operator.NOT_EQUAL_TO, "value");

    DefaultQueryDSLCriteriaResolver resolver = new DefaultQueryDSLCriteriaResolver();

    Stream<BooleanExpression> actual = resolver.resolve(filter, Expressions.asNumber(16));

    assertThat(actual).isEmpty();
  }

  @Test
  void should_not_apply_multi_value_filter_without_a_StringExpression() {

    Filter filter =
        new MultiValueFilter("property", ValueFilter.Operator.NOT_EQUAL_TO, List.of("a", "3", "d"));

    DefaultQueryDSLCriteriaResolver resolver = new DefaultQueryDSLCriteriaResolver();

    Stream<BooleanExpression> actual = resolver.resolve(filter, Expressions.asNumber(16));

    assertThat(actual).isEmpty();
  }

  @Test
  void should_not_apply_date_filter_without_a_TemporalExpression() {

    Filter filter = new DateFilter("property", DateFilter.Operator.TODAY);

    DefaultQueryDSLCriteriaResolver resolver = new DefaultQueryDSLCriteriaResolver();

    Stream<BooleanExpression> actual = resolver.resolve(filter, Expressions.asNumber(16));

    assertThat(actual).isEmpty();
  }

  @Test
  void should_not_apply_date_range_filter_without_a_TemporalExpression() {

    Filter filter = new DateRangeFilter("property", LocalDate.MIN, LocalDate.MAX);

    DefaultQueryDSLCriteriaResolver resolver = new DefaultQueryDSLCriteriaResolver();

    Stream<BooleanExpression> actual = resolver.resolve(filter, Expressions.asNumber(16));

    assertThat(actual).isEmpty();
  }
}
