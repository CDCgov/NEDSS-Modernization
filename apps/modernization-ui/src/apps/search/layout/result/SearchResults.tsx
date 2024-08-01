import { ReactNode } from 'react';
import { Term, useSearchResultDisplay } from 'apps/search';
import { SearchResultsHeader } from './header/SearchResultsHeader';
import styles from './search-results.module.scss';
import { usePage } from 'page';
import { Pagination } from 'design-system/Pagination/Pagination';
import { ColumnPreferenceProvider } from 'design-system/table/preferences';

type Props = {
    children: ReactNode;
    onRemoveTerm: (term: Term) => void;
};

const SearchResults = ({ children, onRemoveTerm }: Props) => {
    const {
        page: { total }
    } = usePage();

    const { view, terms } = useSearchResultDisplay();

    return (
        <ColumnPreferenceProvider>
            <div className={styles.results}>
                <SearchResultsHeader
                    onRemoveTerm={onRemoveTerm}
                    className={styles.header}
                    view={view}
                    total={total}
                    terms={terms}
                />
                <main className={styles.content}>{children}</main>
                <div className={styles.pagination}>
                    <Pagination />
                </div>
            </div>
        </ColumnPreferenceProvider>
    );
};

export { SearchResults };
