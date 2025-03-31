import { Predicate } from './predicate';

const not =
    <V>(predicate: Predicate<V>): Predicate<V> =>
    (value) =>
        !predicate(value);

export { not };
