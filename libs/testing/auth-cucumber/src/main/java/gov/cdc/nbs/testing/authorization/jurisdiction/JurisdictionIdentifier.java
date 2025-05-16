package gov.cdc.nbs.testing.authorization.jurisdiction;

public record JurisdictionIdentifier(long identifier, String code) {

  public static JurisdictionIdentifier ALL = new JurisdictionIdentifier(0, "ALL");
}
