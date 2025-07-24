import { vi } from 'vitest';
import { render } from '@testing-library/react';
import { AddPatientExtended } from './AddPatientExtended';
import { createMemoryRouter, Navigate, RouterProvider, useNavigate } from 'react-router';
import { CountiesCodedValues } from 'location';
import { useShowCancelModal } from '../cancelAddPatientPanel';
import { PatientDataEntryMethodProvider } from '../usePatientDataEntryMethod';
import { Selectable } from 'options';
import { SkipLinkProvider } from 'SkipLink/SkipLinkContext';
import { PageProvider } from 'page';

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

vi.mock('options/concepts', () => ({
    useConceptOptions: () => ({ options: [] })
}));

const mockCountyCodedValues: CountiesCodedValues = { counties: [{ name: 'CountyA', value: 'A', group: 'G' }] };
vi.mock('location/useCountyCodedValues', () => ({
    useCountyCodedValues: () => mockCountyCodedValues
}));

vi.mock('react-router', async () => {
    const actual = await vi.importActual<any>('react-router');
    return {
        ...actual,
        useNavigate: jest.fn()
    };
});

const mockLocationCodedValues = {
    states: {
        all: [{ name: 'StateName', value: '1' }]
    },
    counties: {
        byState: () => [{ name: 'CountyName', value: '2' }]
    },
    countries: [{ name: 'CountryName', value: '3' }]
};

vi.mock('location/useLocationCodedValues', () => ({
    useLocationCodedValues: () => mockLocationCodedValues
}));

const mockRaceCategories: Selectable[] = [{ value: '1', name: 'race name' }];

const mockDetailedRaces: Selectable[] = [
    { value: '2', name: 'detailed race1' },
    { value: '3', name: 'detailed race2' }
];

vi.mock('options/race', () => ({
    useRaceCategoryOptions: () => mockRaceCategories,
    useDetailedRaceOptions: () => mockDetailedRaces
}));

vi.mock('apps/patient/data/identification/useIdentificationCodedValues', () => ({
    useIdentificationCodedValues: () => ({
        types: [{ value: 'type-value', name: 'type-name' }],
        authorities: [{ value: 'authority-value', name: 'authority-name' }]
    })
}));

const mockNameCodedValues = {
    types: [{ name: 'Adopted name', value: 'AN' }],
    prefixes: [{ name: 'Miss', value: 'MS' }],
    suffixes: [{ name: 'Sr.', value: 'SR' }],
    degrees: [{ name: 'BA', value: 'BA' }]
};

vi.mock('apps/patient/data/name/useNameCodedValues', () => ({
    useNameCodedValues: () => mockNameCodedValues
}));

const mockPatientPhoneCodedValues = {
    types: [{ name: 'Phone', value: 'PH' }],
    uses: [{ name: 'Home', value: 'H' }]
};

vi.mock('apps/patient/data/phoneEmail/usePhoneCodedValues', () => ({
    usePhoneCodedValues: () => mockPatientPhoneCodedValues
}));

vi.mock('../cancelAddPatientPanel/useShowCancelModal', () => ({
    useShowCancelModal: jest.fn()
}));

const renderWithRouter = () => {
    const routes = [
        {
            path: '/',
            element: (
                <PageProvider>
                    <PatientDataEntryMethodProvider>
                    <SkipLinkProvider>
                        <AddPatientExtended />
                    </SkipLinkProvider>
                </PatientDataEntryMethodProvider>
                </PageProvider>
            )
        },
        {
            path: '/add-patient',
            element: <Navigate to={'/'} />
        }
    ];

    const router = createMemoryRouter(routes, { initialEntries: ['/'] });
    return render(<RouterProvider router={router} />);
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
        const { getByRole } = renderWithRouter();

        const cancelButton = getByRole('button', { name: 'Cancel' });
        const saveButton = getByRole('button', { name: 'Save' });

        expect(cancelButton).toBeInTheDocument();
        expect(saveButton).toBeInTheDocument();
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
