import React from 'react';
import { render, screen, waitFor } from '@testing-library/react';
import SystemManagementPage from './SystemManagementPage';
import userEvent from '@testing-library/user-event';
import { MemoryRouter } from 'react-router';

const mockPermissions = ['LDFADMINISTRATION-SYSTEM', 'DECISION_SUPPORT_ADMIN', 'REPORTADMIN'];
const mockAllows = (permission: string) => mockPermissions.includes(permission);
const mockAllowFn = jest.fn(mockAllows);

jest.mock('../../../libs/permission/usePermissions', () => ({
    usePermissions: () => ({ permissions: mockPermissions, allows: mockAllowFn }),
}));

describe('SystemManagementPage', () => {
    beforeEach(() => {
        mockAllowFn.mockImplementation(mockAllows);
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

    it('filters sections based on user input', async () => {
        render(
            <MemoryRouter>
                <SystemManagementPage />
            </MemoryRouter>
        );
        const user = userEvent.setup();
        await user.type(screen.getByPlaceholderText(/filter by keyword/i), 'page');

        await waitFor(() => {
            expect(screen.queryByText(/Manage local results/i)).not.toBeInTheDocument();
            expect(screen.queryByText(/Manage alerts/i)).not.toBeInTheDocument();

            expect(
                screen.getByText((content) => content.toLowerCase().includes('manage pages'))
            ).toBeInTheDocument();
        });
    });
});
