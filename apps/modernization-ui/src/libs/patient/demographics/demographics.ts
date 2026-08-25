import { HasPendingEntry } from 'design-system/entry/pending';
import { Supplier } from 'libs/supplying';

import { HasAddressDemographics } from './address';
import { AddressDemographicDefaults } from './address/address';
import { HasAdministrativeInformation, initial as initialAdministrative } from './administrative';
import { HasEthnicityDemographic, initial as initialEthnicity } from './ethnicity';
import { HasGeneralInformationDemographic, initial as initialGeneral } from './general';
import { HasIdentificationDemographics } from './identification';
import { HasMortalityDemographic, initial as initialMortality } from './mortality';
import { HasNameDemographics } from './name';
import { HasPhoneEmailDemographics } from './phoneEmail';
import { HasRaceDemographics } from './race';
import { HasSexBirthDemographic, initial as initialSexBirth } from './sex-birth';

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
        races: [],
    };
};

export { initial };
