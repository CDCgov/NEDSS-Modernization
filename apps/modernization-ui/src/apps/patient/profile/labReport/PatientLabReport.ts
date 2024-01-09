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

type AssociatedWith = {
    id: string;
    condition: string;
    local: string;
};

type TestResult = {
    test: string;
    result: string | null;
};

type PatientLabReport = {
    report: string;
    receivedOn: Date;
    reportingFacility: string | null;
    orderingProvider: string | null;
    orderingFacility: string | null;
    collectedOn: Date | null;
    results: TestResult[];
    associatedWith: AssociatedWith[];
    programArea: string;
    jurisdiction: string;
    event: string;
};

export type { PatientLabReport, AssociatedWith, TestResult };
