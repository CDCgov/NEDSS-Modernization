import { ComponentType, FC, ReactNode, useMemo } from 'react';
import { Sizing } from 'design-system/field';
import { SortableDataTable, DataTableProps, Column } from 'design-system/table';
import { ColumnPreference, useColumnPreferences, withColumnPreferences } from 'design-system/table/preferences';
import { CollapsibleCard } from '../collapsible';
import { TableCardHeader } from './TableCardHeader';

export type TableCardProps<V> = {
    id: string;
    className?: string;
    /** Whether the card is collapsible (shows the collapse header control). Default is true. */
    collapsible?: boolean;
    defaultCollapsed?: boolean;
    title: string;
    sizing?: Sizing;
    actions?: ReactNode;
    /** Used to store/retrieve column preferences in local storage */
    columnPreferencesKey: string;
    /** When provided, uses these preferences as the starting point if no data in local storage */
    defaultColumnPreferences?: ColumnPreference[];
    columns: Column<V>[];
    data: V[];
};

/**
 * Represents a specialized card component that contains a DataTable and settings to manage the column preferences.
 * @param {TableCardProps} props Component props
 * @return {TableCard} component
 */
const TableCard = <V,>({
    id,
    className,
    collapsible = true,
    title,
    actions,
    columns,
    columnPreferencesKey,
    defaultColumnPreferences,
    ...props
}: TableCardProps<V>) => {
    const columnPreferences = useMemo(
        () =>
            defaultColumnPreferences ??
            columns.map((column) => ({
                id: column.id,
                name: column.name,
                moveable: true,
                toggleable: true
            })),
        [defaultColumnPreferences, columns]
    );
    const ColumnPreferencesCard = withColumnPreferences(CollapsibleCard, {
        storageKey: columnPreferencesKey,
        defaults: columnPreferences
    });
    const ManagedDataTable = withColumnPreferencesDataTable(SortableDataTable<V>);
    return (
        <ColumnPreferencesCard
            id={id}
            className={className}
            collapsible={collapsible}
            defaultCollapsed={collapsible && props.data.length === 0}
            header={<TableCardHeader title={title} actions={actions} resultCount={props.data.length} />}
            showCollapseSeparator={true}>
            <ManagedDataTable {...props} id={`${id}-table`} columns={columns} />
        </ColumnPreferencesCard>
    );
};

/**
 * Higher-order component to wrap a DataTable component with columns applied according to column preferences.
 * If there is no column preferences context, the original columns are used.
 * @param {FC} WrappedComponent DataTable component
 * @return {FC<DataTableProps<V>>} A modified DataTable component with columns applied
 */
const withColumnPreferencesDataTable = <V,>(WrappedComponent: ComponentType<DataTableProps<V>>) => {
    const EnhancedComponent: FC<DataTableProps<V>> = (props) => {
        const { apply } = useColumnPreferences();
        const columns = apply(props.columns);
        return <WrappedComponent {...props} columns={columns} />;
    };

    return EnhancedComponent;
};

export { TableCard };
