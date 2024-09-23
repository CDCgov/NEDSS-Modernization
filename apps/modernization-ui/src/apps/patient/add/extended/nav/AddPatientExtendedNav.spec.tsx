import { render, screen as rtlScreen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { AddPatientExtendedNav } from './AddPatientExtendedNav';

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
        render(<AddPatientExtendedNav />);

        expect(rtlScreen.getByText('On this page')).toBeInTheDocument();
        expect(rtlScreen.getByText('Administrative')).toBeInTheDocument();
    });

    it('renders links with correct href attributes', () => {
        render(<AddPatientExtendedNav />);

        const link1 = rtlScreen.getByText('Administrative');
        const link2 = rtlScreen.getByText('Name');

        expect(link1.getAttribute('href')).toBe('#administrative');
        expect(link2.getAttribute('href')).toBe('#name');
    });

    it('calls scrollIntoView when a link is clicked', () => {
        const scrollIntoViewMock = jest.fn();
        const mockElement = { scrollIntoView: scrollIntoViewMock };
        jest.spyOn(document, 'getElementById').mockReturnValue(mockElement as any);

        render(<AddPatientExtendedNav />);

        const link = rtlScreen.getByText('Administrative');
        userEvent.click(link);

        // Ensure the click event is processed
        setTimeout(() => {
            expect(scrollIntoViewMock).toHaveBeenCalledWith({ behavior: 'smooth', block: 'start' });
        }, 0);
    });
});
