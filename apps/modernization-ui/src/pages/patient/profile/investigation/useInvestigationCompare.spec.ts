import { renderHook } from '@testing-library/react-hooks';
import { act, waitFor } from '@testing-library/react';
import { useInvestigationCompare } from './useInvestigationCompare';

describe('useInvestigationCompare', () => {
    describe('given a waiting comparison', () => {
        it('should move to selecting when given comparable investigation', async () => {
            const { result } = renderHook(() => useInvestigationCompare());

            const { comparable, select, selected } = result.current;

            act(() =>
                select({
                    investigation: '73',
                    condition: 'condition-value',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    event: 'event-value',
                    comparable: true
                })
            );

            await waitFor(() => {
                expect(comparable).toBe(false);
                expect(selected).toHaveLength(0);
            });
        });
    });

    describe('given a selecting comparison', () => {
        it('should be comparable after given an investigation with the same condition', async () => {
            const { result } = renderHook(() => useInvestigationCompare());

            const { comparable, select, selected } = result.current;

            act(() =>
                select({
                    investigation: '73',
                    condition: 'condition-value',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    event: 'event-value',
                    comparable: true
                })
            );

            act(() =>
                select({
                    investigation: '179',
                    condition: 'condition-value',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    event: 'event-value',
                    comparable: true
                })
            );

            waitFor(() => {
                expect(comparable).toBe(true);
                expect(selected).toEqual(
                    expect.arrayContaining([
                        expect.objectContaining({ investigation: '73' }),
                        expect.objectContaining({ investigation: '179' })
                    ])
                );
            });
        });

        it('should not be comparable after given an investigation with a different condition', async () => {
            const { result } = renderHook(() => useInvestigationCompare());

            const { comparable, select, selected } = result.current;

            act(() =>
                select({
                    investigation: '73',
                    condition: 'condition-value',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    event: 'event-value',
                    comparable: true
                })
            );

            act(() =>
                select({
                    investigation: '179',
                    condition: 'other-condition',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    event: 'event-value',
                    comparable: true
                })
            );

            waitFor(() => {
                expect(comparable).toBe(false);
                expect(selected).toHaveLength(0);
            });
        });

        it('should not be comparable after given more than two investigations with the same condition', async () => {
            const { result } = renderHook(() => useInvestigationCompare());

            const { comparable, select, selected } = result.current;

            act(() =>
                select({
                    investigation: '73',
                    condition: 'condition-value',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    event: 'event-value',
                    comparable: true
                })
            );

            act(() =>
                select({
                    investigation: '179',
                    condition: 'condition-value',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    event: 'event-value',
                    comparable: true
                })
            );

            act(() =>
                select({
                    investigation: '283',
                    condition: 'condition-value',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    event: 'event-value',
                    comparable: true
                })
            );

            waitFor(() => {
                expect(comparable).toBe(false);
                expect(selected).toHaveLength(0);
            });
        });
    });

    describe('given an uncomparable comparison', () => {
        it('should be comparable after removing the third investigation', () => {
            const { result } = renderHook(() => useInvestigationCompare());

            const { comparable, select, deselect, selected } = result.current;

            act(() =>
                select({
                    investigation: '73',
                    condition: 'condition-value',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    event: 'event-value',
                    comparable: true
                })
            );

            act(() =>
                select({
                    investigation: '179',
                    condition: 'condition-value',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    event: 'event-value',
                    comparable: true
                })
            );

            act(() =>
                select({
                    investigation: '283',
                    condition: 'condition-value',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    event: 'event-value',
                    comparable: true
                })
            );

            act(() =>
                deselect({
                    investigation: '179',
                    condition: 'condition-value',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    event: 'event-value',
                    comparable: true
                })
            );

            waitFor(() => {
                expect(comparable).toBe(true);
                expect(selected).toEqual(
                    expect.arrayContaining([
                        expect.objectContaining({ investigation: '73' }),
                        expect.objectContaining({ investigation: '283' })
                    ])
                );
            });
        });

        it('should be comparable after removing the investigation with a differing condition', () => {
            const { result } = renderHook(() => useInvestigationCompare());

            const { comparable, select, deselect, selected } = result.current;

            act(() =>
                select({
                    investigation: '73',
                    condition: 'condition-value',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    event: 'event-value',
                    comparable: true
                })
            );

            act(() =>
                select({
                    investigation: '179',
                    condition: 'other-condition',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    event: 'event-value',
                    comparable: true
                })
            );

            act(() =>
                select({
                    investigation: '283',
                    condition: 'condition-value',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    event: 'event-value',
                    comparable: true
                })
            );

            act(() =>
                deselect({
                    investigation: '179',
                    condition: 'other-condition',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    event: 'event-value',
                    comparable: true
                })
            );

            waitFor(() => {
                expect(comparable).toBe(true);
                expect(selected).toEqual(
                    expect.arrayContaining([
                        expect.objectContaining({ investigation: '73' }),
                        expect.objectContaining({ investigation: '283' })
                    ])
                );
            });
        });

        it('should remain uncomparable after removing the investigation with a similar condition', () => {
            const { result } = renderHook(() => useInvestigationCompare());

            const { comparable, select, deselect, selected } = result.current;

            act(() =>
                select({
                    investigation: '73',
                    condition: 'condition-value',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    event: 'event-value',
                    comparable: true
                })
            );

            act(() =>
                select({
                    investigation: '179',
                    condition: 'other-condition',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    event: 'event-value',
                    comparable: true
                })
            );

            act(() =>
                select({
                    investigation: '283',
                    condition: 'condition-value',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    event: 'event-value',
                    comparable: true
                })
            );

            act(() =>
                deselect({
                    investigation: '283',
                    condition: 'condition-value',
                    status: 'status-value',
                    jurisdiction: 'jurisdiction-value',
                    event: 'event-value',
                    comparable: true
                })
            );

            waitFor(() => {
                expect(comparable).toBe(false);
                expect(selected).toHaveLength(0);
            });
        });
    });
});
