import { asDateEntry, asISODate } from './entry';

/**
 * Converts a string representation of a date in the format of MM/DD/YYYY into a string
 * representation of date in the format of YYYY-MM-DD.  If the given value is not in the
 * expected format the result will be undefined.
 *
 * @param {string} value A string representation of a date in the MM/DD/YYYY format
 * @return {string} A string representation of a date in the YYYY-MM-DD format
 */
const asStrictISODate = (value?: string): string | undefined => {
    const entry = asDateEntry(value);
    return asISODate(entry);
};

export { asStrictISODate };
