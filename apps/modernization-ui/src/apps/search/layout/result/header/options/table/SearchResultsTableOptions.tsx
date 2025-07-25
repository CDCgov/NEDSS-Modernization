import { Sizing } from 'design-system/field';
import { ColumnPreferencesAction } from 'design-system/table/preferences';
import { useFilter } from 'design-system/filter';
import { Button } from 'components/button';
import { FeatureToggle } from 'feature';
import { useSorting } from 'libs/sorting';

import styles from './search-results-table-options.module.scss';

type Props = {
    disabled?: boolean;
    sizing?: Sizing;
};

const SearchResultsTableOptions = ({ disabled = false, sizing }: Props) => {
    const { active, toggle, clearAll, filter } = useFilter();
    const sorting = useSorting();

    const handleFilterSortReset = () => {
        clearAll();
        sorting?.reset();
    };

    return (
        <>
            <FeatureToggle guard={(features) => features.patient.search.filters.enabled}>
                <div className={styles.filter}>
                    {filter && (
                        <Button tertiary onClick={handleFilterSortReset} sizing={sizing}>
                            Reset sort/filters
                        </Button>
                    )}
                    <Button
                        aria-label="Filter"
                        data-tooltip-position="top"
                        data-tooltip-offset="center"
                        secondary={!active}
                        active={active}
                        disabled={disabled}
                        icon="filter_alt"
                        onClick={toggle}
                        sizing={sizing}
                    />
                </div>
            </FeatureToggle>
            <ColumnPreferencesAction sizing={sizing} />
        </>
    );
};

export { SearchResultsTableOptions };
