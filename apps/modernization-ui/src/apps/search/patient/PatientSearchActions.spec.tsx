import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router';
import { axe } from 'jest-axe';
import { PatientSearchActions } from './PatientSearchActions';

jest.mock('./add/useAddPatientFromSearch', () => ({
    useAddPatientFromSearch: () => ({
        add: jest.fn()
    })
}));

jest.mock('libs/permission', () => ({
    Permitted: ({ children }: { children: React.ReactNode }) => children,
    permissions: {
        patient: {
            add: 'patient.add'
        }
    }
}));

describe('PatientSearchActions Accessibility', () => {
    it('should not have any accessibility violations', async () => {
        const { container } = render(
            <MemoryRouter>
                <PatientSearchActions disabled={false} />
            </MemoryRouter>
        );
        expect(await axe(container)).toHaveNoViolations();
    });

    it('should have aria-keyshortcuts attribute set to Alt+A', () => {
        const { getByRole } = render(
            <MemoryRouter>
                <PatientSearchActions disabled={false} />
            </MemoryRouter>
        );
        const button = getByRole('button');
        expect(button).toHaveAttribute('aria-keyshortcuts', 'Alt+A');
    });
}); 