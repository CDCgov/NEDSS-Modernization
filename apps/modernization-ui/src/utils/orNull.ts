function orNull<T>(value: T | null | undefined): T | null {
    return (value && value) || null;
}

/*
 * checks if an object has any defined properties
 */
function objectOrUndefined(object: any) {
    if (object === undefined) {
        return undefined;
    }
    return Object.values(object).find((e) => e !== undefined) ? object : undefined;
}

export { orNull, objectOrUndefined };
