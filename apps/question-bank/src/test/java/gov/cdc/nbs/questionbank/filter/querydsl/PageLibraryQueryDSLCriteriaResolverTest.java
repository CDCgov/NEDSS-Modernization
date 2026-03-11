package gov.cdc.nbs.questionbank.filter.querydsl;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import gov.cdc.nbs.questionbank.entity.condition.QConditionCode;
import gov.cdc.nbs.questionbank.filter.MultiValueFilter;
import gov.cdc.nbs.questionbank.filter.SingleValueFilter;
import gov.cdc.nbs.questionbank.filter.ValueFilter.Operator;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PageLibraryQueryDSLCriteriaResolverTest {

  @Spy
  private final PageLibraryQueryDSLCriteriaResolver resolver =
      new PageLibraryQueryDSLCriteriaResolver();

  @Test
  void should_handle_conditions_not_in() {
    MultiValueFilter filter =
        new MultiValueFilter(
            "conditions",
            Operator.NOT_EQUAL_TO,
            Collections.singletonList("2019 Novel Coronavirus"));
    resolver.resolve(filter, QConditionCode.conditionCode.conditionCodesetNm);

    verify(resolver).conditionNotIn(Mockito.any());
  }

  @Test
  void should_not_handle_conditions_contains() {
    MultiValueFilter filter =
        new MultiValueFilter(
            "conditions",
            Operator.STARTS_WITH,
            Collections.singletonList("2019 Novel Coronavirus"));
    resolver.resolve(filter, QConditionCode.conditionCode.conditionCodesetNm);
    verify(resolver, times(0)).conditionNotIn(Mockito.any());
  }

  @Test
  void should_not_handle_single_value() {
    SingleValueFilter filter =
        new SingleValueFilter("conditions", Operator.NOT_EQUAL_TO, "2019 Novel Coronavirus");
    resolver.resolve(filter, QConditionCode.conditionCode.conditionCodesetNm);
    verify(resolver, times(0)).conditionNotIn(Mockito.any());
  }

  @Test
  void should_not_handle_string_expression() {
    MultiValueFilter filter =
        new MultiValueFilter(
            "conditions",
            Operator.NOT_EQUAL_TO,
            Collections.singletonList("2019 Novel Coronavirus"));
    resolver.resolve(filter, QConditionCode.conditionCode.nbsUid);
    verify(resolver, times(0)).conditionNotIn(Mockito.any());
  }
}
