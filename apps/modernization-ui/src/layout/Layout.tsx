import { Outlet } from 'react-router';
import { SkipLinkProvider } from 'SkipLink/SkipLinkContext';
import { NavBar } from 'shared/header/NavBar';
import { MainContentContainer } from './MainContentContainer';
import { AlertProvider } from 'libs/alert';
import { PageProvider } from 'page';

const Layout = () => {
    return (
        <MainContentContainer>
            <SkipLinkProvider>
                <AlertProvider>
                    <PageProvider>
                        <NavBar />
                        <Outlet />
                    </PageProvider>
                </AlertProvider>
            </SkipLinkProvider>
        </MainContentContainer>
    );
};

export { Layout };
