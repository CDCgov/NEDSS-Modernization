import { Login } from 'pages/login';
import { Outlet, useLocation, useNavigate } from 'react-router-dom';
import { LoginHeader } from './LoginHeader/LoginHeader';
import classNames from 'classnames';
import style from './loginLayout.module.scss';
import { AlertBanner } from 'apps/page-builder/components/AlertBanner/AlertBanner';
import { tabs } from './tabs';
import { TabNavigation } from 'components/TabNavigation/TabNavigation';

const LoginLayout = () => {
    const navigate = useNavigate();
    const location = useLocation();
    const currentPath = location.pathname;

    const handleTabNavigation = (type: string) => {
        navigate(`/login/${type}`);
    };

    const isActive = (path: string) => {
        return currentPath.includes(path);
    };

    return (
        <>
            <LoginHeader />
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
                        <TabNavigation tabsList={tabs} handleTabNavigation={handleTabNavigation} isActive={isActive} />
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
