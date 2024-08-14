import { maybeUseSorting } from 'sorting';
import { Column } from './DataTable';
import styles from './data-table.module.scss';
import classNames from 'classnames';
import { NoData } from 'components/NoData';
import { HeightClamp } from './HeightClamp';

type Props<V> = {
    columns: Column<V>[];
    row: V;
    index: number;
};

export const DataTableRow = <V,>({ columns, row, index }: Props<V>) => {
    const sorting = maybeUseSorting();

    return (
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
                        {column.render(row) ? <HeightClamp> {column.render(row)} </HeightClamp> : <NoData />}
                    </td>
                );
            })}
        </tr>
    );
};
