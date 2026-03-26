package gov.cdc.nbs.event.report.morbidity;

import gov.cdc.nbs.testing.authorization.jurisdiction.JurisdictionIdentifier;
import gov.cdc.nbs.testing.authorization.programarea.ProgramAreaIdentifier;

public record MorbidityReportIdentifier(
    long identifier,
    String local,
    long revision,
    ProgramAreaIdentifier programArea,
    JurisdictionIdentifier jurisdiction) {}
