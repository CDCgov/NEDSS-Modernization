import { render } from '@testing-library/react';
import { PageTitleProvider, usePageTitle } from '.';
import { act } from 'react';

const TestComponent = () => {
    const { pageTitle, setPageTitle } = usePageTitle();
    return (
        <div>
            <span data-testid="page-title">{pageTitle}</span>
            <button onClick={() => setPageTitle('New Title')}>Set Title</button>
        </div>
    );
};

describe('PageTitleContext', () => {
    it('should provide a default page title', () => {
        const { getByTestId } = render(
            <PageTitleProvider>
                <TestComponent />
            </PageTitleProvider>
        );

        const pageTitleElement = getByTestId('page-title');
        expect(pageTitleElement.textContent).toBe('');
    });

    it('should update the page title', () => {
        const { getByTestId, getByText } = render(
            <PageTitleProvider>
                <TestComponent />
            </PageTitleProvider>
        );

        const pageTitleElement = getByTestId('page-title');
        const button = getByText('Set Title');

        act(() => {
            button.click();
        });

        expect(pageTitleElement.textContent).toBe('New Title');
    });

    it('should throw an error if usePageTitle is used outside of PageTitleProvider', () => {
        const consoleError = console.error;
        console.error = jest.fn();

        expect(() => render(<TestComponent />)).toThrow('usePageTitle must be used within a PageTitleProvider');

        console.error = consoleError;
    });
});
