export type { AddressDemographic, AddressDemographicRequest } from './address';
export type { AdministrativeInformation, AdministrativeInformationRequest } from './administrative';
export type { EthnicityDemographic, EthnicityDemographicRequest } from './ethnicity';
export type { GeneralInformationDemographic, GeneralInformationDemographicRequest } from './general';
export type { IdentificationDemographic, IdentificationDemographicRequest } from './identification';
export type { MortalityDemographic, MortalityDemographicRequest } from './mortality';
export type { NameDemographic, NameDemographicRequest } from './name';
export type { PhoneEmailDemographic, PhoneEmailDemographicRequest } from './phoneEmail';
export type { RaceDemographic, RaceDemographicRequest } from './race';
export type { SexBirthDemographic, SexDemographicRequest, BirthDemographicRequest } from './sex-birth';

export { initial } from './demographics';
export type { PatientDemographics, PatientDemographicsDefaults, PatientDemographicsEntry } from './demographics';
export type { PatientDemographicsRequest } from './request';

export { PatientDemographicsForm } from './PatientDemographicsForm';
export { usePatientDemographicDefaults } from './usePatientDemographicDefaults';

export { transformer } from './transformer';

export { sections } from './sections';
