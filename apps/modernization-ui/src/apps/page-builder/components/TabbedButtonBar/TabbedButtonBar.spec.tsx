import { act, render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { TabbedButtonBar } from './TabbedButtonBar';

describe('Tabbed button bar', () => {
    it('should display a button for each entry', () => {
        const { getAllByRole } = render(<TabbedButtonBar entries={['1', '2']} onChange={() => {}} />);
        const buttons = getAllByRole('button');
        expect(buttons[0]).toHaveTextContent('1');
        expect(buttons[1]).toHaveTextContent('2');
        expect(buttons.length).toBe(2);
    });

    it('should emit onChange events', async () => {
        const mockFn = jest.fn();
        const { getAllByRole } = render(<TabbedButtonBar entries={['1', '2']} onChange={mockFn} />);
        const buttons = getAllByRole('button');
        act(() => {
            userEvent.click(buttons[1]);
        });
        expect(mockFn).toBeCalledWith('2');
    });

    it('should set active class on click', async () => {
        const { getAllByRole } = render(<TabbedButtonBar entries={['1', '2']} onChange={() => {}} />);
        const buttons = getAllByRole('button');
        expect(buttons[0]).toHaveClass('active');
        expect(buttons[1]).not.toHaveClass('active');
        act(() => {
            userEvent.click(buttons[1]);
        });
        expect(buttons[0]).not.toHaveClass('active');
        expect(buttons[1]).toHaveClass('active');
    });
});
