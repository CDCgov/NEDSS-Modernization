import React from 'react';
import { render, screen } from '@testing-library/react';
import SystemManagementPage from './SystemManagementPage';

describe('SystemManagementPage', () => {
    it('renders heading and search bar', () => {
        render(<SystemManagementPage />);

        // Check for heading (e.g., h1 with the correct text)
        const heading = screen.getByRole('heading', { name: /system management/i });
        expect(heading).toBeInTheDocument();

        // Check for input (search bar) by label or placeholder
        const searchInput = screen.getByRole('textbox', { name: /search/i });
        expect(searchInput).toBeInTheDocument();
        expect(searchInput).toHaveAttribute('placeholder', 'Filter by keyword');
    });
});
