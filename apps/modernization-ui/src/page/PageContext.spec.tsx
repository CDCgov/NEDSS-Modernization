import { render, renderHook } from '@testing-library/react';
import { PageProvider, usePage } from '.';
import { act } from 'react';

describe('PageContext', () => {
    it('should provide a default page title', () => {
        const { result } = renderHook(() => usePage(), {
            wrapper: PageProvider
        });

        expect(result.current.title).toBeUndefined();
    });

    it('should update the page title', () => {
        const { result } = renderHook(() => usePage(), {
            wrapper: PageProvider
        });

        act(() => {
            result.current.setTitle('New Title');
        });

        expect(result.current.title).toBe('New Title');
    });

    it('should throw an error if usePage is used outside of PageProvider', () => {
        const consoleError = console.error;
        console.error = jest.fn();

        expect(() => renderHook(() => usePage())).toThrow('usePage must be used within a PageProvider');

        console.error = consoleError;
    });
});
