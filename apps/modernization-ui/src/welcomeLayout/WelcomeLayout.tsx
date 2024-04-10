import { Outlet } from 'react-router-dom';
import { WelcomeHeader } from './WelcomeHeader/WelcomeHeader';
import style from './welcomeLayout.module.scss';
import { AlertBanner } from 'apps/page-builder/components/AlertBanner/AlertBanner';
import { TabNavigationEntry, TabNavigation } from 'components/TabNavigation/TabNavigation';

const WelcomeLayout = () => {
    return (
        <div className={style.welcome}>
            <header>
                <WelcomeHeader />
            </header>
            <main>
                <AlertBanner type="warning">
                    <small>
                        Please only enter dummy data into the demo site. Please do not enter real PHI/PII data as this
                        is a public site.
                    </small>
                </AlertBanner>
                <div className={style.content}>
                    <h1>NBS Modernization</h1>
                    <TabNavigation>
                        <TabNavigationEntry path={'about'}>About</TabNavigationEntry>
                        <TabNavigationEntry path={'our-vision'}>Our Vision</TabNavigationEntry>
                        <TabNavigationEntry path={'get-involved'}>Get Involved</TabNavigationEntry>
                    </TabNavigation>
                    <div className={style.container}>
                        <Outlet />
                    </div>
                </div>
            </main>
            <aside>
                <span>Sign in / Sign up placeholder</span>
            </aside>
        </div>
    );
};

export { WelcomeLayout };
