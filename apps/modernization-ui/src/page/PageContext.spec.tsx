import { render } from '@testing-library/react';
import { PageProvider, usePage } from '.';
import { act } from 'react';

const TestComponent = () => {
    const { title, setTitle } = usePage();
    return (
        <div>
            <h1>{title}</h1>
            <button onClick={() => setTitle('New Title')}>Set Title</button>
        </div>
    );
};

describe('PageContext', () => {
    it('should provide a default page title', () => {
        const { getByRole } = render(
            <PageProvider>
                <TestComponent />
            </PageProvider>
        );

        const titleElement = getByRole('heading', { level: 1 });
        expect(titleElement.textContent).toBe('');
    });

    it('should update the page title', () => {
        const { getByRole, getByText } = render(
            <PageProvider>
                <TestComponent />
            </PageProvider>
        );

        const titleElement = getByRole('heading', { level: 1 });
        const button = getByText('Set Title');

        act(() => {
            button.click();
        });

        expect(titleElement.textContent).toBe('New Title');
    });

    it('should throw an error if usePage is used outside of PageProvider', () => {
        const consoleError = console.error;
        console.error = jest.fn();

        expect(() => render(<TestComponent />)).toThrow('usePage must be used within a PageProvider');

        console.error = consoleError;
    });
});
