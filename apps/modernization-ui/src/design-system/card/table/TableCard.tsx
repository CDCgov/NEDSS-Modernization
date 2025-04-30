import { CollapsibleCard } from '../collapsible';
import { Sizing } from 'design-system/field';
import { DataTable, DataTableProps } from 'design-system/table';
import { TableCardAction, TableCardHeader } from './TableCardHeader';

export type TableCardProps<V> = {
    id: string;
    className?: string;
    /** Whether the card is collapsible (shows the collapse header control). Default is true. */
    collapsible?: boolean;
    title: string;
    sizing?: Sizing;
    tableClassName?: string;
    actions?: TableCardAction[];
} & Omit<DataTableProps<V>, 'id' | 'className'>;

export const TableCard = <V,>({
    id,
    className,
    collapsible = true,
    title,
    tableClassName,
    actions,
    ...props
}: TableCardProps<V>) => {
    return (
        <CollapsibleCard
            id={id}
            className={className}
            collapsible={collapsible}
            header={
                <TableCardHeader
                    sizing={props?.sizing}
                    resultCount={props?.data?.length ?? 0}
                    title={title}
                    actions={actions}
                />
            }
            showCollapseSeparator={true}>
            <DataTable id={`${id}-table`} className={tableClassName} {...props} />
        </CollapsibleCard>
    );
};
