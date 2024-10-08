import { renderHook, act } from '@testing-library/react-hooks';
import { useNavigationBlock } from './useNavigationBlock';
import { useBlocker } from 'react-router-dom';

jest.mock('react-router-dom', () => ({
    useBlocker: jest.fn()
}));

/*
shouldBlock is false: no blocking should happen
shouldBlock is true: watches for router navigation
*/

const defaultBlockerResult = {
    state: 'unblocked',
    proceed: jest.fn(),
    reset: jest.fn()
};

describe('useNavigationBlock', () => {
    let mockBlocker: any;

    beforeEach(() => {
        mockBlocker = { ...defaultBlockerResult };
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

    it('should call proceed when unblock is invoked', () => {
        const { result } = renderHook(() => useNavigationBlock({ shouldBlock: true }));
        act(() => {
            result.current.unblock();
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

    it('should not block when route not in list of blocked routes', () => {
        let blockerResult: boolean | undefined = undefined;
        (useBlocker as jest.Mock).mockImplementation((fn) => {
            blockerResult = fn({ currentLocation: { pathname: '/current' }, nextLocation: { pathname: '/next' } });
            return { ...defaultBlockerResult };
        });
        renderHook(() => useNavigationBlock({ shouldBlock: true, blockedRoutes: ['/my-route'] }));
        expect(blockerResult).toBe(false);
    });

    it('should block when route in list of blocked routes', () => {
        let blockerResult: boolean | undefined = undefined;
        (useBlocker as jest.Mock).mockImplementation((fn) => {
            blockerResult = fn({ currentLocation: { pathname: '/current' }, nextLocation: { pathname: '/next' } });
            return { ...defaultBlockerResult };
        });
        renderHook(() => useNavigationBlock({ shouldBlock: true, blockedRoutes: ['/next'] }));
        expect(blockerResult).toBe(true);
    });

    it('should not block when route in list of unblockable routes', () => {
        let blockerResult: boolean | undefined = undefined;
        (useBlocker as jest.Mock).mockImplementation((fn) => {
            blockerResult = fn({ currentLocation: { pathname: '/current' }, nextLocation: { pathname: '/expired' } });
            return { ...defaultBlockerResult };
        });
        renderHook(() => useNavigationBlock({ shouldBlock: true }));
        expect(blockerResult).toBe(false);
    });
});
