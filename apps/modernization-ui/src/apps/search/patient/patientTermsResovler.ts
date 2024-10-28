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
        terms.push(fromValue('dateOfBirth', 'Dob')(entry.dateOfBirth));
    }

    if (entry.gender) {
        terms.push(fromSelectable('gender', 'Sex')(entry.gender));
    }

    if (entry.id) {
        terms.push(fromValue('id', 'Patient Id')(entry.id));
    }

    if (entry.address) {
        terms.push(fromValue('address', 'Street address')(entry.address));
    }

    if (entry.city) {
        terms.push(fromValue('city', 'City')(entry.city));
    }

    if (entry.state) {
        terms.push(fromSelectable('state', 'State')(entry.state));
    }

    if (entry.zip) {
        terms.push(fromValue('zip', 'Zip code')(String(entry.zip)));
    }

    if (entry.email) {
        terms.push(fromValue('email', 'Email')(entry.email));
    }

    if (entry.phoneNumber) {
        terms.push(fromValue('phoneNumber', 'Phone')(entry.phoneNumber));
    }

    if (entry.race) {
        terms.push(fromSelectable('race', 'Race')(entry.race));
    }

    if (entry.ethnicity) {
        terms.push(fromSelectable('ethnicity', 'Ethnicity')(entry.ethnicity));
    }

    if (entry.identification && entry.identificationType) {
        terms.push(fromSelectable('identificationType', 'Id Type')(entry.identificationType));
        terms.push(fromValue('identification', 'Id')(entry.identification));
    }

    return terms;
};

export { patientTermsResolver };
