import { Outlet } from 'react-router';
import { SkipLinkProvider } from 'SkipLink/SkipLinkContext';
import { NavBar } from 'shared/header/NavBar';
import { AlertProvider } from 'libs/alert';
import { PageProvider } from 'page';

const Layout = () => {
    return (
        <AlertProvider>
            <PageProvider>
                <SkipLinkProvider>
                    <NavBar />
                    <Outlet />
                </SkipLinkProvider>
            </PageProvider>
        </AlertProvider>
    );
};

export { Layout };
