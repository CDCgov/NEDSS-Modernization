package gov.cdc.nbs.event.document;

import io.cucumber.java.ParameterType;


public class DocumentSteps {

  @ParameterType(name = "documentType", value = "(?i)lab report|laboratory report|morbidity report|case report")
  public String documentType(final String value) {
    return switch (value.toLowerCase()) {
      case "lab report", "laboratory report" -> "Laboratory Report";
      case "morbidity report" -> "Morbidity Report";
      case "case report" -> "Case Report";
      default -> value;
    };
  }


}
