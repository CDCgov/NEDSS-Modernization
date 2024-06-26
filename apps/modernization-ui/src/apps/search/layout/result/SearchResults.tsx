import { ReactNode } from 'react';
import { useSearchResultDisplay } from 'apps/search';
import { SearchResultsHeader } from './header/SearchResultsHeader';

import styles from './search-results.module.scss';
import { usePage } from 'page';

type Props = {
    children: ReactNode;
};

const SearchResults = ({ children }: Props) => {
    const {
        page: { total }
    } = usePage();

    const { view, terms } = useSearchResultDisplay();

    return (
        <div className={styles.results}>
            <SearchResultsHeader className={styles.header} view={view} total={total} terms={terms} />
            <main className={styles.content}>{children}</main>
            <div className={styles.pagingation}></div>
        </div>
    );
};

export { SearchResults };
