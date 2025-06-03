export type PatientInvestigation = {
    investigationId: string;
    identifier: number;
    startedOn?: Date;
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
