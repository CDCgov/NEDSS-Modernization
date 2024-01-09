import { Document, Headers, isAssociatedWith } from './PatientDocuments';
import { Direction, sortBy, sortByAlphanumeric, sortByDate, withDirection, simpleSort, descending } from 'sorting';

type Comparator<T> = (left: T, right: T) => number;

export type SortCriteria = {
    name?: Headers;
    type?: Direction;
};

export const sort = (documents: Document[], { name, type }: SortCriteria): Document[] =>
    documents.slice().sort(withDirection(resolveComparator(name), type));

const resolveComparator = (name?: Headers): Comparator<Document> => {
    switch (name) {
        case Headers.DateReceived:
            return sortByDate('receivedOn');
        case Headers.Type:
            return sortBy('type');
        case Headers.SendingFacility:
            return sortBy('sendingFacility');
        case Headers.DateReported:
            return sortByDate('reportedOn');
        case Headers.Condition:
            return sortByAlphanumeric('condition');
        case Headers.AssociatedWith:
            return sortByAssociatedWith;
        case Headers.EventID:
            return sortByAlphanumeric('event');
        default:
            return defaultSort;
    }
};

const sortByAssociatedWith = (left: Document, right: Document): number => {
    const value = left.associatedWith;
    const comp = right.associatedWith;

    return value && isAssociatedWith(value) && comp && isAssociatedWith(comp)
        ? sortByAlphanumeric('local')(value, comp)
        : simpleSort(value, comp);
};

const defaultSort = descending(sortByDate('receivedOn'));
