import { render, screen } from '@testing-library/react';
import { SaveReportModal } from './saveReportModal';
import { axe } from 'jest-axe';

const mockSave = vi.fn();
const modalRef = { current: null };

describe('SaveReportModal', () => {
    it('should render with no accessibility violations', async () => {
        const { container } = render(<SaveReportModal onSave={mockSave} saveReportModalRef={modalRef} />);
        screen.debug();
        expect(await axe(container)).toHaveNoViolations();
    });
});
