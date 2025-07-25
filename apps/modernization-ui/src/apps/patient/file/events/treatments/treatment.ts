import { AssociatedInvestigation } from 'libs/events/investigations/associated';
import { Provider } from 'libs/provider';

type PatientFileTreatment = {
    patient: number;
    id: number;
    local: string;
    createdOn?: Date;
    treatedOn?: Date;
    description?: string;
    organization?: string;
    provider?: Provider;
    associations?: Array<AssociatedInvestigation>;
};

export type { PatientFileTreatment };
