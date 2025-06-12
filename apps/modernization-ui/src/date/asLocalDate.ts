import { add } from 'date-fns';

/**
 * Parses a string containing a UTC date in the ISO-8601 format and returns a Date object in the local timezone.
 * Note that it uses the current date to determine whether to adjust for daylight savings.
 * @param {string} value The date string in ISO-8601 format (e.g., "2023-01-17T00:00:00Z").
 * @return {Date} A Date object representing the local date and time, adjusted for the local timezone.
 */
const asLocalDate = (value: string): Date => {
    const date = new Date(value);
    // get offset for the specified date, incorporating daylight saving time if applicable
    const offset = { minutes: date.getTimezoneOffset() };
    return add(date, offset);
};

export { asLocalDate };
