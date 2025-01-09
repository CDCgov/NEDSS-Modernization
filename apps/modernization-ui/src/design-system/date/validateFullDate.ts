import { DateEntry } from './entry';

const validateFullDate = (name: string) => (value: DateEntry) => {
    if (!value) {
        return `The ${name} must be in the format MM/DD/YYYY.`;
    } else if (!value?.year) {
        return `The ${name} is missing the year.`;
    } else if (!value?.month) {
        return `The ${name} is missing the month.`;
    } else if (!value?.day) {
        return `The ${name} is missing the day.`;
    }
    return true;
};

export { validateFullDate };
