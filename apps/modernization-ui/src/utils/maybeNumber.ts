/**
 ** Returns a number if the value is truthy, otherwise returns null.
 * @param {number | string | null | undefined} value The value to convert to a number.
 * @returnsssss {number | null} The converted number or null if the value is falsy.
 */
const maybeNumber = (value: number | string | null | undefined): number | null => (value ? +value : null);

export { maybeNumber };
