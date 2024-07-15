import classNames from 'classnames';
import { Column } from './DataTable';
import { Direction, SortingInteraction, maybeUseSorting } from 'sorting';

import sprite from '@uswds/uswds/img/sprite.svg';
import styles from './header.module.scss';

type Props<V> = {
    className?: string;
    children: Column<V>;
};

const Header = <V,>({ children, ...remaining }: Props<V>) => {
    const sorting = maybeUseSorting();

    const isSortable = sorting && children.sortable;

    return isSortable ? (
        <SortableHeader sorting={sorting} {...remaining}>
            {children}
        </SortableHeader>
    ) : (
        <StandardHeader {...remaining}>{children}</StandardHeader>
    );
};

const StandardHeader = <V,>({ className, children }: Props<V>) => (
    <th className={classNames(styles.header, className, { [styles.fixed]: children.fixed })}>{children.name}</th>
);

type SortableProps<V> = Props<V> & { sorting: SortingInteraction };

const SortableHeader = <V,>({ className, sorting, children }: SortableProps<V>) => {
    const direction = sorting.property === children.id ? ensureDirection(sorting.direction) : Direction.None;
    const ariaSort = resolveSortAria(direction);
    const icon = resolveSortIcon(direction);

    return (
        <th
            className={classNames(styles.header, className, {
                [styles.fixed]: children.fixed,
                [styles.sorted]: direction !== Direction.None
            })}
            {...(ariaSort && { 'aria-sort': ariaSort })}>
            <div>
                {children.name}
                <svg tabIndex={0} role="img" aria-label={`Sort`} onClick={() => sorting.toggle(children.id)}>
                    <use xlinkHref={`${sprite}#${icon}`}></use>
                </svg>
            </div>
        </th>
    );
};

const ensureDirection = (direction?: Direction) => direction ?? Direction.None;

const resolveSortIcon = (direction: Direction) => {
    switch (direction) {
        case Direction.Ascending:
            return 'arrow_downward';
        case Direction.Descending:
            return 'arrow_upward';
        default:
            return 'sort_arrow';
    }
};

const resolveSortAria = (direction: Direction) => {
    switch (direction) {
        case Direction.Ascending:
            return 'ascending';
        case Direction.Descending:
            return 'descending';
        default:
            return;
    }
};

export { Header };
