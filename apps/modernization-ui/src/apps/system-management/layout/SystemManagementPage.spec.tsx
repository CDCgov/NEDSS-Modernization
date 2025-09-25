import { render, screen } from '@testing-library/react';
import SystemManagementPage from './SystemManagementPage';
import { MemoryRouter } from 'react-router';
import { vi } from 'vitest';

const mockPermissions = ['LDFADMINISTRATION-SYSTEM', 'SRTADMIN-SYSTEM', 'ALERTADMIN-SYSTEM'];
const mockAllows = (permission: string) => mockPermissions.includes(permission);
const mockAllowFn = jest.fn(mockAllows);

vi.mock('libs/permission/usePermissions', () => ({
    usePermissions: () => ({ permissions: mockPermissions, allows: mockAllowFn })
}));

vi.mock('./VisibleWrapper', () => {
    const React = require('react');
    const { useEffect } = require('react');

    return {
        __esModule: true,
        default: ({ children, onVisibilityChange }: any) => {
            useEffect(() => {
                onVisibilityChange(true);
            }, []);
            return <div>{children}</div>;
        }
    };
});

describe('SystemManagementPage', () => {
    beforeEach(() => {
        mockAllowFn.mockImplementation(mockAllows);
        vi.resetModules();
    });

    it('renders heading and search bar', () => {
        render(
            <MemoryRouter>
                <SystemManagementPage />
            </MemoryRouter>
        );

        expect(screen.getByRole('heading', { name: /system management/i })).toBeInTheDocument();
        const input = screen.getByRole('textbox', { name: /search/i });
        expect(input).toBeInTheDocument();
        expect(input).toHaveAttribute('placeholder', 'Filter by keyword');
    });

    it('renders only visible cards', () => {
        render(
            <MemoryRouter>
                <SystemManagementPage />
            </MemoryRouter>
        );

        expect(screen.getByText(/Manage pages/i)).toBeInTheDocument();
        expect(screen.queryByText(/deduplication/i)).not.toBeInTheDocument();
    });

    it('respects permissions and only renders allowed sections', () => {
        render(
            <MemoryRouter>
                <SystemManagementPage />
            </MemoryRouter>
        );

        // Page, Case Report and Decision Support section is allowed by permissions
        expect(screen.queryByText(/Manage Pages/i)).toBeInTheDocument();
        expect(screen.queryByText(/Manage laboratories/i)).toBeInTheDocument();
        expect(screen.queryByText(/Manage Alerts/i)).toBeInTheDocument();

        // Security and Person Match section shouldn't render
        expect(screen.queryByText(/Security/i)).not.toBeInTheDocument();
        expect(screen.queryByText(/Manage data elements/i)).not.toBeInTheDocument();
    });
});
