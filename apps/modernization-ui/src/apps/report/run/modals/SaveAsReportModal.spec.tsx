import { render, screen, waitFor } from '@testing-library/react';
import { SaveAsReportModal } from './SaveAsReportModal.tsx';
import { axe } from 'jest-axe';
import userEvent from '@testing-library/user-event/dist/cjs/index.js';

const mockSaveAs = vi.fn();
const modalRef = { current: null };

vi.mock('../../utils/getUserReportCreatePermissions.ts', () => ({
    getUserReportCreatePermissionsOptions: vi.fn(() => [
        { name: 'Private', value: 'PRIVATE' },
        { name: 'Public', value: 'PUBLIC' },
    ]),
}));

vi.mock('options/report', () => ({
    useReportSections: () => [
        { name: 'Default Report Section', label: 'Default Report Section', value: '1000', order: 1 },
        { name: 'STD Report Section', label: 'STD Report Section', value: '2000', order: 2 },
        { name: 'TB Report Section', label: 'TB Report Section', value: '3000', order: 3 },
    ],
}));

describe('SaveAsReportModal', () => {
    it('should render with no accessibility violations', async () => {
        const { container } = render(<SaveAsReportModal onSaveAs={mockSaveAs} saveAsReportModalRef={modalRef} />);
        expect(await axe(container)).toHaveNoViolations();
    });

    it('should display errors when submitting an empty form', async () => {
        const mockOnSaveAs = vi.fn();
        const user = userEvent.setup();

        const { container, findByLabelText, findByRole, findByText, queryByText } = render(
            <SaveAsReportModal onSaveAs={mockOnSaveAs} saveAsReportModalRef={modalRef} />
        );
        const saveAsNewButton = await findByRole('button', { name: 'Save as new' });
        await user.click(saveAsNewButton);

        expect(await axe(container)).toHaveNoViolations();
        expect(await findByText('Report name is required')).toBeVisible();
        expect(await findByText('Description is required')).toBeVisible();
        expect(await findByText('Report section is required')).toBeVisible();

        const nameInput = await findByLabelText('Report name');
        await user.type(nameInput, 'Test report');
        expect(queryByText('Report name is required')).toBeNull();

        const descInput = await findByLabelText('Description');
        await user.type(descInput, 'Test report description');
        expect(queryByText('Description is required')).toBeNull();

        const sectionInput = await findByLabelText('Report section');
        await user.selectOptions(sectionInput, '1000');
        expect(queryByText('Report section is required')).toBeNull();

        const privateRadio = screen.getByRole('radio', { name: 'Private' });
        const publicRadio = screen.getByRole('radio', { name: 'Public' });
        expect(privateRadio).toBeChecked(); // first value should be default
        expect(publicRadio).not.toBeChecked();

        await user.click(publicRadio);
        expect(privateRadio).not.toBeChecked();
        expect(publicRadio).toBeChecked();

        await user.click(saveAsNewButton);

        await waitFor(() => {
            expect(mockOnSaveAs).toHaveBeenCalledWith(
                expect.objectContaining({
                    reportTitle: 'Test report',
                    description: 'Test report description',
                    sectionCode: '1000',
                    group: 'PUBLIC',
                })
            );
        });
    });
});
