import { anyOf, Predicate } from 'utils/predicate';
import { permits } from './permits';

/**
 * Returns a {@link Predicate} that evaluates to true at least of the required permissions are permitted.
 *
 * @param {string[]} required An array of required permissions
 * @return {Predicate<string[]>}
 */
const permitsAny = (...required: string[]): Predicate<string[]> => anyOf(...required.map(permits));

export { permitsAny };
