import { today } from 'date';
import {
    AdministrativeEntry,
    NameEntry,
    AddressEntry,
    PhoneEmailEntry,
    IdentificationEntry,
    EthnicityEntry,
    SexEntry,
    BirthEntry,
    MortalityEntry,
    GeneralInformationEntry
} from 'apps/patient/data/entry';
import { RaceEntry } from 'apps/patient/data/race';

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
    ethnicity: {
        asOf: asOf
    },
    mortality: {
        asOf: asOf
    },
    general: {
        asOf: asOf
    },
    phoneEmails: []
});

export { initial };
