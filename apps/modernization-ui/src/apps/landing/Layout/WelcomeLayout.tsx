import { useState } from 'react';
import { Outlet } from 'react-router-dom';
import { TabNavigationEntry, TabNavigation } from 'components/TabNavigation/TabNavigation';
import { SignIn } from 'apps/landing/SignIn/SignIn';
import { SignUp } from 'apps/landing/SignUp/SignUp';
import { LoginWrapper } from './LoginWrapper';

import style from './welcomeLayout.module.scss';
import { SkipLinkProvider } from 'SkipLink/SkipLinkContext';

const WelcomeLayout = () => {
    const [event, setEvent] = useState('login');

    return (
        <SkipLinkProvider>
            <LoginWrapper header={<h1>Welcome to the NBS 7 demo site</h1>}>
                <div className={style.welcome}>
                    <main>
                        <div className={style.information}>
                            <TabNavigation className={style.tabs}>
                                <TabNavigationEntry path={'about'}>About</TabNavigationEntry>
                                <TabNavigationEntry path={'learn'}>Learn more</TabNavigationEntry>
                            </TabNavigation>
                            <div className={style['tab-content']}>
                                <Outlet />
                            </div>
                        </div>
                    </main>
                    <aside>
                        {event === 'login' ? <SignIn handleWelcomeEvent={(event) => setEvent(event)} /> : <SignUp />}
                        <div className={style.agency}>
                            <img src="/cdc.svg" alt="" />
                            <span>Centers for Disease Control and Prevention</span>
                        </div>
                    </aside>
                </div>
            </LoginWrapper>
        </SkipLinkProvider>
    );
};

export { WelcomeLayout };
