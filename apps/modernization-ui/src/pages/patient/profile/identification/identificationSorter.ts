import {
    Comparator,
    Direction,
    descending,
    sortByAlpha,
    sortByDate,
    sortByNestedProperty,
    withDirection
} from 'sorting';
import { Column, Identification } from './identification';

export type SortCriteria = {
    name?: Column;
    type?: Direction;
};

export const sort = (identifications: Identification[], { name, type }: SortCriteria): Identification[] =>
    identifications.slice().sort(withDirection(resolveComparator(name), type));

const resolveComparator = (name: Column | undefined): Comparator<Identification> => {
    switch (name) {
        case Column.AsOf:
            return sortByDate('asOf');
        case Column.Type:
            return sortByNestedProperty('type');
        case Column.Authority:
            return sortByNestedProperty('authority');
        case Column.Value:
            return sortByAlpha('value');
        default:
            return defaultSort;
    }
};

const defaultSort: Comparator<Identification> = descending(sortByDate('asOf'));
