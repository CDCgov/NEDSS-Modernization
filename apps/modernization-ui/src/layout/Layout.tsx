import { Outlet } from 'react-router';
import { SkipLinkProvider } from 'SkipLink/SkipLinkContext';
import { NavBar } from 'shared/header/NavBar';
import { ScrollToTop } from './ScrollToTop';
import { AlertProvider } from 'libs/alert';
import { PageProvider } from 'page';

const Layout = () => {
    return (
        <SkipLinkProvider>
            <ScrollToTop />
            <AlertProvider>
                <PageProvider>
                    <NavBar />
                    <Outlet />
                </PageProvider>
            </AlertProvider>
        </SkipLinkProvider>
    );
};

export { Layout };
