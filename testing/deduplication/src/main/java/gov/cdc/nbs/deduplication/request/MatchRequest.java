package gov.cdc.nbs.deduplication.request;

import java.util.List;

public record MatchRequest(
    String localId,
    Name name,
    String dateOfBirth,
    String currentSex,
    List<Identification> identifications,
    List<Address> addresses) {

  public record Name(
      String first,
      String last) {
  }

  public record Identification(
      String value,
      String type,
      String authority,
      String authorityDesc,
      String authorityIdType) {
  }

  public record Address(
      String street,
      String city,
      String state,
      String zip) {
  }
}
