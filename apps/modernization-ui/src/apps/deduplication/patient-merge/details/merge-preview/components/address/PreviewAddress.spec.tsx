import React from 'react';
import { render } from '@testing-library/react';
import { PreviewAddress } from './PreviewAddress';
import { MergeCandidate } from '../../../../../api/model/MergeCandidate';
import { AddressId } from '../../../merge-review/model/PatientMergeForm';
import { MemoryRouter } from 'react-router';

const Fixture = () => {
    return (
        <MemoryRouter>
            <PreviewAddress selectedAddresses={selectedAddresses} mergeCandidates={mockMergeCandidates} />
        </MemoryRouter>
    );
};
const mockMergeCandidates: MergeCandidate[] = [
    {
        personUid: '1',
        personLocalId: 'ABC123',
        addTime: '2023-01-01T00:00:00Z',
        adminComments: { date: '2023-01-01', comment: 'Test' },
        names: [],
        addresses: [
            {
                id: 'addr-1',
                asOf: '2023-01-01',
                type: 'Home',
                use: 'Primary',
                address: '123 Main St',
                city: 'Anytown',
                state: 'CA',
                zipcode: '12345',
                county: 'Orange',
                country: 'USA',
            },
            {
                id: 'addr-2',
                asOf: '2023-01-02',
                type: 'Work',
                use: 'Secondary',
                address: undefined,
                city: undefined,
                state: undefined,
                zipcode: undefined,
                county: undefined,
                country: undefined,
            }
        ],
        phoneEmails: [],
        identifications: [],
        races: [],
        ethnicity: {},
        sexAndBirth: {},
        mortality: {},
        general: {},
        investigations: [],
    }
];

const selectedAddresses: AddressId[] = [
    { locatorId: 'addr-1' },
    { locatorId: 'addr-2' }
];

describe('PreviewAddress Component', () => {
    it('renders the address table', () => {
        const { getByText } = render(<Fixture />);
        expect(getByText('123 Main St')).toBeInTheDocument();
    });
});