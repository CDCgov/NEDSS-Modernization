import { ReactNode } from 'react';
import classNames from 'classnames';
import { Header } from './Header';
import { DataTableRow } from './DataTableRow';
import { Sizing } from 'design-system/field';
import { FilterDescriptor } from 'design-system/filter';

import styles from './data-table.module.scss';

type Column<V> = {
    id: string;
    name: string;
    fixed?: boolean;
    sortable?: boolean;
    className?: string;
    render: (value: V, index: number) => ReactNode | undefined;
    filter?: FilterDescriptor;
};

type DataTableProps<V> = {
    id: string;
    className?: string;
    columns: Column<V>[];
    data: V[];
    sizing?: Sizing;
};

const DataTable = <V,>({ id, className, columns, data, sizing }: DataTableProps<V>) => {
    const resolvedClasses = classNames('usa-table--borderless', styles.table, sizing ? styles[sizing] : undefined);
    return (
        <div id={id} className={resolvedClasses}>
            <table className={classNames('usa-table', className)}>
                <thead>
                    <tr>
                        {columns.map((column, index) => (
                            <Header key={index} className={column.className}>
                                {column}
                            </Header>
                        ))}
                    </tr>
                    <tr className={styles.border} aria-hidden>
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

export type { Column, FilterDescriptor };
