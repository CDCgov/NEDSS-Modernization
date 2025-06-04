package gov.cdc.nbs.configuration.features;

import gov.cdc.nbs.configuration.features.address.Address;
import gov.cdc.nbs.configuration.features.deduplication.Deduplication;
import gov.cdc.nbs.configuration.features.page_builder.PageBuilder;
import gov.cdc.nbs.configuration.features.patient.Patient;
import gov.cdc.nbs.configuration.features.search.Search;
import gov.cdc.nbs.configuration.features.system.management.SystemManagement;



public record Features(
    Search search,
    Address address,
    PageBuilder pageBuilder,
    Deduplication deduplication,
    Patient patient,
    SystemManagement systemManagement) {

}
