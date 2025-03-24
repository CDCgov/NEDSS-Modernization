import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { useAlert } from 'alert';
import { useSearchNavigation } from 'apps/search';
import { useProfileContext } from '../ProfileContext';
import { DeletePatient } from './DeletePatient';
import { DeletabilityResult, resolveDeletability } from './resolveDeletability';
import { useDeletePatientMutation } from 'generated/graphql/schema';

jest.mock('alert', () => ({
    useAlert: jest.fn()
}));

jest.mock('apps/search', () => ({
    useSearchNavigation: jest.fn()
}));

jest.mock('../ProfileContext', () => ({
    useProfileContext: jest.fn()
}));

jest.mock('generated/graphql/schema', () => ({
    useDeletePatientMutation: jest.fn()
}));

jest.mock('./resolveDeletability', () => ({
    resolveDeletability: jest.fn(),
    DeletabilityResult: {
        Deletable: 0,
        Associations: 1,
        Inactive: 2
    }
}));

const mockPatient = {
    id: '10056284',
    local: 'PSN10091000GA01',
    shortId: 91000,
    version: 10,
    deletable: true,
    status: 'ACTIVE',
    summary: {
        legalName: {
            first: 'sdfaszxc',
            middle: null,
            last: ''
        },
        birthday: null,
        age: null,
        gender: 'Female'
    }
};

describe('DeletePatient Component', () => {
    beforeEach(() => {
        (useAlert as jest.Mock).mockReturnValue({
            showSuccess: jest.fn(),
            showError: jest.fn()
        });

        (useSearchNavigation as jest.Mock).mockReturnValue({
            go: jest.fn()
        });

        (useProfileContext as jest.Mock).mockReturnValue({
            summary: mockPatient.summary
        });

        (useDeletePatientMutation as jest.Mock).mockReturnValue([jest.fn()]);
    });

    it('should handle the deletion flow', async () => {
        const user = userEvent.setup();

        (resolveDeletability as jest.Mock).mockReturnValue(DeletabilityResult.Deletable);
        const { getByRole, getByText } = render(<DeletePatient patient={mockPatient} />);

        const deleteButton = getByRole('button', { name: /delete patient/i });

        await user.click(deleteButton);

        expect(getByText('Permanently delete patient?')).toBeInTheDocument();
        const confirmButton = getByRole('button', {
            name: 'Yes, delete'
        });

        await user.click(confirmButton);
    });

    it('should show first name only if it is present on dialog', async () => {
        const user = userEvent.setup();

        (resolveDeletability as jest.Mock).mockReturnValue(DeletabilityResult.Deletable);
        const { getByRole, getByText } = render(<DeletePatient patient={mockPatient} />);

        const deleteButton = getByRole('button', { name: /delete patient/i });
        await user.click(deleteButton);

        expect(getByText('--, sdfaszxc')).toBeInTheDocument();
    });

    it('should show last name only if it is present on dialog', async () => {
        const user = userEvent.setup();

        (resolveDeletability as jest.Mock).mockReturnValue(DeletabilityResult.Deletable);
        const mockPatientWithLastName = {
            ...mockPatient,
            summary: {
                ...mockPatient.summary,
                legalName: { ...mockPatient.summary.legalName, last: 'test last', first: '' }
            }
        };

        (useProfileContext as jest.Mock).mockReturnValue({
            summary: mockPatientWithLastName.summary
        });

        const { getByRole, getByText } = render(<DeletePatient patient={mockPatientWithLastName} />);

        const deleteButton = getByRole('button', { name: /delete patient/i });
        await user.click(deleteButton);

        expect(getByText('test last, --')).toBeInTheDocument();
    });

    it('should show warning if patient has associations', async () => {
        const user = userEvent.setup();

        (resolveDeletability as jest.Mock).mockReturnValue(DeletabilityResult.Has_Associations);

        const { getByRole, getByText } = render(<DeletePatient patient={mockPatient} />);

        const deleteButton = getByRole('button', { name: /delete patient/i });
        await user.click(deleteButton);

        expect(getByText('This patient file is inactive and cannot be deleted.')).toBeInTheDocument();
    });

    it('should show warning if patient is inactive', async () => {
        const user = userEvent.setup();
        (resolveDeletability as jest.Mock).mockReturnValue(DeletabilityResult.Is_Inactive);

        const { getByRole, getByText } = render(<DeletePatient patient={mockPatient} />);

        const deleteButton = getByRole('button', { name: /delete patient/i });
        await user.click(deleteButton);

        expect(getByText('This patient file is inactive and cannot be deleted.')).toBeInTheDocument();
    });
});
