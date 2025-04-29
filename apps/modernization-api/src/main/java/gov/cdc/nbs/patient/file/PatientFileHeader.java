package gov.cdc.nbs.patient.file;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonProperty;

public record PatientFileHeader(
    @JsonProperty(required = true) long id,
    @JsonProperty(required = true) String patientId,
    @JsonProperty(required = true) String local,
    @JsonProperty(required = true) String status,
    @JsonProperty(required = true) String deletable,
    @JsonProperty(required = true) String sex,
    @JsonProperty(required = true) String birthday,
    MostRecentLegalName name) {

  record MostRecentLegalName(String first, String last, String middle, String suffix) {
  }
}
