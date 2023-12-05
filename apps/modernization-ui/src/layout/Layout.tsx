import { Outlet } from 'react-router-dom';
import { AlertProvider } from 'alert';
import ApolloWrapper from 'providers/ApolloContext';
import { SkipLinkProvider } from 'SkipLink/SkipLinkContext';
import NavBar from 'shared/header/NavBar';

const Layout = () => {
    return (
        <ApolloWrapper>
            <AlertProvider>
                <SkipLinkProvider>
                    <NavBar />
                    <Outlet />
                </SkipLinkProvider>
            </AlertProvider>
        </ApolloWrapper>
    );
};

export { Layout };
