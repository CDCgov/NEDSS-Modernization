package gov.cdc.nbs.patient.search.indexing.telecom;

import gov.cdc.nbs.patient.search.SearchablePatient;
import java.util.List;

public record SearchablePatientTelecom(
    List<SearchablePatient.Phone> phones, List<SearchablePatient.Email> emails) {}
