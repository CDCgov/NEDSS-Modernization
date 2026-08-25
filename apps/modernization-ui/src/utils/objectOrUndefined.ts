/*
 * checks if an object has any defined properties
 */
function objectOrUndefined(object?: object) {
    if (object === undefined) {
        return undefined;
    }
    return Object.values(object).some((e) => e !== undefined) ? object : undefined;
}

export { objectOrUndefined };
