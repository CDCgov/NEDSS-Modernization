import { ReactNode } from 'react';
import classNames from 'classnames';
import { Header } from './Header';
import { maybeUseSorting } from 'sorting';

import styles from './data-table.module.scss';
import { NoData } from 'components/NoData';

type Column<V> = {
    id: string;
    name: string;
    fixed?: boolean;
    sortable?: boolean;
    render: (value: V) => ReactNode | undefined;
};

type Props<V> = {
    id: string;
    className?: string;
    columns: Column<V>[];
    data: V[];
};

const DataTable = <V,>({ id, className, columns, data }: Props<V>) => {
    const sorting = maybeUseSorting();

    console.log('data:', data);

    return (
        <div id={id} className={classNames('usa-table--borderless', styles.table)}>
            <table className={classNames('usa-table', className)}>
                <thead>
                    <tr>
                        {columns.map((column, index) => (
                            <Header key={index}>{column}</Header>
                        ))}
                    </tr>
                    <tr className={styles.border}>
                        <th colSpan={columns.length} />
                    </tr>
                </thead>
                <tbody>
                    {data.map((row, index) => (
                        <tr key={index}>
                            {columns.map((column, y) => {
                                const isSorting = sorting?.property === column.id;
                                return (
                                    <td
                                        key={y}
                                        className={classNames({
                                            [styles.fixed]: column.fixed,
                                            [styles.sorted]: isSorting
                                        })}>
                                        {column.render(row) ? column.render(row) : <NoData />}
                                    </td>
                                );
                            })}
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export { DataTable };

export type { Column };
