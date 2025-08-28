import { Validator } from './validation';

/**
 * Invokes the given validator if the value is present.  When the value is not present
 * the given validator is not invoked and the validation return as successful.
 *
 * @param {Validator} validator The validator to invoke.
 * @return {Validator}
 */
const validateIfPresent =
    <I>(validator: Validator<I>): Validator<I | null | undefined> =>
    (value?: I | null): boolean | string =>
        value ? validator(value) : true;

export { validateIfPresent };
