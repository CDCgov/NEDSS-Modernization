import { Term, fromSelectable, fromValue } from 'apps/search/terms';
import { PatientCriteriaEntry } from './criteria';
import { asTextCriteriaValue, TextCriteria } from 'options/operator';
import { fromDateBetweenCriteria, fromDateEqualsCriteria } from '../terms/fromDateCriteria';

const patientTermsResolver = (entry: PatientCriteriaEntry): Term[] => {
    const terms: Term[] = [];

    const pushCriteria = (source: string, title: string, value?: TextCriteria) => {
        // note: we will eventually want to use asTextCriteria here and get the operator i.e. "contains"
        // to populate the operator field in the term
        const stringValue = asTextCriteriaValue(value);
        if (stringValue) {
            terms.push(fromValue(source, title)(stringValue));
        }
    };

    if (entry.lastName) {
        pushCriteria('lastName', 'LAST NAME', entry.lastName);
    }

    if (entry.firstName) {
        pushCriteria('firstName', 'FIRST NAME', entry.firstName);
    }

    if (entry.bornOn) {
        if ('equals' in entry.bornOn) {
            terms.push(fromDateEqualsCriteria('dateOfBirth', 'DOB Equal')(entry.bornOn));
        } else if ('between' in entry.bornOn) {
            terms.push(fromDateBetweenCriteria('dateOfBirth', 'DOB Between')(entry.bornOn));
        }
    }

    if (entry.gender) {
        terms.push(fromSelectable('gender', 'SEX')(entry.gender));
    }

    if (entry.id) {
        terms.push(fromValue('id', 'PATIENT ID')(entry.id));
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
        terms.push(fromValue('zip', 'ZIP CODE')(String(entry.zip)));
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
