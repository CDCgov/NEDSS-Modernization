/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AddressDemographic } from './AddressDemographic';
import type { Administrative } from './Administrative';
import type { BirthDemographic } from './BirthDemographic';
import type { GenderDemographic } from './GenderDemographic';
import type { Identification } from './Identification';
import type { NameDemographic } from './NameDemographic';
import type { Phone } from './Phone';
import type { Race } from './Race';
export type NewPatient = {
    administrative?: Administrative;
    birth?: BirthDemographic;
    gender?: GenderDemographic;
    names?: Array<NameDemographic>;
    addresses?: Array<AddressDemographic>;
    phoneEmails?: Array<Phone>;
    races?: Array<Race>;
    identifications?: Array<Identification>;
};

