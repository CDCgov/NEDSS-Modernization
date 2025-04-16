package gov.cdc.nbs.patient.search;

import io.cucumber.java.ParameterType;

public class SearchableGenderSteps {

  @ParameterType(name = "searchableGender", value = "(?i)(male|female|unknown|no value)")
  public String searchableGender(final String value) {
    return switch (value.toLowerCase()) {
      case "male" -> SearchableGender.MALE.value();
      case "female" -> SearchableGender.FEMALE.value();
      case "unknown" -> SearchableGender.UNKNOWN.value();
      case "no value" -> SearchableGender.NO_VALUE.value();
      default -> value;
    };
  }

}
