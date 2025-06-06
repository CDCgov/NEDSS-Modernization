type PatientInvestigation = {
    patient: number;
    identifier: number;
    local: string;
    startedOn?: Date;
    condition: string;
    status: string;
    caseStatus?: string;
    jurisdiction: string;
    coInfection?: string;
    notification?: string;
    investigator?: {
        first?: string;
        last?: string;
    };
    comparable: boolean;
};

export type { PatientInvestigation };
