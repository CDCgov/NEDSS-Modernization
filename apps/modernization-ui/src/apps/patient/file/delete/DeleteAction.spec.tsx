import { ReactNode } from 'react';
import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { Permitted } from 'libs/permission';
import { DeleteAction } from './DeleteAction';
import { useDeletePatient } from './useDeletePatient';
import { AlertProvider } from 'libs/alert';

const mockShowSuccess = jest.fn();
const mockShowError = jest.fn();
const mockGo = jest.fn();

jest.mock('alert', () => ({
    useAlert: () => ({
        showSuccess: mockShowSuccess,
        showError: mockShowError
    })
}));

jest.mock('apps/search', () => ({
    useSearchNavigation: () => ({
        go: mockGo
    })
}));

jest.mock('./useDeletePatient', () => ({
    useDeletePatient: jest.fn()
}));

jest.mock('libs/permission', () => ({
    Permitted: jest.fn(({ children }: { children: ReactNode }) => <>{children}</>),
    permissions: {
        patient: {
            delete: 'DELETE-PATIENT'
        }
    }
}));

describe('DeleteAction', () => {
    beforeEach(() => {
        jest.clearAllMocks();

        (Permitted as jest.Mock).mockImplementation(({ children }: { children: ReactNode }) => <>{children}</>);
    });

    it('should handle the deletion flow', async () => {
        const user = userEvent.setup();
        const { getByRole, getByText } = render(
            <AlertProvider>
                <DeleteAction
                    patient={{
                        id: 10056284,
                        local: 'PSN10091000GA01',
                        patientId: 91000,
                        deletability: 'Deletable',
                        status: 'ACTIVE'
                    }}
                />
            </AlertProvider>
        );

        const deleteButton = getByRole('button');

        await user.click(deleteButton);

        expect(getByText('Delete patient file')).toBeInTheDocument();
        expect(getByText('Delete', { selector: 'button' })).toBeInTheDocument();
    });

    it('should not show the delete button when user does not have permissions', () => {
        (Permitted as jest.Mock).mockImplementation(() => <></>);
        const { queryByRole } = render(
            <AlertProvider>
                <DeleteAction
                    patient={{
                        id: 10056284,
                        local: 'PSN10091000GA01',
                        patientId: 91000,
                        deletability: 'Deletable',
                        status: 'ACTIVE'
                    }}
                />
            </AlertProvider>
        );
        expect(queryByRole('button')).not.toBeInTheDocument();
    });

    it('should show warning if patient has associations', async () => {
        const user = userEvent.setup();

        const { getByRole, getByText } = render(
            <AlertProvider>
                <DeleteAction
                    patient={{
                        id: 10056284,
                        local: 'PSN10091000GA01',
                        patientId: 91000,
                        deletability: 'Has_Associations',
                        status: 'ACTIVE'
                    }}
                />
            </AlertProvider>
        );

        const deleteButton = getByRole('button');
        await user.hover(deleteButton);

        expect(
            getByText('This patient file has associated event records.', {
                exact: false
            })
        ).toBeInTheDocument();
        expect(
            getByText('The file cannot be deleted until all associated event records have been deleted.', {
                exact: false
            })
        ).toBeInTheDocument();
    });

    it('should show warning if patient is inactive', async () => {
        const user = userEvent.setup();

        const { getByRole, getByText } = render(
            <AlertProvider>
                <DeleteAction
                    patient={{
                        id: 10056284,
                        local: 'PSN10091000GA01',
                        patientId: 91000,
                        deletability: 'Is_Inactive',
                        status: 'ACTIVE'
                    }}
                />
            </AlertProvider>
        );

        const deleteButton = getByRole('button');
        await user.hover(deleteButton);

        expect(getByText('This patient file is inactive and cannot be deleted.')).toBeInTheDocument();
    });

    it('should call deletePatient function when deleted confirmation button is clicked', async () => {
        const mockDeletePatient = jest.fn(() => Promise.resolve({ success: true }));
        (useDeletePatient as jest.Mock).mockReturnValue(mockDeletePatient);
        const user = userEvent.setup();

        const { getByRole, getByText } = render(
            <AlertProvider>
                <DeleteAction
                    patient={{
                        id: 797,
                        local: 'PSN10091000GA01',
                        patientId: 91000,
                        deletability: 'Deletable',
                        status: 'ACTIVE'
                    }}
                />
            </AlertProvider>
        );

        const deleteButton = getByRole('button');

        await user.click(deleteButton);

        expect(getByText('Delete patient file')).toBeInTheDocument();
        const confirmButton = getByText('Delete', { selector: 'button' });

        await user.click(confirmButton);
        expect(mockDeletePatient).toHaveBeenCalledWith(797);
    });

    it('should show success message when patient successfully deleted', async () => {
        (useDeletePatient as jest.Mock).mockImplementation((onComplete) => () => onComplete({ success: true }));

        const user = userEvent.setup();
        const { getByRole, getByText } = render(
            <AlertProvider>
                <DeleteAction
                    patient={{
                        id: 10056284,
                        local: 'PSN10091000GA01',
                        patientId: 91000,
                        deletability: 'Deletable',
                        status: 'ACTIVE',
                        name: {
                            first: 'John',
                            last: 'Doe'
                        }
                    }}
                />
            </AlertProvider>
        );

        const deleteButton = getByRole('button');
        await user.click(deleteButton);

        const confirmButton = getByText('Delete', { selector: 'button' });
        await user.click(confirmButton);

        // eslint-disable-next-line prettier/prettier
        expect(mockShowSuccess).toHaveBeenCalledWith(
            <span>
                You have successfully deleted <strong>Doe, John (Patient ID: 91000)</strong>.
            </span>
        );
        expect(mockGo).toHaveBeenCalled();
    });

    it('should show error message when patient failed to delete', async () => {
        (useDeletePatient as jest.Mock).mockImplementation(
            (onComplete) => () => onComplete({ success: false, message: 'Error in delete' })
        );
        const user = userEvent.setup();
        const { getByRole, getByText } = render(
            <AlertProvider>
                <DeleteAction
                    patient={{
                        id: 797,
                        local: 'PSN10091000GA01',
                        patientId: 91000,
                        deletability: 'Deletable',
                        status: 'ACTIVE'
                    }}
                />
            </AlertProvider>
        );

        const deleteButton = getByRole('button');
        await user.click(deleteButton);

        const confirmButton = getByText('Delete', { selector: 'button' });
        await user.click(confirmButton);

        expect(mockShowSuccess).not.toHaveBeenCalled();
        expect(mockShowError).toHaveBeenCalledWith('Delete failed. Please try again later.');
    });
});
