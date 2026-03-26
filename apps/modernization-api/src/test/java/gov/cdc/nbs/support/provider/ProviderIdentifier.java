package gov.cdc.nbs.support.provider;

public record ProviderIdentifier(long identifier, Name name) {

  public record Name(String prefix, String first, String last) {}
}
