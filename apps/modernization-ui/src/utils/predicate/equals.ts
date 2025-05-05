import { Predicate } from './predicate';

const equals =
    <V>(criteria: V): Predicate<V> =>
    (value: V) =>
        criteria === value;

export { equals };
