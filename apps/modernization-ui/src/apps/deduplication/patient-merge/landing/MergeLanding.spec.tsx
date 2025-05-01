import { render, within } from '@testing-library/react';
import { MergeLanding } from './MergeLanding';
import { MemoryRouter } from 'react-router';
import userEvent from "@testing-library/user-event";
import { useExportMatches } from '../../api/useExportMatches';
import { useSearchParams } from "react-router";

jest.mock('../../api/useExportMatches', () => ({
    useExportMatches: jest.fn(),
}));

jest.mock('react-router', () => ({
    ...jest.requireActual('react-router'),
    useSearchParams: jest.fn(),
}));

const mockExportMatchesCSV = jest.fn();
const mockExportMatchesPDF = jest.fn();

beforeEach(() => {
    mockExportMatchesCSV.mockClear();
    mockExportMatchesPDF.mockClear();

    (useExportMatches as jest.Mock).mockReturnValue({
        exportCSV: mockExportMatchesCSV,
        exportPDF: mockExportMatchesPDF,
    });

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
        const user = userEvent.setup();
        const { getByRole } = render(<Fixture />);
        const buttons = within(getByRole('heading').parentElement!).getAllByRole('button');

        const downloadButton = buttons.find((btn) =>
            btn.querySelector('use')?.getAttribute('xlink:href')?.endsWith('#file_download')
        );

        expect(downloadButton).toBeDefined();

        await user.click(downloadButton!);

        expect(mockExportMatchesCSV).toHaveBeenCalledTimes(1);
    });

    it('should trigger PDF download when the PDF download button is clicked', async () => {
        // Arrange
        const user = userEvent.setup();
        const { getByRole } = render(<Fixture />);
        const buttons = within(getByRole('heading').parentElement!).getAllByRole('button');

        // Find the PDF download button
        const downloadButton = buttons.find((btn) =>
            btn.querySelector('use')?.getAttribute('xlink:href')?.endsWith('#print')
        );

        expect(downloadButton).toBeDefined();

        // Act
        await user.click(downloadButton!);

        // Assert: Make sure the mock function was called
        expect(mockExportMatchesPDF).toHaveBeenCalledTimes(1);
    });
});
