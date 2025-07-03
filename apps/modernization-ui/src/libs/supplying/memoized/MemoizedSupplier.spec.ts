import { MemoizedSupplier } from './MemoizedSupplier';

describe('MemoizedSupplier', () => {
    it('should lazily evaluate the value', () => {
        const supplier = jest.fn().mockReturnValue('value');

        const actual = new MemoizedSupplier(supplier);

        expect(supplier).not.toHaveBeenCalled();

        expect(actual.get()).toEqual('value');

        expect(supplier).toHaveBeenCalled();
    });

    it('should not evaluate the value when given an initial value', () => {
        const supplier = jest.fn().mockReturnValue('value');

        const actual = new MemoizedSupplier(supplier, 'initial');

        expect(actual.get()).toEqual('initial');

        expect(supplier).not.toHaveBeenCalled();
    });

    it('should memoize the evaluated value', () => {
        const supplier = jest.fn().mockReturnValue('value');

        const actual = new MemoizedSupplier(supplier);

        actual.get();
        actual.get();
        actual.get();

        expect(supplier).toHaveBeenCalledTimes(1);
    });

    it('should re-evaluated the memoized value when reset', () => {
        const supplier = jest.fn().mockReturnValue('value');

        const actual = new MemoizedSupplier(supplier);

        actual.get();
        actual.reset();
        actual.get();

        expect(supplier).toHaveBeenCalledTimes(2);
    });

    it('should re-evaluated the memoized value when reset even with an initial value', () => {
        const supplier = jest.fn().mockReturnValue('value');

        const actual = new MemoizedSupplier(supplier, 'initial');

        actual.reset();
        actual.get();

        expect(supplier).toHaveBeenCalledTimes(1);
    });
});
