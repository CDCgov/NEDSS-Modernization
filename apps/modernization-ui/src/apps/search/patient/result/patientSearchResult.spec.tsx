import { render } from '@testing-library/react';
import { PatientSearchResult } from 'generated/graphql/schema';
import {
    displayPhones,
    displayEmails,
    displayAddresses,
    displayOtherNames,
    displayIdentifications
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
        identification: [
            {
                type: 'SSN',
                value: '123-45-6789'
            },
            {
                type: 'MRN',
                value: '123456'
            }
        ],
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
        emails: ['emily.reynolds@owensborohealth.org'],
        detailedPhones: [
            {
                use: 'phone-use-value',
                type: 'phone-type-value',
                number: '270-685-4067'
            }
        ]
    };

    it('should displayPhones returns correct string', () => {
        const { getByText } = render(<>{displayPhones(mockPatient)}</>);
        expect(getByText('phone-use-value')).toBeInTheDocument();
        expect(getByText('270-685-4067')).toBeInTheDocument();
    });

    it('should displayEmails returns correct string', () => {
        const result = displayEmails(mockPatient);
        expect(result).toBe('emily.reynolds@owensborohealth.org');
    });

    it('should render addresses correctly', () => {
        const { getByText, queryAllByText } = render(<>{displayAddresses(mockPatient)}</>);
        expect(getByText('Home')).toBeInTheDocument();
        expect(queryAllByText('2222 Test Valley Rd', { exact: false })).toHaveLength(2);
        expect(queryAllByText('3333 Test Valley Rd', { exact: false })).toHaveLength(1);
        expect(getByText('Business')).toBeInTheDocument();
        expect(getByText('Alternate')).toBeInTheDocument();
    });

    it('should render other names with header and content', () => {
        const { getByText } = render(<>{displayOtherNames(mockPatient)}</>);
        expect(getByText('Alias')).toBeInTheDocument();
        expect(getByText('TestnullTest, Johnny')).toBeInTheDocument();
    });

    it('should render identifications with header and content', () => {
        const { getByText } = render(displayIdentifications(mockPatient));
        expect(getByText('SSN')).toBeInTheDocument();
        expect(getByText('123-45-6789')).toBeInTheDocument();
        expect(getByText('MRN')).toBeInTheDocument();
        expect(getByText('123456')).toBeInTheDocument();
    });
});
