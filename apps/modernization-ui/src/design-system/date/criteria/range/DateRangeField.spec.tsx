import { act, render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { axe } from 'jest-axe';
import { DateRangeField } from './DateRangeField';

describe('DateRangeField Component', () => {
    it('should render with no accessibility violations', async () => {
        const { container } = render(<DateRangeField id="testing-date-range-accessibility" onChange={jest.fn()} />);

        expect(await axe(container)).toHaveNoViolations();
    });

    it('should render the component with initial values', () => {
        const { getByRole } = render(
            <DateRangeField
                id="testing-date-range"
                value={{
                    between: {
                        from: '02/17/1990',
                        to: '02/17/2000'
                    }
                }}
                onChange={jest.fn()}
            />
        );
        const from = getByRole('textbox', { name: 'From' });
        const to = getByRole('textbox', { name: 'To' });

        expect(from).toHaveValue('02/17/1990');
        expect(to).toHaveValue('02/17/2000');
    });

    it('should call from input change handler when the from date is entered', async () => {
        const mockOnChange = jest.fn();
        const { getByRole } = render(<DateRangeField id="testing-date-range-from-entered" onChange={mockOnChange} />);

        const from = getByRole('textbox', { name: 'From' });

        const user = userEvent.setup();

        await user.type(from, '02012023{tab}');

        expect(mockOnChange).toHaveBeenCalledWith({ between: expect.objectContaining({ from: '02/01/2023' }) });
    });

    it('should call from input change handler when the from date is changed', async () => {
        const mockOnChange = jest.fn();
        const { getByRole } = render(
            <DateRangeField
                id="testing-date-range-from-change"
                value={{
                    between: {
                        from: '02/17/1990'
                    }
                }}
                onChange={mockOnChange}
            />
        );

        const from = getByRole('textbox', { name: 'From' });

        const user = userEvent.setup();

        await user.clear(from).then(() => user.type(from, '02012023{tab}'));

        expect(mockOnChange).toHaveBeenCalledWith({ between: expect.objectContaining({ from: '02/01/2023' }) });
    });

    it('should call from input change handler when the to date is entered', async () => {
        const mockOnChange = jest.fn();
        const { getByRole } = render(<DateRangeField id="testing-date-range-to-entered" onChange={mockOnChange} />);

        const to = getByRole('textbox', { name: 'To' });

        const user = userEvent.setup();

        await user.type(to, '02012023{tab}');

        expect(mockOnChange).toHaveBeenCalledWith({ between: expect.objectContaining({ to: '02/01/2023' }) });
    });

    it('should call from input change handler when the to date is changed', async () => {
        const mockOnChange = jest.fn();
        const { getByRole } = render(
            <DateRangeField
                id="testing-date-range-to-change"
                value={{
                    between: {
                        from: '02/17/1990',
                        to: '02/17/2000'
                    }
                }}
                onChange={mockOnChange}
            />
        );

        const to = getByRole('textbox', { name: 'To' });

        const user = userEvent.setup();

        await user.clear(to).then(() => user.type(to, '02012023{tab}'));

        expect(mockOnChange).toHaveBeenCalledWith({ between: expect.objectContaining({ to: '02/01/2023' }) });
    });
});
