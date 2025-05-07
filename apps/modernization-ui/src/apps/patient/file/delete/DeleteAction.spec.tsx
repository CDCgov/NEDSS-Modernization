import { ReactNode } from 'react';
import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { DeleteAction } from './DeleteAction';
import { DeletabilityResult, resolveDeletability } from './resolveDeletability';
import { Patient } from '../patientData';
import { PatientSummary } from 'generated/graphql/schema';
import { Permitted } from 'libs/permission';
import { useDeletePatientFile } from './useDeletePatientFile';

const mockPatient: Patient = {
    id: '10056284',
    local: 'PSN10091000GA01',
    shortId: 91000,
    version: 10,
    deletable: true,
    status: 'ACTIVE'
};

const mockSummary: Partial<PatientSummary> = {
    legalName: {
        first: 'John',
        middle: null,
        last: 'Deletable'
    },
    birthday: null,
    age: null,
    gender: 'Female'
};

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

jest.mock('./useDeletePatientFile', () => ({
    useDeletePatientFile: jest.fn()
}));

jest.mock('../PatientFileContext', () => ({
    usePatientFileContext: () => ({
        patient: mockPatient,
        summary: mockSummary,
        changed: jest.fn()
    })
}));

jest.mock('libs/permission', () => ({
    Permitted: jest.fn(({ children }: { children: ReactNode }) => <>{children}</>),
    permissions: {
        patient: {
            delete: 'DELETE-PATIENT'
        }
    }
}));

jest.mock('./resolveDeletability', () => ({
    resolveDeletability: jest.fn(),
    DeletabilityResult: {
        Deletable: 'Deletable',
        Has_Associations: 'Has_Associations',
        Is_Inactive: 'Is_Inactive'
    }
}));

describe('DeleteAction', () => {
    beforeEach(() => {
        jest.clearAllMocks();
        (Permitted as jest.Mock).mockImplementation(({ children }: { children: ReactNode }) => <>{children}</>);
    });

    it('should handle the deletion flow', async () => {
        const user = userEvent.setup();
        (resolveDeletability as jest.Mock).mockReturnValue(DeletabilityResult.Deletable);
        const { getByRole, getByText } = render(<DeleteAction />);

        const deleteButton = getByRole('button');

        await user.click(deleteButton);

        expect(getByText('Delete patient file')).toBeInTheDocument();
        expect(getByText('Delete', { selector: 'button' })).toBeInTheDocument();
    });

    it('should not show the delete button when user does not have permissions', () => {
        (Permitted as jest.Mock).mockImplementation(() => <></>);
        const { queryByRole } = render(<DeleteAction />);
        expect(queryByRole('button')).not.toBeInTheDocument();
    });

    it('should show warning if patient has associations', async () => {
        const user = userEvent.setup();
        (resolveDeletability as jest.Mock).mockReturnValue(DeletabilityResult.Has_Associations);
        const { getByRole, getByText } = render(<DeleteAction />);

        const deleteButton = getByRole('button');
        await user.hover(deleteButton);

        expect(
            getByText('The file cannot be deleted until all associated event records have been deleted.', {
                exact: false
            })
        ).toBeInTheDocument();
    });

    it('should show warning if patient is inactive', async () => {
        const user = userEvent.setup();
        (resolveDeletability as jest.Mock).mockReturnValue(DeletabilityResult.Is_Inactive);
        const { getByRole, getByText } = render(<DeleteAction />);

        const deleteButton = getByRole('button');
        await user.hover(deleteButton);

        expect(getByText('This patient file is inactive and cannot be deleted.')).toBeInTheDocument();
    });

    it('should call deletePatient function when deleted confirmation button is clicked', async () => {
        const mockDeletePatient = jest.fn(() => Promise.resolve({ success: true }));
        (useDeletePatientFile as jest.Mock).mockReturnValue(mockDeletePatient);
        const user = userEvent.setup();
        (resolveDeletability as jest.Mock).mockReturnValue(DeletabilityResult.Deletable);
        const { getByRole, getByText } = render(<DeleteAction />);

        const deleteButton = getByRole('button');

        await user.click(deleteButton);

        expect(getByText('Delete patient file')).toBeInTheDocument();
        const confirmButton = getByText('Delete', { selector: 'button' });

        await user.click(confirmButton);
        expect(mockDeletePatient).toHaveBeenCalledWith(parseInt(mockPatient.id));
    });

    it('should show success message when patient successfully deleted', async () => {
        (useDeletePatientFile as jest.Mock).mockImplementation((onComplete) => () => onComplete({ success: true }));
        (resolveDeletability as jest.Mock).mockReturnValue(DeletabilityResult.Deletable);
        const user = userEvent.setup();
        const { getByRole, getByText } = render(<DeleteAction />);

        const deleteButton = getByRole('button');
        await user.click(deleteButton);

        const confirmButton = getByText('Delete', { selector: 'button' });
        await user.click(confirmButton);

        expect(mockShowSuccess).toHaveBeenCalledWith('Deleted patient: 10056284');
        expect(mockGo).toHaveBeenCalled();
    });

    it('should show error message when patient failed to delete', async () => {
        (useDeletePatientFile as jest.Mock).mockImplementation(
            (onComplete) => () => onComplete({ success: false, message: 'Error in delete' })
        );
        (resolveDeletability as jest.Mock).mockReturnValue(DeletabilityResult.Deletable);
        const user = userEvent.setup();
        const { getByRole, getByText } = render(<DeleteAction />);

        const deleteButton = getByRole('button');
        await user.click(deleteButton);

        const confirmButton = getByText('Delete', { selector: 'button' });
        await user.click(confirmButton);

        expect(mockShowSuccess).not.toHaveBeenCalled();
        expect(mockShowError).toHaveBeenCalledWith('Delete failed. Please try again later.');
    });
});
