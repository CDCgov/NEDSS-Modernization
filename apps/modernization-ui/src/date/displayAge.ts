import { now } from 'design-system/date/clock';
import { differenceInDays, differenceInMonths, differenceInYears } from 'date-fns';
import { Maybe } from 'utils';

/**
 * Converts a string or Date to a Date object.
 * If the input is a string, it must be in a valid date format.
 * @param date - The date to be converted
 * @return {Date | undefined} - The converted Date object or undefined if the input is invalid
 */
const asDate = (date: Maybe<string | Date>): Date | undefined => {
    if (typeof date === 'string') {
        if (date.length < 10) {
            return undefined;
        }
        date = new Date(date);
    }

    if (!(date instanceof Date) || isNaN(date.getTime())) {
        return undefined;
    }

    return date;
};

/**
 * Converts the given date of birth and the reference date to a human-readable age string.
 * The age is calculated as the difference in years, months, or days.
 * If the date of birth is invalid or greater than the reference date, it returns undefined.
 * @param dateOfBirth The date of birth to be converted
 * @param from The reference date to calculate the age from
 * @return {string | undefined} - The human-readable age string or undefined if the input is invalid
 * @example
 * displayAgeAsOf('2000-01-01', '2023-10-01') // returns '23 years'
 */
const displayAgeAsOf = (dateOfBirth: Maybe<string | Date>, from: Maybe<string | Date>) => {
    const dateOfBirthDate = asDate(dateOfBirth);
    const fromDate = asDate(from);
    if (!dateOfBirthDate || !fromDate || dateOfBirthDate > fromDate) {
        return undefined;
    }

    const years = differenceInYears(fromDate, dateOfBirthDate);
    if (years > 0) {
        return years > 1 ? `${years} years` : `${years} year`;
    }

    const months = differenceInMonths(fromDate, dateOfBirthDate);
    if (months > 0) {
        return months > 1 ? `${months} months` : `${months} month`;
    }

    const days = differenceInDays(fromDate, dateOfBirthDate);
    return days > 1 || days === 0 ? `${days} days` : `${days} day`;
};

/**
 * Converts the given date of birth from now to a human-readable age string.
 * @param dateOfBirth The date of birth to be converted
 * @return {string | undefined} - The human-readable age string as of today or undefined if the dateOfBirth is invalid
 */
const displayAgeAsOfToday = (dateOfBirth: Maybe<string | Date>) => displayAgeAsOf(dateOfBirth, now());

export { asDate, displayAgeAsOf, displayAgeAsOfToday };
