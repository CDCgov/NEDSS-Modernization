package gov.cdc.nbs.questionbank.filter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

class FilterJsonDeserializer extends JsonDeserializer<Filter> {

  private final ValueFilterResolver valueFilterResolver;

  FilterJsonDeserializer() {
    this.valueFilterResolver = new ValueFilterResolver();
  }

  @Override
  public Filter deserialize(
      final JsonParser parser,
      final DeserializationContext context
  ) throws IOException {

    JsonNode root = parser.getCodec().readTree(parser);

    String property = root.get("property").asText();
    JsonNode operatorNode = root.get("operator");

    JsonNode valueNode = root.get("value");

    if (valueNode != null && valueNode.isTextual()) {
      return this.valueFilterResolver.single(property, operatorNode, valueNode);
    }

    JsonNode valuesNode = root.get("values");

    if (valuesNode != null && valuesNode.isArray()) {
      return this.valueFilterResolver.multi(property, operatorNode, valuesNode);
    }

    return null;
  }


}
