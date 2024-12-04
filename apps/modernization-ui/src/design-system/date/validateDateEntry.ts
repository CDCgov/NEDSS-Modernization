import { getDaysInMonth } from 'date-fns';
import { DateEntry } from './entry';
import { occursInThePast } from './occursInThePast';
import { validateAll } from 'validation';
import { now } from './clock';

const validateYear = (name: string) => (value: DateEntry) => {
    if (value.year) {
        if (value.year < 1875) {
            return `The ${name} should occur after 12/31/1874.`;
        } else if (value.year > now().getFullYear()) {
            return `The ${name} should occur before or within the current year.`;
        }
    }
    return true;
};

const validateMonth = (name: string) => (value: DateEntry) => {
    if (typeof value.month === 'number' && (value.month > 12 || value.month < 1)) {
        return `The ${name} should have a month between 1 (January) and 12 (December).`;
    }
    return true;
};

const validateDay = (name: string) => (value: DateEntry) => {
    if (typeof value.day === 'number') {
        if (value.month && value.year) {
            const limit = getDaysInMonth(new Date(value.year, value.month - 1));

            if (value.day > limit) {
                return `The ${name} should have at most ${limit} days`;
            }
        } else if (value.day > 31) {
            return `The ${name} should have at most 31 days`;
        } else if (value.day < 1) {
            return `The ${name} should be at least the first day of the month`;
        }
    }
    return true;
};

/**
 * Validates that the given string represents a valid date entry in the format MM/DD/YYYY with
 * appropriate values for the month, day, and year.  Any failed validations will include the
 * name of the field being validated.
 *
 * @param {string} name The name of the field being validated
 * @return {Validator}
 */
const validateDateEntry =
    (name: string) =>
    (value: DateEntry): boolean | string =>
        validateAll(validateYear(name), validateMonth(name), validateDay(name), occursInThePast(name))(value);

export { validateDateEntry };
