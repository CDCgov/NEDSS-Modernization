import { asDateEntry } from './entry';
import { validateDateEntry } from './validateDateEntry';

/**
 * Validates that the given string represents a valid date in the format MM/DD/YYYY.  Any failed
 *  validations will include the name of the field being validated.
 *
 * @param {string} name The name of the field being validated
 * @return {Validator}
 */
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
