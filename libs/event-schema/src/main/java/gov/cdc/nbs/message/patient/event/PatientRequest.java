package gov.cdc.nbs.message.patient.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes( {
    @JsonSubTypes.Type(PatientRequest.Create.class),
    @JsonSubTypes.Type(PatientRequest.Delete.class),
})
public sealed interface PatientRequest {

  String requestId();

  long patientId();

  long userId();

  default String type() {
    return PatientRequest.class.getSimpleName();
  }


  record Create(
      String requestId,
      long patientId,
      long userId,
      PatientCreateData data
  ) implements PatientRequest {

  }


  record Delete(
      String requestId,
      long patientId,
      long userId
  ) implements PatientRequest {
  }
}
