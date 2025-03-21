import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';

import { Numeric, NumericProps } from './Numeric';

const Fixture = ({ id = 'testing-numeric', ...remaining }: Partial<NumericProps>) => (
    <div>
        <label htmlFor={id}>Numeric input test</label>
        <Numeric id={id} {...remaining} />
    </div>
);

describe('when entering numeric values', () => {
    it('should render successfully', () => {
        const { getByRole } = render(<Fixture />);

        const input = getByRole('spinbutton', { name: 'Numeric input test' });

        expect(input).toHaveClass('usa-input');
        expect(input).toHaveAttribute('type', 'number');
    });

    it('should alert when value changed', async () => {
        const user = userEvent.setup();
        const onChange = jest.fn();

        const { getByRole } = render(<Fixture onChange={onChange} />);

        const input = getByRole('spinbutton', { name: 'Numeric input test' });

        await user.type(input, '7');
        await user.tab();

        expect(onChange).toHaveBeenCalledWith(7);
    });

    it('should allow entry of numeric values', async () => {
        const user = userEvent.setup();
        const { getByRole } = render(<Fixture />);

        const input = getByRole('spinbutton', { name: 'Numeric input test' });

        await user.type(input, '7');
        await user.tab();

        expect(input).toHaveValue(7);
    });

    it('should allow pasting of numeric values', async () => {
        const user = userEvent.setup();
        const { getByRole } = render(<Fixture />);

        const input = getByRole('spinbutton', { name: 'Numeric input test' });

        await user.type(input, '743');
        await user.tab();

        expect(input).toHaveValue(743);
    });

    it('should not allow alpha characters values', async () => {
        const user = userEvent.setup();
        const { getByRole } = render(<Fixture />);

        const input = getByRole('spinbutton', { name: 'Numeric input test' });

        await user.type(input, 'a');
        await user.tab();

        expect(input).not.toHaveValue();
    });

    it('should display given value', () => {
        const { getByRole } = render(<Fixture value={5} />);

        const input = getByRole('spinbutton', { name: 'Numeric input test' });

        expect(input).toHaveValue(5);
    });

    describe('that require non-scientific values ', () => {
        it.each([
            'e',
            '-',
            '=',
            '_',
            '+',
            '[[',
            '{{',
            ']',
            '}',
            ';',
            ':',
            '"',
            "'",
            '<',
            ',',
            '>',
            '.',
            '/',
            '?',
            '*',
            '-',
            '+',
            '`',
            '!',
            '@',
            '#',
            '$',
            '%',
            '^',
            '&',
            '*',
            '(',
            ')'
        ])('should not allow input of "%s"', async (value) => {
            const user = userEvent.setup();
            const { getByRole } = render(<Fixture />);

            const input = getByRole('spinbutton', { name: 'Numeric input test' });

            await user.type(input, value);
            await user.tab();

            expect(input).not.toHaveValue();
        });
    });
});
