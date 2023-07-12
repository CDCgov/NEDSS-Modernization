import { Outlet } from 'react-router-dom';
import { PagesProvider } from './PagesContext';

const PageBuilderContextProvider = () => {
    return (
        <PagesProvider>
            <Outlet />
        </PagesProvider>
    );
};

export default PageBuilderContextProvider;
