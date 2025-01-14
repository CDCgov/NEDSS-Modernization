type PredicateFN<T> = (item: T) => boolean;
type MapFN<I, O> = (input: I) => O;

const mapIf = <R, S>(mapFn: MapFN<R, S>, predicateFn: PredicateFN<R>, items: R[]): S[] => {
    return items.reduce((existing: S[], next: R) => {
        if (predicateFn(next)) {
            const mapped = mapFn(next);
            return [...existing, mapped];
        }
        return existing;
    }, []);
};

export { mapIf };
