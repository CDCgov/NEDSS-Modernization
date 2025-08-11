import {
    PatientFileContact as PatientFileContactResponse,
    PatientFileContacts as PatientFileContactsResponse
} from 'generated';

import { maybeDate } from 'date';
import { PatientFileContact, PatientFileContacts } from './contacts';

const transformer = (response: PatientFileContactsResponse): PatientFileContacts => ({
    ...response,
    contacts: response.contacts.map(transformContact, [])
});

const transformContact = (response: PatientFileContactResponse): PatientFileContact => ({
    ...response,
    createdOn: maybeDate(response.createdOn),
    namedOn: maybeDate(response.namedOn)
});

export { transformer };
