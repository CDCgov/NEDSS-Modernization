import { AddressDemographicRequest } from './address';
import { NameDemographicRequest } from './name';
import { AdministrativeInformationRequest } from './administrative';
import { EthnicityDemographicRequest } from './ethnicity';
import { GeneralInformationDemographicRequest } from './general';
import { IdentificationDemographicRequest } from './identification';
import { MortalityDemographicRequest } from './mortality';
import { PhoneEmailDemographicRequest } from './phoneEmail';
import { RaceDemographicRequest } from './race';
import { BirthDemographicRequest, SexDemographicRequest } from './sex-birth';

type PatientDemographicsRequest = {
    administrative?: AdministrativeInformationRequest;
    names?: NameDemographicRequest[];
    addresses?: AddressDemographicRequest[];
    phoneEmails?: PhoneEmailDemographicRequest[];
    identifications?: IdentificationDemographicRequest[];
    races?: RaceDemographicRequest[];
    ethnicity?: EthnicityDemographicRequest;
    birth?: BirthDemographicRequest;
    gender?: SexDemographicRequest;
    mortality?: MortalityDemographicRequest;
    general?: GeneralInformationDemographicRequest;
};

export type { PatientDemographicsRequest };
