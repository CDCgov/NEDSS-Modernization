import { allOf, Predicate } from 'utils/predicate';
import { permits } from './permits';

/**
 * Returns a {@link Predicate} that evaluates to true when all of the required permissions are permitted.
 *
 * @param {string[]} required An array of required permissions
 * @return {Predicate<string[]>}
 */
const permitsAll = (...required: string[]): Predicate<string[]> => allOf(...required.map(permits));

export { permitsAll };
