import { Direction, sortByAlphanumeric, sortByDate, withDirection, simpleSort, descending, Comparator } from 'sorting';
import { Headers, Vaccination, isAssociatedWith } from './PatientVaccination';

export type SortCriteria = {
    name?: Headers;
    type?: Direction;
};

export const sort = (contacts: Vaccination[], { name, type }: SortCriteria): Vaccination[] =>
    contacts.slice().sort(withDirection(resolveComparator(name), type));

const resolveComparator = (name?: Headers): Comparator<Vaccination> => {
    switch (name) {
        case Headers.DateCreated:
            return sortByDate('createdOn');
        case Headers.Provider:
            return sortByAlphanumeric('provider');
        case Headers.DateAdministered:
            return sortByDate('administeredOn');
        case Headers.VaccineAdministered:
            return sortByAlphanumeric('administered');
        case Headers.AssociatedWith:
            return sortByAssociatedWith;
        case Headers.Event:
            return sortByAlphanumeric('event');
        default:
            return defaultSort;
    }
};

const sortByAssociatedWith = (left: Vaccination, right: Vaccination): number => {
    const value = left.associatedWith;
    const comp = right.associatedWith;

    return value && isAssociatedWith(value) && comp && isAssociatedWith(comp)
        ? sortByAlphanumeric('local')(value, comp)
        : simpleSort(value, comp);
};

const defaultSort = descending(sortByDate('createdOn'));
