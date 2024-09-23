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
});
