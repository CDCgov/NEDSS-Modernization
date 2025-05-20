import { ReactNode } from 'react';
import classNames from 'classnames';
import { Shown } from 'conditional-render';
import { DataTableHeader } from './header/DataTableHeader';
import { DataTableRow } from './DataTableRow';
import { Sizing } from 'design-system/field';
import { FilterDescriptor } from 'design-system/filter';
import { NoDataRow } from './NoDataRow';

import styles from './data-table.module.scss';
import { Comparator, ComparatorType } from 'sorting';

type SortIconType = 'default' | 'alpha' | 'numeric';

type Column<V> = {
    id: string;
    name: string;
    fixed?: boolean;
    sortable?: boolean;
    className?: string;
    render: (value: V, index: number) => ReactNode | undefined;
    filter?: FilterDescriptor;
    sortIconType?: SortIconType;
    comparator?: ComparatorType | Comparator<V>;
};

type DataTableProps<V> = {
    id: string;
    className?: string;
    columns: Column<V>[];
    data: V[];
    sizing?: Sizing;
    rowHeightConstrained?: boolean;
    noDataFallback?: boolean;
};

const DataTable = <V,>({
    id,
    className,
    columns,
    data,
    sizing,
    noDataFallback,
    rowHeightConstrained = true
}: DataTableProps<V>) => {
    const resolvedClasses = classNames('usa-table--borderless', styles.table, sizing && styles[sizing]);
    return (
        <div id={id} className={resolvedClasses}>
            <table className={classNames('usa-table', className)}>
                <DataTableHeader columns={columns} sizing={sizing} />
                <tbody>
                    <Shown when={data.length > 0} fallback={noDataFallback && <NoDataRow colSpan={columns.length} />}>
                        {data.map((row, index) => (
                            <DataTableRow
                                index={index}
                                row={row}
                                columns={columns}
                                sizing={sizing}
                                heightConstrained={rowHeightConstrained}
                                key={index}
                            />
                        ))}
                    </Shown>
                </tbody>
            </table>
        </div>
    );
};

export { DataTable };

export type { Column, FilterDescriptor, SortIconType, DataTableProps };
