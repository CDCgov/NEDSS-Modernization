import React from 'react';
import { render, screen } from '@testing-library/react';
import SystemManagementPage from './SystemManagementPage';

jest.mock('components/heading', () => ({
    Heading: ({ level, children }: { level: number; children: React.ReactNode }) => (
        <h1 data-testid="heading">{children}</h1>
    )
}));

jest.mock('design-system/search/SearchBar', () => ({
    SearchBar: ({ placeholder }: { placeholder?: string }) => (
        <input type="text" placeholder={placeholder} data-testid="search-bar" />
    )
}));

describe('SystemManagementPage', () => {
    it('renders heading and search bar', () => {
        render(<SystemManagementPage />);

        // Check for heading
        const heading = screen.getByTestId('heading');
        expect(heading).toHaveTextContent('System Management');

        // Check for SearchBar with placeholder
        const searchBar = screen.getByTestId('search-bar');
        expect(searchBar).toBeInTheDocument();
        expect(searchBar).toHaveAttribute('placeholder', 'Filter by keyword');
    });
});
