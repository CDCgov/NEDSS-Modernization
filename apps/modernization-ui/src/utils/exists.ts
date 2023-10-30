/**
 * A predicate that evalutes to true if the provided value exists.
 *
 * @param {T} value the value to test
 * @return {boolean}
 */
const exists = <T>(value?: T): boolean => (typeof value === 'boolean' || value ? true : false);

export { exists };
