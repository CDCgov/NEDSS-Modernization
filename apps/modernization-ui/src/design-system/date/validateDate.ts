import { asDateEntry } from './entry';
import { validateDateEntry } from './validateDateEntry';

const validateDate =
    (name: string) =>
    (value: string): boolean | string => {
        const entered = asDateEntry(value);

        if (!entered) {
            return `The ${name} should be in the format MM/DD/YYYY.`;
        }

        return validateDateEntry(name)(entered);
    };

export { validateDate };
