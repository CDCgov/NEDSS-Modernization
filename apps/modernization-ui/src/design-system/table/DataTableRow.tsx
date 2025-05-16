import { maybeUseSorting } from 'sorting';
import { Column } from './DataTable';
import classNames from 'classnames';
import { NoData } from 'components/NoData';
import { Sizing } from 'design-system/field';
import { Shown } from 'conditional-render';
import { defaultCellValueRenderer } from './defaultCellValueRenderer';

import styles from './data-table.module.scss';

type Props<V> = {
    columns: Column<V>[];
    row: V;
    index: number;
    sizing?: Sizing;
    heightConstrained?: boolean;
};

export const DataTableRow = <V,>({ columns, row, index }: Props<V>) => {
    const sorting = maybeUseSorting();

    return (
        <tr key={index}>
            {columns.map((column, y) => {
                const sorted = sorting?.property === column.id;
                const children = renderColumn(row, index, column);
                return (
                    <td
                        key={y}
                        className={classNames({
                            [styles.fixed]: column.fixed,
                            [styles.sorted]: sorted
                        })}>
                        <Shown when={!!children} fallback={<NoData display="dashes" />}>
                            {children}
                        </Shown>
                    </td>
                );
            })}
        </tr>
    );
};

const renderColumn = <R,>(row: R, index: number, column: Column<R>) =>
    column.render?.(row, index) ?? defaultCellValueRenderer(column.value?.(row));
