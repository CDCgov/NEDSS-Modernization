import React from 'react';
import { render } from '@testing-library/react';
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
                asOf: '2023-01-02',
                type: 'Home',
                use: 'Home',
                countryCode: '1',
                phoneNumber: '672727252',
                extension: '',
                email: 'harry.potter@hogwarts.uk',
                url: '',
                comments: '',
            }
        ],
        identifications: [],
        races: [],
        ethnicity: {},
        sexAndBirth: {},
        mortality: {},
        general: {},
        investigations: [],
    }
];

const selectedPhoneEmails: PhoneEmailId[] = [
    { locatorId: '1' },
];

describe('PreviewPhoneAndEmail Component', () => {
    it('renders the phone and email table', () => {
        const { getByText } = render(<Fixture />);
        expect(getByText('672727252')).toBeInTheDocument();
    });
});