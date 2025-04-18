import { renderHook } from '@testing-library/react';
import { PageProvider, usePage } from '.';
import { act } from 'react';

const setup = () => {
    return renderHook(() => usePage(), {
        wrapper: PageProvider
    })
}

describe('PageContext', () => {
    it('should provide a default page title', () => {
        const { result } = setup();

        expect(result.current.title).toBeUndefined();
    });

    it('should update the page title', () => {
        const { result } = setup();

        act(() => {
            result.current.setTitle('New Title');
        });

        expect(result.current.title).toBe('New Title');
    });

    it('should reset the page title', () => {
        const { result } = setup();

        act(() => {
            result.current.setTitle('Temporary Title');
        });

        expect(result.current.title).toBe('Temporary Title');

        act(() => {
            result.current.resetTitle();
        });
        expect(result.current.title).toBeUndefined();
    });


    it('should throw an error if usePage is used outside of PageProvider', () => {
        expect(() => renderHook(() => usePage())).toThrow('usePage must be used within a PageProvider');
    });
});
