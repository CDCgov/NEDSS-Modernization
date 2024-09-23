import { render, screen as rtlScreen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { NavSection, SectionNavigation } from './SectionNavigation';

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
        render(<SectionNavigation title="On this page" sections={mockSections} />);

        expect(rtlScreen.getByText('On this page')).toBeInTheDocument();
        expect(rtlScreen.getByText('Section 1')).toBeInTheDocument();
        expect(rtlScreen.getByText('Section 2')).toBeInTheDocument();
    });

    it('renders links with correct href attributes', () => {
        render(<SectionNavigation title="On this page" sections={mockSections} />);

        const link1 = rtlScreen.getByText('Section 1');
        const link2 = rtlScreen.getByText('Section 2');

        expect(link1.getAttribute('href')).toBe('#section1');
        expect(link2.getAttribute('href')).toBe('#section2');
    });

    it('calls scrollIntoView when a link is clicked', () => {
        const scrollIntoViewMock = jest.fn();
        const mockElement = { scrollIntoView: scrollIntoViewMock };
        jest.spyOn(document, 'getElementById').mockReturnValue(mockElement as any);

        render(<SectionNavigation title="On this page" sections={mockSections} />);

        const link = rtlScreen.getByText('Section 1');
        userEvent.click(link);

        // Ensure the click event is processed
        setTimeout(() => {
            expect(scrollIntoViewMock).toHaveBeenCalledWith({ behavior: 'smooth', block: 'start' });
        }, 0);
    });
});
