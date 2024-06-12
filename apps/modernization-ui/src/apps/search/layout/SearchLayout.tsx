import { ReactNode } from 'react';
import { Button } from 'components/button';
import { useSearch } from 'apps/search/useSearch';
import { SearchLanding } from './landing';
import { SearchResults } from './result';

import styles from './search-layout.module.scss';
import { Loading } from 'components/Spinner';
import { SearchNavigation } from './navigation/SearchNavigation';

type Renderer = () => ReactNode;

type Props = {
    actions?: Renderer;
    criteria: Renderer;
    resultsAsList: Renderer;
    resultsAsTable: Renderer;
    onSearch: () => void;
    onClear: () => void;
};

const SearchLayout = ({ actions, criteria, resultsAsList, resultsAsTable, onSearch, onClear }: Props) => {
    const { view, status } = useSearch();

    return (
        <section className={styles.search}>
            <SearchNavigation actions={actions} />
            <div className={styles.content}>
                <div className={styles.criteria}>
                    <search>{criteria()}</search>
                    <div className={styles.actions}>
                        <Button type="button" onClick={onSearch}>
                            Search
                        </Button>
                        <Button type="button" outline onClick={onClear}>
                            Clear all
                        </Button>
                    </div>
                </div>
                <div className={styles.results}>
                    {status === 'waiting' && <SearchLanding />}
                    {status === 'searching' && <Loading className={styles.loading} />}
                    {status === 'completed' && (
                        <SearchResults>
                            {view == 'list' && resultsAsList()}
                            {view == 'table' && resultsAsTable()}
                        </SearchResults>
                    )}
                </div>
            </div>
        </section>
    );
};

export { SearchLayout };
