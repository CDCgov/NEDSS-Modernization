import { Column } from '../header/column';

const columnSortResolver =
    <R, C>(columns: Column<R, C>[]) =>
    (property: string) => {
        const column = columns.find((column) => column.id === property);

        if (column && 'value' in column) {
            return column.value;
        }
    };

export { columnSortResolver };
