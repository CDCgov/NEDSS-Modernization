import { act } from 'react';
import { renderHook, waitFor } from '@testing-library/react';
import { useCompareInvestigation } from './useCompareInvestigation';

describe('useCompareInvestigation', () => {
    describe('given a waiting comparison', () => {
        it('should move to selecting when given comparable investigation', async () => {
            const { result } = renderHook(() => useCompareInvestigation());

            const { select } = result.current;

            act(() =>
                select({
                    patient: 881,
                    identifier: 73,
                    condition: 'condition-value',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    local: 'event-value',
                    comparable: true
                })
            );

            await waitFor(() =>
                expect(result.current).toEqual(
                    expect.objectContaining({
                        comparable: false,
                        selected: []
                    })
                )
            );
        });
    });

    describe('given a selecting comparison', () => {
        it('should be comparable after given an investigation with the same condition', async () => {
            const { result } = renderHook(() => useCompareInvestigation());

            const { select } = result.current;

            act(() =>
                select({
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
                select({
                    patient: 881,
                    identifier: 179,
                    condition: 'condition-value',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    local: 'event-value',
                    comparable: true
                })
            );

            await waitFor(() =>
                expect(result.current).toEqual(
                    expect.objectContaining({
                        comparable: true,
                        selected: expect.arrayContaining([
                            expect.objectContaining({ identifier: 73 }),
                            expect.objectContaining({ identifier: 179 })
                        ])
                    })
                )
            );
        });

        it('should not be comparable after given an investigation with a different condition', async () => {
            const { result } = renderHook(() => useCompareInvestigation());

            const { comparable, select, selected } = result.current;

            act(() =>
                select({
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
                select({
                    patient: 881,
                    identifier: 179,
                    condition: 'other-condition',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    local: 'event-value',
                    comparable: true
                })
            );

            await waitFor(() =>
                expect(result.current).toEqual(
                    expect.objectContaining({
                        comparable: false,
                        selected: []
                    })
                )
            );
        });

        it('should not be comparable after given more than two investigations with the same condition', async () => {
            const { result } = renderHook(() => useCompareInvestigation());

            const { select } = result.current;

            act(() =>
                select({
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
                select({
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
                select({
                    patient: 881,
                    identifier: 283,
                    condition: 'condition-value',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    local: 'event-value',
                    comparable: true
                })
            );

            await waitFor(() =>
                expect(result.current).toEqual(
                    expect.objectContaining({
                        comparable: false
                    })
                )
            );
        });
    });

    describe('given an uncomparable comparison', () => {
        it('should be comparable after removing the third investigation', async () => {
            const { result } = renderHook(() => useCompareInvestigation());

            const { select, deselect } = result.current;

            act(() =>
                select({
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
                select({
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
                select({
                    patient: 881,
                    identifier: 283,
                    condition: 'condition-value',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    local: 'event-value',
                    comparable: true
                })
            );

            act(() =>
                deselect({
                    patient: 881,
                    identifier: 179,
                    condition: 'condition-value',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    local: 'event-value',
                    comparable: true
                })
            );

            await waitFor(() =>
                expect(result.current).toEqual(
                    expect.objectContaining({
                        comparable: true,
                        selected: expect.arrayContaining([
                            expect.objectContaining({ identifier: 73 }),
                            expect.objectContaining({ identifier: 283 })
                        ])
                    })
                )
            );
        });

        it('should be comparable after removing the investigation with a differing condition', async () => {
            const { result } = renderHook(() => useCompareInvestigation());

            const { comparable, select, deselect, selected } = result.current;

            act(() =>
                select({
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
                select({
                    patient: 881,
                    identifier: 179,
                    condition: 'other-condition',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    local: 'event-value',
                    comparable: true
                })
            );

            act(() =>
                select({
                    patient: 881,
                    identifier: 283,
                    condition: 'condition-value',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    local: 'event-value',
                    comparable: true
                })
            );

            act(() =>
                deselect({
                    patient: 881,
                    identifier: 179,
                    condition: 'other-condition',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    local: 'event-value',
                    comparable: true
                })
            );

            await waitFor(() =>
                expect(result.current).toEqual(
                    expect.objectContaining({
                        comparable: true,
                        selected: expect.arrayContaining([
                            expect.objectContaining({ identifier: 73 }),
                            expect.objectContaining({ identifier: 283 })
                        ])
                    })
                )
            );
        });

        it('should remain uncomparable after removing the investigation with a similar condition', async () => {
            const { result } = renderHook(() => useCompareInvestigation());

            const { select, deselect } = result.current;

            act(() =>
                select({
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
                select({
                    patient: 881,
                    identifier: 179,
                    condition: 'other-condition',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    local: 'event-value',
                    comparable: true
                })
            );

            act(() =>
                select({
                    patient: 881,
                    identifier: 283,
                    condition: 'condition-value',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    local: 'event-value',
                    comparable: true
                })
            );

            act(() =>
                deselect({
                    patient: 881,
                    identifier: 283,
                    condition: 'condition-value',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    local: 'event-value',
                    comparable: true
                })
            );

            await waitFor(() =>
                expect(result.current).toEqual(
                    expect.objectContaining({
                        comparable: false,
                        selected: []
                    })
                )
            );
        });
    });
});
