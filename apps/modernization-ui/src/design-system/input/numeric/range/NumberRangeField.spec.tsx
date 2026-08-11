import { useState } from 'react';

import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';

import { EMPTY_NUMBER_BETWEEN_CRITERIA, NumberBetweenCriteria, NumberRangeField } from './NumberRangeField.tsx';

const Fixture = ({
    value,
    onChange,
}: {
    value?: NumberBetweenCriteria;
    onChange?: (v: NumberBetweenCriteria) => void;
}) => {
    const [val, setVal] = useState<NumberBetweenCriteria>(value ?? EMPTY_NUMBER_BETWEEN_CRITERIA);

    const handleChange = (v: NumberBetweenCriteria) => {
        setVal(v);
        onChange?.(v);
    };
    return <NumberRangeField id="testing-number-range" value={val} onChange={handleChange} />;
};

describe('NumberRangeField Component', () => {
    it('should render the component with initial values', () => {
        const { getByLabelText } = render(
            <Fixture
                value={{
                    between: {
                        from: 1,
                        to: 2,
                    },
                }}
            />
        );
        const from = getByLabelText('From');
        const to = getByLabelText('To');

        expect(from).toHaveValue(1);
        expect(to).toHaveValue(2);
    });

    it('should call from input change handler when the from number is entered', async () => {
        const mockOnChange = vi.fn();
        const { getByLabelText } = render(<Fixture onChange={mockOnChange} />);

        const from = getByLabelText('From');

        const user = userEvent.setup();

        await user.type(from, '3500{tab}');

        expect(mockOnChange).toHaveBeenCalledWith({ between: expect.objectContaining({ from: 3500 }) });
    });

    it('should call from input change handler when the from number is changed', async () => {
        const mockOnChange = vi.fn();
        const { getByLabelText } = render(
            <Fixture
                value={{
                    between: {
                        from: 999,
                        to: null,
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
            <Fixture  onChange={mockOnChange} />
        );

        const to = getByLabelText('To');

        const user = userEvent.setup();

        await user.type(to, '900{tab}');

        expect(mockOnChange).toHaveBeenCalledWith({ between: expect.objectContaining({ to: 900 }) });
    });

    it('should call from input change handler when the to number is changed', async () => {
        const mockOnChange = vi.fn();
        const { getByLabelText } = render(
            <Fixture
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
