import { vi } from 'vitest';
import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { CancelAddPatientPanel } from './CancelAddPatientPanel';

const mockSave = jest.fn();

vi.mock('./useShowCancelModal', () => ({
    useShowCancelModal: () => ({ value: false, save: mockSave })
}));

describe('CancelAddPatientPanel', () => {
    it('should render the confirmation modal', () => {
        const { getByRole, getByText } = render(<CancelAddPatientPanel />);

        expect(getByRole('heading', { name: 'Warning' })).toBeInTheDocument();
        expect(getByText('Canceling the form will result in the loss', { exact: false })).toBeInTheDocument();
        expect(getByRole('button', { name: 'Yes, cancel' })).toBeInTheDocument();
        expect(getByRole('button', { name: 'No, back to form' })).toBeInTheDocument();
    });

    it('should invoke the onClose when cancel button is clicked', async () => {
        const onClose = jest.fn();
        const { getByRole } = render(<CancelAddPatientPanel onClose={onClose} />);

        const closer = getByRole('button', { name: 'No, back to form' });

        const user = userEvent.setup();

        await user.click(closer);

        expect(onClose).toBeCalled();
    });

    it('should call onConfirm when confirm button is clicked', async () => {
        const onConfirm = jest.fn();
        const { getByRole } = render(<CancelAddPatientPanel onConfirm={onConfirm} />);

        const confirmButton = getByRole('button', { name: 'Yes, cancel' });

        const user = userEvent.setup();

        await user.click(confirmButton);

        expect(onConfirm).toBeCalled();
    });

    it('should save checkbox state to local storage on confirm', async () => {
        const { getByRole } = render(<CancelAddPatientPanel />);

        const user = userEvent.setup();

        await user.click(getByRole('checkbox'));
        await user.click(getByRole('button', { name: 'Yes, cancel' }));

        expect(getByRole('checkbox')).toBeChecked();
        expect(mockSave).toBeCalledWith(true);
    });
});
