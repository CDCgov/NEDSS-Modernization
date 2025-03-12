import { render } from '@testing-library/react';
import { ValueView } from './ValueView';

describe('DataRow', () => {
    it('should display title and value', () => {
        const { getByText } = render(<ValueView title="title goes here" value="Value goes here" />);

        expect(getByText('title goes here')).toBeInTheDocument();
        expect(getByText('Value goes here')).toBeInTheDocument();
        expect(getByText('title goes here')).not.toHaveClass('required');
    });

    it('should display required indicator', () => {
        const { getByText } = render(<ValueView title="title goes here" value="Value goes here" required />);

        expect(getByText('title goes here')).toHaveClass('required');
    });

    it('should display with correct size when provided', () => {
        const { container } = render(
            <ValueView title="title goes here" value="Value goes here" sizing="small" required />
        );
        expect(container.firstChild).toHaveClass('small');
    });
});
