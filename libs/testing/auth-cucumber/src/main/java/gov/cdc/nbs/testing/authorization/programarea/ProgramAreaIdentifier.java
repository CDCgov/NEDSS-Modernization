package gov.cdc.nbs.testing.authorization.programarea;

import gov.cdc.nbs.testing.authorization.jurisdiction.JurisdictionIdentifier;

public record ProgramAreaIdentifier(long identifier, String code) {

  public long oid(final JurisdictionIdentifier jurisdiction) {
    return (jurisdiction.identifier() * 100000L) + identifier();
  }
}
