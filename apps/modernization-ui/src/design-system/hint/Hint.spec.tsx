import { ComponentProps } from 'react';
import { render } from '@testing-library/react';
import { axe } from 'vitest-axe';
import { Hint } from './Hint';

const Fixture = ({ position = 'right', target }: Partial<ComponentProps<typeof Hint>>) => {
    return (
        <Hint id="hint" position={position} target={target}>
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
        expect(icon?.getAttribute('xlink:href')).toContain('sprite.svg#info_outline');
    });

    it('should display the custom target instead of the icon when provided', () => {
        const { getByText, container } = render(<Fixture target={<span>custom target</span>} />);
        const icon = container.querySelector('svg use');
        expect(getByText('custom target')).toBeInTheDocument();
        expect(icon).not.toBeInTheDocument();
    });
});
