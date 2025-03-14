import { maybeUseSorting } from 'sorting';
import { Column } from './DataTable';
import styles from './data-table.module.scss';
import classNames from 'classnames';
import { NoData } from 'components/NoData';
import { Constraint, HeightConstrained } from './HeightConstrained';
import { useState } from 'react';
import { Sizing } from 'design-system/field';

type Props<V> = {
    columns: Column<V>[];
    row: V;
    index: number;
    sizing?: Sizing;
    heightConstrained?: boolean;
};

export const DataTableRow = <V,>({ columns, row, index, heightConstrained }: Props<V>) => {
    const sorting = maybeUseSorting();
    const [constraint, setConstraint] = useState<Constraint>('acceptable');

    return (
        <tr key={index}>
            {columns.map((column, y) => {
                const isSorting = sorting?.property === column.id;
                const children = column.render(row, index);
                return (
                    <td
                        key={y}
                        className={classNames({
                            [styles.fixed]: column.fixed,
                            [styles.sorted]: isSorting
                        })}>
                        {children ? (
                            heightConstrained ? (
                                <HeightConstrained
                                    key={`hc${index}${y}`}
                                    rowConstraint={constraint}
                                    onChange={setConstraint}
                                    name={column.name.toLowerCase()}>
                                    {children}
                                </HeightConstrained>
                            ) : (
                                children
                            )
                        ) : (
                            <NoData display="dashes" />
                        )}
                    </td>
                );
            })}
        </tr>
    );
};
