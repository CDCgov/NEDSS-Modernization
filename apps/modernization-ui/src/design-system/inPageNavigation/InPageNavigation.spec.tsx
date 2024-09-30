import { render, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { NavSection, InPageNavigation } from './InPageNavigation';

const mockSections: NavSection[] = [
    { id: 'section1', label: 'Section 1' },
    { id: 'section2', label: 'Section 2' }
];

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

describe('AddPatientExtendedNav', () => {
    beforeEach(() => {
        jest.clearAllMocks();
    });

    it('renders the nav title and section links', () => {
        const { container, getByText } = render(<InPageNavigation title="On this page" sections={mockSections} />);

        expect(container.textContent).toBe('On this pageSection 1Section 2');
        expect(getByText('Section 1')).toBeInTheDocument();
        expect(getByText('Section 2')).toBeInTheDocument();
    });

    it('renders links with correct href attributes', () => {
        const { getByText } = render(<InPageNavigation title="On this page" sections={mockSections} />);

        const link1 = getByText('Section 1');
        const link2 = getByText('Section 2');

        expect(link1.getAttribute('href')).toBe('#section1');
        expect(link2.getAttribute('href')).toBe('#section2');
    });

    it('calls scrollIntoView when a link is clicked', () => {
        const scrollIntoViewMock = jest.fn();
        const mockElement = { scrollIntoView: scrollIntoViewMock };
        jest.spyOn(document, 'getElementById').mockReturnValue(mockElement as any);

        const { getByText } = render(<InPageNavigation title="On this page" sections={mockSections} />);

        const link = getByText('Section 1');
        userEvent.click(link);

        waitFor(() => {
            expect(scrollIntoViewMock).toHaveBeenCalledWith({ behavior: 'smooth', block: 'start' });
        });
    });
});
