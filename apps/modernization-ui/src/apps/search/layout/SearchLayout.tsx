import { ReactNode, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import { Button } from 'components/button';
import { Term, useSearchResultDisplay } from 'apps/search';
import { SearchLanding } from './landing';
import { SearchResults } from './result';

import { Loading } from 'components/Spinner';
import { CollapsiblePanel } from 'design-system/collapsible-panel';
import { SearchNavigation } from './navigation/SearchNavigation';
import { usePage } from 'page';
import { NoResults } from './result/none';
import { NoInput } from './result/NoInput';

import styles from './search-layout.module.scss';

type Renderer = () => ReactNode;

type Props = {
    actions?: Renderer;
    criteria: Renderer;
    resultsAsList: Renderer;
    resultsAsTable: Renderer;
    noInput?: Renderer;
    noResults?: Renderer;
    searchEnabled?: boolean;
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
    searchEnabled = true,
    onClear,
    noInput = () => <NoInput />,
    noResults = () => <NoResults />,
    onRemoveTerm
}: Props) => {
    const { view, status, reset } = useSearchResultDisplay();

    const { pathname } = useLocation();

    useEffect(() => {
        reset();
    }, [pathname]);

    const {
        page: { total }
    } = usePage();

    const handleKeyPress = (event: { key: string }) => {
        if (event.key === 'Enter') {
            onSearch();
        }
    };

    useEffect(() => {
        document.addEventListener('keypress', handleKeyPress);
        return () => {
            document.removeEventListener('keypress', handleKeyPress);
        };
    }, [onSearch]);

    return (
        <section className={styles.search}>
            <SearchNavigation className={styles.navigation} actions={actions} />
            <div className={styles.content}>
                <CollapsiblePanel
                    className={styles.panel}
                    contentClassName={styles.criteria}
                    ariaLabel="Search criteria">
                    <div className={styles.inputs}>{criteria()}</div>
                    <div className={styles.actions}>
                        <Button type="button" onClick={onSearch} disabled={!searchEnabled}>
                            Search
                        </Button>
                        <Button type="button" outline onClick={onClear}>
                            Clear all
                        </Button>
                    </div>
                </CollapsiblePanel>

                <div className={styles.results}>
                    {status === 'waiting' && <SearchLanding />}
                    {status === 'searching' && <Loading center />}
                    {status === 'completed' && (
                        <SearchResults onRemoveTerm={onRemoveTerm}>
                            {total === 0 && noResults()}
                            {view === 'list' && total > 0 && resultsAsList()}
                            {view === 'table' && total > 0 && resultsAsTable()}
                        </SearchResults>
                    )}
                    {status === 'no-input' && <SearchResults onRemoveTerm={onRemoveTerm}>{noInput()}</SearchResults>}
                </div>
            </div>
        </section>
    );
};

export { SearchLayout };
