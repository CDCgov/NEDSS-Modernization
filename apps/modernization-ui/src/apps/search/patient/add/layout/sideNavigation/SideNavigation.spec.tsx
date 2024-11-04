import { render } from '@testing-library/react';
import { NavEntry, SideNavigation } from './SideNavigation';
import { MemoryRouter } from 'react-router-dom';

describe('SideNavigation', () => {
    const Fixture = () => (
        <MemoryRouter>
            <SideNavigation title="Side navigation test">
                <NavEntry name="active entry" active />
                <NavEntry name="internal link" href="/internalRoute" />
                <NavEntry name="external link" href="/nbs/externalRoute" external />
            </SideNavigation>
        </MemoryRouter>
    );

    it('should render title', () => {
        const { getByRole } = render(<Fixture />);
        expect(getByRole('heading')).toHaveTextContent('Side navigation test');
    });

    it('should render each entries name', () => {
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
