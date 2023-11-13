import { render } from '@testing-library/react';
import { ReactNode } from 'react';
import { MemoryRouter } from 'react-router-dom';

import { SideNavigation, NavigationEntry } from './SideNavigation';

const WithActiveRoute = ({ active = '/active', children }: { active?: string; children: ReactNode }) => (
    <MemoryRouter initialEntries={[active]}>{children}</MemoryRouter>
);

describe('SideNavigation', () => {
    it('should render a title', async () => {
        const { findByText } = render(
            <WithActiveRoute>
                <SideNavigation title="test title" />
            </WithActiveRoute>
        );

        const entry = await findByText('test title');
        expect(entry).toBeInTheDocument();
    });

    it('should render nav entry', async () => {
        const { findByText } = render(
            <WithActiveRoute>
                <SideNavigation title="test title">
                    <NavigationEntry path="/some-href">test label</NavigationEntry>
                </SideNavigation>
            </WithActiveRoute>
        );

        const entry = await findByText('test label');

        expect(entry).toBeInTheDocument();
        expect(entry).toHaveAttribute('href', '/some-href');
        expect(entry.parentElement).not.toHaveClass('active');
    });

    it('should set active based on path', async () => {
        const { findByText } = render(
            <WithActiveRoute active="/some-href">
                <SideNavigation title="test title">
                    <NavigationEntry path="/some-href">test label</NavigationEntry>
                    <NavigationEntry path="/some-other-href">another label</NavigationEntry>
                </SideNavigation>
            </WithActiveRoute>
        );

        const active = await findByText('test label');
        expect(active.parentElement).toHaveClass('active');
        const other = await findByText('another label');
        expect(other.parentElement).not.toHaveClass('active');
    });
});
