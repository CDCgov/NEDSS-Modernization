import { PatientFileContact, PatientFileContacts } from 'generated';
import { PatientFileContacts as Contacts } from './contactsNamed';
import { PatientFileContact as Contact } from './contactsNamed';
import { maybeDate } from 'date';

const transformer = (response: PatientFileContacts): Contacts => ({
    ...response,
    contacts: response.contacts.map(transformContact, [])
});

const transformContact = (response: PatientFileContact): Contact => ({
    ...response,
    createdOn: maybeDate(response.createdOn),
    namedOn: maybeDate(response.namedOn)
});

export { transformer };
