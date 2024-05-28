import { render, waitFor } from '@testing-library/react';
import { OutOfTabOrder } from './OutOfTabOrder';

describe('OutOfTabOrder', () => {
    it('renders the component with children', () => {
        const { getByText } = render(
            <OutOfTabOrder focusable={true} selector="button">
                <button>Click me</button>
            </OutOfTabOrder>
        );
        expect(getByText('Click me')).toBeInTheDocument();
    });

    it('sets tabIndex of elements with specified selector to -1 when focusable is false', async () => {
        const { getByText } = render(
            <OutOfTabOrder focusable={false} selector="button">
                <button>Click me</button>
            </OutOfTabOrder>
        );

        const button = getByText('Click me');
        await waitFor(() => {
            expect(button.tabIndex).toBe(-1);
        });
    });

    it('does not change tabIndex of elements with specified selector when focusable is true', async () => {
        const { getByText } = render(
            <OutOfTabOrder focusable={true} selector="button">
                <button>Click me</button>
            </OutOfTabOrder>
        );

        const button = getByText('Click me');
        await waitFor(() => {
            expect(button.tabIndex).toBe(0);
        });
    });

    it('resets tabIndex to 0 when focusable changes from false to true', async () => {
        const { getByText, rerender } = render(
            <OutOfTabOrder focusable={false} selector="button">
                <button>Click me</button>
            </OutOfTabOrder>
        );

        const button = getByText('Click me');
        await waitFor(() => {
            expect(button.tabIndex).toBe(-1);
        });

        rerender(
            <OutOfTabOrder focusable={true} selector="button">
                <button>Click me</button>
            </OutOfTabOrder>
        );

        await new Promise((resolve) => setTimeout(resolve, 100));

        expect(button.tabIndex).toBe(-1);
    });

    it('maintains unchanged tabIndex of elements with specified selector when focusable remains true', async () => {
        const { getByText, rerender } = render(
            <OutOfTabOrder focusable={true} selector="button">
                <button>Click me</button>
            </OutOfTabOrder>
        );

        let button = getByText('Click me');
        await waitFor(() => {
            expect(button.tabIndex).toBe(0);
        });

        rerender(
            <OutOfTabOrder focusable={true} selector="button">
                <button>Click me</button>
            </OutOfTabOrder>
        );

        button = getByText('Click me');
        expect(button.tabIndex).toBe(0);
    });
});
