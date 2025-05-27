package gov.cdc.nbs.event.investigation;

import gov.cdc.nbs.testing.authorization.jurisdiction.JurisdictionIdentifier;
import gov.cdc.nbs.testing.authorization.programarea.ProgramAreaIdentifier;

public record InvestigationIdentifier(
    long identifier,
    String local,
    long revision,
    ProgramAreaIdentifier programArea,
    JurisdictionIdentifier jurisdiction
) {
}
