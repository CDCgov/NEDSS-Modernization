import { NewPatientEntry } from 'apps/patient/add';
import { PatientCriteriaEntry, Identification, RaceEthnicity, Contact } from '../criteria';
import { internalizeDate } from 'date';
import { asValue } from 'options';
import { orNull } from 'utils';
import { IdentificationEntry, EmailEntry } from 'apps/patient/add';

const resolveIdentification = (entry: Identification): IdentificationEntry[] =>
    entry.identification && entry.identificationType
        ? [
              {
                  value: entry.identification,
                  type: entry.identificationType.value,
                  authority: null
              }
          ]
        : [];

const resolveRace = (entry: RaceEthnicity): string[] => (entry.race ? [asValue(entry.race)] : []);

const resolveEmail = (entry: Contact): EmailEntry[] => [{ email: entry.email || '' }];

const asNewPatientEntry = (criteria: Partial<PatientCriteriaEntry>): NewPatientEntry => {
    return {
        asOf: internalizeDate(new Date()),
        firstName: orNull(criteria.firstName),
        lastName: orNull(criteria.lastName),
        dateOfBirth: internalizeDate(criteria.dateOfBirth),
        currentGender: orNull(asValue(criteria.gender)),
        streetAddress1: orNull(criteria.address),
        city: orNull(criteria.city),
        state: orNull(asValue(criteria.state)),
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
