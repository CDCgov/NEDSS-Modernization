import { ReactNode, useEffect } from 'react';
import { Button } from 'components/button';
import { Loading } from 'components/Spinner';
import { CollapsiblePanel } from 'design-system/collapsible-panel';
import { SearchNavigation } from './navigation/SearchNavigation';
import { PatientSearchHeader } from './patientSearchHeader/PatientSearchHeader';
import { useSearchInteraction, useSearchResultDisplay } from 'apps/search';
import { SearchLanding } from './landing';
import { SearchResults } from './result';
import { NoResults } from './result/none';
import { NoInput } from './result/NoInput';
import { FeatureToggle } from 'feature';

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
};

const SearchLayout = <R,>({
    actions,
    criteria,
    resultsAsList,
    resultsAsTable,
    onSearch,
    searchEnabled = true,
    onClear,
    noInput = () => <NoInput />,
    noResults = () => <NoResults />
}: Props) => {
    const {
        status,
        results: { total, terms }
    } = useSearchInteraction<R>();

    const { view } = useSearchResultDisplay();

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
            <FeatureToggle
                guard={(features) => features?.search?.events?.enabled}
                fallback={<SearchNavigation className={styles.navigation} actions={actions} />}>
                <PatientSearchHeader className={styles.navigation} actions={actions} />
            </FeatureToggle>

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
                    {status === 'loading' && <Loading center />}
                    {status === 'completed' && (
                        <SearchResults view={view} total={total} terms={terms}>
                            {total === 0 && noResults()}
                            {view === 'list' && total > 0 && resultsAsList()}
                            {view === 'table' && total > 0 && resultsAsTable()}
                        </SearchResults>
                    )}
                    {status === 'no-input' && (
                        <SearchResults view={view} total={total} terms={terms}>
                            {noInput()}
                        </SearchResults>
                    )}
                </div>
            </div>
        </section>
    );
};

export { SearchLayout };
