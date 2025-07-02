import { AssociatedInvestigation } from 'libs/events/investigations/associated';
import { Specimen } from 'libs/events/reports/laboratory';
import { ResultedTest } from 'libs/events/tests';
import { Provider } from 'libs/provider';

type PatientLabReport = {
    patient: number;
    id: number;
    local: string;
    programArea: string;
    jurisdiction: string;
    receivedDate?: Date;
    electronic: boolean;
    processingDecision?: string;
    collectedDate?: Date;
    resultedTests?: ResultedTest[];
    reportingFacility?: string;
    orderingProvider?: Provider;
    orderingFacility?: string;
    specimen?: Specimen;
    associations?: AssociatedInvestigation[];
};

export type { PatientLabReport };
