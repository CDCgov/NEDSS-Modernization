import { ValidationResult, Validator } from './validation';

/**
 * Creates a validator that invokes each given validator in order when provided a value.  The
 * result is the first failed validation or true if the all pass.
 *
 * @param {Validator[]} validators The validators used to validate the value.
 * @return {Validator} A validator.
 */
const validateAll =
    <I>(...validators: Validator<I>[]): Validator<I> =>
    (value: I): ValidationResult =>
        validators.reduce<ValidationResult>((previous, current) => {
            if (typeof previous === 'boolean' && previous) {
                const result = current(value);
                return result;
            }

            return previous;
        }, true);

export { validateAll };
