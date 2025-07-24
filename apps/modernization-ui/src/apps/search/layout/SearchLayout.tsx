import { ReactNode, KeyboardEvent as ReactKeyboardEvent } from 'react';
import { Button } from 'components/button';
import { Loading } from 'components/Spinner';
import { Sizing } from 'design-system/field';
import { CollapsiblePanel } from 'design-system/collapsible-panel';
import { Shown } from 'conditional-render';
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
    sizing?: Sizing;
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
    sizing,
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
        results: { total, filteredTotal, terms }
    } = useSearchInteraction<R>();

    const { view } = useSearchResultDisplay();

    const handleKey = (event: ReactKeyboardEvent<HTMLElement>) => {
        if (event.key === 'Enter' && searchEnabled && !(event.target instanceof HTMLButtonElement)) {
            onSearch();
        }
    };

    return (
        <section className={styles.search} onKeyDown={handleKey}>
            <FeatureToggle
                guard={(features) => features?.search?.events?.enabled}
                fallback={<SearchNavigation className={styles.navigation} actions={actions} />}>
                <PatientSearchHeader className={styles.navigation} />
            </FeatureToggle>

            <div className={styles.content}>
                <CollapsiblePanel
                    className={styles.panel}
                    contentClassName={styles.criteria}
                    ariaLabel="Search criteria">
                    <div className={styles.inputs}>{criteria()}</div>
                    <div className={styles.actions}>
                        <Button type="button" onClick={onSearch} disabled={!searchEnabled} sizing={sizing}>
                            Search
                        </Button>
                        <Button type="button" secondary onClick={onClear} sizing={sizing}>
                            Clear all
                        </Button>
                    </div>
                </CollapsiblePanel>

                <div className={styles.results}>
                    <Shown when={status === 'waiting'}>
                        <SearchLanding />
                    </Shown>
                    <Shown when={status === 'loading'}>
                        <Loading center />
                    </Shown>
                    <Shown when={status === 'completed' || status === 'reloading'}>
                        <SearchResults
                            sizing={sizing}
                            view={view}
                            total={total}
                            filteredTotal={filteredTotal}
                            terms={terms}
                            loading={status === 'reloading'}>
                            {view === 'list' && total > 0 && resultsAsList()}
                            {view === 'table' && resultsAsTable()}
                            {total === 0 && noResults()}
                        </SearchResults>
                    </Shown>
                    <Shown when={status === 'no-input'}>
                        <SearchResults
                            sizing={sizing}
                            view={view}
                            total={total}
                            filteredTotal={filteredTotal}
                            terms={terms}>
                            {noInput()}
                        </SearchResults>
                    </Shown>
                </div>
            </div>
            <Shown when={!!actions}>
                <div className={styles.actions}>{actions?.()}</div>
            </Shown>
        </section>
    );
};

export { SearchLayout };
