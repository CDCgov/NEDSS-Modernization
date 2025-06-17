import classNames from 'classnames';
import { SortingInteraction } from 'libs/sorting';
import { NoData } from 'design-system/data';
import { Sizing } from 'design-system/field';
import { Shown } from 'conditional-render';
import { Column } from './header/column';
import { defaultCellRenderer } from './defaultCellRenderer';

import styles from './data-table.module.scss';

type Props<V> = {
    columns: Column<V>[];
    row: V;
    index: number;
    sizing?: Sizing;
    sorting?: SortingInteraction;
};

export const DataTableRow = <V,>({ columns, sorting, row, index }: Props<V>) => {
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
                        <Shown when={!!children} fallback={<NoData />}>
                            {children}
                        </Shown>
                    </td>
                );
            })}
        </tr>
    );
};

const renderColumn = <R,>(row: R, index: number, column: Column<R>) =>
    'render' in column
        ? //  render function takes precedence
          column.render(row, index)
        : defaultCellRenderer(column.value(row));
