type Maybe<T> = T | null | undefined;

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
