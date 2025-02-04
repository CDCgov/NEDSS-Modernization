import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';

import { TextInput, TextInputProps } from './TextInput';

const Fixture = ({ id = 'testing-text', ...remaining }: Partial<TextInputProps>) => (
    <div>
        <label htmlFor={id}>Text input test</label>
        <TextInput id={id} {...remaining} />
    </div>
);

describe('when entering text values', () => {
    it('should render successfully', () => {
        const { getByRole } = render(<Fixture />);

        const input = getByRole('textbox', { name: 'Text input test' });

        expect(input).toHaveClass('usa-input');
        expect(input).toHaveAttribute('type', 'text');
        expect(input).toHaveAttribute('inputMode', 'text');
    });

    it('should alert when value changed', () => {
        const onChange = jest.fn();

        const { getByRole } = render(<Fixture onChange={onChange} />);

        const input = getByRole('textbox', { name: 'Text input test' });

        userEvent.type(input, 's');
        userEvent.tab();

        expect(onChange).toHaveBeenCalledWith('s');
    });

    it('should display given value', () => {
        const { getByRole } = render(<Fixture value={'given value'} />);

        const input = getByRole('textbox', { name: 'Text input test' });

        expect(input).toHaveValue('given value');
    });

    it('should clear input, call onClear and onChange when icon is clicked', () => {
        const onChange = jest.fn();
        const onClear = jest.fn();

        const { getByRole } = render(<Fixture value={'given value'} clearable onChange={onChange} onClear={onClear} />);

        const svgIcon = getByRole('button', { hidden: true });

        userEvent.click(svgIcon);

        expect(onChange).toHaveBeenCalledWith();
        expect(onClear).toHaveBeenCalled();
    });
});
