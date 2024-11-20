import { Validator } from 'validation';
import { asDate, asDateEntry, DateBetweenCriteria } from './entry';
import { validateDate } from './validateDate';

/**
 * Validates that the given "from" and "to" dates are valid and that the "to" date is not earlier than the "from" date.
 *
 * @param {string} name The name of the range date field being validated
 * @return {Validator}
 */
const validateDateRange =
    (name: string): Validator<DateBetweenCriteria> =>
    (dateRange: DateBetweenCriteria): boolean | string => {
        const fromValidation = dateRange.between.from && validateDate(name)(dateRange.between.from);
        if (typeof fromValidation === 'string') {
            return fromValidation;
        }

        const toValidation = dateRange.between.to && validateDate(name)(dateRange.between.to);
        if (typeof toValidation === 'string') {
            return toValidation;
        }

        const fromDateEntry = asDateEntry(dateRange.between.from);
        const toDateEntry = asDateEntry(dateRange.between.to);

        if (fromDateEntry && toDateEntry) {
            const fromDate = asDate(fromDateEntry);
            const toDate = asDate(toDateEntry);

            if (fromDate && toDate && fromDate > toDate) {
                return `The from date should not be later than the to date.`;
            }
        }

        return true;
    };

export { validateDateRange };
