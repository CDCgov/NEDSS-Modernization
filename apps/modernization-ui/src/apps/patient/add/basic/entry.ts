import { today } from 'date';
import { IdentificationEntry } from 'apps/patient/data';
import { AdministrativeEntry } from 'apps/patient/data/entry';
import { Selectable } from 'options';
import { Sensitive } from 'libs/sensitive';

type NameInformationEntry = {
    last?: string;
    first?: string;
    middle?: string;
    suffix?: Selectable;
};

type BasicPersonalDetailsEntry = {
    bornOn?: string;
    currentSex?: Selectable;
    birthSex?: Selectable;
    deceased?: Selectable;
    deceasedOn?: string;
    maritalStatus?: Selectable;
    stateHIVCase?: Sensitive<string>;
};

type BasicAddressEntry = {
    address1?: string | null;
    address2?: string | null;
    city?: string | null;
    county?: Selectable | null;
    state?: Selectable | null;
    zipcode?: string | null;
    country?: Selectable | null;
    censusTract?: string | null;
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
    personalDetails?: BasicPersonalDetailsEntry;
    address?: BasicAddressEntry;
    phoneEmail?: BasicPhoneEmail;
    ethnicityRace?: BasicEthnicityRace;
    identifications?: BasicIdentificationEntry[];
};

export type {
    BasicNewPatientEntry,
    NameInformationEntry,
    BasicPersonalDetailsEntry,
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
    address: {
        country: {
            value: '840',
            name: 'United States'
        }
    },
    identifications: [],
    ethnicityRace: {
        races: []
    }
});

export { initial };
