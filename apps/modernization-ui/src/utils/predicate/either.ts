import { Predicate } from './predicate';

const either =
    <V>(first: Predicate<V>, second: Predicate<V>): Predicate<V> =>
    (value) =>
        first(value) || second(value);

export { either };
