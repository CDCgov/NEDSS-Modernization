import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import { CaseReportLaboratorySection } from './CaseReportLaboratorySection';

const mockLinks = [
    { text: 'Manage trigger codes for case reporting', href: '/trigger' },
    { group: 'Manage lab results' },
    { text: 'Manage SNOMEDs', href: '/snomeds' }
];

describe('CaseReportLaboratorySection', () => {
    const setup = (filter = '') => {
        const setAlert = jest.fn();
        render(<CaseReportLaboratorySection filter={filter} setAlert={setAlert} links={mockLinks} />);
        return { setAlert };
    };

    it('renders filtered links based on the filter prop', () => {
        setup('SNOMED');
        expect(screen.queryByText(/trigger/i)).not.toBeInTheDocument();
        expect(screen.getByText(/Manage SNOMEDs/i)).toBeInTheDocument();
    });

    it('does not render component if no matching links', () => {
        const { container } = render(
            <CaseReportLaboratorySection filter="xyz" setAlert={jest.fn()} links={mockLinks} />
        );
        expect(container).toBeEmptyDOMElement();
    });

    it('shows confirmation modal when button is clicked', () => {
        setup();
        fireEvent.click(screen.getByRole('button', { name: /reset lab mapping cache/i }));
        expect(screen.getByText(/Are you sure/i)).toBeInTheDocument();
    });

    it('calls setAlert with success after confirming reset', async () => {
        const { setAlert } = setup();
        global.fetch = jest.fn(() =>
            Promise.resolve({
                ok: true
            })
        ) as jest.Mock;

        fireEvent.click(screen.getByRole('button', { name: /reset lab mapping cache/i }));
        fireEvent.click(screen.getByRole('button', { name: /yes, reset/i }));

        await waitFor(() =>
            expect(setAlert).toHaveBeenCalledWith({
                type: 'success',
                message:
                    'Labtest program area mapping cache has been successfully reset. Please restart Wildfly to reflect the changes.'
            })
        );
    });

    it('calls setAlert with error on fetch failure', async () => {
        const { setAlert } = setup();
        global.fetch = jest.fn(() => Promise.reject()) as jest.Mock;

        fireEvent.click(screen.getByRole('button', { name: /reset lab mapping cache/i }));
        fireEvent.click(screen.getByRole('button', { name: /yes, reset/i }));

        await waitFor(() =>
            expect(setAlert).toHaveBeenCalledWith({
                type: 'error',
                message: 'Failed to reset Lab Mapping Cache. Please try again later.'
            })
        );
    });
});
