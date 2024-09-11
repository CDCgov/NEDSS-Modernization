import { getAllByRole, render } from '@testing-library/react';
import { AddPatientExtended } from './AddPatientExtended';
import { MemoryRouter } from 'react-router-dom';
import { CodedValue } from 'coded';

const mockPatientPhoneCodedValues = {
    types: [{ name: 'Phone', value: 'PH' }],
    uses: [{ name: 'Home', value: 'H' }]
};

jest.mock('apps/patient/profile/phoneEmail/usePatientPhoneCodedValues', () => ({
    usePatientPhoneCodedValues: () => mockPatientPhoneCodedValues
}));

const mockRaceCodedValues: CodedValue[] = [{ value: '1', name: 'race name' }];

jest.mock('coded/race/useRaceCodedValues', () => ({
    useRaceCodedValues: () => mockRaceCodedValues
}));

const mockDetailedOptions: CodedValue[] = [
    { value: '2', name: 'detailed race1' },
    { value: '3', name: 'detailed race2' }
];

jest.mock('coded/race/useDetailedRaceCodedValues', () => ({
    useDetailedRaceCodedValues: () => mockDetailedOptions
}));
describe('AddPatientExtended', () => {
    it('should have a heading', () => {
        const { getAllByRole } = render(
            <MemoryRouter>
                <AddPatientExtended />
            </MemoryRouter>
        );

        const headings = getAllByRole('heading');
        expect(headings[1]).toHaveTextContent('New patient - extended');
    });

    it('should have cancel and save buttons', () => {
        const { getAllByRole } = render(
            <MemoryRouter>
                <AddPatientExtended />
            </MemoryRouter>
        );

        const buttons = getAllByRole('button');
        expect(buttons[0]).toHaveTextContent('Cancel');
        expect(buttons[1]).toHaveTextContent('Save');
    });
});
