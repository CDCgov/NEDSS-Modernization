import { render, waitFor } from '@testing-library/react';
import { SaveAsReportModal } from './SaveAsReportModal.tsx';
import { axe } from 'jest-axe';
import userEvent from '@testing-library/user-event/dist/cjs/index.js';

const mockSaveAs = vi.fn();
const modalRef = { current: null };

vi.mock('libs/permission/usePermissions.ts', () => {
    return {
        usePermissions: vi.fn(() => ({
            permissions: [
                'CREATEREPORTPRIVATE-REPORTING',
                'CREATEREPORTPUBLIC-REPORTING',
                'CREATEREPORTREPORTINGFACILITY-REPORTING',
            ],
        })),
    };
});

vi.mock('options/report', () => ({
    useReportSections: () => [
        { name: 'Default Report Section', label: 'Default Report Section', value: '1000', order: 1 },
        { name: 'STD Report Section', label: 'STD Report Section', value: '2000', order: 2 },
        { name: 'TB Report Section', label: 'TB Report Section', value: '3000', order: 3 },
    ],
}));

describe('SaveAsReportModal', () => {
    it('should render with no accessibility violations', async () => {
        const { container } = render(
            <SaveAsReportModal onSaveAs={mockSaveAs} saveAsReportModalRef={modalRef} saving={false} />
        );
        expect(await axe(container)).toHaveNoViolations();
    });

    it('should display errors when submitting an empty form', async () => {
        const mockOnSaveAs = vi.fn();
        const user = userEvent.setup();

        const { container, findByLabelText, findByRole, findByText, queryByText, getByRole } = render(
            <SaveAsReportModal onSaveAs={mockOnSaveAs} saveAsReportModalRef={modalRef} saving={false} />
        );
        const saveAsNewButton = await findByRole('button', { name: 'Save as new' });
        await user.click(saveAsNewButton);

        expect(await axe(container)).toHaveNoViolations();
        expect(await findByText('The Name is required.')).toBeVisible();
        expect(await findByText('The Description is required.')).toBeVisible();
        expect(await findByText('The Section name is required.')).toBeVisible();

        const nameInput = await findByLabelText('Name');
        await user.type(nameInput, 'Test report');
        await user.click(saveAsNewButton);
        expect(queryByText('The Name is required.')).toBeNull();

        const descInput = await findByLabelText('Description');
        await user.type(descInput, 'Test report description');
        await user.click(saveAsNewButton);
        expect(queryByText('The Description is required.')).toBeNull();

        const sectionInput = await findByLabelText('Section name');
        await user.selectOptions(sectionInput, '1000');

        const privateRadio = getByRole('radio', { name: 'Private' });
        const publicRadio = getByRole('radio', { name: 'Public' });
        expect(privateRadio).toBeChecked(); // first value should be default
        expect(publicRadio).not.toBeChecked();

        await user.click(publicRadio);
        expect(privateRadio).not.toBeChecked();
        expect(publicRadio).toBeChecked();

        await user.click(saveAsNewButton);

        await waitFor(() => {
            expect(mockOnSaveAs).toHaveBeenCalledWith({
                reportTitle: 'Test report',
                description: 'Test report description',
                sectionCode: '1000',
                group: 'PUBLIC',
            });
        });
    });
});
