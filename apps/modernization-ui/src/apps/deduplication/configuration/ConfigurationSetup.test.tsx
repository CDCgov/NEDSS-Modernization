import { render, screen, within } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { ConfigurationSetup } from './ConfigurationSetup';
import { MemoryRouter } from 'react-router';

jest.mock('alert', () => ({
    useAlert: jest.fn(() => ({
        showError: jest.fn()
    })),
    AlertProvider: ({ children }: { children: React.ReactNode }) => <>{children}</>
}));

describe('ConfigurationSetup', () => {
    let showErrorMock: jest.Mock;

    beforeEach(() => {
        jest.clearAllMocks();
        showErrorMock = jest.fn();
        const { useAlert } = require('alert');
        useAlert.mockReturnValue({ showError: showErrorMock });
    });

    test('renders the configuration setup page correctly', () => {
        render(
            <MemoryRouter>
                <ConfigurationSetup />
            </MemoryRouter>
        );

        const header = screen.getByRole('banner');
        const heading = within(header).getByRole('heading', { level: 1 });

        expect(heading).toHaveTextContent('Person match configuration');
        expect(screen.getByText('Algorithm not configured')).toBeInTheDocument();
    });

    test('handles error display using useAlert', () => {
        render(
            <MemoryRouter>
                <ConfigurationSetup />
            </MemoryRouter>
        );

        expect(showErrorMock).not.toHaveBeenCalled();
    });

    test('navigating to /deduplication/configuration loads the page', () => {
        render(
            <MemoryRouter initialEntries={['/deduplication/configuration']}>
                <ConfigurationSetup />
            </MemoryRouter>
        );

        expect(screen.getByRole('banner')).toBeInTheDocument();
    });

    test('the page has the title "Person match configuration"', () => {
        render(
            <MemoryRouter>
                <ConfigurationSetup />
            </MemoryRouter>
        );

        const header = screen.getByRole('banner');
        const heading = within(header).getByRole('heading', { level: 1 });

        expect(heading).toHaveTextContent('Person match configuration');
    });

    test('the page has a "Configure data elements" button', () => {
        render(
            <MemoryRouter>
                <ConfigurationSetup />
            </MemoryRouter>
        );

        expect(screen.getByRole('button', { name: 'Configure data elements' })).toBeInTheDocument();
    });

    test('the page has an "Import configuration file" button', () => {
        render(
            <MemoryRouter>
                <ConfigurationSetup />
            </MemoryRouter>
        );

        expect(screen.getByRole('button', { name: 'Import configuration file' })).toBeInTheDocument();
    });

    test('handles button clicks correctly', async () => {
        render(
            <MemoryRouter>
                <ConfigurationSetup />
            </MemoryRouter>
        );

        const configureButton = screen.getByText('Configure data elements');
        const importButton = screen.getByText('Import configuration file');

        await userEvent.click(configureButton);
        await userEvent.click(importButton);

        expect(configureButton).toBeInTheDocument();
        expect(importButton).toBeInTheDocument();
    });
});
