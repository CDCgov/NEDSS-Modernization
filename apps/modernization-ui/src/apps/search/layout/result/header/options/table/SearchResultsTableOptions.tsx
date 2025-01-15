import { OverlayPanel } from 'overlay';
import { Icon } from 'design-system/icon';
import { ColumnPreferencesPanel } from 'design-system/table/preferences';
import { Button } from 'components/button';

import styles from './search-results-table-options.module.scss';
import { useFilterPreferences } from 'design-system/sorting/preferences/useFilterPreferences';

type Props = {
    disabled?: boolean;
};

const SearchResultsTableOptions = ({ disabled = false }: Props) => {
    const { activeFilter, toggleFilter, resetFilter } = useFilterPreferences();

    return (
        <>
            <div className={styles['filter-options']}>
                <Button unpadded unstyled onClick={resetFilter}>
                    Reset sort/filters
                </Button>
                <Button
                    aria-label="Filter"
                    data-tooltip-position="top"
                    data-tooltip-offset="center"
                    outline={!activeFilter}
                    disabled={disabled}
                    icon={<Icon name="filter_alt" aria-label={`Filter`} className={styles['option-icon']} />}
                    onClick={toggleFilter}
                />
            </div>
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
                        icon={<Icon name="settings" aria-label={`Settings`} className={styles['option-icon']} />}
                        onClick={toggle}
                    />
                )}
                render={(close) => <ColumnPreferencesPanel close={close} />}></OverlayPanel>
        </>
    );
};

export { SearchResultsTableOptions };
