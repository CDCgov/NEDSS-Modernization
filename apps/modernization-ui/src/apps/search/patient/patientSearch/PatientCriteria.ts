import { Selectable } from "options";
import { Operator } from "generated/graphql/schema";

export type PatientCriteriaForm = {
    address?: string;
    assigningAuthority?: Selectable;
    city?: string;
    country?: Selectable;
    dateOfBirth?: Date;
    dateOfBirthOperator?: Operator;
    deceased?: Selectable;
    disableSoundex?: boolean;
    email?: string;
    ethnicity?: Selectable;
    firstName?: string;
    gender?: Selectable;
    id?: string;
    identification?: string;
    identificationType?: Selectable;
    labTest?: string;
    lastName?: string;
    mortalityStatus?: Selectable;
    phoneNumber?: string;
    race?: Selectable;
    recordStatus?: Selectable[] | undefined;
    state?: Selectable;
    status?: Selectable;
    treatmentId?: string;
    vaccinationId?: string;
    zip?: number;
};