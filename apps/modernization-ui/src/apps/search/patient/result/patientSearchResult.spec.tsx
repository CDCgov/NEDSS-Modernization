import { render } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import { PatientSearchResult } from 'generated/graphql/schema';
import { displayProfileLink, displayPhones, displayEmails } from './patientSearchResult';

jest.mock('name', () => ({
    displayName: jest.fn(() => jest.fn((name) => `${name.last}, ${name.first}`))
}));
jest.mock('address/display', () => ({
    displayAddress: jest.fn((address) => `${address.street}, ${address.city}`)
}));

describe('patientSearchResult functions', () => {
    const mockPatient: PatientSearchResult = {
        patient: 10043290,
        birthday: '1974-04-04',
        age: 50,
        gender: 'Female',
        status: 'ACTIVE',
        shortId: 84001,
        legalName: {
            first: 'John',
            last: 'Doe'
        },
        names: [
            {
                first: 'jonny',
                last: 'TestnullTest'
            }
        ],
        identification: [],
        addresses: [
            {
                use: 'Home',
                address: '2222 Test Valley Rd',
                city: 'OWENSBORO',
                state: 'KY',
                zipcode: '42303'
            },
            {
                use: 'Home',
                address: '2222 Test Valley Rd',
                city: 'OWENSBORO',
                county: 'Appling County',
                state: 'KY',
                zipcode: '30309'
            },
            {
                use: 'Home',
                address: '3333 Test Valley Rd',
                city: 'OWENSBORO',
                state: 'KY',
                zipcode: '30309'
            }
        ],
        phones: ['270-685-4067'],
        emails: ['emily.reynolds@owensborohealth.org']
    };

    test('displayPhones returns correct string', () => {
        const result = displayPhones(mockPatient);
        expect(result).toBe('270-685-4067');
    });

    test('displayEmails returns correct string', () => {
        const result = displayEmails(mockPatient);
        expect(result).toBe('emily.reynolds@owensborohealth.org');
    });

    test('displayProfileLink renders correctly with shortId', () => {
        const { getByText } = render(
            <BrowserRouter>{displayProfileLink(mockPatient.shortId, 'John Doe')}</BrowserRouter>
        );
        const link = getByText('John Doe');
        expect(link).toHaveAttribute('href', '/patient-profile/84001/summary');
    });
});
