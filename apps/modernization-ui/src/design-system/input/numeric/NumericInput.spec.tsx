import { render } from '@testing-library/react';
import { axe } from 'jest-axe';
import { NumericInput } from './NumericInput';
import userEvent from '@testing-library/user-event/dist/cjs/index.js';

describe('when entering numeric values for a field', () => {
    it('should render with no accessibility violations', async () => {
        const { container } = render(<NumericInput id={'testing-input'} label={'Numeric Input test'} />);

        expect(await axe(container)).toHaveNoViolations();
    });

    describe('NumberRangeField Component', () => {
        it('should render with no accessibility violations', async () => {
            const { container } = render(
                <NumericInput isRange={true} id="testing-date-range-accessibility" onChange={vi.fn()} />
            );

            expect(await axe(container)).toHaveNoViolations();
        });

        it('should render the component with initial values', () => {
            const { getByLabelText } = render(
                <NumericInput
                    id="testing-number-range"
                    value={{
                        between: {
                            from: 1,
                            to: 2,
                        },
                    }}
                    onChange={vi.fn()}
                    isRange={true}
                />
            );
            const from = getByLabelText('From');
            const to = getByLabelText('To');

            expect(from).toHaveValue(1);
            expect(to).toHaveValue(2);
        });

        it('should call from input change handler when the from number is entered', async () => {
            const mockOnChange = vi.fn();
            const { getByLabelText } = render(
                <NumericInput id="testing-number-range-from-entered" onChange={mockOnChange} isRange={true} />
            );

            const from = getByLabelText('From');

            const user = userEvent.setup();

            await user.type(from, '3500{tab}');

            expect(mockOnChange).toHaveBeenCalledWith({ between: expect.objectContaining({ from: 3500 }) });
        });

        it('should call from input change handler when the from number is changed', async () => {
            const mockOnChange = vi.fn();
            const { getByLabelText } = render(
                <NumericInput
                    id="testing-number-range-from-change"
                    value={{
                        between: {
                            from: 999,
                        },
                    }}
                    onChange={mockOnChange}
                    isRange={true}
                />
            );

            const from = getByLabelText('From');

            const user = userEvent.setup();

            await user.clear(from).then(() => user.type(from, '1000{tab}'));

            expect(mockOnChange).toHaveBeenCalledWith({ between: expect.objectContaining({ from: 1000 }) });
        });

        it('should call from input change handler when the to number is entered', async () => {
            const mockOnChange = vi.fn();
            const { getByLabelText } = render(
                <NumericInput id="testing-number-range-to-entered" onChange={mockOnChange} isRange={true} />
            );

            const to = getByLabelText('To');

            const user = userEvent.setup();

            await user.type(to, '900{tab}');

            expect(mockOnChange).toHaveBeenCalledWith({ between: expect.objectContaining({ to: 900 }) });
        });

        it('should call from input change handler when the to number is changed', async () => {
            const mockOnChange = vi.fn();
            const { getByLabelText } = render(
                <NumericInput
                    id="testing-number-range-to-change"
                    value={{
                        between: {
                            from: 10,
                            to: 99,
                        },
                    }}
                    onChange={mockOnChange}
                    isRange={true}
                />
            );

            const to = getByLabelText('To');

            const user = userEvent.setup();

            await user.clear(to).then(() => user.type(to, '100{tab}'));

            expect(mockOnChange).toHaveBeenCalledWith({ between: expect.objectContaining({ to: 100 }) });
        });
    });
});
