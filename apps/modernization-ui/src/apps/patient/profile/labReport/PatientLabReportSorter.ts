import { PatientLabReport, Headers } from './PatientLabReport';
import {
    Comparator,
    Direction,
    sortBy,
    sortByDate,
    sortByNestedPatientLabReportTestArray,
    sortByNestedPatientLabReportAssociatedArray,
    withDirection,
    descending
} from 'sorting';

export type SortCriteria = {
    name?: Headers;
    type?: Direction;
};

export const sort = (reports: PatientLabReport[], { name, type }: SortCriteria): PatientLabReport[] =>
    reports.slice().sort(withDirection(resolveComparator(name), type));

const resolveComparator = (name?: Headers): Comparator<PatientLabReport> => {
    switch (name) {
        case Headers.DateReceived:
            return sortByDate('receivedOn');
        case Headers.DateCollected:
            return sortByDate('collectedOn');
        case Headers.TestResults:
            return sortByNestedPatientLabReportTestArray('results');
        case Headers.AssociatedWith:
            return sortByNestedPatientLabReportAssociatedArray('associatedWith');
        case Headers.ProgramArea:
            return sortBy('programArea');
        case Headers.Jurisdiction:
            return sortBy('jurisdiction');
        case Headers.EventID:
            return sortBy('event');
        default:
            return defaultSort;
    }
};

const defaultSort: Comparator<PatientLabReport> = descending(sortByDate('receivedOn'));
