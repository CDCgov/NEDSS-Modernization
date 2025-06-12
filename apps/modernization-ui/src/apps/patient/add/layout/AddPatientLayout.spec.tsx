import { vi } from 'vitest';
import { render } from '@testing-library/react';
import { NavSection } from 'design-system/inPageNavigation/InPageNavigation';
import { AddPatientLayout } from './AddPatientLayout';

vi.mock('design-system/inPageNavigation/useInPageNavigation', () => ({
    __esModule: true,
    default: jest.fn()
}));

describe('AddPatientLayout', () => {
    const headerActions = () => <button>Action</button>;
    const headerTitle = 'Add Patient';
    const sections: NavSection[] = [
        { id: 'section1', label: 'Section 1' },
        { id: 'section2', label: 'Section 2' }
    ];
    const children = <div>Child Content</div>;

    it('should render the header title and actions', () => {
        const { getByText } = render(
            <AddPatientLayout actions={headerActions} title={headerTitle} sections={sections}>
                {children}
            </AddPatientLayout>
        );

        expect(getByText('Add Patient')).toBeInTheDocument();
        expect(getByText('Action')).toBeInTheDocument();
    });

    it('should render the children content', () => {
        const { getByText } = render(
            <AddPatientLayout actions={headerActions} title={headerTitle} sections={sections}>
                {children}
            </AddPatientLayout>
        );

        expect(getByText('Child Content')).toBeInTheDocument();
    });

    it('should render the in-page navigation with sections', () => {
        const { getByText } = render(
            <AddPatientLayout actions={headerActions} title={headerTitle} sections={sections}>
                {children}
            </AddPatientLayout>
        );

        expect(getByText('On this page')).toBeInTheDocument();
        expect(getByText('Section 1')).toBeInTheDocument();
        expect(getByText('Section 2')).toBeInTheDocument();
    });
});
