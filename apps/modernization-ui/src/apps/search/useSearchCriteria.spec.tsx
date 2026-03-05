import { act } from 'react';
import { renderHook, waitFor } from '@testing-library/react';
import { useSearchParams } from 'react-router';
import { useSearchCriteria } from './useSearchCriteria';
import { decrypt, encrypt } from 'cryptography';

vi.mock('react-router', () => ({
    useSearchParams: vi.fn()
}));

vi.mock('cryptography', () => ({
    decrypt: vi.fn(),
    encrypt: vi.fn()
}));

describe('useSearchCriteria', () => {
    const setSearchParams = vi.fn();

    beforeEach(() => {
        (useSearchParams as vi.Mock).mockReturnValue([new URLSearchParams(), setSearchParams]);
        (decrypt as vi.Mock).mockResolvedValue({});
        (encrypt as vi.Mock).mockResolvedValue({ value: 'encryptedValue' });
    });

    afterEach(() => {
        vi.clearAllMocks();
    });

    it('should initialize with no criteria', () => {
        const { result } = renderHook(() => useSearchCriteria({}));

        expect(result.current.criteria).toBeUndefined();
    });

    it('should evaluate found criteria', async () => {
        const { result, rerender } = renderHook(() => useSearchCriteria({}));

        (useSearchParams as vi.Mock).mockReturnValue([new URLSearchParams('q=foundCriteria'), setSearchParams]);
        (decrypt as vi.Mock).mockResolvedValue({ key: 'value' });

        rerender();

        await waitFor(() => {
            expect(result.current.criteria).toEqual({ key: 'value' });
        });
    });

    it('should clear criteria', async () => {
        const { result } = renderHook(() => useSearchCriteria({}));

        act(() => {
            result.current.clear();
        });

        expect(setSearchParams).toHaveBeenCalledWith(expect.any(Function));
    });

    it('should change criteria', async () => {
        const { result } = renderHook(() => useSearchCriteria({}));

        act(() => {
            result.current.change({ key: 'newValue' });
        });

        expect(encrypt).toHaveBeenCalledWith({ key: 'newValue' });

        await waitFor(() => {
            expect(setSearchParams).toHaveBeenCalled();
        });
    });

    it('should handle default values as a function', async () => {
        const defaultValuesFunc = vi.fn();
        const { result, rerender } = renderHook(() => useSearchCriteria({ defaultValues: defaultValuesFunc }));

        (useSearchParams as vi.Mock).mockReturnValue([new URLSearchParams('q=foundCriteria'), setSearchParams]);
        (decrypt as vi.Mock).mockResolvedValue({ key: 'value' });

        defaultValuesFunc.mockReturnValue({ defaultKey: 'defaultValue' });

        rerender();

        await waitFor(() => {
            expect(defaultValuesFunc).toHaveBeenCalledWith({ key: 'value' });
            expect(result.current.criteria).toEqual({ defaultKey: 'defaultValue', key: 'value' });
        });
    });
});
