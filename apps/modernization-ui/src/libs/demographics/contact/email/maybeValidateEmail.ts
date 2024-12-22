import { validateIfPresent } from 'validation';
import { validateEmail } from './validateEmail';

/**
 * Validates that the given string represents a valid email.  Any failed
 *  validations will include the name of the field being validated.
 *
 * @param {string} name The name of the field being validated
 * @return {Validator}
 */
const maybeValidateEmail = (name: string) => validateIfPresent(validateEmail(name));

export { maybeValidateEmail };
