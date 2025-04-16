import { Outlet } from 'react-router';
import { ApolloWrapper } from 'providers/ApolloContext';
import { SkipLinkProvider } from 'SkipLink/SkipLinkContext';
import NavBar from 'shared/header/NavBar';
import { ScrollToTop } from './ScrollToTop';
import { AlertProvider } from 'alert/useAlert';

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
