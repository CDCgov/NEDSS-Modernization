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
            return sortByDate('addTime');
        case Headers.DateCollected:
            return sortByDate('addTime');
        case Headers.TestResults:
            return sortByDate('addTime');
        case Headers.AssociatedWith:
            return sortBy('addTime');
        case Headers.ProgramArea:
            return sortByAlphanumeric('programAreaCd');
        case Headers.Jurisdiction:
            return sortByAlphanumeric('jurisdictionCd');
        case Headers.EventID:
            return sortByAlphanumeric('localId');
        default:
            return defaultSort;
    }
};

const defaultSort: Comparator<LabReport> = descending(sortByDate('addTime'));
