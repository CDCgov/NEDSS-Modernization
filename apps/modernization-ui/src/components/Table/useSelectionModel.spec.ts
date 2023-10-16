import { renderHook } from '@testing-library/react-hooks';
import { act, waitFor } from '@testing-library/react';

import { useSelectionModel } from './useSelectionModel';

describe('useTableSelection', () => {
    it('should include selected item', () => {
        const { result } = renderHook(() => useSelectionModel());

        const { select, selected } = result.current;

        act(() => select('one'));

        waitFor(() => {
            expect(selected).toEqual(expect.arrayContaining(['one']));
        });
    });

    it('should remove deselected item', () => {
        const { result } = renderHook(() => useSelectionModel());

        const { select, deselect, selected } = result.current;

        act(() => {
            select('one');
        });

        act(() => {
            select('two');
        });

        act(() => {
            deselect('one');
        });

        waitFor(() => {
            expect(selected).toEqual(expect.arrayContaining(['two']));
        });
    });
});
