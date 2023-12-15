import { LabReport, Headers } from './LabReport';
import { Comparator, Direction, sortBy, sortByAlphanumeric, sortByDate, withDirection, descending } from 'sorting';

export type SortCriteria = {
    name?: Headers;
    type?: Direction;
};

export const sort = (reports: LabReport[], { name, type }: SortCriteria): LabReport[] =>
    reports.slice().sort(withDirection(resolveComparator(name), type));

const resolveComparator = (name?: Headers): Comparator<LabReport> => {
    switch (name) {
        case Headers.DateReceived:
            return sortByDate('addTime');
        case Headers.FacilityProvider:
            return sortByAlphanumeric('addTime');
        case Headers.DateCollected:
            return sortBy('addTime');
        case Headers.TestResults:
            return sortBy('addTime');
        case Headers.AssociatedWith:
            return sortBy('addTime');
        case Headers.ProgramArea:
            return sortBy('addTime');
        case Headers.Jurisdiction:
            return sortByAlphanumeric('addTime');
        case Headers.EventID:
            return sortByAlphanumeric('addTime');
        default:
            return defaultSort;
    }
};

const defaultSort: Comparator<LabReport> = descending(sortByDate('addTime'));
