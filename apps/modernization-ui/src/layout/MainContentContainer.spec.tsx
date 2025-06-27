import React from 'react';
import { render, screen } from '@testing-library/react';
import { MemoryRouter } from 'react-router';
import { axe, toHaveNoViolations } from 'jest-axe';

import { MainContentContainer } from './MainContentContainer';

expect.extend(toHaveNoViolations);

const mockScrollTo = jest.fn();
Object.defineProperty(window, 'scrollTo', {
    value: mockScrollTo,
    writable: true
});

const renderWithRouter = (component: React.ReactElement, initialEntries = ['/']) => {
    return render(<MemoryRouter initialEntries={initialEntries}>{component}</MemoryRouter>);
};

describe('MainContentContainer', () => {
    beforeEach(() => {
        mockScrollTo.mockClear();
    });

    describe('Accessibility', () => {
        it('should have no accessibility violations', async () => {
            const { container } = renderWithRouter(
                <MainContentContainer>
                    <div>
                        <h1>Page Title</h1>
                        <p>Some content for testing accessibility</p>
                        <button>Test Button</button>
                    </div>
                </MainContentContainer>
            );

            const results = await axe(container);
            expect(results).toHaveNoViolations();
        });
    });

    describe('Rendering', () => {
        it('should render children correctly', () => {
            renderWithRouter(
                <MainContentContainer>
                    <div data-testid="test-child">Test Content</div>
                </MainContentContainer>
            );

            expect(screen.getByTestId('test-child')).toBeInTheDocument();
            expect(screen.getByText('Test Content')).toBeInTheDocument();
        });
    });

    describe('Scroll and Focus Behavior', () => {
        it('should scroll to top on initial render', () => {
            renderWithRouter(
                <MainContentContainer>
                    <div>Test Content</div>
                </MainContentContainer>
            );

            expect(mockScrollTo).toHaveBeenCalledWith(0, 0);
        });
    });
});
