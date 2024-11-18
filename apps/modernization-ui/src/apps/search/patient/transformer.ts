import { RecordStatus, PersonFilter, IdentificationCriteria } from 'generated/graphql/schema';

import { asValue, asValues } from 'options/selectable';
import { PatientCriteriaEntry } from './criteria';
import { externalizeDate } from 'date';

const resolveIdentification = (data: PatientCriteriaEntry): IdentificationCriteria | undefined =>
    data.identification && data.identificationType
        ? {
              identificationNumber: data.identification,
              identificationType: data.identificationType.value
          }
        : undefined;

export const transform = (data: PatientCriteriaEntry): PersonFilter => {
    const {
        name,
        id,
        location,
        phoneNumber,
        email,
        dateOfBirth,
        morbidity,
        document,
        stateCase,
        abcCase,
        cityCountyCase,
        notification,
        labReport,
        accessionNumber,
        investigation,
        treatment,
        vaccination,
        ...remaining
    } = data;
    return {
        name,
        location,
        id,
        phoneNumber,
        email,
        morbidity,
        document,
        stateCase,
        abcCase,
        cityCountyCase,
        notification,
        labReport,
        accessionNumber,
        investigation,
        vaccination,
        treatment,
        recordStatus: asValues(remaining.status) as RecordStatus[],
        gender: asValue(remaining.gender),
        state: asValue(remaining.state),
        zip: remaining.zip ? String(remaining.zip) : undefined,
        race: asValue(remaining.race),
        ethnicity: asValue(remaining.ethnicity),
        identification: resolveIdentification(remaining),
        dateOfBirth: externalizeDate(dateOfBirth)
    };
};
