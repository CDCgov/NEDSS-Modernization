import { act } from 'react';
import { renderHook, waitFor } from '@testing-library/react';
import { useCompareInvestigation } from './useCompareInvestigation';

describe('useCompareInvestigation', () => {
    describe('given a waiting comparison', () => {
        it('should evaluate a comparable investigation as comparable', () => {
            const { result } = renderHook(() => useCompareInvestigation());

            expect(
                result.current.isComparable({
                    patient: 881,
                    identifier: 73,
                    condition: 'condition-value',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    local: 'event-value',
                    comparable: true
                })
            ).toBe(true);
        });

        it('should not evaluate a non comparable investigation as comparable', () => {
            const { result } = renderHook(() => useCompareInvestigation());

            expect(
                result.current.isComparable({
                    patient: 881,
                    identifier: 73,
                    condition: 'condition-value',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    local: 'event-value',
                    comparable: false
                })
            ).toBe(false);
        });

        it('should select the comparable investigation', () => {
            const { result } = renderHook(() => useCompareInvestigation());

            act(() =>
                result.current.select({
                    patient: 881,
                    identifier: 73,
                    condition: 'condition-value',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    local: 'event-value',
                    comparable: true
                })
            );

            expect(
                result.current.isSelected({
                    patient: 881,
                    identifier: 73,
                    condition: 'condition-value',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    local: 'event-value',
                    comparable: true
                })
            ).toBe(true);
        });
    });

    describe('given a selecting comparison', () => {
        it('should be comparable after given a comparable investigation with the same condition', () => {
            const { result } = renderHook(() => useCompareInvestigation());

            act(() =>
                result.current.select({
                    patient: 881,
                    identifier: 73,
                    condition: 'condition-value',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    local: 'event-value',
                    comparable: true
                })
            );

            act(() =>
                result.current.select({
                    patient: 881,
                    identifier: 179,
                    condition: 'condition-value',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    local: 'event-value',
                    comparable: true
                })
            );

            expect(
                result.current.isSelected({
                    patient: 881,
                    identifier: 73,
                    condition: 'condition-value',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    local: 'event-value',
                    comparable: true
                })
            ).toBe(true);

            expect(
                result.current.isSelected({
                    patient: 881,
                    identifier: 179,
                    condition: 'condition-value',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    local: 'event-value',
                    comparable: true
                })
            ).toBe(true);

            expect(result.current).toEqual(
                expect.objectContaining({
                    comparison: expect.objectContaining({
                        selected: 73,
                        comparedTo: 179
                    })
                })
            );
        });

        it('should be able to compare a comparable investigation with the same condition', () => {
            const { result } = renderHook(() => useCompareInvestigation());

            act(() =>
                result.current.select({
                    patient: 881,
                    identifier: 73,
                    condition: 'condition-value',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    local: 'event-value',
                    comparable: true
                })
            );

            const actual = result.current.isComparable({
                patient: 881,
                identifier: 179,
                condition: 'condition-value',
                status: 'status-value',
                jurisdiction: 'jurisdiction-value',
                local: 'event-value',
                comparable: true
            });

            expect(actual).toBe(true);
        });

        it('should not be able to compare an incomparable investigation', () => {
            const { result } = renderHook(() => useCompareInvestigation());

            act(() =>
                result.current.select({
                    patient: 881,
                    identifier: 73,
                    condition: 'condition-value',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    local: 'event-value',
                    comparable: true
                })
            );

            const actual = result.current.isComparable({
                patient: 881,
                identifier: 179,
                condition: 'other-condition',
                status: 'status-value',
                jurisdiction: 'jurisdiction-value',
                local: 'event-value',
                comparable: true
            });

            expect(actual).toBe(false);
        });

        it('should be able to compare a comparable investigation with the same condition', () => {
            const { result } = renderHook(() => useCompareInvestigation());

            act(() =>
                result.current.select({
                    patient: 881,
                    identifier: 73,
                    condition: 'condition-value',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    local: 'event-value',
                    comparable: true
                })
            );

            const actual = result.current.isComparable({
                patient: 881,
                identifier: 179,
                condition: 'condition-value',
                status: 'status-value',
                jurisdiction: 'jurisdiction-value',
                local: 'event-value',
                comparable: false
            });

            expect(actual).toBe(false);
        });

        it('should not be comparable after given more than two comparable investigations with the same condition', () => {
            const { result } = renderHook(() => useCompareInvestigation());

            act(() =>
                result.current.select({
                    patient: 881,
                    identifier: 73,
                    condition: 'condition-value',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    local: 'event-value',
                    comparable: true
                })
            );

            act(() =>
                result.current.select({
                    patient: 881,
                    identifier: 179,
                    condition: 'condition-value',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    local: 'event-value',
                    comparable: true
                })
            );

            const actual = result.current.isComparable({
                patient: 881,
                identifier: 283,
                condition: 'condition-value',
                status: 'status-value',
                jurisdiction: 'jurisdiction-value',
                local: 'event-value',
                comparable: true
            });

            expect(actual).toBe(false);
        });
    });

    describe('given a comparable comparison', () => {
        it('should not evaluate a comparable investigation with the same condition as comparable', () => {
            const { result } = renderHook(() => useCompareInvestigation());

            act(() =>
                result.current.select({
                    patient: 881,
                    identifier: 73,
                    condition: 'condition-value',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    local: 'event-value',
                    comparable: true
                })
            );

            act(() =>
                result.current.select({
                    patient: 881,
                    identifier: 179,
                    condition: 'condition-value',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    local: 'event-value',
                    comparable: true
                })
            );

            expect(
                result.current.isComparable({
                    patient: 881,
                    identifier: 1033,
                    condition: 'condition-value',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    local: 'event-value',
                    comparable: true
                })
            ).toBe(false);
        });

        it('should not produce a comparison when the selected is removed', () => {
            const { result } = renderHook(() => useCompareInvestigation());

            act(() =>
                result.current.select({
                    patient: 881,
                    identifier: 73,
                    condition: 'condition-value',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    local: 'event-value',
                    comparable: true
                })
            );

            act(() =>
                result.current.select({
                    patient: 881,
                    identifier: 179,
                    condition: 'condition-value',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    local: 'event-value',
                    comparable: true
                })
            );

            act(() =>
                result.current.deselect({
                    patient: 881,
                    identifier: 73,
                    condition: 'condition-value',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    local: 'event-value',
                    comparable: true
                })
            );

            expect(
                result.current.isSelected({
                    patient: 881,
                    identifier: 73,
                    condition: 'condition-value',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    local: 'event-value',
                    comparable: true
                })
            ).toBe(false);

            expect(result.current.comparison).toBeUndefined();
        });

        it('should not produce a comparison when the compare to is removed', () => {
            const { result } = renderHook(() => useCompareInvestigation());

            act(() =>
                result.current.select({
                    patient: 881,
                    identifier: 73,
                    condition: 'condition-value',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    local: 'event-value',
                    comparable: true
                })
            );

            act(() =>
                result.current.select({
                    patient: 881,
                    identifier: 179,
                    condition: 'condition-value',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    local: 'event-value',
                    comparable: true
                })
            );

            act(() =>
                result.current.deselect({
                    patient: 881,
                    identifier: 179,
                    condition: 'condition-value',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    local: 'event-value',
                    comparable: true
                })
            );

            expect(
                result.current.isSelected({
                    patient: 881,
                    identifier: 179,
                    condition: 'condition-value',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    local: 'event-value',
                    comparable: true
                })
            ).toBe(false);

            expect(result.current.comparison).toBeUndefined();
        });
    });
});
