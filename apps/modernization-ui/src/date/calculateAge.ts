import { differenceInYears } from 'date-fns';

const calculate = (from: Date, dateOfBirth: Date): number => differenceInYears(from, dateOfBirth);

const calculateAge = (dateOfBirth: string | Date | null | undefined, from: Date = new Date()): number | undefined => {
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
