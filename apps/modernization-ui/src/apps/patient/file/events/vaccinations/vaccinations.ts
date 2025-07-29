import { AssociatedInvestigation } from 'libs/events/investigations/associated';
import { Provider } from 'libs/provider';

type PatientFileVaccinations = {
    patient: number;
    id: number;
    local: string;
    createdOn?: Date;
    organization?: string;
    provider?: Provider;
    administratedOn?: Date;
    administered?: string;
    associations?: AssociatedInvestigation[];
};

export type { PatientFileVaccinations };
