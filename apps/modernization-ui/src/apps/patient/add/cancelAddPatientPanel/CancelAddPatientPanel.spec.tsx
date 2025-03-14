import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { CancelAddPatientPanel } from './CancelAddPatientPanel';
import { useShowCancelModal } from './useShowCancelModal';

jest.mock('./useShowCancelModal', () => ({
    useShowCancelModal: jest.fn()
}));

describe('CancelAddPatientPanel', () => {
    const defaultSave = jest.fn();

    beforeEach(() => {
        (useShowCancelModal as jest.Mock).mockReturnValue({ value: false, save: defaultSave });
        defaultSave.mockReset();
    });

    it('should render the confirmation modal', () => {
        const { getByRole, getByText } = render(<CancelAddPatientPanel />);

        expect(getByRole('heading', { name: 'Warning' })).toBeInTheDocument();
        expect(getByText('Canceling the form will result in the loss', { exact: false })).toBeInTheDocument();
        expect(getByRole('button', { name: 'Yes, cancel' })).toBeInTheDocument();
        expect(getByRole('button', { name: 'No, back to form' })).toBeInTheDocument();
    });

    it('should invoke the onClose when cancel button is clicked', () => {
        const onClose = jest.fn();
        const { getByRole } = render(<CancelAddPatientPanel onClose={onClose} />);

        const closer = getByRole('button', { name: 'No, back to form' });
        userEvent.click(closer);

        expect(onClose).toBeCalled();
    });

    it('should call onConfirm when confirm button is clicked', async () => {
        const onConfirm = jest.fn();
        const { getByRole } = render(<CancelAddPatientPanel onConfirm={onConfirm} />);

        const confirmButton = getByRole('button', { name: 'Yes, cancel' });
        userEvent.click(confirmButton);
        expect(onConfirm).toBeCalled();
    });

    it('should save checkbox state to local storage on confirm', () => {
        const { getByRole } = render(<CancelAddPatientPanel />);
        userEvent.click(getByRole('checkbox'));
        userEvent.click(getByRole('button', { name: 'Yes, cancel' }));
        expect(getByRole('checkbox')).toBeChecked();
        expect(defaultSave).toBeCalledWith(true);
    });
});
