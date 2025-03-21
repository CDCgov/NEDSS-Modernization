import { ReactNode, act } from 'react';
import { renderHook } from '@testing-library/react';
import { ActiveSorting, SortingPreferenceProvider, useSortingPreferences } from './useSortingPreferences';
import { Direction } from 'sorting';

let mockProperty: string | undefined = undefined;
let mockDirection: Direction | undefined = undefined;
const mockReset = jest.fn();
const mockSortBy = jest.fn();

jest.mock('sorting', () => ({
    useSorting: () => ({
        property: mockProperty,
        direction: mockDirection,
        reset: mockReset,
        sortBy: mockSortBy
    })
}));

let mockValue: ActiveSorting | undefined = undefined;
const mockSave = jest.fn();
const mockRemove = jest.fn();

jest.mock('storage', () => ({
    useLocalStorage: ({ key, initial }: { key: string; initial?: any }) => ({
        value: mockValue,
        save: mockSave,
        remove: mockRemove
    })
}));

const wrapper = ({ children }: { children: ReactNode }) => (
    <SortingPreferenceProvider id="test.sorting">{children}</SortingPreferenceProvider>
);

describe('useSortingPreferences', () => {
    beforeEach(() => {
        jest.clearAllMocks();
        mockProperty = undefined;
        mockDirection = undefined;
        mockValue = undefined;
    });

    it('should initialize unsorted', () => {
        const { result } = renderHook(() => useSortingPreferences(), { wrapper });

        expect(result.current.active).toBeUndefined();
    });

    it('should update the active sorting when sorting', () => {
        const { result } = renderHook(() => useSortingPreferences(), { wrapper });

        act(() => {
            result.current.sortOn({ property: 'property-value', direction: 'asc' as Direction });
        });

        expect(result.current.active).toEqual(
            expect.objectContaining({ property: 'property-value', direction: 'asc' })
        );

        expect(mockSortBy).toBeCalledWith('property-value', 'asc');
    });

    it('should reset the active sorting', () => {
        const { result } = renderHook(() => useSortingPreferences(), { wrapper });

        act(() => {
            result.current.sortOn({ property: 'property-value', direction: 'asc' as Direction });
        });

        act(() => {
            result.current.sortOn();
        });

        expect(result.current.active).toBeUndefined();

        expect(mockReset).toBeCalled();
    });

    it('should sync with active sort changes', () => {
        mockProperty = 'new-active-property';
        mockDirection = 'desc' as Direction;
        const { result } = renderHook(() => useSortingPreferences(), { wrapper });

        expect(result.current.active).toEqual(
            expect.objectContaining({ property: 'new-active-property', direction: 'desc' })
        );
    });

    it('should initialize the active sorting from sorting preferences when present', () => {
        mockValue = { property: 'initial-value', direction: 'desc' as Direction };
        
        const { result } = renderHook(() => useSortingPreferences(), { wrapper });
        
        act(() => {
            result.current.sortOn(mockValue);
        });
        
        expect(result.current.active).toEqual(
            expect.objectContaining({ property: 'initial-value', direction: 'desc' })
        );

        expect(mockSortBy).toBeCalledWith('initial-value', 'desc');
    });

    it('should save sorting preferences when active sorting is changed', () => {
        const { result } = renderHook(() => useSortingPreferences(), { wrapper });

        act(() => {
            result.current.sortOn({ property: 'property-value', direction: 'asc' as Direction });
        });

        expect(mockSave).toBeCalledWith(expect.objectContaining({ property: 'property-value', direction: 'asc' }));
    });

    it('should remove sorting preferences when active sorting is reset', () => {
        const { result } = renderHook(() => useSortingPreferences(), { wrapper });

        act(() => {
            result.current.sortOn({ property: 'property-value', direction: 'asc' as Direction });
        });

        act(() => {
            result.current.sortOn();
        });

        expect(result.current.active).toBeUndefined();

        expect(mockRemove).toBeCalled();
    });
});
