import { ReactNode, useState, useEffect } from 'react';
import { Button } from 'components/button';
import { Term, useSearchResultDisplay } from 'apps/search';
import { SearchLanding } from './landing';
import { SearchResults } from './result';

import styles from './search-layout.module.scss';
import { Loading } from 'components/Spinner';
import { SearchNavigation } from './navigation/SearchNavigation';
import { usePage } from 'page';
import { Icon } from '@trussworks/react-uswds';
import { NoResults } from './result/none';
import { NoInput } from './result/NoInput';
import { useLocation } from 'react-router-dom';

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
        reset?.();
    }, [pathname]);

    const [collapse, setCollapse] = useState<boolean>();

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
                <div className={collapse ? styles.collapse : styles.criteria}>
                    <div className={styles.search}>{criteria()}</div>
                    <div className={styles.actions}>
                        <Button type="button" onClick={onSearch} disabled={!searchEnabled}>
                            Search
                        </Button>
                        <Button type="button" outline onClick={onClear}>
                            Clear all
                        </Button>
                    </div>
                </div>
                <div className={styles.collapseButton}>
                    <div className={collapse ? styles.collapseTrue : styles.content}>
                        {!collapse && <Icon.ExpandLess onClick={() => setCollapse(true)} size={3} />}
                        {collapse && <Icon.ExpandMore onClick={() => setCollapse(false)} size={3} />}
                    </div>
                </div>
                <div className={styles.results}>
                    <div className={styles.resultContent}>
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
            </div>
        </section>
    );
};

export { SearchLayout };
