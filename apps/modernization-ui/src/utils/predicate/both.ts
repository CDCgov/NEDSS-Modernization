import { Predicate } from './predicate';

const both =
    <V>(first: Predicate<V>, second: Predicate<V>): Predicate<V> =>
    (value) =>
        first(value) && second(value);

export { both };
