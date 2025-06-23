/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Allowed } from './Allowed';
import type { Restricted } from './Restricted';
import type { Selectable } from './Selectable';
export type PatientGeneralInformationDemographic = {
    asOf?: string;
    maritalStatus?: Selectable;
    maternalMaidenName?: string;
    adultsInResidence?: number;
    childrenInResidence?: number;
    primaryOccupation?: Selectable;
    educationLevel?: Selectable;
    primaryLanguage?: Selectable;
    speaksEnglish?: Selectable;
    stateHIVCase?: (Allowed | Restricted);
};

