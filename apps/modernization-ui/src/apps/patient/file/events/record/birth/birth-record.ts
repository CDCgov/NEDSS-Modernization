import { DisplayableAddress } from 'address/display';
import { AssociatedInvestigation } from 'libs/events/investigations/associated';
import { DisplayableName } from 'name';

type MotherInformation = {
    name?: DisplayableName;
    address?: DisplayableAddress;
};

type PatientFileBirthRecord = {
    patient: number;
    id: number;
    local: string;
    receivedOn?: Date;
    facility?: string;
    collectedOn?: Date;
    certificate?: string;
    mother?: MotherInformation;
    associations?: AssociatedInvestigation[];
};

export type { MotherInformation, PatientFileBirthRecord };
