
import { renderHook } from '@testing-library/react';
import { useShowCancelModal } from './useShowCancelModal';

const mockSave = vi.fn();
const mockRemove = vi.fn();

vi.mock('storage', () => ({
    useLocalStorage: () => ({ value: false, save: mockSave, remove: mockRemove })
}));

describe('useShowCancelModal', () => {
    it('should initialize with value false', () => {
        const { result } = renderHook(() => useShowCancelModal());
        expect(result.current.value).toBe(false);
    });
});
