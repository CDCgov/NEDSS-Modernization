import { render } from '@testing-library/react';
import { Hint } from './Hint';

describe('Hint', () => {
    it('should display the info_outline icon', () => {
        const { container } = render(<Hint>hint content</Hint>);

        const icon = container.querySelector('svg use');
        expect(icon).toHaveAttribute('xlink:href', 'undefined#check_circle');
    });
});
