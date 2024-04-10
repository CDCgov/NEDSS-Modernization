import { Login } from 'pages/login';
import { Outlet } from 'react-router-dom';
import { LoginTabs } from './LoginTabs';
import { LoginHeader } from './LoginHeader/LoginHeader';
import classNames from 'classnames';
import style from './loginLayout.module.scss';
import { AlertBanner } from 'apps/page-builder/components/AlertBanner/AlertBanner';

const LoginLayout = () => {
    return (
        <>
            <LoginHeader />
            <div className="display-flex">
                <div className="width-full">
                    <AlertBanner type="warning">
                        <small>
                            Please only enter dummy data into the demo site. Please do not enter real PHI/PIl data as
                            this is a public site.
                        </small>
                    </AlertBanner>
                    <div className="padding-x-5">
                        <h1>NBS Modernization</h1>
                        <LoginTabs />
                        <Outlet />
                    </div>
                </div>
                <div className={classNames(style.login)}>
                    <Login />
                </div>
            </div>
        </>
    );
};

export { LoginLayout };
