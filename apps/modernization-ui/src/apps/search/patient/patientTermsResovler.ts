import { Term, fromSelectable, fromValue } from 'apps/search/terms';
import { PatientCriteriaEntry } from './criteria';

const patientTermsResolver = (entry: PatientCriteriaEntry): Term[] => {
    const terms: Term[] = [];

    if (entry.lastName) {
        terms.push(fromValue('lastName', 'Last name')(entry.lastName));
    }

    if (entry.firstName) {
        terms.push(fromValue('firstName', 'First name')(entry.firstName));
    }

    if (entry.dateOfBirth) {
        terms.push(fromValue('dateOfBirth', 'DOB')(entry.dateOfBirth));
    }

    if (entry.gender) {
        terms.push(fromSelectable('gender', 'SEX')(entry.gender));
    }

    if (entry.id) {
        terms.push(fromValue('id', 'Patient Id')(entry.id));
    }

    if (entry.address) {
        terms.push(fromValue('address', 'STREET ADDRESS')(entry.address));
    }

    if (entry.city) {
        terms.push(fromValue('city', 'CITY')(entry.city));
    }

    if (entry.state) {
        terms.push(fromSelectable('state', 'STATE')(entry.state));
    }

    if (entry.zip) {
        terms.push(fromValue('zip', 'ZIP code')(String(entry.zip)));
    }

    if (entry.email) {
        terms.push(fromValue('email', 'EMAIL')(entry.email));
    }

    if (entry.phoneNumber) {
        terms.push(fromValue('phoneNumber', 'PHONE')(entry.phoneNumber));
    }

    if (entry.race) {
        terms.push(fromSelectable('race', 'RACE')(entry.race));
    }

    if (entry.ethnicity) {
        terms.push(fromSelectable('ethnicity', 'ETHNICITY')(entry.ethnicity));
    }

    if (entry.identification && entry.identificationType) {
        terms.push(fromSelectable('identificationType', 'ID TYPE')(entry.identificationType));
        terms.push(fromValue('identification', 'ID')(entry.identification));
    }

    return terms;
};

export { patientTermsResolver };
