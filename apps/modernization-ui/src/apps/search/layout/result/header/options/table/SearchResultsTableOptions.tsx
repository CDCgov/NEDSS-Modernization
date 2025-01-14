import { OverlayPanel } from 'overlay';
import { Icon } from 'design-system/icon';
import { ColumnPreferencesPanel } from 'design-system/table/preferences';
import { Button } from 'components/button';

import styles from './search-results-table-options.module.scss';

type Props = {
    disabled?: boolean;
};

const SearchResultsTableOptions = ({ disabled = false }: Props) => {
    return (
        <OverlayPanel
            className={styles.overlay}
            position="right"
            toggle={({ toggle }) => (
                <Button
                    aria-label="Columns setting"
                    data-tooltip-position="top"
                    data-tooltip-offset="left"
                    outline
                    disabled={disabled}
                    icon={<Icon name="settings" aria-label={`Settings`} className={styles.gear} />}
                    onClick={toggle}
                />
            )}
            render={(close) => <ColumnPreferencesPanel close={close} />}></OverlayPanel>
    );
};

export { SearchResultsTableOptions };
