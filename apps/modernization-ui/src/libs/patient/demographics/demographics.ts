import { HasPendingEntry } from 'design-system/entry/pending';
import { HasAdministrativeInformation, initial as initialAdministrative } from './administrative';
import { HasAddressDemographics } from './address';
import { HasNameDemographics } from './name';
import { HasPhoneEmailDemographics } from './phoneEmail';
import { HasIdentificationDemographics } from './identification';
import { HasRaceDemographics } from './race';
import { HasEthnicityDemographic, initial as initialEthnicity } from './ethnicity';
import { HasSexBirthDemographic, initial as initialSexBirth } from './sex-birth';
import { HasMortalityDemographic, initial as initialMortality } from './mortality';
import { HasGeneralInformationDemographic, initial as initialGeneral } from './general';
import { AddressDemographicDefaults } from './address/address';
import { Supplier } from 'libs/supplying';

type PatientDemographics = HasAdministrativeInformation &
    HasNameDemographics &
    HasAddressDemographics &
    HasPhoneEmailDemographics &
    HasIdentificationDemographics &
    HasRaceDemographics &
    HasEthnicityDemographic &
    HasSexBirthDemographic &
    HasMortalityDemographic &
    HasGeneralInformationDemographic;

export type { PatientDemographics };

type PatientDemographicsEntry = PatientDemographics & HasPendingEntry;

type PatientDemographicsDefaults = {
    asOf: Supplier<string>;
    address?: AddressDemographicDefaults;
};

export type { PatientDemographicsEntry, PatientDemographicsDefaults };

const initial = (defaults: PatientDemographicsDefaults): PatientDemographicsEntry => {
    return {
        pending: [],
        administrative: initialAdministrative(defaults.asOf),
        ethnicity: initialEthnicity(defaults.asOf),
        sexBirth: initialSexBirth(defaults.asOf),
        mortality: initialMortality(defaults.asOf),
        general: initialGeneral(defaults.asOf),
        names: [],
        addresses: [],
        phoneEmails: [],
        identifications: [],
        races: []
    };
};

export { initial };
