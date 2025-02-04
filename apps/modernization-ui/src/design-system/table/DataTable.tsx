import { ReactNode } from 'react';
import classNames from 'classnames';
import { Header } from './Header';
import styles from './data-table.module.scss';
import { DataTableRow } from './DataTableRow';
import { Sizing } from 'design-system/field';

type Column<V> = {
    id: string;
    name: string;
    fixed?: boolean;
    sortable?: boolean;
    className?: string;
    render: (value: V, index: number) => ReactNode | undefined;
    filter?: ReactNode;
};

type DataTableProps<V> = {
    id: string;
    className?: string;
    columns: Column<V>[];
    data: V[];
    filterable?: boolean;
    sizing?: Sizing;
};

const DataTable = <V,>({ id, className, columns, filterable, data, sizing = 'medium' }: DataTableProps<V>) => {
    const resolvedClasses = classNames(
        'usa-table--borderless',
        styles.table,
        { [styles.small]: sizing === 'small' },
        { [styles.medium]: sizing === 'medium' },
        { [styles.large]: sizing === 'large' }
    );
    return (
        <div id={id} className={resolvedClasses}>
            <table className={classNames('usa-table', className)}>
                <thead>
                    <tr>
                        {columns.map((column, index) => (
                            <Header key={index} className={column.className} filterable={filterable}>
                                {column}
                            </Header>
                        ))}
                    </tr>
                    <tr className={styles.border}>
                        <th colSpan={columns.length} />
                    </tr>
                </thead>
                <tbody>
                    {data.map((row, index) => (
                        <DataTableRow index={index} row={row} columns={columns} key={index} />
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export { DataTable };

export type { Column };
