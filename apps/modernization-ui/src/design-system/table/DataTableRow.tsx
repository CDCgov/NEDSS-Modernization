import { maybeUseSorting } from 'sorting';
import { Column } from './DataTable';
import styles from './data-table.module.scss';
import classNames from 'classnames';
import { NoData } from 'components/NoData';
import { Constraint, HeightConstrained } from './HeightConstrained';
import { useState } from 'react';

type Props<V> = {
    columns: Column<V>[];
    row: V;
    index: number;
};

export const DataTableRow = <V,>({ columns, row, index }: Props<V>) => {
    const sorting = maybeUseSorting();
    const [constraint, setConstraint] = useState<Constraint>('acceptable');

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
                        {column.render(row, index) ? (
                            <HeightConstrained
                                rowConstraint={constraint}
                                onChange={setConstraint}
                                name={column.name.toLowerCase()}>
                                {column.render(row, index)}
                            </HeightConstrained>
                        ) : (
                            <NoData showText={false} />
                        )}
                    </td>
                );
            })}
        </tr>
    );
};
