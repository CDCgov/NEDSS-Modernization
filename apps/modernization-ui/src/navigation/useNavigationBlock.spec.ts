import { renderHook, act } from '@testing-library/react-hooks';
import { useNavigationBlock } from './useNavigationBlock';
import { useBlocker } from 'react-router-dom';

jest.mock('react-router-dom', () => ({
    useBlocker: jest.fn()
}));

/*
shouldBlock is false: no blocking should happen
shouldBlock is true:
*/

describe('useNavigationBlock', () => {
    let mockBlocker: any;

    beforeEach(() => {
        mockBlocker = {
            state: 'unblocked',
            proceed: jest.fn(),
            reset: jest.fn()
        };
        (useBlocker as jest.Mock).mockReturnValue(mockBlocker);
    });

    afterEach(() => {
        jest.clearAllMocks();
    });

    it('should return blocked as false when blocker state is unblocked', () => {
        const { result } = renderHook(() => useNavigationBlock({ shouldBlock: false }));
        expect(result.current.blocked).toBe(false);
    });

    it('should return blocked as true when blocker state is blocked', () => {
        mockBlocker.state = 'blocked';
        const { result } = renderHook(() => useNavigationBlock({ shouldBlock: true }));
        expect(result.current.blocked).toBe(true);
    });

    it('should call proceed when proceed is invoked', () => {
        const { result } = renderHook(() => useNavigationBlock({ shouldBlock: true }));
        act(() => {
            result.current.proceed();
        });
        expect(mockBlocker.proceed).toHaveBeenCalled();
    });

    it('should call reset when reset is invoked', () => {
        const { result } = renderHook(() => useNavigationBlock({ shouldBlock: true }));
        act(() => {
            result.current.reset();
        });
        expect(mockBlocker.reset).toHaveBeenCalled();
    });

    it('should call onBlock when navigation is triggered', () => {
        const onBlock = jest.fn();
        mockBlocker.state = 'unblocked';
        const { rerender } = renderHook(() => useNavigationBlock({ shouldBlock: true, onBlock }));
        act(() => {
            mockBlocker.state = 'blocked';
        });
        rerender();
        expect(onBlock).toHaveBeenCalled();
    });
});
