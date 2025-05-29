import { Sizing } from 'design-system/field';
import { Column } from 'design-system/table';
import { ColumnHeader } from 'design-system/table/header/ColumnHeader';

import styles from '../data-table.module.scss';
import { SortingInteraction } from 'libs/sorting';
import { FilterInteraction } from 'design-system/filter';

type HeaderRowProps<V> = {
    columns: Column<V>[];
    sizing?: Sizing;
    sorting?: SortingInteraction;
    filtering?: FilterInteraction;
};

const DataTableHeader = <T,>({ columns, sizing, sorting, filtering }: HeaderRowProps<T>) => {
    return (
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
    );
};

export { DataTableHeader };
