import { getDaysInMonth } from 'date-fns';
import { DateEntry, DateEqualsCriteria } from './entry';
import { occursInThePast } from './occursInThePast';

type ValidationResult = boolean | string;
type Validator<I> = (value: I) => ValidationResult;

const validateAll =
    <I>(...validators: Validator<I>[]) =>
    (value: I): ValidationResult =>
        validators.reduce<ValidationResult>((previous, current) => {
            if (typeof previous === 'boolean' && previous) {
                const result = current(value);
                return result;
            }

            return previous;
        }, true);

const validateYear = (name: string) => (value: DateEntry) => {
    if (value.year && value.year < 1875) {
        return `The ${name} must occur after 12/31/1874.`;
    }
    return true;
};

const validateMonth = (name: string) => (value: DateEntry) => {
    if (typeof value.month === 'number' && (value.month > 12 || value.month < 1)) {
        return `The ${name} must have a month between 1 (January) and 12 (December).`;
    }
    return true;
};

const validateDay = (name: string) => (value: DateEntry) => {
    if (typeof value.day === 'number') {
        if (value.month && value.year) {
            const limit = getDaysInMonth(new Date(value.year, value.month - 1));

            if (value.day > limit) {
                return `The ${name} can have at most ${limit} days`;
            }
        } else if (value.day > 31) {
            return `The ${name} can have at most 31 days`;
        } else if (value.day < 1) {
            return `The ${name} must be at least the first day of the month`;
        }
    }
    return true;
};

const validateDateEntry =
    (name: string) =>
    (value: DateEqualsCriteria): boolean | string =>
        value?.equals &&
        validateAll(validateYear(name), validateMonth(name), validateDay(name), occursInThePast(name))(value?.equals);

export { validateDateEntry };
