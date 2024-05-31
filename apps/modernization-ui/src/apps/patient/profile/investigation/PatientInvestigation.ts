export enum Headers {
    Investigation = 'Investigation #',
    StartDate = 'Start date',
    Condition = 'Condition',
    Status = 'Status',
    CaseStatus = 'Case status',
    Notification = 'Notification',
    Jurisdiction = 'Jurisdiction',
    Investigator = 'Investigator',
    CoInfection = 'Co-infection #'
}

export type Investigation = {
    investigation: string;
    startedOn?: Date | null;
    condition: string;
    status: string;
    caseStatus?: string | null;
    jurisdiction: string;
    event: string;
    coInfection?: string | null;
    notification?: string | null;
    investigator?: string | null;
    comparable: boolean;
};
