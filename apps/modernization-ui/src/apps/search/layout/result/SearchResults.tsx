import { ReactNode } from 'react';
import { useSearch } from 'apps/search/useSearch';
import { SearchResultsHeader } from './header/SearchResultsHeader';

import styles from './search-results.module.scss';

type Props = {
    children: ReactNode;
};

const SearchResults = ({ children }: Props) => {
    const { view, results } = useSearch();

    return (
        <div className={styles.results}>
            <SearchResultsHeader view={view} results={results} />
            <main>{children}</main>
        </div>
    );
};

export { SearchResults };
