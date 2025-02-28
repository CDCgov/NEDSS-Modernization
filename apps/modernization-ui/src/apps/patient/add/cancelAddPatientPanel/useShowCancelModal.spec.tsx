import { renderHook } from '@testing-library/react-hooks';
import { useLocalStorage } from 'storage';
import { useShowCancelModal } from './useShowCancelModal';

jest.mock('storage', () => ({
    useLocalStorage: jest.fn()
}));

describe('useShowCancelModal', () => {
    const defaultSave = jest.fn();

    beforeEach(() => {
        (useLocalStorage as jest.Mock).mockReturnValue({ value: false, save: defaultSave });
        defaultSave.mockReset();
    });

    it('should initialize with value false', () => {
        const { result } = renderHook(() => useShowCancelModal());
        expect(result.current.value).toBe(false);
    });
});
