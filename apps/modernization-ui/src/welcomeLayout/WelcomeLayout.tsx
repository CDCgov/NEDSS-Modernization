import { Outlet } from 'react-router-dom';
import { WelcomeHeader } from './WelcomeHeader/WelcomeHeader';
import classNames from 'classnames';
import style from './welcomeLayout.module.scss';
import { AlertBanner } from 'apps/page-builder/components/AlertBanner/AlertBanner';
import { TabNavigationEntry, TabNavigation } from 'components/TabNavigation/TabNavigation';

const WelcomeLayout = () => {
    return (
        <>
            <WelcomeHeader />
            <div className="display-flex">
                <div className="width-full">
                    <AlertBanner type="warning">
                        <small>
                            Please only enter dummy data into the demo site. Please do not enter real PHI/PII data as
                            this is a public site.
                        </small>
                    </AlertBanner>
                    <div className="padding-x-5">
                        <h1>NBS Modernization</h1>
                        <TabNavigation>
                            <TabNavigationEntry path={'about'}>About</TabNavigationEntry>
                            <TabNavigationEntry path={'our-vision'}>Our Vision</TabNavigationEntry>
                            <TabNavigationEntry path={'get-involved'}>Get Involved</TabNavigationEntry>
                        </TabNavigation>
                        <Outlet />
                    </div>
                </div>
                <div className={classNames(style.login)}>
                    <span>Sign in / Sign up placeholder</span>
                </div>
            </div>
        </>
    );
};

export { WelcomeLayout };
