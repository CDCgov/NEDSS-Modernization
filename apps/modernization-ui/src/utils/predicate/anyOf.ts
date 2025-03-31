import { Predicate } from './predicate';

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
