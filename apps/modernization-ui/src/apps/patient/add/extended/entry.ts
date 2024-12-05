import { today } from 'date';
import {
    AdministrativeEntry,
    PhoneEmailEntry,
    IdentificationEntry,
    SexEntry,
    BirthEntry,
    MortalityEntry,
    GeneralInformationEntry
} from 'apps/patient/data/entry';
import { EthnicityEntry, initial as initialEthnicity } from 'apps/patient/data/ethnicity';

import { AddressEntry, NameEntry, RaceEntry } from 'apps/patient/data';

type ExtendedNewPatientEntry = {
    administrative?: AdministrativeEntry;
    names?: NameEntry[];
    addresses?: AddressEntry[];
    phoneEmails?: PhoneEmailEntry[];
    identifications?: IdentificationEntry[];
    races?: RaceEntry[];
    ethnicity?: EthnicityEntry;
    birthAndSex?: BirthEntry & SexEntry;
    mortality?: MortalityEntry;
    general?: GeneralInformationEntry;
};

export type { ExtendedNewPatientEntry };

const initial = (asOf: string = today()) => ({
    administrative: {
        asOf: asOf
    },
    birthAndSex: {
        asOf: asOf
    },
    ethnicity: initialEthnicity(asOf),
    mortality: {
        asOf: asOf
    },
    general: {
        asOf: asOf
    },
    names: [],
    addresses: [],
    phoneEmails: [],
    identifications: [],
    races: []
});

export { initial };
