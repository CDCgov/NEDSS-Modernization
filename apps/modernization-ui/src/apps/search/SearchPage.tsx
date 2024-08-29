import { Outlet } from 'react-router-dom';
import { SearchResultDisplayProvider } from './useSearchResultDisplay';

const SearchPage = () => (
    <SearchResultDisplayProvider sorting={{ appendToUrl: true }} paging={{ appendToUrl: true }}>
        <Outlet />
    </SearchResultDisplayProvider>
);

export { SearchPage };
