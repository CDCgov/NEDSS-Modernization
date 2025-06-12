import React from 'react';
import { render } from '@testing-library/react';
import { PreviewRace } from './PreviewRace';
import { MergeCandidate } from '../../../../../api/model/MergeCandidate';
import { RaceId } from '../../../merge-review/model/PatientMergeForm';
import { MemoryRouter } from 'react-router';

const Fixture = () => {
    return (
        <MemoryRouter>
            <PreviewRace selectedRaces={selectedRaces} mergeCandidates={mockMergeCandidates} />
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
        races: [
            {
                personUid: '1',
                raceCode: '123',
                asOf: '2023-01-01',
                race: 'RandomRace',
                detailedRaces: ''
            }
        ],
        ethnicity: {},
        sexAndBirth: {},
        mortality: {},
        general: {},
        investigations: [],
    }
];

const selectedRaces: RaceId[] = [
    { personUid: '1', raceCode: '123' },
];

describe('PreviewRace Component', () => {
    it('renders the race table', () => {
        const { getByText } = render(<Fixture />);
        expect(getByText('RandomRace')).toBeInTheDocument();
    });
});