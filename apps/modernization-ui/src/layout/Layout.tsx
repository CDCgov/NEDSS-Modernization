import { Outlet } from 'react-router';
import { SkipLinkProvider } from 'SkipLink/SkipLinkContext';
import { NavBar } from 'shared/header/NavBar';
import { AlertProvider } from 'libs/alert';
import { PageProvider } from 'page';

const Layout = () => {
    return (
        <SkipLinkProvider>
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
