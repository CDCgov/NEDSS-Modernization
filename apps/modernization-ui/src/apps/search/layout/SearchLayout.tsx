import { ReactNode } from 'react';
import { Button } from 'components/button';
import { Results, useSearch } from 'apps/search/useSearch';
import { SearchLanding } from './landing';
import { SearchResults } from './result';

import styles from './search-layout.module.scss';
import { Loading } from 'components/Spinner';

type Renderer = () => ReactNode;

type NavigationRenderer = (results: Results) => ReactNode;

type Props = {
    navigation: NavigationRenderer;
    criteria: Renderer;
    renderAsList: Renderer;
    renderAsTable: Renderer;
    onSearch: () => void;
    onClear: () => void;
};

const SearchLayout = ({ navigation, criteria, renderAsList, renderAsTable, onSearch, onClear }: Props) => {
    const { view, status, results: searchResults } = useSearch();

    return (
        <section className={styles.search}>
            {navigation(searchResults)}
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
                            {view == 'list' && renderAsList()}
                            {view == 'table' && renderAsTable()}
                        </SearchResults>
                    )}
                </div>
            </div>
        </section>
    );
};

export { SearchLayout };
