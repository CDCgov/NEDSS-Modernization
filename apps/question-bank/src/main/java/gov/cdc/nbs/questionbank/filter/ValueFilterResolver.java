package gov.cdc.nbs.questionbank.filter;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Collection;
import java.util.stream.StreamSupport;

class ValueFilterResolver {

  ValueFilter single(
      final String property,
      final JsonNode operatorNode,
      final JsonNode valueNode
  ) {

    ValueFilter.Operator operator = resolveOperator(operatorNode);

    return new SingleValueFilter(
        property,
        operator,
        valueNode.textValue()
    );
  }

  ValueFilter multi(
      final String property,
      final JsonNode operatorNode,
      final JsonNode valuesNode
  ) {
    ValueFilter.Operator operator = resolveOperator(operatorNode);
    return new MultiValueFilter(
        property,
        operator,
        asStrings(valuesNode)
    );

  }

  private ValueFilter.Operator resolveOperator(final JsonNode node) {
    String value = node.asText();

    return switch (value.toLowerCase()) {
      case "equals" -> ValueFilter.Operator.EQUALS;
      case "not equal to", "not-equal-to", "not_equal_to" -> ValueFilter.Operator.NOT_EQUAL_TO;
      case "starts with", "starts-with", "starts_with" -> ValueFilter.Operator.STARTS_WITH;
      case "contains" -> ValueFilter.Operator.CONTAINS;
      default -> throw new IllegalStateException("Unexpected Value Filter Operator: " + value.toLowerCase());
    };
  }

  private Collection<String> asStrings(final JsonNode valueNode) {
    return StreamSupport.stream(valueNode.spliterator(), false)
        .map(JsonNode::asText)
        .toList();
  }

}
