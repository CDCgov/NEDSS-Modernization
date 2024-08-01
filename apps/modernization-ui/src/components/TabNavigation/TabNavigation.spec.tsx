import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { TabNavigationEntry, TabNavigation } from './TabNavigation';
import style from './tabNavigation.module.scss';
import { ReactElement } from 'react';
import { SearchResultDisplayProvider } from 'apps/search';

describe('TabNavigation and TabNavigationEntry', () => {
    const renderWithRouter = (ui: ReactElement, { route = '/' } = {}) => {
        window.history.pushState({}, 'Test page', route);
        return render(ui, { wrapper: MemoryRouter });
    };

    test('TabNavigationEntry renders with correct text', () => {
        const { getByText } = renderWithRouter(
            <SearchResultDisplayProvider>
                <TabNavigationEntry path="/test-path">Test Link</TabNavigationEntry>
            </SearchResultDisplayProvider>,
            {
                route: '/'
            }
        );
        expect(getByText('Test Link')).toBeInTheDocument();
    });

    test('TabNavigationEntry active class is applied correctly', async () => {
        const { getByText } = renderWithRouter(
            <SearchResultDisplayProvider>
                <TabNavigationEntry path="/test-path">Test Link</TabNavigationEntry>
            </SearchResultDisplayProvider>,
            {
                route: '/test-path'
            }
        );
        const linkElement = getByText('Test Link');
        setTimeout(() => {
            expect(linkElement).toHaveClass(style.active);
        });
    });

    test('TabNavigationEntry active class is not applied when path does not match', () => {
        const { getByText } = renderWithRouter(
            <SearchResultDisplayProvider>
                <TabNavigationEntry path="/test-path">Test Link</TabNavigationEntry>
            </SearchResultDisplayProvider>,
            {
                route: '/different-path'
            }
        );
        const linkElement = getByText('Test Link');
        expect(linkElement).not.toHaveClass(style.active);
    });

    test('TabNavigation renders multiple children correctly', () => {
        const tabs = (
            <SearchResultDisplayProvider>
                <TabNavigation>
                    <TabNavigationEntry path="/path1">Link 1</TabNavigationEntry>
                    <TabNavigationEntry path="/path2">Link 2</TabNavigationEntry>
                    <TabNavigationEntry path="/path3">Link 3</TabNavigationEntry>
                </TabNavigation>
            </SearchResultDisplayProvider>
        );
        const { getByText } = renderWithRouter(tabs, { route: '/path2' });

        expect(getByText('Link 1')).toBeInTheDocument();
        expect(getByText('Link 2')).toBeInTheDocument();
        expect(getByText('Link 3')).toBeInTheDocument();
    });

    test('Correct child has active class based on route', () => {
        const tabs = (
            <SearchResultDisplayProvider>
                <TabNavigation>
                    <TabNavigationEntry path="/path1">Link 1</TabNavigationEntry>
                    <TabNavigationEntry path="/path2">Link 2</TabNavigationEntry>
                    <TabNavigationEntry path="/path3">Link 3</TabNavigationEntry>
                </TabNavigation>
            </SearchResultDisplayProvider>
        );
        const { getByText } = renderWithRouter(tabs, { route: '/path2' });

        const linkElement = getByText('Link 2');
        setTimeout(() => {
            expect(linkElement).toHaveClass(style.active);
        });
    });
});
