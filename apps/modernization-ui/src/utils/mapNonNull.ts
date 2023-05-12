export const mapNonNull = <R, S>(fn: (r: R) => S | null, items: (R | null)[] | null | undefined): S[] => {
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
