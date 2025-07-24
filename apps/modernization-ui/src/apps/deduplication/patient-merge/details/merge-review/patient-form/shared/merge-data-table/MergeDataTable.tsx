import { ReactNode } from 'react';
import { Column, DataTable } from 'design-system/table';
import { Checkbox } from 'design-system/checkbox';
import { Button } from 'design-system/button';
import styles from './merge-data-table.module.scss';

type Props<V> = {
    id: string;
    columns: Column<V>[];
    data?: V[];
    rowId: (entry: V) => string;
    isSelected: (entry: V) => boolean;
    onSelect: (entry: V) => void;
    isViewed: (entry: V) => boolean;
    onView: (entry: V) => void;
};
/**
 *
 * Provides a table component for the Patient merge details page. Since these tables all have a Checkbox in the first column and a
 * View icon in the last column, this component handles adding those columns as well as the styling for those columns.
 *
 *  @param {string} props.id a unique identifier for the DataTable
 *  @param {Object[]} props.columns the column definition and how to render the provided data. The Checkbox and View icon columns are added by default and do not need to be provided
 *  @param {Object[]} props.data the data to display in the table
 *  @param {function} props.rowId a unique identifier for each row. used by the Checkbox
 *  @param {function} props.isSelected the Checkbox state
 *  @param {function} props.onSelect the handler for Checkbox onChange
 *  @param {function} props.isViewed the View icon state
 *  @param {function} props.onView the handler for the View icon onClick
 * @return {ReactNode}
 */
export const MergeDataTable = <V,>({
    id,
    columns,
    data,
    rowId,
    isSelected,
    onSelect,
    isViewed,
    onView
}: Props<V>): ReactNode => {
    const displayedColumns: Column<V>[] = [
        {
            id: 'selection',
            name: '',
            render: (v) => (
                <div className={styles.action}>
                    <Checkbox
                        id={`checkbox-column-${rowId(v)}`}
                        label=""
                        className={styles.checkBox}
                        onChange={() => onSelect(v)}
                        selected={isSelected(v)}
                    />
                </div>
            )
        },
        ...columns,
        {
            id: 'view-icon',
            name: '',
            className: styles['selection-header'],
            render: (v) => (
                <div className={styles.action}>
                    <Button
                        tertiary
                        sizing="small"
                        icon="visibility"
                        aria-pressed={isViewed(v)}
                        aria-label="View"
                        onClick={() => onView(v)}
                    />
                </div>
            )
        }
    ];

    return (
        <DataTable<V>
            id={id}
            className={styles.dataTable}
            sizing="small"
            columns={displayedColumns}
            data={data ?? []}
        />
    );
};
