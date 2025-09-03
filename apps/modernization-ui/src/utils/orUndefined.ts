import { exists } from './exists';

/* eslint-disable no-redeclare */
function orUndefined<T>(value: T): NonNullable<T>;
function orUndefined(value: null | undefined): undefined;
function orUndefined<T>(value: T | null | undefined): NonNullable<T> | undefined {
    if (typeof value === 'number') {
        return value;
    } else if (typeof value === 'object' && value) {
        return exists(value) ? value : undefined;
    } else if (value) {
        return value;
    }
}

export { orUndefined };
