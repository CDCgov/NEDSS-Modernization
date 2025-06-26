import React from 'react';
import { render, screen } from '@testing-library/react';
import SystemManagementPage from './SystemManagementPage';
import userEvent from "@testing-library/user-event";

describe('SystemManagementPage', () => {
    it('renders heading and search bar', () => {
        render(<SystemManagementPage />);

        const heading = screen.getByRole('heading', { name: /system management/i });
        expect(heading).toBeInTheDocument();

        const searchInput = screen.getByRole('textbox', { name: /search/i });
        expect(searchInput).toBeInTheDocument();
        expect(searchInput).toHaveAttribute('placeholder', 'Filter by keyword');
    });

    it('filters sections based on user input', async () => {
        render(<SystemManagementPage />);
        const user = userEvent.setup();
        const input = screen.getByPlaceholderText(/filter by keyword/i);

        await user.type(input, 'page');

        expect(screen.queryByText(/Manage local results/i)).not.toBeInTheDocument();
        expect(screen.queryByText(/Manage alerts/i)).not.toBeInTheDocument();
        expect(screen.getByText(/Manage pages/i)).toBeInTheDocument();
    });
});
