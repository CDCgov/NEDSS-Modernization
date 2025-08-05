import { SortHandler, SortingProvider } from 'libs/sorting';
import { DataTable, DataTableProps } from '../DataTable';
import { columnSortResolver } from './columnSortResolver';
import { useColumnPreferences } from '../preferences';

/**
 * A variant of DataTable that provides in memory sorting of rows based on the values of the column
 *  being sorted.  A Column can only be sorted if it defines a value function.
 *
 * Note: This requires React Router context to be available in the component tree.
 *
 * @param {DataTableProps<T>} props - The props for the DataTable component.
 * @return {JSX.Element} - A DataTable component with in memory sorting functionality.
 */
const SortableDataTable = <T,>({ columns, data, ...remaining }: DataTableProps<T>) => {
    return (
        <SortingProvider appendToUrl={false}>
            {(sorting) => (
                <SortHandler sorting={sorting} resolver={columnSortResolver(columns)} data={data}>
                    {({ sorting, sorted }) => (
                        <DataTable {...remaining} features={{ sorting }} columns={columns} data={sorted} />
                    )}
                </SortHandler>
            )}
        </SortingProvider>
    );
};

export { SortableDataTable };
