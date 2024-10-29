import { exists } from './exists';

/* eslint-disable no-redeclare */
function isEmpty<T>(obj: T): boolean;
function isEmpty(obj: null | undefined): true;
function isEmpty<T>(obj: T | null | undefined): boolean {
    for (const key in obj) {
        if (Object.hasOwn(obj, key)) {
            const value = obj[key];
            if (exists(value)) return false;
        }
    }
    return true;
}

export { isEmpty };
