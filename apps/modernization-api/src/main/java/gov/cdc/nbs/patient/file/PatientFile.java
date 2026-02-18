package gov.cdc.nbs.patient.file;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import gov.cdc.nbs.demographics.name.DisplayableName;
import gov.cdc.nbs.time.json.FormattedLocalDateJsonSerializer;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
record PatientFile(
    @JsonProperty(required = true) long id,
    @JsonProperty(required = true) long patientId,
    @JsonProperty(required = true) String local,
    @JsonProperty(required = true) String status,
    @JsonProperty(required = true) PatientDeletability deletability,
    String sex,
    @JsonSerialize(using = FormattedLocalDateJsonSerializer.class) LocalDate birthday,
    @JsonSerialize(using = FormattedLocalDateJsonSerializer.class) LocalDate deceasedOn,
    DisplayableName name) {

  PatientFile withName(final DisplayableName name) {
    return new PatientFile(
        id(),
        patientId(),
        local(),
        status(),
        deletability(),
        sex(),
        birthday(),
        deceasedOn(),
        name);
  }

  PatientFile withDeletability(final PatientDeletability deletability) {
    return new PatientFile(
        id(),
        patientId(),
        local(),
        status(),
        deletability,
        sex(),
        birthday(),
        deceasedOn(),
        name());
  }
}
