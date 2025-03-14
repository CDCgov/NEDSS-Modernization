import { act } from '@testing-library/react';
import { FilterProvider, useFilter } from './useFilter';
import { renderHook } from '@testing-library/react-hooks';
import { ReactNode } from 'react';

const wrapper = ({ children }: { children: ReactNode }) => <FilterProvider>{children}</FilterProvider>;

describe('FilterProvider', () => {
    it('should not be active by default', () => {
        const { result } = renderHook(() => useFilter(), { wrapper });

        expect(result.current.active).toBeFalsy();
    });

    it('should be active when shown', () => {
        const { result } = renderHook(() => useFilter(), { wrapper });

        act(() => {
            result.current.show();
        });

        expect(result.current.active).toBeTruthy();
    });

    it('should not be active when hidden while active', () => {
        const { result } = renderHook(() => useFilter(), { wrapper });

        act(() => {
            result.current.show();
        });

        expect(result.current.active).toBeTruthy();

        act(() => {
            result.current.hide();
        });

        expect(result.current.active).toBeFalsy();
    });

    it('should be active when toggled from inactive', () => {
        const { result } = renderHook(() => useFilter(), { wrapper });

        act(() => {
            result.current.toggle();
        });

        expect(result.current.active).toBeTruthy();
    });

    it('should be inactive when toggled from active', () => {
        const { result } = renderHook(() => useFilter(), { wrapper });

        act(() => {
            result.current.show();
        });

        act(() => {
            result.current.toggle();
        });

        expect(result.current.active).toBeFalsy();
    });

    it('should apply the filter value for the id', () => {
        const { result } = renderHook(() => useFilter(), { wrapper });

        act(() => {
            result.current.add('filtered', 'filtered-value');
        });

        act(() => {
            result.current.apply();
        });

        expect(result.current.filter).toEqual(expect.objectContaining({ filtered: 'filtered-value' }));
    });

    it('should return applied filter value for the id', () => {
        const { result } = renderHook(() => useFilter(), { wrapper });

        act(() => {
            result.current.add('filtered', 'filtered-value');
        });

        act(() => {
            result.current.apply();
        });

        const actual = result.current.valueOf('filtered');

        expect(actual).toEqual('filtered-value');
    });

    it('should clear the filter value for the id', () => {
        const { result } = renderHook(() => useFilter(), { wrapper });

        act(() => {
            result.current.add('filtered', 'filtered-value');
        });

        act(() => {
            result.current.add('other', 'other-value');
        });

        act(() => {
            result.current.apply();
        });

        expect(result.current.filter).toHaveProperty('filtered');

        act(() => {
            result.current.clear('other');
        });

        expect(result.current.filter).toHaveProperty('filtered');
        expect(result.current.filter).not.toHaveProperty('other');
    });

    it('should reset all filter values', () => {
        const { result } = renderHook(() => useFilter(), { wrapper });

        act(() => {
            result.current.add('filtered', 'filtered-value');
        });

        act(() => {
            result.current.add('other', 'other-value');
        });

        act(() => {
            result.current.apply();
        });

        expect(result.current.filter).toHaveProperty('filtered');

        act(() => {
            result.current.reset();
        });

        expect(result.current.filter).toBeUndefined();
    });
});
