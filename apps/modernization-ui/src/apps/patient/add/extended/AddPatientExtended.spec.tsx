import { render } from '@testing-library/react';
import { AddPatientExtended } from './AddPatientExtended';
import { createMemoryRouter, MemoryRouter, Navigate, RouterProvider, useNavigate } from 'react-router-dom';
import { CodedValue } from 'coded';
import { MockedProvider } from '@apollo/react-testing';
import { CountiesCodedValues } from 'location';
import { PatientSexBirthCodedValue } from 'apps/patient/profile/sexBirth/usePatientSexBirthCodedValues';
import { PatientIdentificationCodedValues } from 'apps/patient/profile/identification/usePatientIdentificationCodedValues';
import { PatientEthnicityCodedValue } from 'apps/patient/profile/ethnicity';
import { PatientProfilePermission } from 'apps/patient/profile/permission';
import { PatientGeneralCodedValue } from 'apps/patient/profile/generalInfo';
import { useShowCancelModal } from '../cancelAddPatientPanel';
import { BasicExtendedTransitionProvider } from 'apps/patient/add/useBasicExtendedTransition';
import { Selectable } from 'options';

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

jest.mock('apps/patient/profile/addresses/usePatientAddressCodedValues', () => ({
    usePatientAddressCodedValues: () => mockPatientAddressCodedValues
}));

jest.mock('react-router-dom', () => ({
    ...jest.requireActual('react-router-dom'),
    useNavigate: jest.fn()
}));

const mockLocationCodedValues = {
    states: {
        all: [{ name: 'StateName', value: '1' }]
    },
    counties: {
        byState: () => [{ name: 'CountyName', value: '2' }]
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

const mockRaceCategories: Selectable[] = [{ value: '1', name: 'race name' }];

const mockDetailedRaces: Selectable[] = [
    { value: '2', name: 'detailed race1' },
    { value: '3', name: 'detailed race2' }
];

jest.mock('options/race', () => ({
    useRaceCategoryOptions: () => mockRaceCategories,
    useDetailedRaceOptions: () => mockDetailedRaces
}));

const mockPatientNameCodedValues = {
    types: [{ name: 'Adopted name', value: 'AN' }],
    prefixes: [{ name: 'Miss', value: 'MS' }],
    suffixes: [{ name: 'Sr.', value: 'SR' }],
    degrees: [{ name: 'BA', value: 'BA' }]
};
jest.mock('apps/patient/profile/names/usePatientNameCodedValues', () => ({
    usePatientNameCodedValues: () => mockPatientNameCodedValues
}));

const mockPatientIdentificationCodedValues: PatientIdentificationCodedValues = {
    types: [{ name: 'Account number', value: 'AN' }],
    authorities: [{ name: 'Assigning auth', value: 'AA' }]
};

jest.mock('apps/patient/profile/identification/usePatientIdentificationCodedValues', () => ({
    usePatientIdentificationCodedValues: () => mockPatientIdentificationCodedValues
}));

const mockEthnicityValues: PatientEthnicityCodedValue = {
    ethnicGroups: [
        { name: 'Hispanic or Latino', value: '2135-2' },
        { name: 'Unknown', value: 'UNK' }
    ],
    ethnicityUnknownReasons: [{ name: 'Not asked', value: '6' }],
    detailedEthnicities: [{ name: 'Central American', value: '2155-0' }]
};

jest.mock('apps/patient/profile/ethnicity/usePatientEthnicityCodedValues', () => ({
    usePatientEthnicityCodedValues: () => mockEthnicityValues
}));

const mockPermissions: PatientProfilePermission = { delete: true, compareInvestigation: false, hivAccess: false };

jest.mock('apps/patient/profile/permission/usePatientProfilePermissions', () => ({
    usePatientProfilePermissions: () => mockPermissions
}));

const mockPatientCodedValues: PatientGeneralCodedValue = {
    maritalStatuses: [{ name: 'Married', value: 'M' }],
    primaryOccupations: [{ name: 'Tester', value: 'T' }],
    educationLevels: [{ name: '1 or more years of college', value: '1' }],
    primaryLanguages: [{ name: 'Welsh', value: 'W' }],
    speaksEnglish: [{ name: 'Yes', value: 'Y' }]
};

jest.mock('apps/patient/profile/generalInfo/usePatientGeneralCodedValues', () => ({
    usePatientGeneralCodedValues: () => mockPatientCodedValues
}));

jest.mock('../cancelAddPatientPanel/useShowCancelModal', () => ({
    useShowCancelModal: jest.fn()
}));

const renderWithRouter = () => {
    const routes = [
        {
            path: '/',
            element: (
                <BasicExtendedTransitionProvider>
                    <AddPatientExtended />
                </BasicExtendedTransitionProvider>
            )
        },
        {
            path: '/add-patient',
            element: <Navigate to={'/'} />
        }
    ];

    const router = createMemoryRouter(routes, { initialEntries: ['/'] });
    return render(
        <MockedProvider mocks={[]} addTypename={false}>
            <RouterProvider router={router} />
        </MockedProvider>
    );
};

describe('AddPatientExtended', () => {
    beforeEach(() => {
        (useShowCancelModal as jest.Mock).mockReturnValue({ value: false });
        (useNavigate as jest.Mock).mockReturnValue(jest.fn());
    });

    it('should have a heading', () => {
        const { getAllByRole } = renderWithRouter();

        const headings = getAllByRole('heading');
        expect(headings[1]).toHaveTextContent('New patient - extended');
    });

    it('should have cancel and save buttons', () => {
        const { getAllByRole } = renderWithRouter();

        const buttons = getAllByRole('button');
        expect(buttons[0]).toHaveTextContent('Cancel');
        expect(buttons[1]).toHaveTextContent('Save');
    });

    it('should not be showing modal by default', () => {
        const { queryByRole } = renderWithRouter();

        const modal = queryByRole('dialog', { name: 'Warning' });
        expect(modal).not.toBeInTheDocument();
    });

    it('should redirect to add-patient when cancel is clicked', () => {
        const { getByText } = renderWithRouter();

        const cancelButton = getByText('Cancel');
        cancelButton.click();

        expect(useNavigate).toBeCalled();
    });

    it('should not show modal when local storage flag is set', () => {
        (useShowCancelModal as jest.Mock).mockReturnValue({ value: true });
        const { queryByRole } = renderWithRouter();

        const modal = queryByRole('dialog', { name: 'Warning' });
        expect(modal).not.toBeInTheDocument();
    });
});
