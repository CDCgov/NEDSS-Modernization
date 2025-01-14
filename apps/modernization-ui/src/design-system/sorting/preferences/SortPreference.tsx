import { Icon } from 'design-system/icon';
import classNames from 'classnames';
import { SortingSelectable } from './selectable';

import styles from './sort-preference.module.scss';

type Props = {
    className?: string;
    active?: boolean;
    selectable: SortingSelectable;
    onSelect: (selectable: SortingSelectable) => void;
};

const SortPreference = ({ className, active, selectable, onSelect }: Props) => (
    <span className={classNames(styles.preference, className)} onClick={() => onSelect(selectable)}>
        {active && <Icon className={styles.indicator} name="check" />}
        {!active && <span className={styles.placeholder} />}
        {selectable.name}
    </span>
);

export { SortPreference };
