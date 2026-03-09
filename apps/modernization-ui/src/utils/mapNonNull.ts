type Maybe<T> = T | null | undefined;

/**
 *  Maps an array of items, applying a function that returns a Maybe type.
 */
export const mapNonNull = <R, S>(fn: (r: R) => Maybe<S>, items: (R | null)[] | null | undefined): S[] => {
    if (items) {
        return items.reduce((existing: S[], next: R | null) => {
            if (next) {
                const mapped = fn(next);
                if (mapped) {
                    return [...existing, mapped];
                }
            }
            return existing;
        }, []);
    } else {
        return [];
    }
};
