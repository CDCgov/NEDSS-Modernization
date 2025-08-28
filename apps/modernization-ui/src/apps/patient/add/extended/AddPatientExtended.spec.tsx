import { render, screen } from '@testing-library/react';
import { AddPatientExtended } from './AddPatientExtended';
import { createMemoryRouter, Navigate, RouterProvider, useNavigate } from 'react-router';
import { useShowCancelModal } from '../cancelAddPatientPanel';
import { PatientDataEntryMethodProvider } from '../usePatientDataEntryMethod';
import { Selectable } from 'options';
import { SkipLinkProvider } from 'SkipLink/SkipLinkContext';
import { PageProvider } from 'page';

window.scrollTo = jest.fn();

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

jest.mock('react-router', () => ({
    ...jest.requireActual('react-router'),
    useNavigate: jest.fn()
}));

const mockLocationOptions = {
    states: [{ name: 'StateName', value: '1' }],
    counties: [{ name: 'CountyName', value: '2' }],
    countries: [{ name: 'CountryName', value: '3' }],
    state: jest.fn()
};

jest.mock('options/location', () => ({
    useLocationOptions: () => mockLocationOptions
}));

const mockRaceCategories: Selectable[] = [{ value: '1', name: 'race name' }];

const mockDetailedRaces: Selectable[] = [
    { value: '2', name: 'detailed race1' },
    { value: '3', name: 'detailed race2' }
];

jest.mock('options/race', () => ({
    useRaceCategoryOptions: () => mockRaceCategories,
    useDetailedRaceOptions: () => ({ options: mockDetailedRaces, load: jest.fn })
}));

jest.mock('../cancelAddPatientPanel/useShowCancelModal', () => ({
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
        renderWithRouter();

        const cancelButton = screen.getByRole('button', { name: 'Cancel' });
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
