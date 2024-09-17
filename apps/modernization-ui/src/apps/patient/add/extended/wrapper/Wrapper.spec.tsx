import { render } from '@testing-library/react';
import { Wrapper } from './Wrapper';

describe('Wrapper Component', () => {
    it('renders correctly with given props', () => {
        const { getByText } = render(
            <Wrapper id="test-id" title="Test Title">
                <div>Child Content</div>
            </Wrapper>
        );
        expect(getByText('Test Title')).toBeInTheDocument();
        expect(getByText('Child Content')).toBeInTheDocument();
    });

    it('applies the correct id to the section', () => {
        const { container } = render(
            <Wrapper id="test-id" title="Test Title">
                <div>Child Content</div>
            </Wrapper>
        );
        expect(container.querySelector('section')).toHaveAttribute('id', 'test-id');
    });
});
