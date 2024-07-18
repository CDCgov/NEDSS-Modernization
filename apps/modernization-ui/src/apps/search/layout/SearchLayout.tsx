import { ReactNode } from 'react';
import { Button } from 'components/button';
import { Term, useSearchResultDisplay } from 'apps/search';
import { SearchLanding } from './landing';
import { SearchResults } from './result';

import styles from './search-layout.module.scss';
import { Loading } from 'components/Spinner';
import { SearchNavigation } from './navigation/SearchNavigation';
import { usePage } from 'page';
import { NoResults } from './result/none';
import { NoInput } from './result/NoInput';
import ColumnProvider from '../context/ColumnContextProvider';

type Renderer = () => ReactNode;

type Props = {
    actions?: Renderer;
    criteria: Renderer;
    resultsAsList: Renderer;
    resultsAsTable: Renderer;
    noInput?: Renderer;
    noResults?: Renderer;
    onSearch: () => void;
    onClear: () => void;
    onRemoveTerm: (term: Term) => void;
};

const SearchLayout = ({
    actions,
    criteria,
    resultsAsList,
    resultsAsTable,
    onSearch,
    onClear,
    noInput = () => <NoInput />,
    noResults = () => <NoResults />,
    onRemoveTerm
}: Props) => {
    const { view, status } = useSearchResultDisplay();

    const {
        page: { total }
    } = usePage();

    return (
        <ColumnProvider>
            <section className={styles.search}>
                <SearchNavigation className={styles.navigation} actions={actions} />
                <div className={styles.content}>
                    <div className={styles.criteria}>
                        <div className={styles.search}>{criteria()}</div>
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
                            <SearchResults onRemoveTerm={onRemoveTerm}>
                                {total === 0 && noResults()}
                                {view === 'list' && total > 0 && resultsAsList()}
                                {view === 'table' && total > 0 && resultsAsTable()}
                            </SearchResults>
                        )}
                        {status === 'noInput' && <SearchResults onRemoveTerm={onRemoveTerm}>{noInput()}</SearchResults>}
                    </div>
                </div>
            </section>
        </ColumnProvider>
    );
};

export { SearchLayout };
