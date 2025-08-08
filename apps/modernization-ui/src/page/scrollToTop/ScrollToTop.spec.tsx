import React from 'react';
import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router';
import { axe, toHaveNoViolations } from 'jest-axe';

import { ScrollToTop } from './ScrollToTop';

expect.extend(toHaveNoViolations);

const mockScrollTo = jest.fn();
Object.defineProperty(window, 'scrollTo', {
    value: mockScrollTo,
    writable: true
});

const renderWithRouter = (component: React.ReactElement, initialEntries = ['/']) => {
    return render(<MemoryRouter initialEntries={initialEntries}>{component}</MemoryRouter>);
};

describe('ScrollToTop', () => {
    beforeEach(() => {
        mockScrollTo.mockClear();
    });

    describe('Accessibility', () => {
        it('should have no accessibility violations', async () => {
            const { container } = renderWithRouter(<ScrollToTop title="Test Title" />);

            const results = await axe(container);
            expect(results).toHaveNoViolations();
        });
    });

    describe('Scroll and Focus Behavior', () => {
        it('should scroll to top on initial render', () => {
            renderWithRouter(<ScrollToTop title="Test Title" />);

            expect(mockScrollTo).toHaveBeenCalledWith(0, 0);
        });
    });
});
