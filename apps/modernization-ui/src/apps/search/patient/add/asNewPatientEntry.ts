import { NewPatientEntry } from 'apps/patient/add';
import { PatientCriteriaEntry, Identification, RaceEthnicity, Contact } from '../criteria';
import { internalizeDate } from 'date';
import { asValue } from 'options';
import { orNull } from 'utils';
import { IdentificationEntry, EmailEntry } from 'apps/patient/add';
import { TextCriteria, asTextCriteriaValue } from 'options/operator';

const resolveIdentification = (entry: Identification): IdentificationEntry[] =>
    entry.identification && entry.identificationType
        ? [
              {
                  value: entry.identification,
                  type: entry.identificationType.value,
                  authority: null
              }
          ]
        : [{ type: null, authority: null, value: null }];

const resolveRace = (entry: RaceEthnicity): string[] => (entry.race ? [asValue(entry.race)] : []);

const resolveEmail = (entry: Contact): EmailEntry[] => [{ email: entry.email || '' }];

const resolveCriteria = (textCriteria?: TextCriteria): string | null => orNull(asTextCriteriaValue(textCriteria));

const asNewPatientEntry = (criteria: Partial<PatientCriteriaEntry>): NewPatientEntry => {
    return {
        asOf: internalizeDate(new Date()),
        firstName: resolveCriteria(criteria.name?.first),
        lastName: resolveCriteria(criteria.name?.last),
        dateOfBirth: criteria.dateOfBirth,
        currentGender: orNull(asValue(criteria.gender)),
        streetAddress1: resolveCriteria(criteria.location?.street),
        city: resolveCriteria(criteria.location?.city),
        state: criteria.state,
        zip: (criteria.zip && String(criteria.zip)) || null,
        ethnicity: orNull(asValue(criteria.ethnicity)),
        race: resolveRace(criteria),
        homePhone: orNull(criteria.phoneNumber),
        phoneNumbers: [],
        emailAddresses: resolveEmail(criteria),
        identification: resolveIdentification(criteria)
    };
};

export { asNewPatientEntry };
