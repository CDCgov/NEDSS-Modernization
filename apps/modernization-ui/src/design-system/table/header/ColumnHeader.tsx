import { ReactNode } from 'react';
import classNames from 'classnames';
import { Direction, SortingInteraction } from 'libs/sorting';
import { Button } from 'design-system/button';
import { FilterInteraction } from 'design-system/filter';
import { Column, SortIconType } from 'design-system/table';
import { Icon } from 'design-system/icon';
import { Sizing } from 'design-system/field';
import { HeaderFilterField } from './filter';
import { isNamed, NamedColumn } from './column';

import styles from './column-header.module.scss';

type ColumnOptions = {
    className?: string;
    sizing?: Sizing;
    sorting?: SortingInteraction;
    filtering?: FilterInteraction;
};

type ColumnHeaderProps<V> = ColumnOptions & {
    children: Column<V>;
};

const ColumnHeader = <V,>({ children, className, ...remaining }: ColumnHeaderProps<V>) => {
    return isNamed(children) ? (
        <NamedColumnHeader {...remaining} column={children} className={className} />
    ) : (
        <BaseHeader column={children} {...remaining} className={classNames(className, styles.action)}>
            {children.label}
        </BaseHeader>
    );
};

type BaseHeaderProps<V> = ColumnOptions & {
    column: Column<V>;
} & JSX.IntrinsicElements['th'];

const BaseHeader = <V,>({ className, sizing, column, children, ...remaining }: BaseHeaderProps<V>) => (
    <th
        className={classNames(
            styles.header,
            {
                [styles.fixed]: column.fixed,
                [styles.small]: sizing === 'small',
                [styles.medium]: sizing === 'medium',
                [styles.large]: sizing === 'large'
            },
            className
        )}
        {...remaining}>
        {children}
    </th>
);

type NamedHeaderContentProps<V> = {
    column: NamedColumn<V>;
    sizing?: Sizing;
    filtering?: FilterInteraction;
    children: ReactNode;
};

const NamedHeaderContent = <V,>({ column, sizing, filtering, children }: NamedHeaderContentProps<V>) => (
    <div className={classNames(styles.content, { [styles.extended]: filtering?.active })}>
        {children}
        {filtering?.active && column?.filter?.id && (
            <HeaderFilterField label={column.name} descriptor={column.filter} filtering={filtering} sizing={sizing} />
        )}
    </div>
);

type NamedColumnHeaderProps<V> = ColumnOptions & { column: NamedColumn<V> };

const NamedColumnHeader = <V,>({ sizing, sorting, filtering, column, ...remaining }: NamedColumnHeaderProps<V>) => {
    const isSortable = sorting && column.sortable;

    return isSortable ? (
        <SortableHeader sizing={sizing} sorting={sorting} filtering={filtering} column={column} {...remaining} />
    ) : (
        <BaseHeader column={column} sizing={sizing} {...remaining}>
            <NamedHeaderContent column={column} sizing={sizing} filtering={filtering}>
                {column.name}
            </NamedHeaderContent>
        </BaseHeader>
    );
};

type SortableProps<V> = NamedColumnHeaderProps<V> & { sorting: SortingInteraction };

const SortableHeader = <V,>({ sorting, filtering, column, sizing, ...remaining }: SortableProps<V>) => {
    const direction = sorting.property === column.id ? ensureDirection(sorting.direction) : Direction.None;
    const ariaSort = resolveSortAria(direction);
    const icon = resolveSortIcon(direction, column.sortIconType);

    return (
        <BaseHeader
            column={column}
            sizing={sizing}
            className={classNames({
                [styles.sorted]: direction !== Direction.None
            })}
            aria-sort={ariaSort}
            {...remaining}>
            <NamedHeaderContent column={column} sizing={sizing} filtering={filtering}>
                <div className={styles.sortable}>
                    {column.name}
                    <Button
                        tertiary
                        icon={<Icon name={icon} />}
                        aria-label={`Sort ${column.name}`}
                        onClick={() => sorting.toggle(column.id)}
                    />
                </div>
            </NamedHeaderContent>
        </BaseHeader>
    );
};

const ensureDirection = (direction?: Direction) => direction ?? Direction.None;

const resolveSortIcon = (direction: Direction, type?: SortIconType) => {
    switch (direction) {
        case Direction.Ascending:
            return resolveAscendingIcon(type);
        case Direction.Descending:
            return resolveDescendingIcon(type);
        default:
            return 'sort_arrow';
    }
};

const resolveAscendingIcon = (type?: SortIconType) => (type === 'numeric' ? 'sort_asc_numeric' : 'sort_asc_alpha');

const resolveDescendingIcon = (type?: SortIconType) => (type === 'numeric' ? 'sort_des_numeric' : 'sort_des_alpha');

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

export { ColumnHeader };
