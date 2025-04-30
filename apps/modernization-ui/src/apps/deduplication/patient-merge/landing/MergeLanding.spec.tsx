import { render, within } from '@testing-library/react';
import { MergeLanding } from './MergeLanding';
import { MemoryRouter } from 'react-router';
import userEvent from "@testing-library/user-event";
import { useExportMatchesCSV } from '../../api/useExportMatchesCSV';
import { useSearchParams } from "react-router";

// Mocking the useExportMatchesCSV hook
jest.mock('../../api/useExportMatchesCSV', () => ({
    useExportMatchesCSV: jest.fn(),
}));

// Mocking the useSearchParams hook
jest.mock('react-router', () => ({
    ...jest.requireActual('react-router'),
    useSearchParams: jest.fn(),
}));

const mockExportMatchesCSV = jest.fn();

beforeEach(() => {
    // Mock the implementation of useExportMatchesCSV
    (useExportMatchesCSV as jest.Mock).mockReturnValue({ exportMatchesCSV: mockExportMatchesCSV });

    // Mock useSearchParams to return a default value
    (useSearchParams as jest.Mock).mockReturnValue([new URLSearchParams(), jest.fn()]);
});

const Fixture = () => (
    <MemoryRouter initialEntries={['/merge']}>
        <MergeLanding />
    </MemoryRouter>
);

describe('MergeLanding', () => {
    it('should have the proper heading', () => {
        const { getByRole } = render(<Fixture />);
        expect(getByRole('heading')).toHaveTextContent('Matches requiring review');
    });

    it('should have two buttons in the header', () => {
        const { getByRole } = render(<Fixture />);
        const buttons = within(getByRole('heading').parentElement!).getAllByRole('button');
        expect(buttons).toHaveLength(2);
        expect(buttons[0].children[0].children[0]).toHaveAttribute('xlink:href', 'undefined#print');
        expect(buttons[1].children[0].children[0]).toHaveAttribute('xlink:href', 'undefined#file_download');
    });

    it('should trigger CSV download when the download button is clicked', async () => {
        // Arrange
        const { getByRole } = render(<Fixture />);
        const buttons = within(getByRole('heading').parentElement!).getAllByRole('button');

        // Find the download button
        const downloadButton = buttons.find((btn) =>
            btn.querySelector('use')?.getAttribute('xlink:href')?.endsWith('#file_download')
        );

        expect(downloadButton).toBeDefined();

        // Act
        await userEvent.click(downloadButton!);

        // Assert: Make sure the mock function was called
        expect(mockExportMatchesCSV).toHaveBeenCalledTimes(1);
    });
});
