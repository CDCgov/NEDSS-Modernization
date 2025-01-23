import classNames from 'classnames';
import { Column } from './DataTable';
import { Direction, SortingInteraction, maybeUseSorting } from 'sorting';

import sprite from '@uswds/uswds/img/sprite.svg';
import styles from './header.module.scss';

type Props<V> = {
    className?: string;
    children: Column<V>;
    filterable?: boolean;
};

const Header = <V,>({ children, filterable, ...remaining }: Props<V>) => {
    const sorting = maybeUseSorting();

    const isSortable = sorting && children.sortable;

    return isSortable ? (
        <SortableHeader sorting={sorting} {...remaining} filterable={filterable}>
            {children}
        </SortableHeader>
    ) : (
        <StandardHeader {...remaining} filterable={filterable}>
            {children}
        </StandardHeader>
    );
};

const StandardHeader = <V,>({ className, children, filterable }: Props<V>) => (
    <th className={classNames(styles.header, className, { [styles.fixed]: children.fixed })}>
        {children.name}
        {filterable && children.filter}
    </th>
);

type SortableProps<V> = Props<V> & {
    sorting: SortingInteraction;
    filterable?: boolean;
};

const SortableHeader = <V,>({ className, sorting, children, filterable }: SortableProps<V>) => {
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
            {filterable && children.filter}
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
