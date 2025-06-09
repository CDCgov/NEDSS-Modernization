import { Icon } from 'design-system/icon';
import classNames from 'classnames';
import { SortingSelectable } from './selectable';

import styles from './sort-preference.module.scss';
import { Button } from 'design-system/button';

type Props = {
    className?: string;
    active?: boolean;
    selectable: SortingSelectable;
    onSelect: (selectable: SortingSelectable) => void;
};

const SortPreference = ({ className, active, selectable, onSelect }: Props) => (
    <Button
        className={classNames(styles.preference, className)}
        onClick={() => onSelect(selectable)}
        aria-label={`Sort by ${selectable.name}`}
        tertiary>
        {active && <Icon className={styles.indicator} name="check" />}
        {!active && <span className={styles.placeholder} />}
        {selectable.name}
    </Button>
);

export { SortPreference };
