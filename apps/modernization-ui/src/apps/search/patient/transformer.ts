import { RecordStatus, PersonFilter, IdentificationCriteria } from 'generated/graphql/schema';

import { asValue, asValues } from 'options/selectable';
import { PatientCriteriaEntry } from './criteria';

const resolveIdentification = (data: PatientCriteriaEntry): IdentificationCriteria | undefined =>
    data.identification && data.identificationType
        ? {
              identificationNumber: data.identification,
              identificationType: data.identificationType.value
          }
        : undefined;

export const transform = (data: PatientCriteriaEntry): PersonFilter => {
    return {
        ...data,
        recordStatus: asValues(data.status) as RecordStatus[],
        gender: asValue(data.gender),
        state: asValue(data.state),
        zip: data.zip ? String(data.zip) : undefined,
        race: asValue(data.race),
        ethnicity: asValue(data.ethnicity),
        identification: resolveIdentification(data)
    };
};
