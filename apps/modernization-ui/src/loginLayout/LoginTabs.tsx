import TabButton from 'components/TabButton/TabButton';
import { useLocation, useNavigate } from 'react-router-dom';

export const LoginTabs = () => {
    const navigate = useNavigate();
    const location = useLocation();
    const currentPath = location.pathname;

    const handleTab = (type: string) => {
        navigate(`/login/${type}`);
    };

    const isActive = (path: string) => {
        return currentPath.includes(path);
    };

    return (
        <>
            <TabButton
                className="margin-left-0"
                title="About"
                onClick={() => handleTab('about')}
                active={isActive('about')}
            />
            <TabButton title="Our Vision" onClick={() => handleTab('our-vision')} active={isActive('our-vision')} />
            <TabButton
                title="Get Involved"
                onClick={() => handleTab('get-involved')}
                active={isActive('get-involved')}
            />
        </>
    );
};
