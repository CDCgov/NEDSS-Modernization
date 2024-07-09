import { Direction, descending, sortBy, sortByAlphanumeric, sortByDate, withDirection } from 'sorting';
import { Columns, DocumentReview } from './ReviewDocuments';

type Comparator<T> = (left: T, right: T) => number;

export type SortCriteria = {
    name?: Columns;
    type?: Direction;
};

export const sort = (documents: DocumentReview[], { name, type }: SortCriteria): DocumentReview[] =>
    documents.slice().sort(withDirection(resolveComparator(name), type));

const resolveComparator = (name?: Columns): Comparator<DocumentReview> => {
    switch (name) {
        case Columns.DocumentType:
            return sortBy('type');
        case Columns.DateReceived:
            return sortByDate('dateReceived');
        case Columns.EventDate:
            return sortByDate('eventDate');
        case Columns.EventID:
            return sortByAlphanumeric('localId');
        default:
            return defaultSort;
    }
};

const defaultSort = descending(sortByDate('dateReceived'));
