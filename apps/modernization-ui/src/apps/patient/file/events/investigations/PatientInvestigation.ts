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
    investigationId: string;
    identifier: number;
    startedOn?: string;
    condition: string;
    status: string;
    caseStatus?: string;
    jurisdiction: string;
    coInfection?: string;
    notification?: string;
    investigatorName?: {
        first?: string;
        last?: string;
    };
    comparable: boolean;
};
