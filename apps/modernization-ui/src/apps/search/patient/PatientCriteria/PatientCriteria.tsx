import { Deceased, Gender, Operator, PersonFilter, Scalars } from 'generated/graphql/schema';
import { Selectable } from 'options';
import { useEffect, useState } from 'react';
import {  } from 'types/patients';
import { transformObject } from './transformer';

type Props = {
    handleSubmission: (data: PersonFilter) => void;
    personFilter: PersonFilter | undefined;
    clearAll: () => void;
};

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

export const PatientCriteria = ({ personFilter, handleSubmission, clearAll }: Props) => {

    const onSubmit = (data: PatientCriteriaForm) => {
        handleSubmission(transformObject(data));
    };
    
    return <h2>PatientCriteria</h2>;
};
