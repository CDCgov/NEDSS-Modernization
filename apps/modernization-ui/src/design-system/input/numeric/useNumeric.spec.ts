import { renderHook, act } from '@testing-library/react-hooks';
import { useNumeric } from './useNumeric';

describe('useNumeric', () => {
    it('should initialize with the provided value', () => {
        const { result } = renderHook(() => useNumeric(10));
        expect(result.current.current).toBe(10);
    });

    it('should change the value', () => {
        const { result } = renderHook(() => useNumeric());
        act(() => {
            result.current.change(20);
        });
        expect(result.current.current).toBe(20);
    });

    it('should clear the value', () => {
        const { result } = renderHook(() => useNumeric(30));
        act(() => {
            result.current.clear();
        });
        expect(result.current.current).toBeUndefined();
    });

    it('should initialize the value only if different', () => {
        const { result } = renderHook(() => useNumeric(40));
        act(() => {
            result.current.initialize(40);
        });
        expect(result.current.current).toBe(40);

        act(() => {
            result.current.initialize(50);
        });
        expect(result.current.current).toBe(50);
    });
});
