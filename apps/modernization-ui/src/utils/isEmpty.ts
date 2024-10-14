export function isEmpty<T>(obj: T) {
    for (const key in obj) {
        if (obj[key]) return false;
    }
    return true;
}
