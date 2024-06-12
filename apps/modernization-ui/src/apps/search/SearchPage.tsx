import { SearchProvider } from './useSearch';
import { Outlet } from 'react-router-dom';

const SearchPage = () => (
    <SearchProvider>
        <Outlet />
    </SearchProvider>
);

export { SearchPage };
