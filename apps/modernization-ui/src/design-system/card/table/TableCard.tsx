import { CollapsibleCard } from '../collapsible';
import { Sizing } from 'design-system/field';
import { DataTable, DataTableProps } from 'design-system/table';
import { TableCardAction, TableCardHeader } from './TableCardHeader';
import { ColumnPreference, useColumnPreferences, withColumnPreferences } from 'design-system/table/preferences';
import { ComponentType, FC, useMemo } from 'react';

export type TableCardProps<V> = {
    id: string;
    className?: string;
    /** Whether the card is collapsible (shows the collapse header control). Default is true. */
    collapsible?: boolean;
    title: string;
    sizing?: Sizing;
    tableClassName?: string;
    actions?: TableCardAction[];
    showSettings?: boolean;
    /** Used to store/retrieve column preferences in local storage */
    columnPreferencesKey: string;
    /** When provided, uses these preferences as the starting point if no data in local storage */
    defaultColumnPreferences?: ColumnPreference[];
} & Omit<DataTableProps<V>, 'id' | 'className'>;

/**
 * Represents a specialized card component that contains a DataTable and settings to manage the column preferences.
 * @param {TableCardProps} props Component props
 * @return {TableCard} component
 */
export const TableCard = <V,>({
    id,
    className,
    collapsible = true,
    title,
    tableClassName,
    actions,
    columns,
    showSettings = true,
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
    const ManagedDataTable = withColumnPreferencesDataTable(DataTable<V>);
    return (
        <ColumnPreferencesCard
            id={id}
            className={className}
            collapsible={collapsible}
            header={
                <TableCardHeader
                    title={title}
                    actions={actions}
                    resultCount={props?.data?.length ?? 0}
                    showSettings={showSettings}
                    sizing={props?.sizing}
                />
            }
            showCollapseSeparator={true}>
            <ManagedDataTable {...props} id={`${id}-table`} className={tableClassName} columns={columns} />
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
