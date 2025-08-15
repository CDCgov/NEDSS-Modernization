import { vi } from 'vitest';
import { render } from '@testing-library/react';
import { NavSection } from 'design-system/inPageNavigation/InPageNavigation';
import { AddPatientLayout } from './AddPatientLayout';
import { MemoryRouter } from 'react-router';
import { PageProvider } from 'page';

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
            <MemoryRouter>
                <PageProvider>
                    <AddPatientLayout actions={headerActions} title={headerTitle} sections={sections}>
                        {children}
                    </AddPatientLayout>
                </PageProvider>
            </MemoryRouter>
        );

        expect(getByText('Add Patient')).toBeInTheDocument();
        expect(getByText('Action')).toBeInTheDocument();
    });

    it('should render the children content', () => {
        const { getByText } = render(
            <MemoryRouter>
                <PageProvider>
            <AddPatientLayout actions={headerActions} title={headerTitle} sections={sections}>
                {children}
            </AddPatientLayout>
            </PageProvider>
            </MemoryRouter>
        );

        expect(getByText('Child Content')).toBeInTheDocument();
    });

    it('should render the in-page navigation with sections', () => {
        const { getByText } = render(
            <MemoryRouter>
                <PageProvider>
                    <AddPatientLayout actions={headerActions} title={headerTitle} sections={sections}>
                        {children}
                    </AddPatientLayout>
                </PageProvider>
            </MemoryRouter>
        );

        expect(getByText('On this page')).toBeInTheDocument();
        expect(getByText('Section 1')).toBeInTheDocument();
        expect(getByText('Section 2')).toBeInTheDocument();
    });
});
