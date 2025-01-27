import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';

import { TextInput, TextInputProps } from './TextInput';
import { RefObject } from 'react';

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

    it('should allow pasting of text values', () => {
        const { getByRole } = render(<Fixture />);

        const input = getByRole('textbox', { name: 'Text input test' });

        userEvent.paste(input, 'pasted value');
        userEvent.tab();

        expect(input).toHaveValue('pasted value');
    });

    it('should display given value', () => {
        const { getByRole } = render(<Fixture value={'given value'} />);

        const input = getByRole('textbox', { name: 'Text input test' });

        expect(input).toHaveValue('given value');
    });

    it('should use ref value when changed', () => {
        const mockInputRef: RefObject<HTMLInputElement> = {
            current: document.createElement('input', { is: 'mockInputRef' })
        };
        const { getByRole } = render(<Fixture value={'given value'} inputRef={mockInputRef} />);

        const input = getByRole('textbox', { name: 'Text input test' });

        expect(input).toHaveValue('given value');

        mockInputRef.current!.value = 'ref value';
        expect(input).toHaveValue('ref value');
    });

    it('clears input and calls onClear when icon is clicked', () => {
        const onChange = jest.fn();
        const { getByRole } = render(<Fixture value={'given value'} clearable onChange={onChange} />);

        const svgIcon = getByRole('img', { hidden: true });
        userEvent.click(svgIcon);

        expect(onChange).toHaveBeenCalledWith();
    });
});
