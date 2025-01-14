import { today } from 'date';
import { asValue } from 'options';
import { orNull } from 'utils';
import { resolveDate } from 'design-system/date/entry';
import { TextCriteria, asTextCriteriaValue } from 'options/operator';
import { PatientCriteriaEntry, Identification, RaceEthnicity, Contact } from 'apps/search/patient/criteria';
import { IdentificationEntry, EmailEntry, NewPatientEntry } from 'apps/patient/add';

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
        asOf: today(),
        firstName: resolveCriteria(criteria.name?.first),
        lastName: resolveCriteria(criteria.name?.last),
        dateOfBirth: resolveDate(criteria.bornOn),
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
