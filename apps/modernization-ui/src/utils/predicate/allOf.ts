import { Predicate } from './predicate';

/**
 * Returns a single {@link Predicate} that evaluates to true if all of the given predicates
 * evaluates to true.  If no predicates are passed then the resulting predicate always evaluates
 * to false.
 *
 * @param {Predicate<R>[]} predicates The predicates to evaluate.
 * @return {Predicate<R>}
 */
const allOf =
    <R>(...predicates: Predicate<R>[]) =>
    (item: R) => {
        if (predicates.length === 0) {
            return false;
        }

        for (const predicate of predicates) {
            const result = predicate(item);

            if (!result) {
                return false;
            }
        }

        return true;
    };

export { allOf };
