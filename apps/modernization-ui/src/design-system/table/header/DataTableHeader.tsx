import { SortingInteraction } from 'libs/sorting';
import { FilterInteraction } from 'design-system/filter';
import { Sizing } from 'design-system/field';
import { Column } from './column';
import { ColumnHeader } from './ColumnHeader';

import styles from '../data-table.module.scss';

type HeaderRowProps<V> = {
    columns: Column<V>[];
    sizing?: Sizing;
    sorting?: SortingInteraction;
    filtering?: FilterInteraction;
};

const DataTableHeader = <T,>({ columns, sizing, sorting, filtering }: HeaderRowProps<T>) => {
    return (
        <>
            <colgroup>
                {columns.map((column, index) => (
                    <col className={column.className} key={index} />
                ))}
            </colgroup>
            <thead>
                <tr>
                    {columns.map((column, index) => (
                        <ColumnHeader
                            key={index}
                            className={column.className}
                            sizing={sizing}
                            sorting={sorting}
                            filtering={filtering}>
                            {column}
                        </ColumnHeader>
                    ))}
                </tr>
                <tr className={styles.border} aria-hidden>
                    <th colSpan={columns.length} />
                </tr>
            </thead>
        </>
    );
};

export { DataTableHeader };
