import { Outlet } from 'react-router';
import { ApolloWrapper } from 'providers/ApolloContext';
import { SkipLinkProvider } from 'SkipLink/SkipLinkContext';
import { NavBar } from 'shared/header/NavBar';
import { ScrollToTop } from './ScrollToTop';
import { AlertProvider } from 'alert/useAlert';
import { PageProvider } from 'page';

const Layout = () => {
    return (
        <ApolloWrapper>
            <ScrollToTop />
            <AlertProvider>
                <SkipLinkProvider>
                    <PageProvider>
                        <NavBar />
                        <Outlet />
                    </PageProvider>
                </SkipLinkProvider>
            </AlertProvider>
        </ApolloWrapper>
    );
};

export { Layout };
