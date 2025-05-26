import { useEffect, useState } from 'react';
import { useSorting, SortingProvider, Direction, withDirection } from 'sorting';
import { CellValue, DataTable, DataTableProps } from './DataTable';
import { Mapping } from 'utils';

const InMemorySorting = <T,>(props: DataTableProps<T>) => {
    const sorting = useSorting();

    const [sorted, setSorted] = useState(props.data);

    useEffect(() => {
        const column = props.columns.find((column) => column.id === sorting.property);

        if (column && sorting.direction && sorting.direction !== Direction.None) {
            //  sort using the column values
            if ('value' in column) {
                setSorted(sortByColumn(props.data, column.value, sorting.direction));
            }
        } else {
            //  no sorting needed, pass the data as is
            setSorted(props.data);
        }
    }, [sorting.property, sorting.direction]);

    return <DataTable {...props} features={{ sorting }} data={sorted} />;
};

const sortByColumn = <R, C extends CellValue>(
    data: R[],
    mapping: Mapping<R, C | undefined>,
    direction: Direction
): R[] => {
    return data.slice().sort(withDirection(mappingComparator(mapping), direction));
};

const mappingComparator =
    <R, C extends CellValue>(mapping: Mapping<R, C | undefined>) =>
    (left: R, right: R) => {
        const value = mapping(left);
        const comparing = mapping(right);

        if (value instanceof Date && comparing instanceof Date) {
            return value.getTime() - comparing.getTime();
        } else if (typeof value === 'string' && typeof comparing === 'string') {
            return value.localeCompare(comparing, undefined, { numeric: true });
        }

        return defaultComparator(value, comparing);
    };

const defaultComparator = <V,>(value?: V, comparing?: V) => {
    if (value && !comparing) {
        return 1;
    } else if (!value && comparing) {
        return -1;
    } else if (value! > comparing!) {
        return 1;
    } else if (value! < comparing!) {
        return -1;
    } else {
        return 0;
    }
};

/**
 * Wraps the DataTable component with SortingProvider and uses the useSorting hook to manage
 * sorting state. Uses the comparator on each column (or default one) to determine the sorting logic.
 * Note: This requires React Router context to be available in the component tree.
 * @param {DataTableProps<T>} props - The props for the DataTable component.
 * @return {JSX.Element} - The wrapped DataTable component with sorting functionality.
 */
export const SortableDataTable = <T,>(props: DataTableProps<T>) => {
    return (
        <SortingProvider appendToUrl={false}>
            <InMemorySorting {...props} />
        </SortingProvider>
    );
};
