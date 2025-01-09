import { Mapping } from './mapping';

type Fallback<V> = (() => V) | V;

const resolve = <I>(fallback: Fallback<I>): I => {
    if (fallback instanceof Function) {
        return fallback();
    } else {
        return fallback;
    }
};

const mapOr =
    <R, S, O>(mapping: Mapping<R, S>, fallback: Fallback<O>) =>
    (value?: R | null): S | O =>
        value ? mapping(value) : resolve(fallback);

export { mapOr };
