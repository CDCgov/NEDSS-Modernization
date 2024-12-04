import { internalizeDate } from 'date';
import { now } from './clock';
import { DateEntry } from './entry';

type FullDate = Required<DateEntry>;

const isFullDateInThePast = (name: string, date: Date) => {
    const limit = now();

    return date.getTime() > limit.getTime() ? `The ${name} cannot be after ${internalizeDate(limit)}.` : true;
};

const isFullDate = (value: DateEntry): value is Required<DateEntry> =>
    value.month !== undefined && value.day !== undefined && value.year !== undefined;

const resolveDate = (value: Required<DateEntry>) => new Date(value.year, value.month - 1, value.day);

type MonthYear = DateEntry & Omit<FullDate, 'day'>;

const isMonthYear = (value: DateEntry): value is MonthYear => value.month !== undefined && value.year !== undefined;

const isMonthDateInThePast = (name: string, value: MonthYear) => {
    const date = new Date(value.year, value.month - 1, now().getDate());
    return isFullDateInThePast(name, date);
};

/**
 * Validates that the given string represents a date that occurs on or after the current day.  Any failed
 *  validations will include the name of the field being validated.
 *
 * @param {string} name The name of the field being validated
 * @return {Validator}
 */
const occursInThePast =
    (name: string) =>
    (value: DateEntry): boolean | string => {
        if (isFullDate(value)) {
            return isFullDateInThePast(name, resolveDate(value));
        } else if (isMonthYear(value)) {
            return isMonthDateInThePast(name, value);
        } else {
            return true;
        }
    };

export { occursInThePast };
