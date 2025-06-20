import { Column, CellValue } from 'design-system/table';
import { Entry } from './useMultiValueEntry';
import { Rendered } from 'design-system/table/header/column';

const entryColumns = <E>(columns: Column<E>[]): Column<Entry<E>>[] => {
    return columns.map(wrapped);
};

const wrapped = <E>(column: Column<E>): Column<Entry<E>> => {
    const rendered = ensureRendered(column);
    return { ...column, ...rendered };
};

const ensureRendered = <R, C = CellValue>(rendered: Rendered<R, C>): Rendered<Entry<R>, C> => {
    if ('value' in rendered && 'render' in rendered) {
        return {
            value: (entry: Entry<R>) => rendered.value(entry.value),
            render: (entry: Entry<R>, index: number) => rendered.render(entry.value, index)
        };
    } else if ('render' in rendered) {
        return { render: (entry: Entry<R>, index: number) => rendered.render(entry.value, index) };
    } else if ('value' in rendered) {
        return { value: (entry: Entry<R>) => rendered.value(entry.value) };
    } else {
        // should never get here
        throw new Error('Parameter is not a number!');
    }
};

export { entryColumns };
