import { ComponentType, FC } from 'react';
import { SortableDataTable, DataTableProps, Column } from 'design-system/table';
import {
    ColumnPreference,
    ColumnPreferencesAction,
    useColumnPreferences,
    withColumnPreferences
} from 'design-system/table/preferences';
import { Tag } from 'design-system/tag';
import { Sizing } from 'design-system/field';
import { Card, CardProps } from '../Card';

export type TableCardProps<V> = {
    sizing?: Sizing;
    /** A unique identifier for persisting column preferences. */
    columnPreferencesKey: string;
    /** The column preferences that are used when resetting the preferences. */
    defaultColumnPreferences: ColumnPreference[];
    columns: Column<V>[];
    data: V[];
} & Omit<CardProps, 'children'>;

/**
 * Represents a specialized card component that contains a DataTable and settings to manage the column preferences.
 *
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
    sizing,
    ...props
}: TableCardProps<V>) => {
    const ColumnPreferencesCard = withColumnPreferences(Card, {
        storageKey: columnPreferencesKey,
        defaults: defaultColumnPreferences
    });
    const ManagedDataTable = withColumnPreferencesDataTable(SortableDataTable<V>);
    return (
        <ColumnPreferencesCard
            id={id}
            title={title}
            className={className}
            sizing={sizing}
            collapsible={collapsible}
            open={collapsible && props.data.length > 0}
            flair={<Tag size={sizing}>{props.data.length}</Tag>}
            actions={
                <>
                    {actions}
                    <ColumnPreferencesAction sizing={sizing} />
                </>
            }>
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
