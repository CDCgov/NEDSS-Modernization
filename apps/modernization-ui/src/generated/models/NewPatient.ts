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
import type { Identification } from './Identification';
import type { MortalityDemographic } from './MortalityDemographic';
import type { NameDemographic } from './NameDemographic';
import type { Phone } from './Phone';
import type { Race } from './Race';
export type NewPatient = {
    administrative?: Administrative;
    birth?: BirthDemographic;
    gender?: GenderDemographic;
    ethnicity?: EthnicityDemographic;
    mortality?: MortalityDemographic;
    general?: GeneralInformationDemographic;
    names?: Array<NameDemographic>;
    addresses?: Array<AddressDemographic>;
    phoneEmails?: Array<Phone>;
    races?: Array<Race>;
    identifications?: Array<Identification>;
};

