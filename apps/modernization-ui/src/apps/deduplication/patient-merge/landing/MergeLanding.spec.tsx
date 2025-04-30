import { render, within } from '@testing-library/react';
import { MergeLanding } from './MergeLanding';
import { MemoryRouter } from 'react-router';
import userEvent from "@testing-library/user-event";
import { useExportMatchesCSV } from '../../api/useExportMatchesCSV';
import { useExportMatchesPDF } from '../../api/useExportMatchesPDF'; // Import the new hook for PDF
import { useSearchParams } from "react-router";

// Mocking the useExportMatchesCSV and useExportMatchesPDF hooks
jest.mock('../../api/useExportMatchesCSV', () => ({
    useExportMatchesCSV: jest.fn(),
}));

jest.mock('../../api/useExportMatchesPDF', () => ({
    useExportMatchesPDF: jest.fn(),
}));

// Mocking the useSearchParams hook
jest.mock('react-router', () => ({
    ...jest.requireActual('react-router'),
    useSearchParams: jest.fn(),
}));

const mockExportMatchesCSV = jest.fn();
const mockExportMatchesPDF = jest.fn(); // Mock the PDF export function

beforeEach(() => {
    // Mock the implementation of useExportMatchesCSV and useExportMatchesPDF
    (useExportMatchesCSV as jest.Mock).mockReturnValue({ exportMatchesCSV: mockExportMatchesCSV });
    (useExportMatchesPDF as jest.Mock).mockReturnValue({ exportMatchesPDF: mockExportMatchesPDF });

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

    it('should trigger CSV download when the CSV download button is clicked', async () => {
        // Arrange
        const { getByRole } = render(<Fixture />);
        const buttons = within(getByRole('heading').parentElement!).getAllByRole('button');

        // Find the CSV download button
        const downloadButton = buttons.find((btn) =>
            btn.querySelector('use')?.getAttribute('xlink:href')?.endsWith('#file_download')
        );

        expect(downloadButton).toBeDefined();

        // Act
        await userEvent.click(downloadButton!);

        // Assert: Make sure the mock function was called
        expect(mockExportMatchesCSV).toHaveBeenCalledTimes(1);
    });

    it('should trigger PDF download when the PDF download button is clicked', async () => {
        // Arrange
        const { getByRole } = render(<Fixture />);
        const buttons = within(getByRole('heading').parentElement!).getAllByRole('button');

        // Find the PDF download button (can be identified based on the icon or other criteria)
        const downloadButton = buttons.find((btn) =>
            btn.querySelector('use')?.getAttribute('xlink:href')?.endsWith('#print')
        );

        expect(downloadButton).toBeDefined();

        // Act
        await userEvent.click(downloadButton!);

        // Assert: Make sure the mock function was called
        expect(mockExportMatchesPDF).toHaveBeenCalledTimes(1);
    });
});
