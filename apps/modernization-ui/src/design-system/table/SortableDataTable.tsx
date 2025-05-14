import { ComponentType } from 'react';
import { maybeSortData, useSorting, SortingProvider } from 'sorting';
import { DataTable, DataTableProps } from './DataTable';

// export type SortableDataTableProps<T> = DataTableProps<T> & {
//     comparator?: (a: T, b: T) => number;
// };

// export const sortData = <T,>(data: T[], property: keyof T, direction: Direction | undefined): T[] =>
//     investigations.slice().sort(withDirection(resolveComparator(property), type));

export const withSortable = <T,>(Component: ComponentType<DataTableProps<T>>) => {
    const SortableComponent = (props: DataTableProps<T>) => {
        const { property, direction } = useSorting();
        console.log('SortableDataTable', property, direction, props);
        const column = props.columns.find((column) => column.id === property);
        const comparator = column?.comparator;
        const sortedData = maybeSortData(props.data, comparator, property as keyof T, direction);
        return <Component {...props} data={sortedData} />;
    };

    // const WrappedSortableComponent: FC<DataTableProps<T>> = (props: DataTableProps<T>) => (
    //     <SortingProvider appendToUrl={false}>
    //         <SortableComponent {...props} />
    //     </SortingProvider>
    // );

    SortableComponent.displayName = 'SortableComponent';
    return SortableComponent;
};

/**
 * Wraps the DataTable component with SortingProvider and uses the useSorting hook to manage
 * sorting state. Uses the comparator on each column (or default one) to determine the sorting logic.
 * Note: This requires React Router context to be available in the component tree.
 */
export const SortableDataTable = <T,>(props: DataTableProps<T>) => {
    const WrappedDataTable = withSortable<T>(DataTable);
    return (
        <SortingProvider appendToUrl={false}>
            <WrappedDataTable {...props} />
        </SortingProvider>
    );
};
