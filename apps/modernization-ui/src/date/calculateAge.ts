import { differenceInDays, differenceInMonths, differenceInYears } from 'date-fns';

const calculate = (from: Date, dateOfBirth: Date): { quantity: number; unit: 'years' | 'months' | 'days' } => {
    const years = differenceInYears(from, dateOfBirth);
    const months = differenceInMonths(from, dateOfBirth);
    const days = differenceInDays(from, dateOfBirth);
    if (years > 0) {
        return { quantity: years, unit: 'years' };
    } else if (months > 0) {
        return { quantity: months, unit: 'months' };
    } else if (days > 0) {
        return { quantity: days, unit: 'days' };
    }
    return { quantity: 0, unit: 'days' };
};

const calculateAge = (
    dateOfBirth: string | Date | null | undefined,
    from: Date = new Date()
): { quantity: number; unit: 'years' | 'months' | 'days' } | undefined => {
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
