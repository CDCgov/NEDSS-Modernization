import { renderHook } from '@testing-library/react-hooks';
import { useVerification } from './useVerification';
import { act } from '@testing-library/react';

describe('when verifying values', () => {
    it('should provide violations when the constraint fails', () => {
        const { result } = renderHook(() => useVerification({ constraint: () => 'value is unverified.' }));

        act(() => {
            result.current.verify('value');
        });

        expect(result.current.violation).toContain('value is unverified');
    });

    it('should not provide violations until asked to verify', () => {
        const { result } = renderHook(() => useVerification({ constraint: () => 'value is unverified.' }));

        expect(result.current.violation).toBeUndefined();
    });

    it('should not provide violations when the constraint is met', () => {
        const { result } = renderHook(() => useVerification({ constraint: () => true }));

        act(() => {
            result.current.verify('value');
        });

        expect(result.current.violation).toBeUndefined();
    });
});
