import { Button } from 'components/button';
import { Icon } from 'design-system/icon';
import { SortingPreferencesPanel, SortingSelectable } from 'design-system/sorting/preferences';
import { OverlayPanel } from 'overlay';
import { Direction } from 'sorting';

import styles from './search-results-list-options.module.scss';

const selection: SortingSelectable[] = [
    { property: 'relavance', direction: Direction.Descending, name: 'Closest match' },
    { property: 'legalName', direction: Direction.Ascending, name: 'Patient name (A-Z)' },
    { property: 'legalName', direction: Direction.Descending, name: 'Patient name (Z-A)' },
    { property: 'birthday', direction: Direction.Ascending, name: 'Date of birth (Ascending)' },
    { property: 'birthday', direction: Direction.Descending, name: 'Date of birth (Descending)' }
];

type Props = {
    disabled?: boolean;
};

const SearchResultsListOptions = ({ disabled = false }: Props) => {
    return (
        <OverlayPanel
            className={styles.options}
            position="right"
            toggle={({ toggle }) => (
                <Button
                    className={styles.opener}
                    aria-label="Sort settings"
                    data-tooltip-position="top"
                    outline
                    disabled={disabled}
                    icon={<Icon name="sort_arrow" className={styles.icon} />}
                    onClick={toggle}
                />
            )}
            render={(close) => <SortingPreferencesPanel selection={selection} onClose={close} />}
        />
    );
};

export { SearchResultsListOptions };
