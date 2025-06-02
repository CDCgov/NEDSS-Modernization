import { ReactNode } from 'react';
import classNames from 'classnames';
import { Shown } from 'conditional-render';
import { Mapping } from 'utils';
import { SortingInteraction } from 'libs/sorting';
import { Sizing } from 'design-system/field';
import { FilterDescriptor, FilterInteraction } from 'design-system/filter';
import { DataTableHeader } from './header/DataTableHeader';
import { DataTableRow } from './DataTableRow';
import { NoDataRow } from './NoDataRow';

import styles from './data-table.module.scss';

type SortIconType = 'default' | 'alpha' | 'numeric';

type CellValue = string | number | boolean | Date;

type HasRenderFunction<R> = { render: (value: R, index: number) => ReactNode | undefined };
type HasValueFunction<R, C = CellValue> = { value: Mapping<R, C | undefined> };

type RenderMethod<R, C = CellValue> =
    | HasRenderFunction<R>
    | HasValueFunction<R, C>
    | (HasRenderFunction<R> & HasValueFunction<R, C>);

type Column<R, C = CellValue> = {
    id: string;
    name: string;
    fixed?: boolean;
    sortable?: boolean;
    className?: string;
    filter?: FilterDescriptor;
    sortIconType?: SortIconType;
} & RenderMethod<R, C>;

type DataTableFeatures = {
    sorting?: SortingInteraction;
    filtering?: FilterInteraction;
};

type DataTableProps<V> = {
    id: string;
    className?: string;
    columns: Column<V>[];
    data: V[];
    sizing?: Sizing;
    noDataFallback?: boolean;
    features?: DataTableFeatures;
};

const DataTable = <V,>({ id, className, columns, data, sizing, features = {}, noDataFallback }: DataTableProps<V>) => {
    const resolvedClasses = classNames('usa-table--borderless', styles.table, {
        [styles.sized]: sizing,
        [styles.small]: sizing === 'small',
        [styles.medium]: sizing === 'medium',
        [styles.large]: sizing === 'large'
    });
    return (
        <div id={id} className={resolvedClasses}>
            <table className={classNames('usa-table', className)}>
                <DataTableHeader
                    columns={columns}
                    sizing={sizing}
                    filtering={features.filtering}
                    sorting={features.sorting}
                />
                <tbody>
                    <Shown when={data.length > 0} fallback={noDataFallback && <NoDataRow colSpan={columns.length} />}>
                        {data.map((row, index) => (
                            <DataTableRow
                                sorting={features.sorting}
                                index={index}
                                row={row}
                                columns={columns}
                                sizing={sizing}
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

export type { Column, CellValue, FilterDescriptor, SortIconType, DataTableProps, DataTableFeatures, HasValueFunction };
