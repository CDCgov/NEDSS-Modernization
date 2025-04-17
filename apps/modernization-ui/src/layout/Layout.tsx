import { Outlet } from 'react-router';
import { ApolloWrapper } from 'providers/ApolloContext';
import { SkipLinkProvider } from 'SkipLink/SkipLinkContext';
import NavBar from 'shared/header/NavBar';
import { ScrollToTop } from './ScrollToTop';
import { AlertProvider } from 'alert/useAlert';
import { PageTitleProvider } from 'PageTitle';

const Layout = () => {
    return (
        <ApolloWrapper>
            <ScrollToTop>
                <AlertProvider>
                    <SkipLinkProvider>
                        <PageTitleProvider>
                            <NavBar />
                            <Outlet />
                        </PageTitleProvider>
                    </SkipLinkProvider>
                </AlertProvider>
            </ScrollToTop>
        </ApolloWrapper>
    );
};

export { Layout };
