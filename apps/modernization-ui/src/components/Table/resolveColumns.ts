import { Header } from './Table';

type Column = {
    name: string;
    position: number;
    type: 'data' | 'selection';
    sortable: boolean;
};

const resolveColumns = (selectable: boolean, headers: Header[]): Column[] => {
    if (selectable) {
        return [{ name: 'selection', type: 'selection', position: 0, sortable: false }, ...headers.map(asColumn(1))];
    } else {
        return headers.map(asColumn());
    }
};

const asColumn =
    (offset = 0) =>
    (header: Header, position: number): Column => {
        return { name: header.name, type: 'data', position: position + offset, sortable: header.sortable || false };
    };
export { resolveColumns };

export type { Column };
