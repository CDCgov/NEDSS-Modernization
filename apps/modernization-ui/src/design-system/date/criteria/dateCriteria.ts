import { DateEntry, displayDateEntry } from 'design-system/date/entry';

type DateEqualsCriteria = {
    equals: DateEntry;
};

type DateRange = {
    from?: string;
    to?: string;
};

type DateBetweenCriteria = {
    between: DateRange;
};

type DateCriteria = DateEqualsCriteria | DateBetweenCriteria;

const isDateEqualsCriteria = (criteria?: DateCriteria): criteria is DateEqualsCriteria =>
    !!criteria && 'equals' in criteria;
const isDateBetweenCriteria = (criteria?: DateCriteria): criteria is DateBetweenCriteria =>
    !!criteria && 'between' in criteria;

/**
 * Resolves the string representation of date equals criteria if the
 * criteria contains a date with a month, day, and year.
 *
 * @param {DateCriteria | undefined} criteria The DateCriteria to resolve a date from
 * @return {string | undefined} A MM/DD/YYYY formatted string that represents a Date.
 */
const resolveDate = (criteria?: DateCriteria) => {
    if (criteria && isDateEqualsCriteria(criteria)) {
        const date = criteria.equals;
        return date.day && date.month && date.year ? displayDateEntry(date) : undefined;
    }
};

const initialDateEqualsCriteria: DateEqualsCriteria = { equals: {} };
const initialDateBetweenCriteria: DateBetweenCriteria = { between: {} };

export { isDateEqualsCriteria, isDateBetweenCriteria, resolveDate };
export { initialDateEqualsCriteria, initialDateBetweenCriteria };
export type { DateRange, DateCriteria, DateBetweenCriteria, DateEqualsCriteria };
