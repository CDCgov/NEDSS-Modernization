import { render, screen, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { CaseReportLaboratorySection } from './CaseReportLaboratorySection';

describe('CaseReportLaboratorySection', () => {
    const setup = (filter = '') => {
        const setAlert = jest.fn();
        render(<CaseReportLaboratorySection filter={filter} setAlert={setAlert} />);
        return { setAlert };
    };

    it('renders filtered links based on the filter prop', () => {
        setup('SNOMED');
        expect(screen.queryByText(/trigger/i)).not.toBeInTheDocument();
        expect(screen.getByText(/Manage SNOMEDs/i)).toBeInTheDocument();
    });

    it('does not render component if no matching links', () => {
        const { container } = render(
            <CaseReportLaboratorySection filter="xyz" setAlert={jest.fn()} />
        );
        expect(container).toBeEmptyDOMElement();
    });

    it('shows confirmation modal when button is clicked', async () => {
        const user = userEvent.setup();
        setup();

        await user.click(screen.getByRole('button', { name: /reset lab mapping cache/i }));
        expect(screen.getByText(/Are you sure/i)).toBeInTheDocument();
    });

    it('calls setAlert with success after confirming reset', async () => {
        const user = userEvent.setup();
        const { setAlert } = setup();

        global.fetch = jest.fn(() =>
            Promise.resolve({ ok: true })
        ) as jest.Mock;

        await user.click(screen.getByRole('button', { name: /reset lab mapping cache/i }));
        await user.click(screen.getByRole('button', { name: /yes, reset/i }));

        await waitFor(() =>
            expect(setAlert).toHaveBeenCalledWith({
                type: 'success',
                message:
                    'Labtest program area mapping cache has been successfully reset. Please restart Wildfly to reflect the changes.'
            })
        );
    });

    it('calls setAlert with error on fetch failure', async () => {
        const user = userEvent.setup();
        const { setAlert } = setup();

        global.fetch = jest.fn(() => Promise.reject()) as jest.Mock;

        await user.click(screen.getByRole('button', { name: /reset lab mapping cache/i }));
        await user.click(screen.getByRole('button', { name: /yes, reset/i }));

        await waitFor(() =>
            expect(setAlert).toHaveBeenCalledWith({
                type: 'error',
                message: 'Failed to reset Lab Mapping Cache. Please try again later.'
            })
        );
    });
});
