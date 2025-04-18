import { render } from '@testing-library/react';
import { Hint } from './Hint';
import userEvent from '@testing-library/user-event';

describe('Hint', () => {
    it('should display the info_outline icon', () => {
        const { container } = render(<Hint>hint content</Hint>);

        const icon = container.querySelector('svg use');
        expect(icon).toHaveAttribute('xlink:href', 'undefined#info_outline');
    });

    it('should display content on mouseover', async () => {
        const user = userEvent.setup();
        const { queryByText, container } = render(<Hint>hint content</Hint>);
        const icon = container.querySelector('svg');

        expect(queryByText('hint content')).not.toBeInTheDocument();
        await user.hover(icon!);
        expect(queryByText('hint content')).toBeInTheDocument();
    });

    it('should set top value to default', async () => {
        const user = userEvent.setup();
        const { getByText, container } = render(<Hint>hint content</Hint>);
        const icon = container.querySelector('svg');

        await user.hover(icon!);
        const content = getByText('hint content');
        expect(content).toHaveStyle('top: 26px');
    });

    it('should offset top value based on provided marginTop', async () => {
        const user = userEvent.setup();
        const { getByText, container } = render(<Hint marginTop={30}>hint content</Hint>);
        const icon = container.querySelector('svg');

        await user.hover(icon!);
        const content = getByText('hint content');
        expect(content).toHaveStyle('top: 56px');
    });

    it('should set left value to default', async () => {
        const user = userEvent.setup();
        const { getByText, container } = render(<Hint>hint content</Hint>);
        const icon = container.querySelector('svg');

        await user.hover(icon!);
        const content = getByText('hint content');
        expect(content).toHaveStyle('left: 0px');
    });

    it('should offset left value based on provided marginLeft', async () => {
        const user = userEvent.setup();
        const { getByText, container } = render(<Hint marginLeft={-20}>hint content</Hint>);
        const icon = container.querySelector('svg');

        await user.hover(icon!);
        const content = getByText('hint content');
        expect(content).toHaveStyle('left: -20px');
    });

    it('should set left appropriately when position left is specified', async () => {
        const user = userEvent.setup();
        const { getByText, container } = render(<Hint position="left">hint content</Hint>);
        const icon = container.querySelector('svg');

        await user.hover(icon!);
        const content = getByText('hint content');
        expect(content).toHaveStyle('left: -263px');
    });

    it('should set left appropriately when position left and marginLeft is specified', async () => {
        const user = userEvent.setup();
        const { getByText, container } = render(
            <Hint position="left" marginLeft={100}>
                hint content
            </Hint>
        );
        const icon = container.querySelector('svg');

        await user.hover(icon!);
        const content = getByText('hint content');
        expect(content).toHaveStyle('left: -163px');
    });
});
