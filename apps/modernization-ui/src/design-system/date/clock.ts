/**
 * A function with a return value that is equivalent to new Date().  The main use-case of this
 * function is to strengthen tests that rely on the current date.
 *
 * @return {Date} the current instant as a Date
 */
const now = () => new Date();

export { now };
