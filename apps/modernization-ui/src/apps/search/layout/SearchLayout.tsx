import { ReactNode } from 'react';
import { Button } from 'components/button';

import { Results, useSearch } from 'apps/search/useSearch';
import { SearchLanding } from './landing';
import styles from './search-layout.module.scss';

type Renderer = () => ReactNode;

type NavigationRenderer = (results: Results | null) => ReactNode;

type Props = {
    navigation: NavigationRenderer;
    criteria: Renderer;
    results: Renderer;
    onSearch: () => void;
    onClear: () => void;
};

const SearchLayout = ({ navigation, criteria, results, onSearch, onClear }: Props) => {
    const { status, results: searchResults } = useSearch();

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
                <div className={styles.results}>{status === 'waiting' ? <SearchLanding /> : results()}</div>
            </div>
        </section>
    );
};

export { SearchLayout };
