import { render, screen } from '@testing-library/react';
import { Field, Orientation, Sizing } from './Field';
import { ReactNode } from 'react';

describe('Field Component', () => {
    const mockProps: {
        className?: string;
        htmlFor: string;
        children: ReactNode;
        orientation?: Orientation;
        sizing?: Sizing;
        label: string;
        helperText?: string;
        error?: string;
        required?: boolean;
        warning?: string;
    } = {
        htmlFor: 'test-field',
        children: 'Test Content',
        label: 'Test Label'
    };

    it('renders with correct name and value with the large class by default', () => {
        const { label } = mockProps;
        const { container, getByText } = render(<Field {...mockProps} />);

        const labelText = getByText(`${label}`);
        const spanElement = container.querySelector('span');
        expect(spanElement).toHaveClass('large');
        expect(labelText).toBeInTheDocument();
    });

    it('applies the large class when the sizing prop is set to large', async () => {
        render(
            <Field {...mockProps} sizing="large">
                <input type="text" />
            </Field>
        );

        const input = screen.getByRole('textbox');
        expect(input.parentElement).toHaveClass('large');
    });

    it('applies the medium class when the sizing prop is set to medium', async () => {
        render(
            <Field {...mockProps} sizing="medium">
                <input type="text" />
            </Field>
        );

        const input = screen.getByRole('textbox');
        expect(input.parentElement).toHaveClass('medium');
    });

    it('applies the small class when the sizing prop is set to small', async () => {
        render(
            <Field {...mockProps} sizing="small">
                <input type="text" />
            </Field>
        );

        const input = screen.getByRole('textbox');
        expect(input.parentElement).toHaveClass('small');
    });
});
