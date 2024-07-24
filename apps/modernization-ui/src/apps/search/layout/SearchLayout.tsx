import { ReactNode, useState } from 'react';
import { Button } from 'components/button';
import { Term, useSearchResultDisplay } from 'apps/search';
import { SearchLanding } from './landing';
import { SearchResults } from './result';

import styles from './search-layout.module.scss';
import { Loading } from 'components/Spinner';
import { SearchNavigation } from './navigation/SearchNavigation';
import { usePage } from 'page';
import { Icon } from '@trussworks/react-uswds';

type Renderer = () => ReactNode;

type Props = {
    actions?: Renderer;
    criteria: Renderer;
    resultsAsList: Renderer;
    resultsAsTable: Renderer;
    noInputResults?: Renderer;
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
    noInputResults,
    noResults,
    onRemoveTerm
}: Props) => {
    const { view, status } = useSearchResultDisplay();

    const [collapse, setCollapse] = useState<boolean>();

    const {
        page: { total }
    } = usePage();

    return (
        <section className={styles.search}>
            <SearchNavigation className={styles.navigation} actions={actions} />
            <div className={styles.content}>
                <div className={collapse ? styles.collapse : styles.criteria}>
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
                                {total === 0 && noResults?.()}
                                {view === 'list' && resultsAsList()}
                                {view === 'table' && resultsAsTable()}
                            </SearchResults>
                        )}
                        {status === 'noInput' && (
                            <SearchResults onRemoveTerm={onRemoveTerm}>{noInputResults?.()}</SearchResults>
                        )}
                    </div>
                </div>
            </div>
        </section>
    );
};

export { SearchLayout };
