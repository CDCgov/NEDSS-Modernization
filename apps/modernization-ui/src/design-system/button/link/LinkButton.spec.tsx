import { render, screen } from '@testing-library/react';
import { axe } from 'jest-axe';
import { LinkButton } from './LinkButton';

describe('LinkButton', () => {
    it('should render with no accessibility violations', async () => {
        const { container } = render(<LinkButton href="#">Click me</LinkButton>);
        expect(await axe(container)).toHaveNoViolations();
    });

    it('should render the button with the label', () => {
        render(<LinkButton href="#">Click me</LinkButton>);
        expect(screen.getByRole('link')).toHaveTextContent('Click me');
    });
});
