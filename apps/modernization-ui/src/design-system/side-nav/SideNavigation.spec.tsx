import { render } from '@testing-library/react';
import { axe } from 'jest-axe';
import { MemoryRouter } from 'react-router-dom';
import { SideNavigation } from './SideNavigation';
import { NavEntry } from './NavEntry';

describe('SideNavigation', () => {
    const Fixture = () => (
        <MemoryRouter>
            <SideNavigation title="Side navigation test">
                <NavEntry name="active entry" active />
                <NavEntry name="internal link" path="/internalRoute" />
                <NavEntry name="external link" href="/nbs/externalRoute" />
            </SideNavigation>
        </MemoryRouter>
    );

    it('should render with no accessibility violations', async () => {
        const { container } = render(<Fixture />);

        expect(await axe(container)).toHaveNoViolations();
    });

    it('should render title', () => {
        const { getByRole } = render(<Fixture />);
        expect(getByRole('heading')).toHaveTextContent('Side navigation test');
    });

    it('should render each entry', () => {
        const { getByRole } = render(<Fixture />);
        const list = getByRole('list');
        expect(list.children[0]).toHaveTextContent('active entry');
        expect(list.children[1]).toHaveTextContent('internal link');
        expect(list.children[2]).toHaveTextContent('external link');
    });

    it('should create links for internal and external entries', () => {
        const { getByText } = render(<Fixture />);
        expect(getByText('active entry')).not.toHaveAttribute('href');
        expect(getByText('internal link')).toHaveAttribute('href', '/internalRoute');
        expect(getByText('external link')).toHaveAttribute('href', '/nbs/externalRoute');
    });
});
