import { Investigator } from 'libs/events/investigations';

type PatientFileInvestigation = {
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
    investigator?: Investigator;
    comparable: boolean;
};

export type { PatientFileInvestigation };
