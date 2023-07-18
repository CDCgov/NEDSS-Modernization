import { Outlet } from 'react-router-dom';
import { PagesProvider } from './PagesContext';

const PageBuilderContextProvider = () => {
    return (
        // Wrap other Providers here: Questions, Value Sets, Templates et al.
        <PagesProvider>
            <Outlet />
        </PagesProvider>
    );
};

export default PageBuilderContextProvider;
