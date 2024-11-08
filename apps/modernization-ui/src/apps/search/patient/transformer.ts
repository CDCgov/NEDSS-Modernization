import { RecordStatus, PersonFilter, IdentificationCriteria } from 'generated/graphql/schema';

import { asValue, asValues } from 'options/selectable';
import { PatientCriteriaEntry } from './criteria';
import { externalizeDate } from 'date';
import { asTextCriteria } from 'options/operator';

const resolveIdentification = (data: PatientCriteriaEntry): IdentificationCriteria | undefined =>
    data.identification && data.identificationType
        ? {
              identificationNumber: data.identification,
              identificationType: data.identificationType.value
          }
        : undefined;

export const transform = (data: PatientCriteriaEntry): PersonFilter => {
    const {
        includeSimilar,
        lastName,
        firstName,
        id,
        address,
        city,
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
        ...remaining
    } = data;
    return {
        disableSoundex: !includeSimilar,
        name: {
            last: asTextCriteria(lastName)
        },
        firstName,
        id,
        address,
        city,
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
