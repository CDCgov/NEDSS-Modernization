import { PatientIdentification } from 'generated/graphql/schema';
import {
    Comparator,
    Direction,
    descending,
    sortByAlpha,
    sortByDate,
    sortByNestedProperty,
    withDirection
} from 'sorting';
import { Headers } from './identification';

export type SortCriteria = {
    name?: Headers;
    type?: Direction;
};

export const sort = (identifications: PatientIdentification[], { name, type }: SortCriteria): PatientIdentification[] =>
    identifications.slice().sort(withDirection(resolveComparator(name), type));

const resolveComparator = (name: Headers | undefined): Comparator<PatientIdentification> => {
    switch (name) {
        case Headers.AsOf:
            return sortByDate('asOf');
        case Headers.Type:
            return sortByNestedProperty('type');
        case Headers.Authority:
            return sortByNestedProperty('authority');
        case Headers.Value:
            return sortByAlpha('value');
        default:
            return defaultSort;
    }
};

const defaultSort: Comparator<PatientIdentification> = descending(sortByDate('asOf'));
