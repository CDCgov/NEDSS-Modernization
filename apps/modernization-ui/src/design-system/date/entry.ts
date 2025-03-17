import { mapOr, maybeMap } from 'utils/mapping';

type DateEntry = {
    day?: number;
    month?: number;
    year?: number;
};

const maybeNumber = maybeMap(Number);

const DATE_ENTRY_FORMAT = /^(?<month>\d{2})\/(?<day>\d{2})\/(?<year>\d{4})$/;

/**
 * Creates a DateEntry from a string that matches the format MM/DD/YYYY.
 *
 * @param {string} value  The textual date value in the MM/DD/YYYY format.
 * @return {DateEntry | undefined}
 */
const asDateEntry = (value?: string): DateEntry | undefined => {
    if (value) {
        const match = value.match(DATE_ENTRY_FORMAT);

        if (match) {
            const year = maybeNumber(match.groups?.year);
            const month = maybeNumber(match.groups?.month);
            const day = maybeNumber(match.groups?.day);

            return { year, month, day };
        }
    }
};

const withLeadingZero = (value: number) => String(value).padStart(2, '0');
const MISSING_VALUE_PLACEHOLDER = '--';
const maybeWithLeadingZero = mapOr(withLeadingZero, MISSING_VALUE_PLACEHOLDER);

const displayDateEntry = (value: DateEntry) => {
    const month = maybeWithLeadingZero(value.month);
    const day = maybeWithLeadingZero(value.day);
    const year = value.year ?? MISSING_VALUE_PLACEHOLDER;

    return `${month}/${day}/${year}`;
};

const asISODate = (value?: DateEntry): string | undefined => {
    if (value && value.year && value.month && value.day) {
        return `${value.year}-${withLeadingZero(value.month)}-${withLeadingZero(value.day)}`;
    }
};

const asDate = (value?: DateEntry) => {
    if (value?.year && value.month && value.day) {
        return new Date(value.year, value.month - 1, value.day);
    }
};

export { DATE_ENTRY_FORMAT, asDateEntry, asDate, displayDateEntry, asISODate };
export type { DateEntry };

//

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
