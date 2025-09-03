import { vi } from 'vitest';
import { renderHook } from '@testing-library/react';
import { useShowCancelModal } from './useShowCancelModal';

const mockSave = jest.fn();
const mockRemove = jest.fn();

vi.mock('storage', () => ({
    useLocalStorage: () => ({ value: false, save: mockSave, remove: mockRemove })
}));

describe('useShowCancelModal', () => {
    it('should initialize with value false', () => {
        const { result } = renderHook(() => useShowCancelModal());
        expect(result.current.value).toBe(false);
    });
});
