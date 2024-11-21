import { Term, fromDateBetweenCriteria, fromDateEqualsCriteria, fromSelectable, fromValue } from 'apps/search/terms';
import { PatientCriteriaEntry } from './criteria';
import { asTextCriteriaValue, TextCriteria } from 'options/operator';

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

    if (entry.name?.last) {
        pushCriteria('lastName', 'LAST NAME', entry.name.last);
    }

    if (entry.name?.first) {
        pushCriteria('firstName', 'FIRST NAME', entry.name.first);
    }

    if (entry.dateOfBirth) {
        terms.push(fromValue('dateOfBirth', 'DOB')(entry.dateOfBirth));
    }

    if (entry.bornOn) {
        if ('equals' in entry.bornOn && Object.keys(entry.bornOn.equals).length > 0) {
            terms.push(fromDateEqualsCriteria('bornOn', 'DOB Equal')(entry.bornOn));
        } else if ('between' in entry.bornOn && Object.keys(entry.bornOn.between).length > 0) {
            terms.push(fromDateBetweenCriteria('bornOn', 'DOB Between')(entry.bornOn));
        }
    }

    if (entry.gender) {
        terms.push(fromSelectable('gender', 'SEX')(entry.gender));
    }

    if (entry.id) {
        terms.push(fromValue('id', 'PATIENT ID')(entry.id));
    }

    if (entry.location?.street) {
        pushCriteria('address', 'STREET ADDRESS', entry.location?.street);
    }

    if (entry.location?.city) {
        pushCriteria('city', 'CITY', entry.location?.city);
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

    if (entry.abcCase) {
        terms.push(fromValue('abcCase', 'ABCS CASE ID')(entry.abcCase));
    }

    if (entry.accessionNumber) {
        terms.push(fromValue('accessionNumber', 'ACCESSION NUMBER ID')(entry.accessionNumber));
    }

    if (entry.morbidity) {
        terms.push(fromValue('morbidity', 'MORBIDITY REPORT ID')(entry.morbidity));
    }

    if (entry.cityCountyCase) {
        terms.push(fromValue('cityCountyCase', 'CITY/COUNTY CASE ID')(entry.cityCountyCase));
    }

    if (entry.document) {
        terms.push(fromValue('document', 'DOCUMENT ID')(entry.document));
    }

    if (entry.investigation) {
        terms.push(fromValue('investigation', 'INVESTIGATION ID')(entry.investigation));
    }

    if (entry.labReport) {
        terms.push(fromValue('labReport', 'LAB ID')(entry.labReport));
    }

    if (entry.notification) {
        terms.push(fromValue('notification', 'NOTIFICATION ID')(entry.notification));
    }

    if (entry.stateCase) {
        terms.push(fromValue('stateCase', 'STATE CASE ID')(entry.stateCase));
    }

    if (entry.treatment) {
        terms.push(fromValue('treatment', 'TREATMENT ID')(entry.treatment));
    }

    if (entry.vaccination) {
        terms.push(fromValue('vaccination', 'VACCINATION ID')(entry.vaccination));
    }

    return terms;
};

export { patientTermsResolver };
