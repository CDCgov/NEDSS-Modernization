import { Direction, sortByAlphanumeric, withDirection, ascending, Comparator } from 'sorting';
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
            return sortByAlphanumeric('pageName');
        case Headers.EventType:
            return sortByAlphanumeric('eventType');
        case Headers.RelatedConditions:
            return sortByAlphanumeric('relatedConditions');
        case Headers.Status:
            return sortByAlphanumeric('status');
        case Headers.LastUpdatedBy:
            return sortByAlphanumeric('lastUpdatedBy');
        default:
            return defaultSort;
    }
};

const defaultSort = ascending(sortByAlphanumeric('pageName'));
