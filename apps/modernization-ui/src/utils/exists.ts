/**
 * A predicate that evaluates the existence of a value.
 *
 * The following values are considered existing;
 *  - Any boolean
 *  - Any number
 *  - A non-empty string
 *  - An array
 *  - Any other truthy value
 *
 * The following values are considered non-existent;
 *  - An empty string
 *  - An empty object
 *  - Any other falsy value
 *
 * @param {T} value the value to test
 * @return {boolean}
 */
function exists<T>(value: T | null | undefined): value is NonNullable<T> {
    if (typeof value === 'object' && value && !Array.isArray(value)) {
        return Object.keys(value).length > 0;
    } else if (typeof value === 'string') {
        return value.length > 0;
    }
    return typeof value !== 'undefined' && value !== null;
}

export { exists };
