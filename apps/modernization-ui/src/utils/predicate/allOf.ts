import { Predicate } from './predicate';

const allOf =
    <R>(...predicates: Predicate<R>[]) =>
    (item: R) => {
        for (const predicate of predicates) {
            const result = predicate(item);

            if (!result) {
                return false;
            }
        }

        return true;
    };

export { allOf };
