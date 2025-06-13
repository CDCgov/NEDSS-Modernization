import { ReactNode } from 'react';
import { SortHandler, SortingInteraction, SortingProvider, useSorting } from 'libs/sorting';
import { Column } from './header/column';
import { DataTable, DataTableProps } from './DataTable';

/**
 * A variant of DataTable that provides in memory sorting of rows based on the values of the column
 *  being sorted.  A Column can only be sorted if it defines a value function.
 *
 * Note: This requires React Router context to be available in the component tree.
 *
 * @param {DataTableProps<T>} props - The props for the DataTable component.
 * @return {JSX.Element} - A DataTable component with in memory sorting functionality.
 */
const SortableDataTable = <T,>({ columns, data, ...remaining }: DataTableProps<T>) => (
    <SortingProvider appendToUrl={false}>
        <UsingSort>
            {(sorting) => (
                <SortHandler sorting={sorting} resolver={maybeResolveMapping(columns)} data={data}>
                    {({ sorting, sorted }) => (
                        <DataTable {...remaining} features={{ sorting }} columns={columns} data={sorted} />
                    )}
                </SortHandler>
            )}
        </UsingSort>
    </SortingProvider>
);

type UsingSortProps = {
    children: (sorting: SortingInteraction) => ReactNode | ReactNode[];
};

const UsingSort = ({ children }: UsingSortProps) => {
    const sorting = useSorting();

    return children(sorting);
};

const maybeResolveMapping =
    <R, C>(columns: Column<R, C>[]) =>
    (property: string) => {
        const column = columns.find((column) => column.id === property);

        if (column && 'value' in column) {
            return column.value;
        }
    };

export { SortableDataTable };
