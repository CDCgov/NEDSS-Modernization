package gov.cdc.nbs.message.patient.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

public record PatientEvent(String requestId,
        long patientId,
        long userId,
        PatientEventType eventType,
        @JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION) @JsonSubTypes( {
                @Type(PatientCreateData.class),
                @Type(UpdateGeneralInfoData.class),
                @Type(UpdateMortalityData.class),
                @Type(UpdateSexAndBirthData.class)}) Record data){


    public enum PatientEventType {
        CREATE,
        UPDATE_GENERAL_INFO,
        UPDATE_MORTALITY,
        UPDATE_SEX_AND_BIRTH,
        DELETE
    }
}
