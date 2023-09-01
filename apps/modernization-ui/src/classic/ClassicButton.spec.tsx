import { UserContext } from 'user';
import { ClassicButton } from './ClassicButton';
import { render, fireEvent, waitFor } from '@testing-library/react';

describe('when a ClassicButton is clicked', () => {
    const { location } = window;

    const getHrefSpy = jest.fn(() => 'example.com');
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

    it('should redirect to the url returned by the API', async () => {
        jest.spyOn(global, 'fetch').mockReturnValue(
            Promise.resolve({
                // @ts-expect-error : Only relevant properties are mocked; header Location
                headers: {
                    get: jest.fn((v) => (v === 'Location' && 'redirected-url') || null)
                }
            })
        );

        const user = {
            state: {
                isLoggedIn: true,
                isLoginPending: false,
                loginError: undefined,
                userId: undefined,
                displayName: undefined,
                getToken: () => 'token'
            },
            login: (_username: string) => {},
            logout: () => {}
        };

        const { findByText } = render(
            <UserContext.Provider value={user}>
                <ClassicButton url="redirect-url">Button text</ClassicButton>
            </UserContext.Provider>
        );

        const button = await findByText('Button text');

        fireEvent.click(button);

        await waitFor(() => {
            expect(global.fetch).toHaveBeenLastCalledWith('redirect-url', {
                headers: { Authorization: 'Bearer token' }
            });

            expect(setHrefSpy).toHaveBeenCalledWith('redirected-url');
        });
    });
});
