import { Investigator } from 'libs/events/investigations';

type PatientFileOpenInvestigation = {
    patient: number;
    identifier: number;
    local: string;
    startedOn?: Date;
    condition: string;
    caseStatus?: string;
    jurisdiction: string;
    coInfection?: string;
    notification?: string;
    investigator?: Investigator;
};

export type { PatientFileOpenInvestigation };
