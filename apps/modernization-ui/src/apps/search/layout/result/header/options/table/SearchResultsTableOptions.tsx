import classNames from 'classnames';
import { OverlayPanel } from 'overlay';
import { Icon } from 'design-system/icon';
import { Sizing } from 'design-system/field';
import { ColumnPreferencesPanel } from 'design-system/table/preferences';
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
                        className={classNames({ [styles.filtered]: active })}
                        secondary={!active}
                        disabled={disabled}
                        icon={<Icon name="filter_alt" />}
                        onClick={toggle}
                        sizing={sizing}
                    />
                </div>
            </FeatureToggle>
            <OverlayPanel
                className={styles.overlay}
                position="right"
                toggle={({ toggle }) => (
                    <Button
                        aria-label="Settings"
                        data-tooltip-position="top"
                        data-tooltip-offset="center"
                        outline
                        disabled={disabled}
                        icon={<Icon name="settings" />}
                        onClick={toggle}
                        sizing={sizing}
                    />
                )}
                render={(close) => <ColumnPreferencesPanel close={close} />}></OverlayPanel>
        </>
    );
};

export { SearchResultsTableOptions };
