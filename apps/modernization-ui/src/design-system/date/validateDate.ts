import { validateAll } from 'validation';
import { asDateEntry } from './entry';
import { validateDateEntry } from './validateDateEntry';
import { validateFullDate } from './validateFullDate';

const validateDate =
    (name: string) =>
    (value: string): boolean | string => {
        const entered = asDateEntry(value);

        if (!entered) {
            return `The ${name} should be in the format MM/DD/YYYY.`;
        }

        return validateAll(validateDateEntry(name), validateFullDate(name))(entered);
    };

export { validateDate };
