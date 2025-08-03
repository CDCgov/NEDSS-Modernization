import { today } from 'date';
import { AdministrativeInformation, initial as initialAdministrative } from './administrative';
import { AddressDemographic } from './address';
import { NameDemographic } from './name';
import { PhoneEmailDemographic } from './phoneEmail';
import { IdentificationDemographic } from './identification';
import { RaceDemographic } from './race';
import { EthnicityDemographic, initial as initialEthnicity } from './ethnicity';
import { SexBirthDemographic, initial as initialSexBirth } from './sex-birth';
import { MortalityDemographic, initial as initialMortality } from './mortality';
import { GeneralInformationDemographic, initial as initialGeneral } from './general';

type PatientDemographics = {
    administrative?: AdministrativeInformation;
    names?: NameDemographic[];
    addresses?: AddressDemographic[];
    phoneEmails?: PhoneEmailDemographic[];
    identifications?: IdentificationDemographic[];
    races?: RaceDemographic[];
    ethnicity?: EthnicityDemographic;
    sexBirth?: SexBirthDemographic;
    mortality?: MortalityDemographic;
    general?: GeneralInformationDemographic;
};

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
