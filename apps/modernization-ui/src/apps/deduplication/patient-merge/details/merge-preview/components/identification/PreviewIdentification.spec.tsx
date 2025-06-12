import React from 'react';
import { render } from '@testing-library/react';
import { PreviewIdentification } from './PreviewIdentification';
import { MergeCandidate } from '../../../../../api/model/MergeCandidate';
import {AddressId, IdentificationId} from '../../../merge-review/model/PatientMergeForm';
import { MemoryRouter } from 'react-router';

const Fixture = () => {
    return (
        <MemoryRouter>
            <PreviewIdentification selectedIdentifications={selectedIdentifications} mergeCandidates={mockMergeCandidates} />
        </MemoryRouter>
    );
};
const mockMergeCandidates: MergeCandidate[] = [
    {
        personUid: '1',
        personLocalId: 'ABC123',
        addTime: '2023-01-01T00:00:00Z',
        adminComments: { date: '2023-01-01', comment: 'Test comments' },
        names: [],
        addresses: [],
        phoneEmails: [],
        identifications: [
            {
                personUid: '1',
                sequence: '1',
                asOf: '2023-01-02',
                type: 'SSN',
                assigningAuthority: 'CDW',
                value: '123-456-789',
            }
        ],
        races: [],
        ethnicity: {},
        sexAndBirth: {},
        mortality: {},
        general: {},
        investigations: [],
    }
];

const selectedIdentifications: IdentificationId[] = [
    { personUid: '1', sequence: '1' },
];

describe('PreviewIdentification Component', () => {
    it('renders the identification table', () => {
        const { getByText } = render(<Fixture />);
        expect(getByText('SSN')).toBeInTheDocument();
    });
});