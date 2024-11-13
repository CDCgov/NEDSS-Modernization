import { differenceInDays, differenceInMonths, differenceInYears } from 'date-fns';

const calculate = (
    from: Date,
    dateOfBirth: Date
): { quantity: number; unit: 'years' | 'year' | 'months' | 'month' | 'days' | 'day' } => {
    const years = differenceInYears(from, dateOfBirth);
    const months = differenceInMonths(from, dateOfBirth);
    const days = differenceInDays(from, dateOfBirth);
    if (years > 0) {
        return { quantity: years, unit: years === 1 ? 'year' : 'years' };
    } else if (months > 0) {
        return { quantity: months, unit: months === 1 ? 'month' : 'months' };
    } else if (days > 0) {
        return { quantity: days, unit: days === 1 ? 'day' : 'days' };
    }
    return { quantity: 0, unit: 'days' };
};

const calculateAge = (
    dateOfBirth: string | Date | null | undefined,
    from: Date = new Date()
): { quantity: number; unit: 'years' | 'year' | 'months' | 'month' | 'days' | 'day' } | undefined => {
    if (dateOfBirth) {
        if (typeof dateOfBirth === 'string') {
            return calculate(from, new Date(dateOfBirth));
        } else if (dateOfBirth instanceof Date) {
            return calculate(from, dateOfBirth);
        }
    }

    return undefined;
};

export { calculateAge };
