package gov.cdc.nbs.configuration.features;

import gov.cdc.nbs.configuration.features.address.Address;
import gov.cdc.nbs.configuration.features.page_builder.PageBuilder;
import gov.cdc.nbs.configuration.features.patient.Patient;
import gov.cdc.nbs.configuration.features.search.Search;


public record Features(Search search, Address address, PageBuilder pageBuilder, Patient patient) {


}
