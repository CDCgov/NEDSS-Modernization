import { isFuture } from 'date-fns';
import { DateEntry } from './entry';
import { today } from 'date';

const isInThePast = (name: string, date?: Date) =>
    date && isFuture(date) ? `The ${name} cannot be after ${today()}.` : true;

const resolveMonth = (value: DateEntry) => (value.month ? value.month - 1 : undefined);

const resolveDate = (value: DateEntry) => {
    const { year, day } = value;
    const month = resolveMonth(value);
    const now = new Date();
    return new Date(year ?? now.getFullYear(), month ?? now.getMonth(), day ?? now.getDate());
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
        const resolved = resolveDate(value);
        return isInThePast(name, resolved);
    };

export { occursInThePast };
