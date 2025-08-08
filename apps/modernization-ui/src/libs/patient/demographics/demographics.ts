import { today } from 'date';
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

const initial = (asOf: string = today()) => ({
    administrative: initialAdministrative(asOf),
    ethnicity: initialEthnicity(asOf),
    sexBirth: initialSexBirth(asOf),
    mortality: initialMortality(asOf),
    general: initialGeneral(asOf),
    names: [],
    addresses: [],
    phoneEmails: [],
    identifications: [],
    races: []
});

export { initial };
