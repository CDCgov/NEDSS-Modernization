import { render } from '@testing-library/react';
import { axe } from 'jest-axe';
import { NavEntry, NavEntryProps } from './NavEntry';
import { MemoryRouter } from 'react-router-dom';

const Fixture = (props: NavEntryProps) => (
    <MemoryRouter>
        <ul>
            <NavEntry {...props} />
        </ul>
    </MemoryRouter>
);

describe('when a navigation menu item', () => {
    describe('is active', () => {
        it('should render with no accessibility violations', async () => {
            const { container } = render(<Fixture name="internal link" path="/internalRoute" active />);

            expect(await axe(container)).toHaveNoViolations();
        });

        it('should display a link to a modernized page as active', () => {
            const { queryByRole, getByText } = render(<Fixture name="internal link" path="/internalRoute" active />);

            const link = queryByRole('link', { name: 'internal link' });

            expect(link).not.toBeInTheDocument();

            const active = getByText('internal link');

            expect(active).toHaveClass('active');
        });

        it('should display a link to a modernized page as active', () => {
            const { queryByRole, getByText } = render(
                <Fixture name="external link" href="/nbs/externalRoute" active />
            );

            const link = queryByRole('link', { name: 'external link' });

            expect(link).not.toBeInTheDocument();

            const active = getByText('external link');

            expect(active).toHaveClass('active');
        });
    });

    describe('is a link to a modernized page', () => {
        it('should render with no accessibility violations', async () => {
            const { container } = render(<Fixture name="internal link" path="/internalRoute" />);

            expect(await axe(container)).toHaveNoViolations();
        });

        it('should display as a link', () => {
            const { getByRole } = render(<Fixture name="internal link" path="/internalRoute" />);

            const link = getByRole('link', { name: 'internal link' });

            expect(link).toHaveAttribute('href', '/internalRoute');
        });
    });

    describe('is a link to an NBS6 page', () => {
        it('should render with no accessibility violations', async () => {
            const { container } = render(<Fixture name="external link" href="/nbs/externalRoute" />);

            expect(await axe(container)).toHaveNoViolations();
        });

        it('should display as a link', () => {
            const { getByRole } = render(<Fixture name="external link" href="/nbs/externalRoute" />);

            const link = getByRole('link', { name: 'external link' });

            expect(link).toHaveAttribute('href', '/nbs/externalRoute');
        });
    });
});
