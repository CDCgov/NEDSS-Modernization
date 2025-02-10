import classNames from 'classnames';
import { Direction, SortingInteraction, maybeUseSorting } from 'sorting';
import { FilterInteraction, maybeUseFilter } from 'design-system/filter';
import { HeaderFilterField } from './header/filter';
import { Column } from './DataTable';
import { Icon } from 'design-system/icon';

import styles from './header.module.scss';
import { Sizing } from 'design-system/field';

type Props<V> = {
    className?: string;
    sizing?: Sizing;
    children: Column<V>;
};

const Header = <V,>({ children, ...remaining }: Props<V>) => {
    const sorting = maybeUseSorting();
    const filtering = maybeUseFilter();

    const isSortable = sorting && children.sortable;

    return isSortable ? (
        <SortableHeader sorting={sorting} {...remaining} filtering={filtering}>
            {children}
        </SortableHeader>
    ) : (
        <StandardHeader {...remaining} filtering={filtering}>
            {children}
        </StandardHeader>
    );
};

type StandardHeaderProps<V> = Props<V> & { filtering?: FilterInteraction };

const StandardHeader = <V,>({ className, children, filtering, sizing }: StandardHeaderProps<V>) => (
    <th
        className={classNames(styles.header, className, sizing && styles[sizing], {
            [styles.fixed]: children.fixed
        })}>
        <div className={classNames(styles.content)}>
            {children.name}
            {filtering && filtering.active && children.filter != undefined && (
                <HeaderFilterField label={children.name} descriptor={children.filter} filtering={filtering} />
            )}
        </div>
    </th>
);

type SortableProps<V> = StandardHeaderProps<V> & {
    sorting: SortingInteraction;
};

const SortableHeader = <V,>({ className, sorting, children, filtering, sizing }: SortableProps<V>) => {
    const direction = sorting.property === children.id ? ensureDirection(sorting.direction) : Direction.None;
    const ariaSort = resolveSortAria(direction);
    const icon = resolveSortIcon(direction);

    return (
        <th
            className={classNames(styles.header, className, sizing && styles[sizing], {
                [styles.fixed]: children.fixed,
                [styles.sorted]: direction !== Direction.None
            })}
            aria-sort={ariaSort}>
            <div className={classNames(styles.content, { [styles.extended]: filtering?.active })}>
                <div className={styles.sortable}>
                    {children.name}
                    <Icon
                        name={icon}
                        aria-label={`Sort ${children.name}`}
                        onClick={() => sorting.toggle(children.id)}
                    />
                </div>
                {filtering && filtering.active && children.filter != undefined && (
                    <HeaderFilterField label={children.name} descriptor={children.filter} filtering={filtering} />
                )}
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
