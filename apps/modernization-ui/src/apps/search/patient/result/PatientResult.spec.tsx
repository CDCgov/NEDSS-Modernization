import React from 'react';
import { render, fireEvent } from '@testing-library/react';
import { PatientResult } from './PatientResult';

const mockPatientSearchResult = {
    addresses: [
        {
            address: '123 Fake St',
            address2: null,
            city: 'Faketown',
            state: 'FS',
            use: 'HOME',
            zipcode: '12345'
        }
    ],
    age: 31,
    birthday: '1990-01-01',
    emails: ['john.doe@example.com'],
    gender: 'Male',
    identification: [
        {
            type: 'SSN',
            value: '123-45-6789'
        }
    ],
    legalName: {
        first: 'John',
        last: 'Doe',
        middle: null,
        suffix: null
    },
    names: [],
    patient: 12345678,
    phones: ['555-555-5555'],
    shortId: 12345,
    status: 'ACTIVE'
};

describe('PatientResult', () => {
    it('renders correctly with provided data', () => {
        const { getByText } = render(<PatientResult result={mockPatientSearchResult} onSelected={() => {}} />);
        expect(getByText('LEGAL NAME')).toBeInTheDocument();
        expect(getByText('John Doe')).toBeInTheDocument();
        expect(getByText('Date of birth')).toBeInTheDocument();
        expect(getByText('01/01/1990 (31)')).toBeInTheDocument();
        expect(getByText('Sex')).toBeInTheDocument();
        expect(getByText('Male')).toBeInTheDocument();
        expect(getByText('Patient Id')).toBeInTheDocument();
        expect(getByText('12345')).toBeInTheDocument();
        expect(getByText('PHONE NUMBER')).toBeInTheDocument();
        expect(getByText('555-555-5555')).toBeInTheDocument();
        expect(getByText('EMAIL')).toBeInTheDocument();
        expect(getByText('john.doe@example.com')).toBeInTheDocument();
        expect(getByText('SSN')).toBeInTheDocument();
        expect(getByText('123-45-6789')).toBeInTheDocument();
    });

    it('calls the onSelected callback when the legal name is clicked', () => {
        const { getByText } = render(<PatientResult result={mockPatientSearchResult} onSelected={() => {}} />);

        const legalNameElement = getByText(
            `${mockPatientSearchResult.legalName.first} ${mockPatientSearchResult.legalName.last}`
        );
        fireEvent.click(legalNameElement);
    });
});
