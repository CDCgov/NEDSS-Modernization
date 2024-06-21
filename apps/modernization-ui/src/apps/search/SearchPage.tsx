import { SearchProvider } from './useSearch';
import { Outlet } from 'react-router-dom';

const SearchPage = () => (
    <SearchProvider paging={{ pageSize: 25 }}>
        <Outlet />
    </SearchProvider>
);

export { SearchPage };
