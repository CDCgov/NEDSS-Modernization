import { DisplayableAddress } from 'address/display';

type DisplayablePhone = {
    type?: string;
    use?: string;
    number?: string;
};

type DisplayableIdentification = {
    type?: string;
    value?: string;
};

type PatientFileDemographicsSummary = {
    address?: DisplayableAddress;
    phone?: DisplayablePhone;
    email?: string;
    ethnicity?: string;
    identifications?: DisplayableIdentification[];
    races?: string[];
};

export type { PatientFileDemographicsSummary, DisplayablePhone, DisplayableIdentification };
