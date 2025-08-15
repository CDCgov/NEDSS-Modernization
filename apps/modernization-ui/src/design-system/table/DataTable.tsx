import { ReactNode } from 'react';
import classNames from 'classnames';
import { Shown } from 'conditional-render';
import { SortingInteraction } from 'libs/sorting';
import { Column } from './header/column';
import { Sizing } from 'design-system/field';
import { FilterInteraction } from 'design-system/filter';
import { DataTableHeader } from './header/DataTableHeader';
import { DataTableRow } from './DataTableRow';
import { NoDataRow } from './NoDataRow';

import styles from './data-table.module.scss';

type EmptyRenderer = (columns: number) => ReactNode | ReactNode[] | undefined;

type DataTableFeatures = {
    sorting?: SortingInteraction;
    filtering?: FilterInteraction;
};

type DataTableOptions = {
    stickyHeaders?: boolean;
};

type DataTableProps<V> = {
    id: string;
    className?: string;
    columns: Column<V>[];
    data: V[];
    sizing?: Sizing;
    onEmpty?: EmptyRenderer;
    features?: DataTableFeatures;
    options?: DataTableOptions;
};

const DataTable = <V,>({
    id,
    className,
    columns,
    data,
    sizing,
    onEmpty = defaultEmptyHandler,
    features = {},
    options = {}
}: DataTableProps<V>) => {
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
                    sticky={options.stickyHeaders}
                />
                <tbody>
                    <Shown when={data.length > 0} fallback={onEmpty(columns.length)}>
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

const defaultEmptyHandler = (columns: number) => <NoDataRow columns={columns}>No data has been added.</NoDataRow>;

export { DataTable };

export type { DataTableProps, DataTableFeatures };
