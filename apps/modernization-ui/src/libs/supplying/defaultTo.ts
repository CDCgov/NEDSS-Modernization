import { Supplier } from './supplier';

const defaultTo = <T>(fallback?: T | Supplier<T>, value?: T) => {
    if (value) {
        return value;
    }
    return fallback instanceof Function ? fallback() : fallback;
};

export { defaultTo };
