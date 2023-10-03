package gov.cdc.nbs.patient.search.address;

record PatientSearchResultAddress(
    String use,
    String address,
    String address2,
    String city,
    String state,
    String zipcode
) {
}
