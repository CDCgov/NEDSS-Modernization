import { act, render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { axe } from 'jest-axe';
import { DateRangeEntry } from './DateRangeEntry';

describe('DateRangeEntry Component', () => {
    it('should render with no accessibility violations', async () => {
        const { container } = render(<DateRangeEntry id="testing-date-range-accessibility" onChange={jest.fn()} />);

        expect(await axe(container)).toHaveNoViolations();
    });

    it('should render the component with initial values', () => {
        const { getByRole } = render(
            <DateRangeEntry
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

    it('should call from input change handler when the from date is entered', () => {
        const mockOnChange = jest.fn();
        const { getByRole } = render(<DateRangeEntry id="testing-date-range-from-entered" onChange={mockOnChange} />);

        const from = getByRole('textbox', { name: 'From' });

        act(() => {
            userEvent.type(from, '02012023');
            userEvent.tab();
        });

        expect(mockOnChange).toHaveBeenCalledWith({ between: expect.objectContaining({ from: '02/01/2023' }) });
    });

    it('should call from input change handler when the from date is changed', () => {
        const mockOnChange = jest.fn();
        const { getByRole } = render(
            <DateRangeEntry
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

        act(() => {
            userEvent.clear(from);
            userEvent.type(from, '02012023');
            userEvent.tab();
        });

        expect(mockOnChange).toHaveBeenCalledWith({ between: expect.objectContaining({ from: '02/01/2023' }) });
    });

    it('should call from input change handler when the to date is entered', () => {
        const mockOnChange = jest.fn();
        const { getByRole } = render(<DateRangeEntry id="testing-date-range-to-entered" onChange={mockOnChange} />);

        const to = getByRole('textbox', { name: 'To' });

        act(() => {
            userEvent.type(to, '02012023');
            userEvent.tab();
        });

        expect(mockOnChange).toHaveBeenCalledWith({ between: expect.objectContaining({ to: '02/01/2023' }) });
    });

    it('should call from input change handler when the to date is changed', () => {
        const mockOnChange = jest.fn();
        const { getByRole } = render(
            <DateRangeEntry
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

        act(() => {
            userEvent.clear(to);
            userEvent.type(to, '02012023');
            userEvent.tab();
        });

        expect(mockOnChange).toHaveBeenCalledWith({ between: expect.objectContaining({ to: '02/01/2023' }) });
    });
});
