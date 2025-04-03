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

type BasicPersonalDetailsEntry = {
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

const defaultsInitial = (defaults: BasicNewPatientEntry, asOf: string = today()): BasicNewPatientEntry => ({
    administrative: {
        asOf: asOf
    },
    name: {},
    address: {
        address1: defaults.address?.address1 ?? undefined,
        address2: defaults.address?.address2 ?? undefined,
        state: defaults.address?.state ?? undefined,
        county: defaults.address?.county ?? undefined,
        zipcode: defaults.address?.zipcode ?? undefined,
        country: {
            value: '840',
            name: 'United States'
        }
    },
    identifications: (defaults.identifications?.length ?? 0 > 0) ? defaults.identifications : [],
    ethnicityRace: {
        races: (defaults.ethnicityRace?.races?.length ?? 0 > 0) ? defaults.ethnicityRace?.races : []
    }
});

export { initial, defaultsInitial };
