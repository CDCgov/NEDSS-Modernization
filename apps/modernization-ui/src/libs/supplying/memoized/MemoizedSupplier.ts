import { Supplier } from 'libs/supplying';

/**
 * A holder for a lazily evaluated memoized value, that can then be reset to re-evaluate the value.
 *
 */
class MemoizedSupplier<V> {
    #supplier: Supplier<V>;
    #value: V | undefined;

    /**
     * Creates a new DataProvider that uses the given supplier to lazily evaluate the value if it is not present.  If an initial value is provided the value is only evaluated after being reset.
     *
     * @param {Supplier<V>} supplier The supplier used to evaluate the value.
     * @param {V} initial An optional initial value,
     */
    constructor(supplier: Supplier<V>, initial?: V) {
        this.#supplier = supplier;
        this.#value = initial;
    }

    get(): V {
        if (this.#value !== undefined) {
            return this.#value;
        }
        return (this.#value = this.#supplier());
    }

    reset() {
        this.#value = undefined;
    }
}

export { MemoizedSupplier };
