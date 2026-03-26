package gov.cdc.nbs.patient.search;

import java.time.LocalDate;

public record PatientSearchResult(
    long patient, String local, LocalDate birthday, Integer age, String gender, String status) {}
