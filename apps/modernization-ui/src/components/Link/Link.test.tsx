import { render } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import { Link } from './Link';

describe('Link Component', () => {
    it('renders the link with the correct text and href', () => {
        const { getByText } = render(<Link name="TestLink" link="https://example.com" />);
        const linkElement = getByText('TestLink');

        expect(linkElement).toBeInTheDocument();
        expect(linkElement).toHaveAttribute('href', 'https://example.com');
    });

    it('applies the default "link" class', () => {
        const { getByText } = render(<Link name="TestLink" link="https://example.com" />);
        const linkElement = getByText('TestLink');

        expect(linkElement).toHaveClass('link');
        expect(linkElement).toHaveClass('text-normal');
        expect(linkElement).toHaveClass('margin-0');
        expect(linkElement).toHaveClass('width-full');
    });
});
