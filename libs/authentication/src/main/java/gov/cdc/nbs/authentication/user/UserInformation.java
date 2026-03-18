package gov.cdc.nbs.authentication.user;

record UserInformation(
    long identifier, String first, String last, String username, boolean enabled) {}
