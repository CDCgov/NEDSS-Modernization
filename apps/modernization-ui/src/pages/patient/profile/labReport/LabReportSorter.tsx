import { LabReport, Headers } from './PatientInvestigation';
import { Comparator, Direction, sortBy, sortByAlphanumeric, sortByDate, withDirection, descending } from 'sorting';

export type SortCriteria = {
    name?: Headers;
    type?: Direction;
};

export const sort = (investigations: Investigation[], { name, type }: SortCriteria): Investigation[] =>
    investigations.slice().sort(withDirection(resolveComparator(name), type));

const resolveComparator = (name?: Headers): Comparator<Investigation> => {
    switch (name) {
        case Headers.StartDate:
            return sortByDate('startedOn');
        case Headers.Condition:
            return sortByAlphanumeric('condition');
        case Headers.Status:
            return sortBy('status');
        case Headers.CaseStatus:
            return sortBy('caseStatus');
        case Headers.Notification:
            return sortBy('notification');
        case Headers.Jurisdiction:
            return sortBy('jurisdiction');
        case Headers.Investigator:
            return sortBy('investigator');
        case Headers.Investigation:
            return sortByAlphanumeric('event');
        case Headers.CoInfection:
            return sortByAlphanumeric('coInfection');
        default:
            return defaultSort;
    }
};

const defaultSort: Comparator<Investigation> = descending(sortByDate('startedOn'));
