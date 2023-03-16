package gov.cdc.nbs.message.patient.event;

public record PatientEvent(
        String requestId,
        long patientId,
        long userId,
        PatientEventType eventType,
        Record record) {


    public enum PatientEventType {
        CREATE,
        UPDATE_GENERAL_INFO,
        UPDATE_MORTALITY,
        UPDATE_SEX_AND_BIRTH,
        DELETE
    }
}
