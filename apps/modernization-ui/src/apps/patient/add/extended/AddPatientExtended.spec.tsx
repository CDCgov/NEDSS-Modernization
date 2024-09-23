import { getByRole, render } from '@testing-library/react';
import { AddPatientExtended } from './AddPatientExtended';
import { MemoryRouter } from 'react-router-dom';
import { CodedValue } from 'coded';
import { MockedProvider } from '@apollo/react-testing';
import { CountiesCodedValues } from 'location';
import { PatientSexBirthCodedValue } from 'apps/patient/profile/sexBirth/usePatientSexBirthCodedValues';

const mockSexBirthCodedValues: PatientSexBirthCodedValue = {
    genders: [
        { name: 'Male', value: 'M' },
        { name: 'Female', value: 'F' },
        { name: 'Unknown', value: 'U' }
    ],
    preferredGenders: [{ name: 'FTM', value: 'FTM' }],
    genderUnknownReasons: [{ name: 'Did not ask', value: 'DNA' }],
    multipleBirth: [{ name: 'Yes', value: 'Y' }],
    states: {
        all: [{ name: 'Alabama', value: 'AL', abbreviation: 'AL' }],
        byValue: jest.fn(),
        byAbbreviation: jest.fn()
    },
    counties: {
        byState: jest.fn()
    },
    countries: [{ name: 'United States of America', value: 'US' }]
};

jest.mock('apps/patient/profile/sexBirth/usePatientSexBirthCodedValues', () => ({
    usePatientSexBirthCodedValues: () => mockSexBirthCodedValues
}));

const mockCountyCodedValues: CountiesCodedValues = { counties: [{ name: 'CountyA', value: 'A', group: 'G' }] };
jest.mock('location/useCountyCodedValues', () => ({
    useCountyCodedValues: () => mockCountyCodedValues
}));
const mockPatientAddressCodedValues = {
    types: [{ name: 'House', value: 'H' }],
    uses: [{ name: 'Home', value: 'HM' }]
};

class MockIntersectionObserver {
    observe = jest.fn();
    unobserve = jest.fn();
    disconnect = jest.fn();
}

Object.defineProperty(window, 'IntersectionObserver', {
    writable: true,
    configurable: true,
    value: MockIntersectionObserver
});

// Mock IntersectionObserverEntry
Object.defineProperty(window, 'IntersectionObserverEntry', {
    writable: true,
    configurable: true,
    value: jest.fn()
});

jest.mock('apps/patient/profile/addresses/usePatientAddressCodedValues', () => ({
    usePatientAddressCodedValues: () => mockPatientAddressCodedValues
}));

const mockLocationCodedValues = {
    states: {
        all: [{ name: 'StateName', value: '1' }]
    },
    counties: {
        byState: (state: string) => [{ name: 'CountyName', value: '2' }]
    },
    countries: [{ name: 'CountryName', value: '3' }]
};

jest.mock('location/useLocationCodedValues', () => ({
    useLocationCodedValues: () => mockLocationCodedValues
}));
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
        query: jest.fn().mockResolvedValue({ data: { patient: { id: '123', name: 'John Doe' } } })
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
        const { getAllByRole } = render(
            <MockedProvider mocks={[]} addTypename={false}>
                <MemoryRouter>
                    <AddPatientExtended />
                </MemoryRouter>
            </MockedProvider>
        );

        const buttons = getAllByRole('button');
        expect(buttons[0]).toHaveTextContent('Cancel');
        expect(buttons[1]).toHaveTextContent('Save');
    });
});
