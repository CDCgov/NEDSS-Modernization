import classNames from 'classnames';
import { OverlayPanel } from 'overlay';
import { Icon } from 'design-system/icon';
import { ColumnPreferencesPanel } from 'design-system/table/preferences';
import { useFilter } from 'design-system/filter';
import { Button } from 'components/button';

import styles from './search-results-table-options.module.scss';
import { FeatureToggle } from 'feature';

type Props = {
    disabled?: boolean;
};

const SearchResultsTableOptions = ({ disabled = false }: Props) => {
    const { active, toggle, reset, filter } = useFilter();

    return (
        <>
            <FeatureToggle guard={(features) => features.patient.search.filters.enabled}>
                <div className={styles['filter-options']}>
                    {filter && (
                        <Button unpadded unstyled onClick={reset}>
                            Reset sort/filters
                        </Button>
                    )}
                    <Button
                        aria-label="Filter"
                        data-tooltip-position="top"
                        data-tooltip-offset="center"
                        className={classNames({ [styles.activeFilter]: active })}
                        outline={!active}
                        disabled={disabled}
                        icon={<Icon name="filter_alt" aria-label={`Filter`} className={styles['option-icon']} />}
                        onClick={toggle}
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
                        icon={<Icon name="settings" aria-label={`Settings`} className={styles['option-icon']} />}
                        onClick={toggle}
                    />
                )}
                render={(close) => <ColumnPreferencesPanel close={close} />}></OverlayPanel>
        </>
    );
};

export { SearchResultsTableOptions };
