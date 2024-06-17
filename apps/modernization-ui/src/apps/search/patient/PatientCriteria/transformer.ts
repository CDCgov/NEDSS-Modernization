import {
    Deceased,
    IdentificationCriteria,
    PatientMorbidityLabResult,
    RecordStatus
} from 'generated/graphql/schema';
import { PersonFilter } from 'generated/graphql/schema';
import { PatientCriteriaForm } from './PatientCriteria';
import { asValue, asValues} from 'options/selectable';

export type PatientCriteria = PersonFilter & IdentificationCriteria & PatientMorbidityLabResult;

export const transformObject = (data: PatientCriteriaForm): PatientCriteria => {
    return {
        ...data,
        assigningAuthority: data.assigningAuthority ? asValue(data.assigningAuthority) as string : undefined,
        country: data.country ? asValue(data.country) as string : undefined,
        deceased: data.deceased ? asValue(data.deceased) as Deceased : undefined,
        ethnicity: data.ethnicity ? asValue(data.ethnicity) as string : undefined,
        gender: data.gender ? asValue(data.gender) as string : undefined,
        identification: data.identification ? { identificationNumber: data.identification } : undefined,
        identificationType: data.identificationType ? asValue(data.identificationType) as string : undefined,
        labTest: data.labTest ?? 'default',
        mortalityStatus: data.mortalityStatus ? asValue(data.mortalityStatus) as string : undefined,
        race: data.race ? asValue(data.race) as string : undefined,
        recordStatus: data.recordStatus ? asValues(data.recordStatus) as RecordStatus[] : [],
        state: data.state ? asValue(data.state) as string : undefined,
        status: data.status ? asValue(data.status) as string : undefined,
        zip: data.zip !== undefined ? String(data.zip) : undefined
    };
};