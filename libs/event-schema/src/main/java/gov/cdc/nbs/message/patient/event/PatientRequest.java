package gov.cdc.nbs.message.patient.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(PatientRequest.Delete.class),
    @JsonSubTypes.Type(PatientRequest.AddName.class),
    @JsonSubTypes.Type(PatientRequest.UpdateName.class),
    @JsonSubTypes.Type(PatientRequest.DeleteName.class),
    @JsonSubTypes.Type(PatientRequest.UpdateAdministrative.class),
    @JsonSubTypes.Type(PatientRequest.UpdateGeneralInfo.class),
    @JsonSubTypes.Type(PatientRequest.UpdateMortality.class),
    @JsonSubTypes.Type(PatientRequest.UpdateSexAndBirth.class),
    @JsonSubTypes.Type(PatientRequest.AddAddress.class),
    @JsonSubTypes.Type(PatientRequest.UpdateAddress.class),
    @JsonSubTypes.Type(PatientRequest.DeleteAddress.class),
    @JsonSubTypes.Type(PatientRequest.AddIdentification.class),
    @JsonSubTypes.Type(PatientRequest.UpdateIdentification.class),
    @JsonSubTypes.Type(PatientRequest.DeleteIdentification.class),
    @JsonSubTypes.Type(PatientRequest.AddEmail.class),
    @JsonSubTypes.Type(PatientRequest.UpdateEmail.class),
    @JsonSubTypes.Type(PatientRequest.DeleteEmail.class),
    @JsonSubTypes.Type(PatientRequest.AddPhone.class),
    @JsonSubTypes.Type(PatientRequest.UpdatePhone.class),
    @JsonSubTypes.Type(PatientRequest.DeletePhone.class),
    @JsonSubTypes.Type(PatientRequest.AddRace.class),
    @JsonSubTypes.Type(PatientRequest.UpdateRace.class),
    @JsonSubTypes.Type(PatientRequest.DeleteRace.class),
    @JsonSubTypes.Type(PatientRequest.UpdateEthnicity.class),
})
public sealed interface PatientRequest {

    String requestId();

    long patientId();

    long userId();

    default String type() {
        return PatientRequest.class.getSimpleName();
    }

    record Delete(
        String requestId,
        long patientId,
        long userId) implements PatientRequest {
    }


    record UpdateGeneralInfo(
        String requestId,
        long patientId,
        long userId,
        UpdateGeneralInfoData data) implements PatientRequest {
    }


    record UpdateMortality(
        String requestId,
        long patientId,
        long userId,
        UpdateMortalityData data) implements PatientRequest {
    }


    record AddName(
        String requestId,
        long patientId,
        long userId,
        UpdateNameData data) implements PatientRequest {
    }


    record UpdateName(
        String requestId,
        long patientId,
        long userId,
        UpdateNameData data) implements PatientRequest {
    }


    record DeleteName(
        String requestId,
        long patientId,
        short personNameSeq,
        long userId) implements PatientRequest {
    }


    record UpdateAdministrative(
        String requestId,
        long patientId,
        long userId,
        UpdateAdministrativeData data) implements PatientRequest {
    }


    record UpdateSexAndBirth(
        String requestId,
        long patientId,
        long userId,
        UpdateSexAndBirthData data) implements PatientRequest {
    }


    record UpdateEthnicity(
        String requestId,
        long patientId,
        long userId,
        UpdateEthnicityData data) implements PatientRequest {
    }


    record AddAddress(
        String requestId,
        long patientId,
        long userId,
        UpdateAddressData data) implements PatientRequest {
    }


    record UpdateAddress(
        String requestId,
        long patientId,
        long userId,
        UpdateAddressData data) implements PatientRequest {
    }


    record DeleteAddress(
        String requestId,
        long patientId,
        long id,
        long userId) implements PatientRequest {
    }


    record AddEmail(
        String requestId,
        long patientId,
        long userId,
        UpdateEmailData data) implements PatientRequest {
    }


    record UpdateEmail(
        String requestId,
        long patientId,
        long userId,
        UpdateEmailData data) implements PatientRequest {
    }


    record DeleteEmail(
        String requestId,
        long patientId,
        long id,
        long userId) implements PatientRequest {
    }


    record AddPhone(
        String requestId,
        long patientId,
        long userId,
        UpdatePhoneData data) implements PatientRequest {
    }


    record UpdatePhone(
        String requestId,
        long patientId,
        long userId,
        UpdatePhoneData data) implements PatientRequest {
    }


    record DeletePhone(
        String requestId,
        long patientId,
        long id,
        long userId) implements PatientRequest {
    }


    record AddIdentification(
        String requestId,
        long patientId,
        long userId,
        UpdateIdentificationData data) implements PatientRequest {
    }


    record UpdateIdentification(
        String requestId,
        long patientId,
        long userId,
        UpdateIdentificationData data) implements PatientRequest {
    }


    record DeleteIdentification(
        String requestId,
        long patientId,
        short id,
        long userId) implements PatientRequest {
    }


    record AddRace(
        String requestId,
        long patientId,
        long userId,
        AddRaceData data) implements PatientRequest {
    }


    record UpdateRace(
        String requestId,
        long patientId,
        long userId,
        UpdateRaceData data) implements PatientRequest {
    }


    record DeleteRace(
        String requestId,
        long patientId,
        String raceCd,
        long userId) implements PatientRequest {
    }
}
