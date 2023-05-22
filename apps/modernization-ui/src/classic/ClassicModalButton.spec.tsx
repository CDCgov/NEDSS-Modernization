import { UserContext } from 'user';
import { ClassicModalButton } from './ClassicModalButton';
import { render, fireEvent, waitFor } from '@testing-library/react';
import { ClassicModalProvider } from './ClassicModalContext';

describe('when a ClassicButton is clicked', () => {
    const open = jest.fn();

    beforeAll(() => {
        window.open = open;
    });

    afterAll(() => {
        jest.resetAllMocks();
    });

    beforeEach(() => {
        jest.restoreAllMocks();
    });

    it('should open a window with the url returned by the API', async () => {
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
            login: (_username: string, _password: string) => Promise.resolve(true),
            logout: () => {}
        };

        const { findByText } = render(
            <UserContext.Provider value={user}>
                <ClassicModalProvider>
                    <ClassicModalButton url="redirect-url">Button text</ClassicModalButton>
                </ClassicModalProvider>
            </UserContext.Provider>
        );

        const button = await findByText('Button text');

        fireEvent.click(button);

        await waitFor(() => {
            expect(global.fetch).toHaveBeenLastCalledWith('redirect-url', {
                headers: { Authorization: 'Bearer token' }
            });

            expect(open).toHaveBeenCalledWith(
                'redirected-url',
                'classic',
                'width=980px, height=900px, status=no, unadorned=yes, scroll=yes, help=no, resizable=no'
            );
        });
    });
});
