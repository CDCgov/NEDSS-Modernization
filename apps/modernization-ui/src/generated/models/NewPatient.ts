/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AddressDemographic } from './AddressDemographic';
import type { Administrative } from './Administrative';
import type { BirthDemographic } from './BirthDemographic';
import type { EthnicityDemographic } from './EthnicityDemographic';
import type { GenderDemographic } from './GenderDemographic';
import type { GeneralInformationDemographic } from './GeneralInformationDemographic';
import type { IdentificationDemographic } from './IdentificationDemographic';
import type { MortalityDemographic } from './MortalityDemographic';
import type { NameDemographic } from './NameDemographic';
import type { PhoneDemographic } from './PhoneDemographic';
import type { RaceDemographic } from './RaceDemographic';
export type NewPatient = {
    administrative?: Administrative;
    birth?: BirthDemographic;
    gender?: GenderDemographic;
    ethnicity?: EthnicityDemographic;
    mortality?: MortalityDemographic;
    general?: GeneralInformationDemographic;
    names?: Array<NameDemographic>;
    addresses?: Array<AddressDemographic>;
    phoneEmails?: Array<PhoneDemographic>;
    races?: Array<RaceDemographic>;
    identifications?: Array<IdentificationDemographic>;
};

