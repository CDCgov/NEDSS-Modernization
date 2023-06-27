import { Direction, sortByAlphanumeric, sortByDate, withDirection, descending, Comparator } from 'sorting';
import { Headers, Pages } from './Pages';

export type SortCriteria = {
    name?: Headers;
    type?: Direction;
};

export const sort = (contacts: Pages[], { name, type }: SortCriteria): Pages[] =>
    contacts.slice().sort(withDirection(resolveComparator(name), type));

const resolveComparator = (name?: Headers): Comparator<Pages> => {
    switch (name) {
        case Headers.PageName:
            return sortByDate('pageName');
        case Headers.EventType:
            return sortByAlphanumeric('eventType');
        case Headers.RelatedConditions:
            return sortByDate('relatedConditions');
        case Headers.Status:
            return sortByAlphanumeric('status');
        case Headers.LastUpdatedBy:
            return sortByAlphanumeric('lastUpdatedBy');
        default:
            return defaultSort;
    }
};

const defaultSort = descending(sortByAlphanumeric('pageName'));
