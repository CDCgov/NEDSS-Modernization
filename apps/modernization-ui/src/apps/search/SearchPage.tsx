import { Outlet } from 'react-router-dom';
import { SearchResultDisplayProvider } from './useSearchResultDisplay';

const SearchPage = () => (
    <SearchResultDisplayProvider paging={{ pageSize: 25 }}>
        <Outlet />
    </SearchResultDisplayProvider>
);

export { SearchPage };
