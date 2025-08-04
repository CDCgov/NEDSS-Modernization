import { AssociatedInvestigation } from 'libs/events/investigations/associated';

type PatientFileDocument = {
    patient: number;
    id: number;
    local: string;
    receivedOn?: Date;
    sendingFacility?: string;
    reportedOn?: Date;
    condition?: string;
    updated?: boolean;
    associations?: Array<AssociatedInvestigation>;
};

export type { PatientFileDocument };
