import React from 'react';
import { render } from '@testing-library/react';
import { PreviewName } from './PreviewName';
import { MergeCandidate } from '../../../../../api/model/MergeCandidate';
import { NameId } from '../../../merge-review/model/PatientMergeForm';
import { MemoryRouter } from 'react-router';

const Fixture = () => {
    return (
        <MemoryRouter>
            <PreviewName selectedNames={selectedNames} mergeCandidates={mockMergeCandidates} />
        </MemoryRouter>
    );
};
const mockMergeCandidates: MergeCandidate[] = [
    {
        personUid: '1',
        personLocalId: 'ABC123',
        addTime: '2023-01-01T00:00:00Z',
        adminComments: { date: '2023-01-01', comment: 'Test comments' },
        names: [
            {
                personUid: '1',
                sequence: '1',
                asOf: '2023-01-02',
                type: 'Legal',
                prefix: '',
                last: 'Potter',
                secondLast: '',
                first: 'Harry',
                middle: '',
                secondMiddle: '',
                suffix: '',
                degree: '',
            }
        ],
        addresses: [],
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

const selectedNames: NameId[] = [
    { personUid: '1', sequence: '1' },
];

describe('PreviewName Component', () => {
    it('renders the name table', () => {
        const { getByText } = render(<Fixture />);
        expect(getByText('Harry')).toBeInTheDocument();
    });
});