import React from 'react';
import { render, within } from '@testing-library/react';
import { PreviewPhoneAndEmail } from './PreviewPhoneAndEmail';
import { MergeCandidate } from '../../../../../api/model/MergeCandidate';
import { PhoneEmailId } from '../../../merge-review/model/PatientMergeForm';
import { MemoryRouter } from 'react-router';

const Fixture = () => {
    return (
        <MemoryRouter>
            <PreviewPhoneAndEmail selectedPhoneEmails={selectedPhoneEmails} mergeCandidates={mockMergeCandidates} />
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
        phoneEmails: [
            {
                id: '1',
                asOf: '2022-01-05',
                type: 'Home',
                use: 'Home',
                countryCode: '1',
                phoneNumber: '672727252',
                extension: '',
                email: 'harry.potter@hogwarts.uk',
                url: '',
                comments: ''
            },
            {
                id: '2',
                asOf: '2023-01-04',
                type: 'Home',
                use: 'Home',
                countryCode: '1',
                phoneNumber: '12345',
                extension: '',
                email: 'harry.potter@hogwarts.uk',
                url: '',
                comments: ''
            }
        ],
        identifications: [],
        races: [],
        ethnicity: {},
        sexAndBirth: {},
        mortality: {},
        general: {},
        investigations: []
    }
];

const selectedPhoneEmails: PhoneEmailId[] = [{ locatorId: '1' }, { locatorId: '2' }];

describe('PreviewPhoneAndEmail Component', () => {
    it('renders the phone and email table', () => {
        const { getByText } = render(<Fixture />);
        expect(getByText('672727252')).toBeInTheDocument();
    });

    it('renders the phone and emails in the correct order', () => {
        const { getAllByRole } = render(<Fixture />);

        const rows = getAllByRole('row');

        const firstRowCells = within(rows[1]).getAllByRole('cell');
        expect(firstRowCells[0]).toHaveTextContent('01/04/2023');

        const secondRowCells = within(rows[2]).getAllByRole('cell');
        expect(secondRowCells[0]).toHaveTextContent('01/05/2022');
    });
});
