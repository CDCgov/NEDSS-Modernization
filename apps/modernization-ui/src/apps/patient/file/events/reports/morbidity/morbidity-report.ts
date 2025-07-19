import { AssociatedInvestigation } from 'libs/events/investigations/associated';
import { ResultedTest } from 'libs/events/tests';
import { Provider } from 'libs/provider';

type PatientFileMorbidityReport = {
    patient: number;
    id: number;
    local: string;
    jurisdiction: string;
    addedOn: Date;
    receivedOn?: Date;
    reportedOn?: Date;
    condition?: string;
    reportingFacility?: string;
    orderingProvider?: Provider;
    reportingProvider?: Provider;
    treatments?: string[];
    resultedTests?: ResultedTest[];
    associations?: AssociatedInvestigation[];
};

export type { PatientFileMorbidityReport };
