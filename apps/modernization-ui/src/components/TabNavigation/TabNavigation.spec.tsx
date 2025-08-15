import { render, waitFor } from '@testing-library/react';
import { MemoryRouter } from 'react-router';
import { TabNavigationEntry, TabNavigation } from './TabNavigation';
import style from './tabNavigation.module.scss';
import { ReactElement } from 'react';

const renderWithRouter = (ui: ReactElement, { route = '/' } = {}) => {
    return render(ui, {
        wrapper: ({ children }) => <MemoryRouter initialEntries={[route]}>{children}</MemoryRouter>
    });
};

describe('TabNavigation and TabNavigationEntry', () => {
    test('TabNavigationEntry renders with correct text', () => {
        const { getByText } = renderWithRouter(<TabNavigationEntry path="/test-path">Test Link</TabNavigationEntry>, {
            route: '/'
        });
        expect(getByText('Test Link')).toBeInTheDocument();
    });

    test('TabNavigationEntry active class is applied correctly', async () => {
        const { getByText } = renderWithRouter(<TabNavigationEntry path="/test-path">Test Link</TabNavigationEntry>, {
            route: '/test-path'
        });
        const linkElement = getByText('Test Link');
        await waitFor(() => {
            expect(linkElement).toHaveClass(style.active);
        });
    });

    test('TabNavigationEntry active class is not applied when path does not match', async () => {
        const { getByText } = renderWithRouter(<TabNavigationEntry path="/test-path">Test Link</TabNavigationEntry>, {
            route: '/different-path'
        });
        const linkElement = getByText('Test Link');
        await waitFor(() => {
            expect(linkElement).not.toHaveClass(style.active);
        });
    });

    test('TabNavigation renders multiple children correctly', () => {
        const tabs = (
            <TabNavigation>
                <TabNavigationEntry path="/path1">Link 1</TabNavigationEntry>
                <TabNavigationEntry path="/path2">Link 2</TabNavigationEntry>
                <TabNavigationEntry path="/path3">Link 3</TabNavigationEntry>
            </TabNavigation>
        );
        const { getByText } = renderWithRouter(tabs, { route: '/path2' });

        expect(getByText('Link 1')).toBeInTheDocument();
        expect(getByText('Link 2')).toBeInTheDocument();
        expect(getByText('Link 3')).toBeInTheDocument();
    });

    test('Correct child has active class based on route', async () => {
        const tabs = (
            <TabNavigation>
                <TabNavigationEntry path="/path1">Link 1</TabNavigationEntry>
                <TabNavigationEntry path="/path2">Link 2</TabNavigationEntry>
                <TabNavigationEntry path="/path3">Link 3</TabNavigationEntry>
            </TabNavigation>
        );
        const { getByText } = renderWithRouter(tabs, { route: '/path2' });

        const linkElement = getByText('Link 2');
        // Wait for the next tick and then for the class to be applied
        await new Promise((resolve) => setTimeout(resolve, 0));
        await waitFor(() => {
            expect(linkElement).toHaveClass(style.active);
        });
    });
});
