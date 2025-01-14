import { renderHook, act } from '@testing-library/react-hooks';
import { useSearchParams } from 'react-router-dom';
import { useSearchCriteria } from './useSearchCriteria';
import { decrypt, encrypt } from 'cryptography';

jest.mock('react-router-dom', () => ({
    useSearchParams: jest.fn()
}));

jest.mock('cryptography', () => ({
    decrypt: jest.fn(),
    encrypt: jest.fn()
}));

describe('useSearchCriteria', () => {
    const setSearchParams = jest.fn();

    beforeEach(() => {
        (useSearchParams as jest.Mock).mockReturnValue([new URLSearchParams(), setSearchParams]);
        (decrypt as jest.Mock).mockResolvedValue({});
        (encrypt as jest.Mock).mockResolvedValue({ value: 'encryptedValue' });
    });

    afterEach(() => {
        jest.clearAllMocks();
    });

    it('should initialize with no criteria', () => {
        const { result } = renderHook(() => useSearchCriteria({}));

        expect(result.current.criteria).toBeUndefined();
    });

    it('should evaluate found criteria', async () => {
        (useSearchParams as jest.Mock).mockReturnValue([new URLSearchParams('q=foundCriteria'), setSearchParams]);
        (decrypt as jest.Mock).mockResolvedValue({ key: 'value' });

        const { result, waitForNextUpdate } = renderHook(() => useSearchCriteria({}));

        await waitForNextUpdate();

        expect(result.current.criteria).toEqual({ key: 'value' });
    });

    it('should clear criteria', async () => {
        const { result } = renderHook(() => useSearchCriteria({}));

        act(() => {
            result.current.clear();
        });

        expect(setSearchParams).toHaveBeenCalledWith(expect.any(Function));
    });

    it('should change criteria', async () => {
        const { result, waitForNextUpdate } = renderHook(() => useSearchCriteria({}));

        act(() => {
            result.current.change({ key: 'newValue' });
        });

        await waitForNextUpdate();

        expect(encrypt).toHaveBeenCalledWith({ key: 'newValue' });
        expect(setSearchParams).toHaveBeenCalledWith(expect.any(Function), { replace: true });
    });

    it('should handle default values as a function', async () => {
        (useSearchParams as jest.Mock).mockReturnValue([new URLSearchParams('q=foundCriteria'), setSearchParams]);
        (decrypt as jest.Mock).mockResolvedValue({ key: 'value' });

        const defaultValuesFunc = jest.fn().mockReturnValue({ defaultKey: 'defaultValue' });

        const { result, waitForNextUpdate } = renderHook(() => useSearchCriteria({ defaultValues: defaultValuesFunc }));

        await waitForNextUpdate();

        expect(defaultValuesFunc).toHaveBeenCalledWith({ key: 'value' });
        expect(result.current.criteria).toEqual({ defaultKey: 'defaultValue', key: 'value' });
    });
});
