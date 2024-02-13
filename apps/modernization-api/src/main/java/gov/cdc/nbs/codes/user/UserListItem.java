package gov.cdc.nbs.codes.user;

record UserListItem(
    long nedssEntryId,
    String userId,
    String userFirstNm,
    String userLastNm
) {
}
