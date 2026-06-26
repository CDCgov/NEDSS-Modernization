import { render } from '@testing-library/react';
import { SaveReportModal } from './SaveReportModal.tsx';
import { axe } from 'jest-axe';

const mockSave = vi.fn();
const modalRef = { current: null };

describe('SaveReportModal', () => {
    it('should render with no accessibility violations', async () => {
        const { container } = render(<SaveReportModal onSave={mockSave} saveReportModalRef={modalRef} />);
        expect(await axe(container)).toHaveNoViolations();
    });
});
