import { Provider } from 'libs/provider';
import { ResultedTest } from 'libs/events/tests';

type PatientFileDocumentRequiringReview = {
    patient: number;
    id: number;
    local: string;
    type: string;
    eventDate?: string;
    dateReceived?: string;
    isElectronic?: boolean;
    isUpdate?: boolean;
    reportingFacility?: string;
    orderingFacility?: string;
    orderingProvider?: Provider;
    sendingFacility?: string;
    condition?: string;
    treatments?: string[];
    resultedTests?: ResultedTest[];
};

export type { PatientFileDocumentRequiringReview };
