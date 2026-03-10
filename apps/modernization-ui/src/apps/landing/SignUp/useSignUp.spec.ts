import { act, renderHook } from '@testing-library/react';
import { useSignUp } from './useSignUp';

describe('useSignUp', () => {
    const { location } = window;

    const getHrefSpy = vi.fn(() => 'invalid');
    const setHrefSpy = vi.fn((href) => href);

    beforeAll(() => {
        const mockLocation = { ...location };
        Object.defineProperty(mockLocation, 'href', {
            get: getHrefSpy,
            set: setHrefSpy,
        });

        // @ts-expect-error : location is mocked to check that the href is changed by the redirect
        delete window.location;
        // @ts-expect-error : location is mocked to check that the href is changed by the redirect
        window.location = mockLocation;
    });

    afterAll(() => {
        // @ts-expect-error : location is mocked to check that the href is changed by the redirect
        window.location = location;
    });

    beforeEach(() => {
        vi.restoreAllMocks();
    });

    it('should prepare an email message to request access from the requester', () => {
        const { result } = renderHook(() => useSignUp());

        act(() => {
            result.current.signUp('requester');
        });

        expect(setHrefSpy).toHaveBeenCalledWith(expect.stringContaining('mailto:'));
        expect(setHrefSpy).toHaveBeenCalledWith(expect.stringContaining('requester'));
    });
});
