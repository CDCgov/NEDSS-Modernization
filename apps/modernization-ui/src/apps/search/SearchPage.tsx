import { Outlet } from 'react-router-dom';
import { SearchResultDisplayProvider } from './useSearchResultDisplay';

const SearchPage = () => (
    <SearchResultDisplayProvider>
        <Outlet />
    </SearchResultDisplayProvider>
);

export { SearchPage };
