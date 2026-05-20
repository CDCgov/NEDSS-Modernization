/**
 * Same as `console.error`, but specifically opting into sending the log to the user so
 * that linting can differentiate between a true production log statement and leftover
 * debugging cruft.
 */
export const logErrorToUserConsole: typeof console.error = (...args) => {
    // This function is used to explicitly opt in to sending logs to the end user
    // eslint-disable-next-line no-console
    console.error(...args);
};
