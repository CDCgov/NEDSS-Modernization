package gov.cdc.nbs.message.patient.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(PatientRequest.Create.class),
    @JsonSubTypes.Type(PatientRequest.Delete.class),
    @JsonSubTypes.Type(PatientRequest.AddName.class),
    @JsonSubTypes.Type(PatientRequest.UpdateName.class),
    @JsonSubTypes.Type(PatientRequest.DeleteName.class),
    @JsonSubTypes.Type(PatientRequest.updateAdministrative.class),
    @JsonSubTypes.Type(PatientRequest.UpdateGeneralInfo.class),
    @JsonSubTypes.Type(PatientRequest.UpdateMortality.class),
    @JsonSubTypes.Type(PatientRequest.UpdateSexAndBirth.class)
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


  record UpdateGeneralInfo(
      String requestId,
      long patientId,
      long userId,
      UpdateGeneralInfoData data
  ) implements PatientRequest {
  }


  record UpdateMortality(
      String requestId,
      long patientId,
      long userId,
      UpdateMortalityData data
  ) implements PatientRequest {
  }

  record AddName(
      String requestId,
      long patientId,
      long userId,
      UpdateNameData data
  ) implements PatientRequest {
  }

  record UpdateName(
      String requestId,
      long patientId,
      long userId,
      UpdateNameData data
  ) implements PatientRequest {
  }

  record DeleteName(
    String requestId,
    long patientId,
    short personNameSeq,
    long userId
) implements PatientRequest {
}

record updateAdministrative(
    String requestId,
    long patientId,
    long userId,
    UpdateAdministrativeData data
) implements PatientRequest {
}

  record UpdateSexAndBirth(
      String requestId,
      long patientId,
      long userId,
      UpdateSexAndBirthData data
  ) implements PatientRequest {
  }
}
