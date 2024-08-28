import { OverlayPanel } from 'overlay';
import { ColumnPreferencesPanel } from 'design-system/table/preferences';
import { Button } from 'components/button';

import styles from './search-results-table-options.module.scss';

import sprite from '@uswds/uswds/img/sprite.svg';

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
                    outline
                    disabled={disabled}
                    icon={
                        <svg role="img" aria-label={`Settings`} className={styles.gear}>
                            <use xlinkHref={`${sprite}#settings`} />
                        </svg>
                    }
                    onClick={toggle}
                />
            )}
            render={(close) => <ColumnPreferencesPanel close={close} />}></OverlayPanel>
    );
};

export { SearchResultsTableOptions };
