package gov.cdc.nbs.report.mappers;

import gov.cdc.nbs.entity.odse.FilterValue;
import gov.cdc.nbs.entity.odse.ReportFilter;
import gov.cdc.nbs.id.IdGeneratorService;
import gov.cdc.nbs.report.ReportConstants;
import gov.cdc.nbs.report.models.AdvancedFilterRequest;
import gov.cdc.nbs.report.models.AdvancedQuery;
import gov.cdc.nbs.report.models.BasicFilterRequest;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class FilterValueMapper {
  private final IdGeneratorService idGenerator;

  private int sequenceNumber = 1;

  public FilterValueMapper(IdGeneratorService idGenerator) {
    this.idGenerator = idGenerator;
  }

  public List<FilterValue> fromBasicFilterRequest(
      ReportFilter basicFilter, BasicFilterRequest request) {
    return request.values().stream()
        .map(
            value ->
                FilterValue.builder()
                    .id(generateFilterValueId())
                    .reportFilter(basicFilter)
                    .valueType(ReportConstants.BASIC_FILTER_VALUE_TYPE)
                    .valueTxt(value)
                    .build())
        .toList();
  }

  public List<FilterValue> fromAdvancedFilterRequest(
      ReportFilter advancedFilter, AdvancedFilterRequest request) {
    List<FilterValue> filterValues = new ArrayList<>();

    AdvancedQuery.RuleGroup root = request.value();

    FilterValue openParen = buildOpenParenFilterValue(advancedFilter);
    filterValues.add(openParen);

    for (int i = 0; i < root.rules().size(); i++) {
      AdvancedQuery rule = root.rules().get(i);

      if (rule instanceof AdvancedQuery.Rule r) {
        FilterValue clause = buildClauseFilterValue(advancedFilter, r);
        filterValues.add(clause);

        if (i < root.rules().size() - 1) {
          FilterValue operator = buildOperatorFilterValue(advancedFilter, r);
          filterValues.add(operator);

          sequenceNumber++;
        }
      } else if (rule instanceof AdvancedQuery.RuleGroup r) {
        List<FilterValue> ruleGroupValues =
            fromAdvancedFilterRequest(
                advancedFilter, new AdvancedFilterRequest(request.reportFilterUid(), r));
        filterValues.addAll(ruleGroupValues);
      }
    }

    FilterValue closeParen = buildCloseParenFilterValue(advancedFilter);
    filterValues.add(closeParen);

    return filterValues;
  }

  //////////////////////////////////////////////////////////////////////////

  private FilterValue buildClauseFilterValue(ReportFilter advancedFilter, AdvancedQuery.Rule rule) {
    FilterValue clause =
        FilterValue.builder()
            .id(generateFilterValueId())
            .valueType(ReportConstants.AdvancedFilterValueType.CLAUSE.toString())
            .reportFilter(advancedFilter)
            .columnUid(rule.columnId())
            .operator(rule.operator())
            .valueTxt(rule.value())
            .sequenceNumber(sequenceNumber)
            .build();

    sequenceNumber++;

    return clause;
  }

  private FilterValue buildOperatorFilterValue(
      ReportFilter advancedFilter, AdvancedQuery.Rule rule) {
    FilterValue operator =
        FilterValue.builder()
            .id(generateFilterValueId())
            .reportFilter(advancedFilter)
            .valueType(ReportConstants.AdvancedFilterValueType.OPERATOR.toString())
            .operator(rule.operator())
            .sequenceNumber(sequenceNumber)
            .build();

    sequenceNumber++;

    return operator;
  }

  private FilterValue buildOpenParenFilterValue(ReportFilter advancedFilter) {
    return buildParenFilterValue(advancedFilter, "(");
  }

  private FilterValue buildCloseParenFilterValue(ReportFilter advancedFilter) {
    return buildParenFilterValue(advancedFilter, ")");
  }

  private FilterValue buildParenFilterValue(ReportFilter advancedFilter, String paren) {
    FilterValue parenValue =
        FilterValue.builder()
            .id(generateFilterValueId())
            .reportFilter(advancedFilter)
            .valueType(ReportConstants.AdvancedFilterValueType.OPERATOR.toString())
            .operator(paren)
            .sequenceNumber(sequenceNumber)
            .build();

    sequenceNumber++;

    return parenValue;
  }

  private Long generateFilterValueId() {
    var generatedId = idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS);
    return generatedId.getId();
  }
}
