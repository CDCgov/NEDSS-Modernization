import { UserContext } from 'user';
import { ClassicModalLink } from './ClassicModalLink';
import { render, fireEvent, waitFor } from '@testing-library/react';
import { ClassicModalProvider } from './ClassicModalContext';

describe('when a ClassicLink is clicked', () => {
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
                getToken: () => 'token'
            },
            login: (_username: string) => {},
            logout: () => {}
        };

        const { findByText } = render(
            <UserContext.Provider value={user}>
                <ClassicModalProvider>
                    <ClassicModalLink url="redirect-url">Link text</ClassicModalLink>
                </ClassicModalProvider>
            </UserContext.Provider>
        );

        const button = await findByText('Link text');

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
