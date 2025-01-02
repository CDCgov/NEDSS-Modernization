import { act } from 'react';
import { renderHook } from '@testing-library/react';
import { useNavigationBlock } from './useNavigationBlock';
import { useBlocker } from 'react-router';

const mockUseNavigate = jest.fn();

jest.mock('react-router', () => ({
    useBlocker: jest.fn(),
    useNavigate: () => mockUseNavigate
}));

/*
activated is false: no blocking should happen
activated is true: watches for router navigation
activated is enabled by default
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

    it('should block navigation when blocking is activated', () => {
        mockBlocker.state = 'blocked';
        const { result } = renderHook(() => useNavigationBlock({ activated: true }));
        expect(result.current.blocked).toBe(true);
    });
    it('should allow navigation when blocking is not activated', () => {
        const { result } = renderHook(() => useNavigationBlock({ activated: false }));
        expect(result.current.blocked).toBe(false);
    });

    it('should proceed with unblocked navigation when reset', () => {
        mockBlocker.state = 'blocked';
        const { result } = renderHook(() => useNavigationBlock({ activated: true }));
        act(() => {
            result.current.unblock();
        });
        expect(mockBlocker.proceed).toHaveBeenCalled();
    });

    it('should reset blocked navigation', () => {
        mockBlocker.state = 'blocked';
        const { result } = renderHook(() => useNavigationBlock({ activated: true }));
        act(() => {
            result.current.reset();
        });
        expect(mockBlocker.reset).toHaveBeenCalled();
    });

    it('should call onBlock when navigation is triggered', () => {
        const onBlock = jest.fn();
        mockBlocker.state = 'unblocked';
        const { rerender } = renderHook(() => useNavigationBlock({ activated: true, onBlock }));
        act(() => {
            mockBlocker.state = 'blocked';
        });
        rerender();
        expect(onBlock).toHaveBeenCalled();
    });

    it('should not block when route in list of unblockable routes', () => {
        let blockerResult: boolean | undefined = undefined;
        (useBlocker as jest.Mock).mockImplementation((fn) => {
            blockerResult = fn({ currentLocation: { pathname: '/current' }, nextLocation: { pathname: '/expired' } });
            return { ...defaultBlockerResult };
        });

        const { result } = renderHook(() => useNavigationBlock({ activated: true }));

        act(() => {
            result.current.block();
        });

        expect(blockerResult).toBe(false);
    });

    it('should block navigation when block is engaged', () => {
        let blockerResult: boolean | undefined = undefined;
        (useBlocker as jest.Mock).mockImplementation((fn) => {
            blockerResult = fn({ currentLocation: { pathname: '/current' }, nextLocation: { pathname: '/next' } });
            return { ...defaultBlockerResult };
        });
        const { result } = renderHook(() => useNavigationBlock({ activated: true }));

        act(() => {
            result.current.block();
        });

        expect(blockerResult).toBe(true);
    });

    it('should not block navigation when block is not engaged', () => {
        let blockerResult: boolean | undefined = undefined;
        (useBlocker as jest.Mock).mockImplementation((fn) => {
            blockerResult = fn({ currentLocation: { pathname: '/current' }, nextLocation: { pathname: '/next' } });
            return { ...defaultBlockerResult };
        });
        const { result } = renderHook(() => useNavigationBlock({ activated: true }));

        act(() => {
            result.current.block();
        });

        act(() => {
            result.current.allow();
        });

        expect(blockerResult).toBe(false);
    });
});
