import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { axe } from 'jest-axe';
import { NumberRangeField } from './NumberRangeField.tsx';

describe('NumberRangeField Component', () => {
    it('should render the component with initial values', () => {
        const { getByLabelText } = render(
            <NumberRangeField
                id="testing-number-range"
                value={{
                    between: {
                        from: 1,
                        to: 2,
                    },
                }}
                onChange={vi.fn()}
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
            <NumberRangeField id="testing-number-range-from-entered" onChange={mockOnChange} />
        );

        const from = getByLabelText('From');

        const user = userEvent.setup();

        await user.type(from, '3500{tab}');

        expect(mockOnChange).toHaveBeenCalledWith({ between: expect.objectContaining({ from: 3500 }) });
    });

    it('should call from input change handler when the from number is changed', async () => {
        const mockOnChange = vi.fn();
        const { getByLabelText } = render(
            <NumberRangeField
                id="testing-number-range-from-change"
                value={{
                    between: {
                        from: 999,
                    },
                }}
                onChange={mockOnChange}
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
            <NumberRangeField id="testing-number-range-to-entered" onChange={mockOnChange} />
        );

        const to = getByLabelText('To');

        const user = userEvent.setup();

        await user.type(to, '900{tab}');

        expect(mockOnChange).toHaveBeenCalledWith({ between: expect.objectContaining({ to: 900 }) });
    });

    it('should call from input change handler when the to number is changed', async () => {
        const mockOnChange = vi.fn();
        const { getByLabelText } = render(
            <NumberRangeField
                id="testing-number-range-to-change"
                value={{
                    between: {
                        from: 10,
                        to: 99,
                    },
                }}
                onChange={mockOnChange}
            />
        );

        const to = getByLabelText('To');

        const user = userEvent.setup();

        await user.clear(to).then(() => user.type(to, '100{tab}'));

        expect(mockOnChange).toHaveBeenCalledWith({ between: expect.objectContaining({ to: 100 }) });
    });
});
