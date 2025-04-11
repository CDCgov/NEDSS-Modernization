/**
 *
 * @description This function checks if a given value is an empty object.
 * @param {unknown} value The value to check if it is an empty object.
 * @returns {boolean} Returns true if the value is an empty object, false otherwise.
 */

const isEmptyObject = (value: unknown): boolean => {
    // Checks if the value is an object (using typeof), is not null (since the javascript typeof null is 'object'), is not an array, and has no own enumerable properties
    return typeof value === 'object' && value !== null && !Array.isArray(value) && Object.keys(value).length === 0;
};

export { isEmptyObject };
