import { Predicate } from './predicate';

/**
 * Returns a single {@link Predicate} that evaluates to true when any of the given predicates
 * evaluates to true.  If no predicates are passed then the resulting predicate always evaluates
 * to false.
 *
 * @param {Predicate<R>[]} predicates The predicates to evaluate.
 * @return {Predicate<R>}
 */
const anyOf =
    <R>(...predicates: Predicate<R>[]) =>
    (item: R) => {
        for (const predicate of predicates) {
            const result = predicate(item);

            if (result) {
                return true;
            }
        }

        return false;
    };

export { anyOf };
