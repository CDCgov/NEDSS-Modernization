import {
    Comparator,
    Direction,
    descending,
    sortByAlpha,
    sortByDate,
    sortByNestedProperty,
    withDirection
} from 'sorting';
import { Column, Name } from './NameEntry';

export type SortCriteria = {
    name?: Column;
    type?: Direction;
};

export const sort = (names: Name[], { name, type }: SortCriteria): Name[] =>
    names.slice().sort(withDirection(resolveComparator(name), type));

const resolveComparator = (name: Column | undefined): Comparator<Name> => {
    switch (name) {
        case 'As of':
            return sortByDate('asOf');
        case 'Prefix':
            return sortByNestedProperty('prefix');
        case 'Name (last, first middle)':
            return sortByAlpha('last');
        case 'Suffix':
            return sortByAlpha('suffix');
        case 'Degree':
            return sortByNestedProperty('degree');
        case 'Type':
            return sortByNestedProperty('use');
        default:
            return defaultSort;
    }
};

const defaultSort: Comparator<Name> = descending(sortByDate('asOf'));
