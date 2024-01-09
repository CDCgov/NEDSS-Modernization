import { Comparator, Direction, descending, sortBy, sortByAlphanumeric, sortByDate, withDirection } from 'sorting';
import { Column, Treatment } from './treatment';

export type SortCriteria = {
    name?: Column;
    type?: Direction;
};

export const sort = (treatment: Treatment[], { name, type }: SortCriteria): Treatment[] =>
    treatment.slice().sort(withDirection(resolveComparator(name), type));

const resolveComparator = (name: Column | undefined): Comparator<Treatment> => {
    switch (name) {
        case Column.DateCreated:
            return sortByDate('createdOn');
        case Column.Provider:
            return sortBy('provider');
        case Column.TreatmentDate:
            return sortByDate('treatedOn');
        case Column.Treatment:
            return sortBy('description');
        case Column.EventNumber:
            return sortByAlphanumeric('event');
        default:
            return defaultSort;
    }
};

const defaultSort: Comparator<Treatment> = descending(sortByDate('createdOn'));
