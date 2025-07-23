import { renderHook, act } from '@testing-library/react';
import { Location } from 'react-router';
import { PageProvider, usePage } from './PageContext';

const mockLocation = {} as Location;

jest.mock('react-router', () => ({
    useLocation: () => mockLocation
}));

const setup = () => {
    return renderHook(() => usePage(), {
        wrapper: PageProvider
    });
};

describe('PageContext', () => {
    beforeEach(() => {
        mockLocation.pathname = '/';
        document.title = '';
    });

    it('should provide a default page title', () => {
        mockLocation.pathname = '/some-longish-name/path';

        const { result } = setup();

        expect(result.current.title).toEqual('Some Longish Name');
    });

    it('should update the page title', () => {
        const { result } = setup();

        act(() => {
            result.current.setTitle('New Title');
        });

        expect(result.current.title).toBe('New Title');
    });

    it('should reset the page title', () => {
        mockLocation.pathname = '/default';
        const { result } = setup();

        act(() => {
            result.current.setTitle('Temporary Title');
        });

        expect(result.current.title).toBe('Temporary Title');

        act(() => {
            result.current.resetTitle();
        });
        expect(result.current.title).toEqual('Default');
    });

    it('should update document title when page title changes', () => {
        const { result } = setup();

        act(() => {
            result.current.setTitle('Test Page');
        });

        expect(document.title).toBe('NBS | Test Page');
    });
});
