import { render } from '@testing-library/react';
import { NavigationEntry } from './NavigationEntry';
import { BrowserRouter } from 'react-router-dom';

describe('Navigation Entry', () => {
    it('should render a link', async () => {
        const { findByText } = render(<NavigationEntry label="test" href="/someLink" />);
        const entry = await findByText('test');
        expect(entry).toBeInTheDocument();
        expect(entry).toHaveAttribute('href', '/someLink');
    });

    it('should render a nav link', async () => {
        const { findByText } = render(
            <BrowserRouter>
                <NavigationEntry label="test" href="/someLink" useNav />
            </BrowserRouter>
        );
        const entry = await findByText('test');
        expect(entry).toBeInTheDocument();
        expect(entry).toHaveAttribute('href', '/someLink');
    });
});
