import { render, screen } from '@testing-library/react';
import { axe } from 'jest-axe';
import { NavLinkButton, NavLinkButtonProps } from './NavLinkButton';
import { MemoryRouter } from 'react-router';

const Fixture = (props: NavLinkButtonProps) => (
    <MemoryRouter>
        <NavLinkButton {...props} />
    </MemoryRouter>
);

describe('NavLinkButton', () => {
    it('should render with no accessibility violations', async () => {
        const { container } = render(<Fixture to="/">Click me</Fixture>);
        expect(await axe(container)).toHaveNoViolations();
    });

    it('should render the button with the label', () => {
        render(<Fixture to="/">Click me</Fixture>);
        expect(screen.getByRole('link')).toHaveTextContent('Click me');
    });
});
