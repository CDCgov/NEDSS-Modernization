package gov.cdc.nbs.patient.delete;

sealed interface PatientDeleteResult {

    long patient();
    
    record PatientDeleteSuccessful(
        long patient
    ) implements PatientDeleteResult {
    }


    record PatientDeleteFailed(
        long patient,
        String reason
    ) implements PatientDeleteResult {
    }
}
