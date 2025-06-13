import React from 'react';
import { render } from '@testing-library/react';
import { PreviewEthnicity } from './PreviewEthnicity';
import { MergeCandidate } from '../../../../../api/model/MergeCandidate';
import { PatientMergeForm } from '../../../merge-review/model/PatientMergeForm';
import { MemoryRouter } from 'react-router';

const Fixture = () => {
    return (
        <MemoryRouter>
            <PreviewEthnicity mergeFormData={mergeFormData} mergeCandidates={mockMergeCandidates} />
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
        identifications: [],
        races: [],
        ethnicity: {
            asof: '2023-01-02',
            asOf: '2023-01-02',
            ethnicity: 'White',
            reasonUnknown: '',
            spanishOrigin: 'N/A'
        },
        sexAndBirth: {},
        mortality: {},
        general: {},
        investigations: [],
    }
];

const mergeFormData: PatientMergeForm = {
    survivingRecord: '123',
    adminComments: '123',
    ethnicity: '1',
    names: [
        {
            personUid: '123',
            sequence: '1',
            asOf: '',
            type: '',
            first: 'John',
            middle: 'Q',
            last: 'Public',
            suffix: 'Jr.',
        },
    ],
    sexAndBirth: {
        currentSex: '123',
        dateOfBirth: '123',
    },
} as any;

describe('PreviewEthnicity Component', () => {
    it('renders the ethnicity table', () => {
        const { getByText } = render(<Fixture />);
        expect(getByText('White')).toBeInTheDocument();
    });
});