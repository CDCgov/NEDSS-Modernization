import React from 'react';
import { Card } from 'design-system/card/Card';
import { SortableDataTable } from 'design-system/table/SortableDataTable';
import { Tag } from 'design-system/tag/Tag';
import { Column } from 'design-system/table/DataTable';
import styles from './SortableTableCard.module.scss';

type SortableTableCardProps<T> = {
    id: string;
    title: string;
    columns: Column<T, any>[];
    data: T[];
};

export function SortableTableCard<T>({ id, title, columns, data }: SortableTableCardProps<T>) {
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
            <SortableDataTable id={`${id}-table`} columns={columns} data={data} className={styles.dataTable} />
            <div className={styles.footer} />
        </Card>
    );
}
