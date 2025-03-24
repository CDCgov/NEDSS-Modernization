import { Outlet } from 'react-router';
import { AlertProvider } from 'alert';
import { ApolloWrapper } from 'providers/ApolloContext';
import { SkipLinkProvider } from 'SkipLink/SkipLinkContext';
import NavBar from 'shared/header/NavBar';
import { ScrollToTop } from './ScrollToTop';

const Layout = () => {
    return (
        <ApolloWrapper>
            <ScrollToTop>
                <AlertProvider>
                    <SkipLinkProvider>
                        <NavBar />
                        <Outlet />
                    </SkipLinkProvider>
                </AlertProvider>
            </ScrollToTop>
        </ApolloWrapper>
    );
};

export { Layout };
