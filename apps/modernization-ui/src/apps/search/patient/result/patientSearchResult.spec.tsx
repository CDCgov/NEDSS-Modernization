import { render } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import { PatientSearchResult } from 'generated/graphql/schema';
import {
    displayProfileLink,
    displayPhones,
    displayEmails,
    displayAddresses,
    displayOtherNames
} from './patientSearchResult';

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
                first: 'Johnny',
                last: 'TestnullTest',
                type: 'Alias'
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
                use: 'Business',
                address: '2222 Test Valley Rd',
                city: 'OWENSBORO',
                county: 'Appling County',
                state: 'KY',
                zipcode: '30309'
            },
            {
                use: 'Alternate',
                address: '3333 Test Valley Rd',
                city: 'OWENSBORO',
                state: 'KY',
                zipcode: '30309'
            }
        ],
        phones: ['270-685-4067'],
        emails: ['emily.reynolds@owensborohealth.org']
    };

    it('should displayPhones returns correct string', () => {
        const result = displayPhones(mockPatient);
        expect(result).toBe('270-685-4067');
    });

    it('should displayEmails returns correct string', () => {
        const result = displayEmails(mockPatient);
        expect(result).toBe('emily.reynolds@owensborohealth.org');
    });

    it('should displayProfileLink renders correctly with shortId', () => {
        const { getByText } = render(
            <BrowserRouter>{displayProfileLink(mockPatient.shortId, 'John Doe')}</BrowserRouter>
        );
        const link = getByText('John Doe');
        expect(link).toHaveAttribute('href', '/patient-profile/84001/summary');
    });

    it('should render addresses correctly', () => {
        const { getByText, queryAllByText } = render(displayAddresses(mockPatient));
        expect(getByText('Home')).toBeInTheDocument();
        expect(queryAllByText('2222 Test Valley Rd', { exact: false })).toHaveLength(2);
        expect(queryAllByText('3333 Test Valley Rd', { exact: false })).toHaveLength(1);
        expect(getByText('Business')).toBeInTheDocument();
        expect(getByText('Alternate')).toBeInTheDocument();
    });

    it('should render other names correctly', () => {
        const { getByText } = render(displayOtherNames(mockPatient));
        expect(getByText('Alias')).toBeInTheDocument();
        expect(getByText('Johnny TestnullTest')).toBeInTheDocument();
    });
});
