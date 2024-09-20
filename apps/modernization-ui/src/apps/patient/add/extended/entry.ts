import {
    AdministrativeEntry,
    NameEntry,
    AddressEntry,
    PhoneEmailEntry,
    IdentificationEntry,
    RaceEntry,
    EthnicityEntry,
    SexEntry,
    BirthEntry,
    MortalityEntry,
    GeneralInformationEntry
} from 'apps/patient/data/entry';

type ExtendedNewPatientEntry = {
    administrative: AdministrativeEntry;
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
