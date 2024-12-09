import { today } from 'date';
import { IdentificationEntry } from 'apps/patient/data';
import { AdministrativeEntry } from 'apps/patient/data/entry';
import { Selectable } from 'options';

type NameInformationEntry = {
    last?: string;
    first?: string;
    middle?: string;
    suffix?: Selectable;
};

type OtherInformationEntry = {
    bornOn?: string;
    currentSex?: Selectable;
    birthSex?: Selectable;
    deceased?: Selectable;
    deceasedOn?: string;
    maritalStatus?: Selectable;
    stateHIVCase?: string;
};

type BasicAddressEntry = {
    address1?: string;
    address2?: string;
    city?: string;
    county?: Selectable;
    state?: Selectable;
    zipcode?: string;
    country?: Selectable;
    censusTract?: string;
};

type BasicWorkPhone = { phone: string; extension?: string };

type BasicPhoneEmail = {
    home?: string;
    work?: BasicWorkPhone;
    cell?: string;
    email?: string;
};

type BasicEthnicityRace = {
    ethnicity?: Selectable;
    races?: Selectable[];
};

type BasicIdentificationEntry = Omit<IdentificationEntry, 'asOf'>;

type BasicNewPatientEntry = {
    administrative: AdministrativeEntry;
    name?: NameInformationEntry;
    other?: OtherInformationEntry;
    address?: BasicAddressEntry;
    phoneEmail?: BasicPhoneEmail;
    ethnicityRace?: BasicEthnicityRace;
    identifications?: BasicIdentificationEntry[];
};

export type {
    BasicNewPatientEntry,
    NameInformationEntry,
    OtherInformationEntry,
    BasicAddressEntry,
    BasicWorkPhone,
    BasicPhoneEmail,
    BasicEthnicityRace,
    BasicIdentificationEntry
};

const initial = (asOf: string = today()): BasicNewPatientEntry => ({
    administrative: {
        asOf: asOf
    },
    identifications: []
});

export { initial };
