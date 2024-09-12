import { render, screen } from '@testing-library/react';
import { AddPatientExtended } from './AddPatientExtended';
import { MemoryRouter } from 'react-router-dom';
import { CodedValue } from 'coded';
import { MockedProvider } from '@apollo/client/testing';

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

jest.mock('@apollo/client', () => ({
    ...jest.requireActual('@apollo/client'),
    useApolloClient: () => ({
        readQuery: jest.fn(),
        writeQuery: jest.fn(),
        cache: {
            modify: jest.fn(),
            evict: jest.fn()
        },
        mutate: jest.fn().mockResolvedValue({ data: { addPatient: { id: '123' } } }),
        query: jest.fn().mockResolvedValue({ data: { patient: { id: '123', name: 'John Doe' } } }),
    })
}));

describe('AddPatientExtended', () => {
    it('should have a heading', () => {
        const { getAllByRole } = render(
            <MockedProvider mocks={[]} addTypename={false}>
                <MemoryRouter>
                    <AddPatientExtended />
                </MemoryRouter>
            </MockedProvider>
        );

        const headings = getAllByRole('heading');
        expect(headings[1]).toHaveTextContent('New patient - extended');
    });

    it('should have cancel and save buttons', () => {
        render(
            <MockedProvider mocks={[]} addTypename={false}>
                <MemoryRouter>
                    <AddPatientExtended />
                </MemoryRouter>
            </MockedProvider>
        );

        const buttons = screen.getAllByRole('button');
        expect(buttons[0]).toHaveTextContent('Cancel');
        expect(buttons[1]).toHaveTextContent('Save');
    });
});
