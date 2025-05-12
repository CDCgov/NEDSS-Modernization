package gov.cdc.nbs.patient.file;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PatientDeletability {
  DELETABLE("Deletable"),
  IS_INACTIVE("Is_Inactive"),
  HAS_ASSOCIATIONS("Has_Associations");


  private final String value;

  PatientDeletability(final String value) {
    this.value = value;
  }

  @JsonValue
  String value() {
    return value;
  }
}
