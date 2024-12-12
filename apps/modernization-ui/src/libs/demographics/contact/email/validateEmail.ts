import { Validator } from 'validation';

const FORMAT = /^[\w\-+.]+@([\w-]+\.)+[\w-]{2,}$/gm;

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
        if (!value.match(FORMAT)) {
            return `Please enter ${name} as an email address (example: youremail@website.com).`;
        }

        return true;
    };

export { validateEmail };
