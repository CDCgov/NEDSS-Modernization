import { CollapsibleCard } from '../collapsible';
import { Sizing } from 'design-system/field';
import { Column, DataTable, DataTableProps } from 'design-system/table';
import { TableCardAction, TableCardHeader } from './TableCardHeader';
import { ColumnPreference, useMaybeColumnPreferences, withColumnPreferences } from 'design-system/table/preferences';
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
    columnPreferencesKey?: string;
    defaultColumnPreferences?: ColumnPreference[];
} & Omit<DataTableProps<V>, 'id' | 'className'>;

export const TableCard = <V,>({
    id,
    className,
    collapsible = true,
    title,
    tableClassName,
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
    const ManagedDataTable = withMaybeColumnPreferencesDataTable(DataTable<V>);
    return (
        <ColumnPreferencesCard
            id={id}
            className={className}
            collapsible={collapsible}
            header={<TableCardHeader title={title} actions={actions} showSettings={columnPreferencesKey != null} />}
            showCollapseSeparator={true}>
            <ManagedDataTable id={`${id}-table`} className={tableClassName} columns={columns} {...props} />
        </ColumnPreferencesCard>
    );
};

const withMaybeColumnPreferencesDataTable = <V,>(WrappedComponent: ComponentType<DataTableProps<V>>) => {
    const EnhancedComponent: FC<DataTableProps<V>> = (props) => {
        const interaction = useMaybeColumnPreferences();
        const apply = interaction?.apply ?? ((columns: Array<Column<V>>) => columns);
        const columns = apply(props.columns);
        return <WrappedComponent {...props} columns={columns} />;
    };

    return EnhancedComponent;
};
