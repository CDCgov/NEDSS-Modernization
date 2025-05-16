import { ComponentType } from 'react';
import { maybeSortData, useSorting, SortingProvider } from 'sorting';
import { DataTable, DataTableProps } from './DataTable';

const withSortable = <T,>(Component: ComponentType<DataTableProps<T>>) => {
    const SortableComponent = (props: DataTableProps<T>) => {
        const { property, direction } = useSorting();
        const column = props.columns.find((column) => column.id === property);
        const comparator = column?.comparator;
        const sortedData = maybeSortData(props.data, comparator, property as keyof T, direction);
        return <Component {...props} data={sortedData} />;
    };

    SortableComponent.displayName = 'SortableComponent';
    return SortableComponent;
};

/**
 * Wraps the DataTable component with SortingProvider and uses the useSorting hook to manage
 * sorting state. Uses the comparator on each column (or default one) to determine the sorting logic.
 * Note: This requires React Router context to be available in the component tree.
 * @param {DataTableProps<T>} props - The props for the DataTable component.
 * @return {JSX.Element} - The wrapped DataTable component with sorting functionality.
 */
export const SortableDataTable = <T,>(props: DataTableProps<T>) => {
    const WrappedDataTable = withSortable<T>(DataTable);
    return (
        <SortingProvider appendToUrl={false}>
            <WrappedDataTable {...props} />
        </SortingProvider>
    );
};
