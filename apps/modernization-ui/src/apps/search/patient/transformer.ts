import { RecordStatus, PersonFilter, IdentificationCriteria } from 'generated/graphql/schema';

import { asValue, asValues } from 'options/selectable';
import { PatientCriteriaEntry } from './criteria';
// import { externalizeDate } from 'date';
//import { asDateEntry, DateCriteria } from 'design-system/date/entry';

const resolveIdentification = (data: PatientCriteriaEntry): IdentificationCriteria | undefined =>
    data.identification && data.identificationType
        ? {
              identificationNumber: data.identification,
              identificationType: data.identificationType.value
          }
        : undefined;

// const resolveBornOn = (bornOn: DateCriteria | undefined, dateOfBirth?: string): DateCriteria | undefined => {
//     console.log('resolveBornOn', bornOn, dateOfBirth);
//     if (!bornOn && dateOfBirth) {
//         const dateObj = asDateEntry(dateOfBirth);
//         return dateObj && { equals: dateObj };
//     }
//     return bornOn;
// };

export const transform = (data: PatientCriteriaEntry): PersonFilter => {
    const {
        name,
        id,
        location,
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
        treatment,
        vaccination,
        bornOn,
        ...remaining
    } = data;
    return {
        name,
        location,
        bornOn,
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
        identification: resolveIdentification(remaining)
        // dateOfBirth: externalizeDate(dateOfBirth),
        // bornOn: resolveBornOn(bornOn, dateOfBirth)
    };
};
