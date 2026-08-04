import { AddressEntry, IdentificationEntry, NameEntry, PhoneEmailEntry, RaceEntry } from 'apps/patient/data';
import {
    AdministrativeEntry,
    BirthEntry,
    GeneralInformationEntry,
    MortalityEntry,
    SexEntry,
} from 'apps/patient/data/entry';
import { EthnicityEntry, initial as initialEthnicity } from 'apps/patient/data/ethnicity';
import { today } from 'date';

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
        asOf,
    },
    birthAndSex: {
        asOf,
    },
    ethnicity: initialEthnicity(asOf),
    mortality: {
        asOf,
    },
    general: {
        asOf,
    },
    names: [],
    addresses: [],
    phoneEmails: [],
    identifications: [],
    races: [],
});

export { initial };
