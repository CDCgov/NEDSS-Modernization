import React from 'react';
import { render, within } from '@testing-library/react';
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
                asOf: '2025-01-01T00:00',
                type: 'Legal',
                prefix: '',
                last: 'Potter',
                secondLast: '',
                first: 'Harry',
                middle: '',
                secondMiddle: '',
                suffix: '',
                degree: ''
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
        investigations: []
    },
    {
        personUid: '2',
        personLocalId: 'ABC123',
        addTime: '2023-01-01T00:00:00Z',
        adminComments: { date: '2023-01-01', comment: 'Test comments' },
        names: [
            {
                personUid: '2',
                sequence: '1',
                asOf: '2025-03-10T00:00',
                type: 'Legal',
                prefix: '',
                last: 'Hagrid',
                secondLast: '',
                first: 'Rubeus',
                middle: '',
                secondMiddle: '',
                suffix: '',
                degree: ''
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
        investigations: []
    }
];

const selectedNames: NameId[] = [
    { personUid: '1', sequence: '1' },
    { personUid: '2', sequence: '1' }
];

describe('PreviewName Component', () => {
    it('renders the name table', () => {
        const { getByText } = render(<Fixture />);
        expect(getByText('Harry')).toBeInTheDocument();
    });

    it('renders the names in the proper order', () => {
        const { getAllByRole } = render(<Fixture />);

        const rows = getAllByRole('row');

        const firstRowCells = within(rows[1]).getAllByRole('cell');
        expect(firstRowCells[0]).toHaveTextContent('03/10/2025');

        const secondRowCells = within(rows[2]).getAllByRole('cell');
        expect(secondRowCells[0]).toHaveTextContent('01/01/2025');
    });
});
