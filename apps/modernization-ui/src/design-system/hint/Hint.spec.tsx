import { render } from '@testing-library/react';
import { Hint } from './Hint';
import userEvent from '@testing-library/user-event';
import { ComponentProps } from 'react';
import { axe } from 'jest-axe';

const Fixture = ({
    marginTop = 0,
    marginLeft = 0,
    position = 'right',
    target
}: Partial<ComponentProps<typeof Hint>>) => {
    return (
        <Hint id="hint" marginTop={marginTop} marginLeft={marginLeft} position={position} target={target}>
            hint content
        </Hint>
    );
};

describe('Hint', () => {
    it('should render with no accessibility violations', async () => {
        const { container } = render(<Fixture />);

        expect(await axe(container)).toHaveNoViolations();
    });

    it('should display the info_outline icon by default', () => {
        const { container } = render(<Fixture />);

        const icon = container.querySelector('svg use');
        expect(icon).toHaveAttribute('xlink:href', 'undefined#info_outline');
    });

    it('should display the custom target instead of the icon when provided', () => {
        const { getByText, container } = render(<Fixture target={<span>custom target</span>} />);
        const icon = container.querySelector('svg use');
        expect(getByText('custom target')).toBeInTheDocument();
        expect(icon).not.toBeInTheDocument();
    });

    it('should display content on mouseover', async () => {
        const user = userEvent.setup();
        const { queryByText, container } = render(<Fixture />);
        const target = container.querySelector('.target');

        expect(queryByText('hint content')).not.toBeInTheDocument();
        await user.hover(target!);
        expect(queryByText('hint content')).toBeInTheDocument();
    });

    it('should set top value to default', async () => {
        const user = userEvent.setup();
        const { getByText, container } = render(<Fixture />);
        const target = container.querySelector('.target');

        await user.hover(target!);
        const content = getByText('hint content');
        expect(content).toHaveStyle('top: 26px');
    });

    it('should offset top value based on provided marginTop', async () => {
        const user = userEvent.setup();
        const { getByText, container } = render(<Fixture marginTop={30} />);
        const target = container.querySelector('.target');

        await user.hover(target!);
        const content = getByText('hint content');
        expect(content).toHaveStyle('top: 56px');
    });

    it('should set left value to default', async () => {
        const user = userEvent.setup();
        const { getByText, container } = render(<Fixture />);
        const target = container.querySelector('.target');

        await user.hover(target!);
        const content = getByText('hint content');
        expect(content).toHaveStyle('left: 0px');
    });

    it('should offset left value based on provided marginLeft', async () => {
        const user = userEvent.setup();
        const { getByText, container } = render(<Fixture marginLeft={-20} />);
        const target = container.querySelector('.target');

        await user.hover(target!);
        const content = getByText('hint content');
        expect(content).toHaveStyle('left: -20px');
    });

    it('should set left appropriately when position left is specified', async () => {
        const user = userEvent.setup();
        const { getByText, container } = render(<Fixture position="left" />);
        const target = container.querySelector('.target');

        await user.hover(target!);
        const content = getByText('hint content');
        expect(content).toHaveStyle('left: -263px');
    });

    it('should set left appropriately when position left and marginLeft is specified', async () => {
        const user = userEvent.setup();
        const { getByText, container } = render(<Fixture position="left" marginLeft={100} />);
        const icon = container.querySelector('svg');

        await user.hover(icon!);
        const content = getByText('hint content');
        expect(content).toHaveStyle('left: -163px');
    });
});
