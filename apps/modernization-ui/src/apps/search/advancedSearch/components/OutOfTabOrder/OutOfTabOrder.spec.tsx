import { render } from '@testing-library/react';
import { OutOfTabOrder } from './OutOfTabOrder';

describe('OuterTabOrder', () => {
    it('renders the component with children', () => {
        const { getByText } = render(
            <OutOfTabOrder submitted={false}>
                <button>Click me</button>
            </OutOfTabOrder>
        );
        expect(getByText('Click me')).toBeInTheDocument();
    });

    it('changes tabIndex of buttons when submitted is true', () => {
        const { getByText } = render(
            <OutOfTabOrder submitted={true}>
                <button>Click me</button>
            </OutOfTabOrder>
        );

        const button = getByText('Click me');
        expect(button.tabIndex).toBe(0);
    });

    it('maintains unchanged tabIndex when submitted remains false', () => {
        const { getByText, rerender } = render(
            <OutOfTabOrder submitted={false}>
                <button>Click me</button>
            </OutOfTabOrder>
        );

        let button = getByText('Click me');
        expect(button.tabIndex).toBe(0);

        rerender(
            <OutOfTabOrder submitted={false}>
                <button>Click me</button>
            </OutOfTabOrder>
        );

        button = getByText('Click me');
        expect(button.tabIndex).toBe(0);
    });
});
