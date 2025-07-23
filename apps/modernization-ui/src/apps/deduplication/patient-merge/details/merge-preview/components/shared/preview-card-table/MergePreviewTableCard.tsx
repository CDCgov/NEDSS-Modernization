import { Card } from 'design-system/card/Card';
import { SortableDataTable } from 'design-system/table';
import { Tag } from 'design-system/tag/Tag';
import { Column } from 'design-system/table';
import styles from './MergePreviewTableCard.module.scss';

type SortableTableCardProps<T> = {
    id: string;
    title: string;
    columns: Column<T, any>[];
    data: T[];
};

export function MergePreviewTableCard<T>({ id, title, columns, data }: SortableTableCardProps<T>) {
    return (
        <Card
            id={id}
            title={title}
            level={2}
            collapsible={false}
            className={styles.card}
            flair={
                <Tag size="small" weight="bold">
                    {data.length}
                </Tag>
            }>
            <SortableDataTable
                id={`${id}-table`}
                columns={columns}
                data={data}
                sizing={'small'}
                className={styles.dataTable}
            />
            <div className={styles.footer} />
        </Card>
    );
}
