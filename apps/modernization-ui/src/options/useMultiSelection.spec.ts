import { renderHook, act } from '@testing-library/react-hooks';
import { useMultiSelection } from './useMultiSelection';
import { Selectable } from './selectable';

describe('when using the useMultiSelection hook', () => {
    it('should initialize without initially selected values', () => {
        const available: Selectable[] = [
            { name: 'One Name', label: 'One Label', value: 'ONE' },
            { name: 'Two Name', label: 'Two Label', value: 'TWO' },
            { name: 'Three Name', label: 'Three Label', value: 'THREE' }
        ];

        const { result } = renderHook(() => useMultiSelection({ available }));

        expect(result.current.selected).toHaveLength(0);

        expect(result.current.items).toEqual(
            expect.arrayContaining([
                expect.objectContaining({ selected: false, value: expect.objectContaining({ value: 'ONE' }) }),
                expect.objectContaining({ selected: false, value: expect.objectContaining({ value: 'TWO' }) }),
                expect.objectContaining({ selected: false, value: expect.objectContaining({ value: 'THREE' }) })
            ])
        );
    });

    it('should initialize with selected values', () => {
        const available: Selectable[] = [
            { name: 'One Name', label: 'One Label', value: 'ONE' },
            { name: 'Two Name', label: 'Two Label', value: 'TWO' },
            { name: 'Three Name', label: 'Three Label', value: 'THREE' }
        ];

        const selected: Selectable[] = [
            { name: 'Three Name', label: 'Three Label', value: 'THREE' },
            { name: 'One Name', label: 'One Label', value: 'ONE' }
        ];

        const { result } = renderHook(() => useMultiSelection({ available, selected }));

        expect(result.current.selected).toEqual(
            expect.arrayContaining([
                expect.objectContaining({ value: 'THREE' }),
                expect.objectContaining({ value: 'ONE' })
            ])
        );

        expect(result.current.items).toEqual(
            expect.arrayContaining([
                expect.objectContaining({ selected: true, value: expect.objectContaining({ value: 'ONE' }) }),
                expect.objectContaining({ selected: false, value: expect.objectContaining({ value: 'TWO' }) }),
                expect.objectContaining({ selected: true, value: expect.objectContaining({ value: 'THREE' }) })
            ])
        );
    });

    it('should return item as selected when the item has been selected', () => {
        const available: Selectable[] = [
            { name: 'One Name', label: 'One Label', value: 'ONE' },
            { name: 'Two Name', label: 'Two Label', value: 'TWO' },
            { name: 'Three Name', label: 'Three Label', value: 'THREE' }
        ];

        const { result } = renderHook(() => useMultiSelection({ available }));

        act(() => {
            result.current.select({ name: 'Three Name', label: 'Three Label', value: 'THREE' });
        });

        expect(result.current.selected).toEqual([expect.objectContaining({ value: 'THREE' })]);
        expect(result.current.items).toEqual(
            expect.arrayContaining([
                expect.objectContaining({ selected: false, value: expect.objectContaining({ value: 'ONE' }) }),
                expect.objectContaining({ selected: false, value: expect.objectContaining({ value: 'TWO' }) }),
                expect.objectContaining({ selected: true, value: expect.objectContaining({ value: 'THREE' }) })
            ])
        );
    });

    it('should not return item as selected when the item has been deselected', () => {
        const available: Selectable[] = [
            { name: 'One Name', label: 'One Label', value: 'ONE' },
            { name: 'Two Name', label: 'Two Label', value: 'TWO' },
            { name: 'Three Name', label: 'Three Label', value: 'THREE' }
        ];

        const selected: Selectable[] = [
            { name: 'Three Name', label: 'Three Label', value: 'THREE' },
            { name: 'One Name', label: 'One Label', value: 'ONE' }
        ];

        const { result } = renderHook(() => useMultiSelection({ available, selected }));

        act(() => {
            result.current.deselect({ name: 'Three Name', label: 'Three Label', value: 'THREE' });
        });

        expect(result.current.selected).toEqual([expect.objectContaining({ value: 'ONE' })]);

        expect(result.current.items).toEqual(
            expect.arrayContaining([
                expect.objectContaining({ selected: true, value: expect.objectContaining({ value: 'ONE' }) }),
                expect.objectContaining({ selected: false, value: expect.objectContaining({ value: 'TWO' }) }),
                expect.objectContaining({ selected: false, value: expect.objectContaining({ value: 'THREE' }) })
            ])
        );
    });

    it('should not return any items as selected when reset', () => {
        const available: Selectable[] = [
            { name: 'One Name', label: 'One Label', value: 'ONE' },
            { name: 'Two Name', label: 'Two Label', value: 'TWO' },
            { name: 'Three Name', label: 'Three Label', value: 'THREE' }
        ];

        const selected: Selectable[] = [
            { name: 'Three Name', label: 'Three Label', value: 'THREE' },
            { name: 'One Name', label: 'One Label', value: 'ONE' }
        ];

        const { result } = renderHook(() => useMultiSelection({ available, selected }));

        act(() => {
            result.current.reset();
        });

        expect(result.current.selected).toHaveLength(0);

        expect(result.current.items).toEqual(
            expect.arrayContaining([
                expect.objectContaining({ selected: false, value: expect.objectContaining({ value: 'ONE' }) }),
                expect.objectContaining({ selected: false, value: expect.objectContaining({ value: 'TWO' }) }),
                expect.objectContaining({ selected: false, value: expect.objectContaining({ value: 'THREE' }) })
            ])
        );
    });

    it('should default items as selected when reset with specific items', () => {
        const available: Selectable[] = [
            { name: 'One Name', label: 'One Label', value: 'ONE' },
            { name: 'Two Name', label: 'Two Label', value: 'TWO' },
            { name: 'Three Name', label: 'Three Label', value: 'THREE' }
        ];

        const selected: Selectable[] = [
            { name: 'Three Name', label: 'Three Label', value: 'THREE' },
            { name: 'One Name', label: 'One Label', value: 'ONE' }
        ];

        const { result } = renderHook(() => useMultiSelection({ available, selected }));

        act(() => {
            result.current.reset([{ name: 'Two Name', label: 'Two Label', value: 'TWO' }]);
        });

        expect(result.current.selected).toEqual([expect.objectContaining({ value: 'TWO' })]);

        expect(result.current.items).toEqual(
            expect.arrayContaining([
                expect.objectContaining({ selected: false, value: expect.objectContaining({ value: 'ONE' }) }),
                expect.objectContaining({ selected: true, value: expect.objectContaining({ value: 'TWO' }) }),
                expect.objectContaining({ selected: false, value: expect.objectContaining({ value: 'THREE' }) })
            ])
        );
    });
});
