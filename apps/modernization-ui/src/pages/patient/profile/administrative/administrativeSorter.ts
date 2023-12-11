import { Comparator, Direction, descending, sortByAlpha, sortByDate, withDirection } from 'sorting';
import { Column, Administrative } from './administrative';

export type SortCriteria = {
    name?: Column;
    type?: Direction;
};

export const sort = (administratives: Administrative[], { name, type }: SortCriteria): Administrative[] =>
    administratives.slice().sort(withDirection(resolveComparator(name), type));

const resolveComparator = (name: Column | undefined): Comparator<Administrative> => {
    switch (name) {
        case Column.AsOf:
            return sortByDate('asOf');
        case Column.Comment:
            return sortByAlpha('comment');
        default:
            return defaultSort;
    }
};

const defaultSort: Comparator<Administrative> = descending(sortByDate('asOf'));
