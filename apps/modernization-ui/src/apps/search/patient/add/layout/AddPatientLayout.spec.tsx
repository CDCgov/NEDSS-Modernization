import { render } from '@testing-library/react';
import { AddPatientLayout } from './AddPatientLayout';
import { NavSection } from 'design-system/inPageNavigation/InPageNavigation';

class MockIntersectionObserver {
    observe = jest.fn();
    unobserve = jest.fn();
    disconnect = jest.fn();
    constructor(callback: (entries: IntersectionObserverEntry[]) => void) {
        callback([{ isIntersecting: true, target: { id: 'section1' } } as IntersectionObserverEntry]);
    }
}

Object.defineProperty(window, 'IntersectionObserver', {
    writable: true,
    configurable: true,
    value: MockIntersectionObserver
});

const headerActions = <button>Save</button>;
const headerTitle = 'Add New Patient';
const formContent = (
    <form>
        <input placeholder="Patient Name" />
    </form>
);
const inPageSections: NavSection[] = [
    { id: 'section1', label: 'Section 1' },
    { id: 'section2', label: 'Section 2' }
];
const children = <div>Additional Content</div>;

describe('AddPatientLayout', () => {
    beforeEach(() => {
        jest.clearAllMocks();
    });

    it('should render the side navigation', () => {
        const { getByText } = render(
            <AddPatientLayout
                headerActions={headerActions}
                headerTitle={headerTitle}
                formContent={formContent}
                inPageSections={inPageSections}
            />
        );

        expect(getByText('New patient')).toBeInTheDocument();
    });

    it('should render the header content with title and actions', () => {
        const { getByText } = render(
            <AddPatientLayout
                headerActions={headerActions}
                headerTitle={headerTitle}
                formContent={formContent}
                inPageSections={inPageSections}
            />
        );

        expect(getByText(headerTitle)).toBeInTheDocument();
        expect(getByText('Save')).toBeInTheDocument();
    });

    it('should render the patient form', () => {
        const { getByPlaceholderText } = render(
            <AddPatientLayout
                headerActions={headerActions}
                headerTitle={headerTitle}
                formContent={formContent}
                inPageSections={inPageSections}
            />
        );

        expect(getByPlaceholderText('Patient Name')).toBeInTheDocument();
    });

    it('should render the in-page navigation with sections', () => {
        const { getByText } = render(
            <AddPatientLayout
                headerActions={headerActions}
                headerTitle={headerTitle}
                formContent={formContent}
                inPageSections={inPageSections}
            />
        );

        inPageSections.forEach((section) => {
            expect(getByText(section.label)).toBeInTheDocument();
        });
    });

    it('should render children content', () => {
        const { getByText } = render(
            <AddPatientLayout
                headerActions={headerActions}
                headerTitle={headerTitle}
                formContent={formContent}
                inPageSections={inPageSections}>
                {children}
            </AddPatientLayout>
        );

        expect(getByText('Additional Content')).toBeInTheDocument();
    });
});
