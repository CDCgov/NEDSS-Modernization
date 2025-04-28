import { render } from '@testing-library/react';
import { Card } from './Card';

describe('Card Component', () => {
    it('renders correctly with given props', () => {
        const { getByText, getByRole } = render(
            <Card id="test-id" title="Test Title" info="Additional info">
                <div>Child Content</div>
            </Card>
        );
        expect(getByRole('heading')).toHaveTextContent('Test Title');
        expect(getByText('Child Content')).toBeInTheDocument();
        expect(getByText('Additional info')).toBeInTheDocument();
    });

    it('applies the correct id to the section', () => {
        const { container } = render(
            <Card id="test-id" title="Test Title">
                <div>Child Content</div>
            </Card>
        );
        expect(container.querySelector('section')).toHaveAttribute('id', 'test-id');
    });

    it('does not render subtext when subtext prop is not provided', () => {
        // Render the Card without subtext
        const { queryByText } = render(
            // eslint-disable-next-line react/no-children-prop
            <Card id="test-card" title="Test Title" children={<p>Test content</p>}>
                {/* No subtext prop here */}
            </Card>
        );

        // Check that subtext is not in the document
        const subtextElement = queryByText(/subtext/i);
        expect(subtextElement).toBeNull();
    });

    it('renders subtext when subtext prop is provided', () => {
        // Render the Card with subtext
        const { getByText } = render(
            // eslint-disable-next-line react/no-children-prop
            <Card id="test-card" title="Test Title" subtext="This is the subtext" children={<p>Test content</p>}>
                {/* Subtext is passed */}
            </Card>
        );

        // Check that subtext is in the document
        const subtextElement = getByText(/This is the subtext/i);
        expect(subtextElement).toBeInTheDocument();
    });
});
