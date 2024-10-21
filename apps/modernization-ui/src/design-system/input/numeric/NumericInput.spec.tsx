import { render } from '@testing-library/react';
import { axe } from 'jest-axe';
import { NumericInput } from './NumericInput';
import userEvent from '@testing-library/user-event';

describe('when entering numeric values', () => {
    it('should render with no accessibility violations', async () => {
        const { container } = render(<NumericInput id={'testing-input'} label={'Numeric Input test'} />);

        expect(await axe(container)).toHaveNoViolations();
    });

    it('should render successfully', () => {
        const { getByRole } = render(<NumericInput id={'testing-input'} label={'Numeric Input test'} />);

        const input = getByRole('spinbutton', { name: 'Numeric Input test' });

        expect(input).toHaveClass('usa-input');
        expect(input).toHaveAttribute('type', 'number');
    });

    it('should alert when value changed', () => {
        const onChange = jest.fn();

        const { getByRole } = render(
            <NumericInput id={'testing-input'} label={'Numeric Input test'} onChange={onChange} />
        );

        const input = getByRole('spinbutton', { name: 'Numeric Input test' });

        userEvent.type(input, '7');
        userEvent.tab();

        expect(onChange).toHaveBeenCalledWith(7);
    });

    it('should allow entry of numeric values', () => {
        const { getByRole } = render(<NumericInput id={'testing-input'} label={'Numeric Input test'} />);

        const input = getByRole('spinbutton', { name: 'Numeric Input test' });

        userEvent.type(input, '7');
        userEvent.tab();

        expect(input).toHaveValue(7);
    });

    it('should allow pasting of numeric values', () => {
        const { getByRole } = render(<NumericInput id={'testing-input'} label={'Numeric Input test'} />);

        const input = getByRole('spinbutton', { name: 'Numeric Input test' });

        userEvent.paste(input, '743');
        userEvent.tab();

        expect(input).toHaveValue(743);
    });

    it('should not allow alpha characters values', () => {
        const { getByRole } = render(<NumericInput id={'testing-input'} label={'Numeric Input test'} />);

        const input = getByRole('spinbutton', { name: 'Numeric Input test' });

        userEvent.type(input, 'a');
        userEvent.tab();

        expect(input).not.toHaveValue();
    });

    it('should display given value', () => {
        const { getByRole } = render(<NumericInput id={'testing-input'} label={'Numeric Input test'} value={5} />);

        const input = getByRole('spinbutton', { name: 'Numeric Input test' });

        expect(input).toHaveValue(5);
    });
});
