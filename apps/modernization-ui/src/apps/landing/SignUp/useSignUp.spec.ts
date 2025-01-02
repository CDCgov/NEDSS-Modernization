import { act, renderHook } from '@testing-library/react';
import { useSignUp } from './useSignUp';

describe('useSignUp', () => {
    const { location } = window;

    const getHrefSpy = jest.fn(() => 'invalid');
    const setHrefSpy = jest.fn((href) => href);

    beforeAll(() => {
        const mockLocation = { ...location };
        Object.defineProperty(mockLocation, 'href', {
            get: getHrefSpy,
            set: setHrefSpy
        });

        // @ts-expect-error : location is mocked to check that the href is changed by the redirect
        delete window.location;
        window.location = mockLocation;
    });

    afterAll(() => {
        window.location = location;
    });

    beforeEach(() => {
        jest.restoreAllMocks();
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
