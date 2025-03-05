import { differenceInDays, differenceInMonths, differenceInYears } from 'date-fns';
import { Maybe } from 'utils';

const displayAgeAsOf = (dateOfBirth: Maybe<string | Date>, from: Date) => {
    if (typeof dateOfBirth === 'string') {
        console.log('yype string');
        dateOfBirth = new Date(dateOfBirth);
    }

    console.log(dateOfBirth);

    if (!(dateOfBirth instanceof Date) || isNaN(dateOfBirth.getTime()) || dateOfBirth > from) {
        console.log('here');
        return undefined;
    }

    const years = differenceInYears(from, dateOfBirth);
    if (years > 0) {
        console.log('here2');
        return years > 1 ? `${years} years` : `${years} year`;
    }

    const months = differenceInMonths(from, dateOfBirth);
    if (months > 0) {
        console.log('here3');
        return months > 1 ? `${months} months` : `${months} month`;
    }

    const days = differenceInDays(from, dateOfBirth);
    console.log('here4');
    return days > 1 || days === 0 ? `${days} days` : `${days} day`;
};

const displayAgeAsOfToday = (dateOfBirth: Maybe<string | Date>) => displayAgeAsOf(dateOfBirth, new Date());

export { displayAgeAsOf, displayAgeAsOfToday };
