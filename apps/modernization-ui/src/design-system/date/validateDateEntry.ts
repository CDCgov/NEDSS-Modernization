import { getDaysInMonth } from 'date-fns';
import { DateEntry } from './entry';
import { occursInThePast } from './occursInThePast';
import { validateAll } from 'validation';

const validateYear = (name: string) => (value: DateEntry) => {
    if (value.year && value.year < 1875) {
        return `The ${name} should occur after 12/31/1874.`;
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

const validateDateEntry =
    (name: string) =>
    (value: DateEntry): boolean | string =>
        validateAll(validateYear(name), validateMonth(name), validateDay(name), occursInThePast(name))(value);

export { validateDateEntry };
