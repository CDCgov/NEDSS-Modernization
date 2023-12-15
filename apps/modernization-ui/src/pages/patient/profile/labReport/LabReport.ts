export enum Headers {
    DateReceived = 'Date received',
    FacilityProvider = 'Facility / provider',
    DateCollected = 'Date collected',
    TestResults = 'Test results',
    AssociatedWith = 'Associated with',
    ProgramArea = 'Program area',
    Jurisdiction = 'Jurisdiction',
    EventID = 'Event #'
}

export type LabReport = {
    report: string;
    addTime?: Date | null;
    name: string;
    recordStatusCd: string;
    observations: string;
    observationUid: string | null;
    personParticipations: string | null;
    organizationParticipations: string | null;
    associatedInvestigations: string | null;
    programAreaCd?: string | null;
    jurisdictionCd: string;
    localId: string;
};

