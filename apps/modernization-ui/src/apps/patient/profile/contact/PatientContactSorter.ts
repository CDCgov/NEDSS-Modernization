import { Headers, Tracing, isAssociatedWith, isContact, isCondition } from './PatientContacts';
import { Direction, sortByAlphanumeric, sortByDate, withDirection, simpleSort, descending } from 'sorting';

type Comparator<T> = (left: T, right: T) => number;

export type SortCriteria = {
    name?: Headers;
    type?: Direction;
};

export const sort = (contacts: Tracing[], { name, type }: SortCriteria): Tracing[] =>
    contacts.slice().sort(withDirection(resolveComparator(name), type));

const resolveComparator = (name?: Headers): Comparator<Tracing> => {
    switch (name) {
        case Headers.DateCreated:
            return sortByDate('createdOn');
        case Headers.NamedBy:
        case Headers.ContactsNamed:
            return sortByContact;
        case Headers.DateNamed:
            return sortByDate('namedOn');
        case Headers.Description:
            return sortByCondition;
        case Headers.AssociatedWith:
            return sortByAssociatedWith;
        case Headers.Event:
            return sortByAlphanumeric('event');
        default:
            return defaultSort;
    }
};

const sortByCondition = (left: Tracing, right: Tracing): number => {
    const value = left.condition;
    const comp = right.condition;

    return value && isCondition(value) && comp && isCondition(comp)
        ? sortByAlphanumeric('description')(value, comp)
        : simpleSort(value, comp);
};

const sortByContact = (left: Tracing, right: Tracing): number => {
    const value = left.contact;
    const comp = right.contact;

    return value && isContact(value) && comp && isContact(comp)
        ? sortByAlphanumeric('name')(value, comp)
        : simpleSort(value, comp);
};

const sortByAssociatedWith = (left: Tracing, right: Tracing): number => {
    const value = left.associatedWith;
    const comp = right.associatedWith;

    return value && isAssociatedWith(value) && comp && isAssociatedWith(comp)
        ? sortByAlphanumeric('local')(value, comp)
        : simpleSort(value, comp);
};

const defaultSort = descending(sortByDate('createdOn'));
