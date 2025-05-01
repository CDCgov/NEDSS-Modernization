import userEvent from '@testing-library/user-event';
import { render } from '@testing-library/react';
import { MaskedTextInput, MaskedTextInputProps } from './MaskedTextInput';

const Fixture = ({
    id = 'clearable-input',
    onChange = jest.fn(),
    mask = '_',
    ...remaining
}: Partial<MaskedTextInputProps>) => {
    return <MaskedTextInput id={id} mask={mask} {...remaining} onChange={onChange} />;
};

describe('when entering text input with a specific format', () => {
    it('should accept a value that matches the mask', async () => {
        const { getByRole } = render(<Fixture />);

        const input = getByRole('textbox');

        await userEvent.type(input, '7{tab}');

        expect(input).toHaveValue('7');
    });

    it('should format the value using the mask', async () => {
        const { getByRole } = render(<Fixture mask="(___)-___-____" />);

        const input = getByRole('textbox');

        await userEvent.type(input, '1234567890{tab}');

        expect(input).toHaveValue('(123)-456-7890');
    });

    it('should accept a values that do no match the mask', async () => {
        const { getByRole } = render(<Fixture mask="__" />);

        const input = getByRole('textbox');

        await userEvent.type(input, '777{tab}');

        expect(input).toHaveValue('77');
    });

    it('should default to the given value', () => {
        const { getByRole } = render(<Fixture value="7" />);

        const input = getByRole('textbox');
        expect(input).toHaveValue('7');
    });

    it('should allow the current value to change', async () => {
        const { getByRole } = render(<Fixture mask="___-___-____" value="123-456-7890" />);

        const input = getByRole('textbox');
        expect(input).toHaveValue('123-456-7890');

        await userEvent.type(input, '{backspace}{backspace}');

        expect(input).toHaveValue('123-456-78');
    });

    it('should clear the current value', async () => {
        const { getByRole } = render(<Fixture mask="___-___-____" value="123-456-7890" />);

        const input = getByRole('textbox');
        expect(input).toHaveValue('123-456-7890');

        await userEvent.clear(input);

        expect(input).toHaveValue('');
    });
});
