import { render } from '@testing-library/react';
import { ReactNode } from 'react';
import { MemoryRouter } from 'react-router-dom';

import { SideNavigation, NavigationEntry } from './SideNavigation';

const WithActiveRoute = ({ active = '/active', children }: { active?: string; children: ReactNode }) => (
    <MemoryRouter initialEntries={[active]}>{children}</MemoryRouter>
);

describe('SideNavigation', () => {
    it('should render a title', () => {
        const { getByText } = render(
            <WithActiveRoute>
                <SideNavigation title="test title" />
            </WithActiveRoute>
        );

        const entry = getByText('test title');
        expect(entry).toBeInTheDocument();
    });

    it('should render nav entry', () => {
        const { getByText } = render(
            <WithActiveRoute>
                <SideNavigation title="test title">
                    <NavigationEntry path="/some-href">test label</NavigationEntry>
                </SideNavigation>
            </WithActiveRoute>
        );

        const entry = getByText('test label');

        expect(entry).toBeInTheDocument();
        expect(entry).toHaveAttribute('href', '/some-href');
        expect(entry.parentElement).not.toHaveClass('active');
    });

    it('should set active based on path', () => {
        const { getByText } = render(
            <WithActiveRoute active="/some-href">
                <SideNavigation title="test title">
                    <NavigationEntry path="/some-href">test label</NavigationEntry>
                    <NavigationEntry path="/some-other-href">another label</NavigationEntry>
                </SideNavigation>
            </WithActiveRoute>
        );

        const active = getByText('test label');
        expect(active.parentElement).toHaveClass('active');
        const other = getByText('another label');
        expect(other.parentElement).not.toHaveClass('active');
    });

    it('should show aria label for title', () => {
        const { getByText } = render(
            <WithActiveRoute active="/some-href">
                <SideNavigation title="test title">
                    <NavigationEntry path="/some-href">test label</NavigationEntry>
                    <NavigationEntry path="/some-other-href">another label</NavigationEntry>
                </SideNavigation>
            </WithActiveRoute>
        );
        expect(getByText('test title')).toHaveAttribute('aria-label', 'test title');
    });
});
