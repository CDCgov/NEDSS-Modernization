import { Sizing } from 'design-system/field';
import { Column, SortableDataTable } from 'design-system/table';
import { ColumnPreference, ColumnPreferenceProvider, ColumnPreferencesAction } from 'design-system/table/preferences';
import { Tag } from 'design-system/tag';
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
    return (
        <ColumnPreferenceProvider
            id={`preferences-${id}`}
            key={columnPreferencesKey}
            defaults={defaultColumnPreferences}>
            {(columnPreferences) => (
                <Card
                    id={id}
                    title={title}
                    className={className}
                    collapsible={collapsible}
                    open={collapsible && props.data.length > 0}
                    flair={<Tag size={sizing}>{props.data.length}</Tag>}
                    actions={
                        <>
                            {actions}
                            <ColumnPreferencesAction sizing={sizing} />
                        </>
                    }>
                    <SortableDataTable
                        {...props}
                        id={`${id}-table`}
                        sizing={sizing}
                        columns={columnPreferences.apply(columns)}
                    />
                </Card>
            )}
        </ColumnPreferenceProvider>
    );
};

export { TableCard };
