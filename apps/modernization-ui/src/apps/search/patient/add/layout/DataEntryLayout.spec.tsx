import { render } from '@testing-library/react';
import { DataEntryLayout } from './DataEntryLayout';
import { NavSection } from 'design-system/inPageNavigation/InPageNavigation';

jest.mock('design-system/inPageNavigation/useInPageNavigation', () => ({
    __esModule: true,
    default: jest.fn()
}));

const headerActions = <button>Save</button>;
const headerTitle = 'Add New Patient';
const entryComponent = (
    <form>
        <input placeholder="Patient Name" />
    </form>
);
const sections: NavSection[] = [
    { id: 'section1', label: 'Section 1' },
    { id: 'section2', label: 'Section 2' }
];
const children = <div>Additional Content</div>;

describe('DataEntryLayout', () => {
    beforeEach(() => {
        jest.clearAllMocks();
    });

    it('should render the header content with title and actions', () => {
        const { getByText } = render(
            <DataEntryLayout
                headerActions={headerActions}
                headerTitle={headerTitle}
                entryComponent={entryComponent}
                sections={sections}
            />
        );

        expect(getByText(headerTitle)).toBeInTheDocument();
        expect(getByText('Save')).toBeInTheDocument();
    });

    it('should render the patient form', () => {
        const { getByPlaceholderText } = render(
            <DataEntryLayout
                headerActions={headerActions}
                headerTitle={headerTitle}
                entryComponent={entryComponent}
                sections={sections}
            />
        );

        expect(getByPlaceholderText('Patient Name')).toBeInTheDocument();
    });

    it('should render the in-page navigation with sections', () => {
        const { getByText } = render(
            <DataEntryLayout
                headerActions={headerActions}
                headerTitle={headerTitle}
                entryComponent={entryComponent}
                sections={sections}
            />
        );

        sections.forEach((section) => {
            expect(getByText(section.label)).toBeInTheDocument();
        });
    });

    it('should render children content', () => {
        const { getByText } = render(
            <DataEntryLayout
                headerActions={headerActions}
                headerTitle={headerTitle}
                sections={sections}
                entryComponent={<div>Entry Component</div>}>
                {children}
            </DataEntryLayout>
        );

        expect(getByText('Additional Content')).toBeInTheDocument();
    });
});
