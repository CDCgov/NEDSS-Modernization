import { Validator } from 'validation';

const FORMAT = /^\w+([.-]?\w+)*@\w+([.-/+]?\w+)*(\.\w{2,3})+$/;

/**
 * Validates that the given string represents a valid email.  Any failed
 *  validations will include the name of the field being validated.
 *
 * @param {string} name The name of the field being validated
 * @return {Validator}
 */
const validateEmail =
    (name: string): Validator<string> =>
    (value: string): boolean | string => {
        if (!FORMAT.test(value)) {
            return `Please enter ${name} as an email address (example: youremail@website.com).`;
        }

        return false;
    };

export { validateEmail };
