import classNames from 'classnames';
import { OverlayPanel } from 'overlay';
import { Icon } from 'design-system/icon';
import { ColumnPreferencesPanel } from 'design-system/table/preferences';
import { useFilter } from 'design-system/filter';
import { FeatureToggle } from 'feature';
import { maybeUseSorting } from 'sorting';

import styles from './search-results-table-options.module.scss';
import { Button } from 'design-system/button';

type Props = {
    disabled?: boolean;
};

const SearchResultsTableOptions = ({ disabled = false }: Props) => {
    const { active, toggle, clearAll, filter } = useFilter();
    const sorting = maybeUseSorting();

    const handleFilterSortReset = () => {
        clearAll();
        sorting?.reset();
    };

    return (
        <>
            <FeatureToggle guard={(features) => features.patient.search.filters.enabled}>
                <div className={styles.filter}>
                    {filter && (
                        <Button unpadded unstyled onClick={handleFilterSortReset}>
                            Reset sort/filters
                        </Button>
                    )}
                    <Button
                        aria-label="Filter"
                        data-tooltip-position="top"
                        data-tooltip-offset="center"
                        className={classNames({ [styles.filtered]: active })}
                        outline={!active}
                        disabled={disabled}
                        icon={<Icon name="filter_alt" />}
                        onClick={toggle}
                        sizing="small"
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
                        sizing="small"
                    />
                )}
                render={(close) => <ColumnPreferencesPanel close={close} />}></OverlayPanel>
        </>
    );
};

export { SearchResultsTableOptions };
