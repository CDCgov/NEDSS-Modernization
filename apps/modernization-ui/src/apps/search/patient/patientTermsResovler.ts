import { Term } from 'apps/search';
import { PatientCriteriaEntry } from './criteria';

const patientTermsResolver = (entry: PatientCriteriaEntry): Term[] => {
    const terms: Term[] = [];

    if (entry.lastName) {
        terms.push({ source: 'lastName', name: 'LAST', value: entry.lastName });
    }

    if (entry.firstName) {
        terms.push({ source: 'firstName', name: 'FIRST', value: entry.firstName });
    }

    if (entry.gender) {
        terms.push({ source: 'gender', name: 'SEX', value: entry.gender.name });
    }

    if (entry.id) {
        terms.push({ source: 'id', name: 'ID', value: entry.id });
    }

    if (entry.address) {
        terms.push({ source: 'address', name: 'ADDRESS', value: entry.address });
    }

    if (entry.city) {
        terms.push({ source: 'city', name: 'CITY', value: entry.city });
    }

    if (entry.state) {
        terms.push({ source: 'state', name: 'STATE', value: entry.state.name });
    }

    if (entry.zip) {
        terms.push({ source: 'zip', name: 'ZIP', value: String(entry.zip) });
    }

    if (entry.email) {
        terms.push({ source: 'email', name: 'EMAIL', value: entry.email });
    }

    if (entry.phoneNumber) {
        terms.push({ source: 'phoneNumber', name: 'PHONE', value: entry.phoneNumber });
    }

    if (entry.race) {
        terms.push({ source: 'race', name: 'RACE', value: entry.race.name });
    }

    if (entry.ethnicity) {
        terms.push({ source: 'ethnicity', name: 'ETHNICITY', value: entry.ethnicity.name });
    }

    if (entry.identification && entry.identificationType) {
        terms.push({ source: 'identificationType', name: 'ID TYPE', value: entry.identificationType.name });
        terms.push({ source: 'identification', name: 'ID', value: entry.identification });
    }

    return terms;
};

export { patientTermsResolver };
