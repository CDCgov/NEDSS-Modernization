import { render } from '@testing-library/react';
import { BaseCard } from './BaseCard';

describe('BaseCard Component', () => {
    it('renders correctly with header and children', () => {
        const { container, getByText } = render(
            <BaseCard id="test-id" header="Test Title">
                <div>Child Content</div>
            </BaseCard>
        );

        expect(container.querySelector('section')).toBeInTheDocument();
        expect(container.querySelector('header')).toHaveTextContent('Test Title');
        expect(getByText('Child Content')).toBeInTheDocument();
    });
});
