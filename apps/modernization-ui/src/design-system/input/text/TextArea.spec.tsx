import { render } from '@testing-library/react';
import { TextArea, TextAreaProps } from './TextArea';
import userEvent from '@testing-library/user-event';

const Fixture = ({ id = 'testing-area', ...remaining }: Partial<TextAreaProps>) => (
    <div>
        <label htmlFor={id}>Text area test</label>
        <TextArea id={id} {...remaining} />
    </div>
);

describe('text area', () => {
    it('should render successfully', () => {
        const { getByRole } = render(<Fixture />);

        const input = getByRole('textbox', { name: 'Text area test' });

        expect(input).toHaveClass('usa-textarea');
        expect(input).toHaveAttribute('inputMode', 'text');
    });
    it('should alert when value changed', () => {
        const onChange = jest.fn();

        const { getByRole } = render(<Fixture onChange={onChange} />);

        const input = getByRole('textbox', { name: 'Text area test' });

        userEvent.type(input, 's');
        userEvent.tab();

        expect(onChange).toHaveBeenCalledWith('s');
    });
    it('should allow pasting of text values', () => {
        const { getByRole } = render(<Fixture />);

        const input = getByRole('textbox', { name: 'Text area test' });

        userEvent.paste(input, 'pasted value');
        userEvent.tab();

        expect(input).toHaveValue('pasted value');
    });
    it('should display given value', () => {
        const { getByRole } = render(<Fixture value={'given value'} />);

        const input = getByRole('textbox', { name: 'Text area test' });

        expect(input).toHaveValue('given value');
    });
});
