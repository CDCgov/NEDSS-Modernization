import { exists } from 'utils/exists';
import { Mapping } from './mapping';

/**
 * Transforms a value using the provided mapping function if the value exists, otherwise returns undefined.
 * @template R The input value type.
 * @template S The output value type.
 * @param {Mapping<R, S>} mapping The mapping function to apply to the value if it exists.
 * @return {function} A function that maps the value or returns undefined if the input value is undefined.
 */
const maybeMap =
    <R, S>(mapping: Mapping<R, S>) =>
    (value?: R | null): S | undefined =>
        exists(value) ? mapping(value) : undefined;

export { maybeMap };
