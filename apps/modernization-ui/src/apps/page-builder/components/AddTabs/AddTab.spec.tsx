import { fireEvent, render, waitFor } from '@testing-library/react';
import { AlertProvider } from 'alert';
import { BrowserRouter } from 'react-router-dom';
import { AddTab } from './index';
import Router from 'react-router';
import { UserContext } from 'user';

jest.mock('react-router', () => ({
    ...jest.requireActual('react-router'),
    useParams: jest.fn()
}));

const props = {
    onAddTab: jest.fn(),
    onCancel: jest.fn()
};

beforeEach(() => {
    jest.spyOn(Router, 'useParams').mockReturnValue({ pageId: '1' });
});

afterEach(() => {
    jest.resetAllMocks();
});

describe('<AddTab />', () => {
    it('Add button should initially be disabled', () => {
        const { container } = render(
            <BrowserRouter>
                <AddTab {...props} />
            </BrowserRouter>
        );
        const btn = container.getElementsByClassName('usa-button')[0];
        expect(btn.hasAttribute('disabled'));
    });

    it('should have a cancel button', () => {
        const { getByText } = render(
            <AlertProvider>
                <AddTab {...props} />
            </AlertProvider>
        );

        expect(getByText('Cancel')).toBeInTheDocument();
    });

    it('should render a grid with 3 inputs labels which are Tab Name, Tab Description, Visible', () => {
        const { getByText } = render(
            <AlertProvider>
                <AddTab {...props} />
            </AlertProvider>
        );
        expect(getByText('Tab Name')).toBeInTheDocument();
        expect(getByText('Tab Description')).toBeInTheDocument();
        expect(getByText('Visible')).toBeInTheDocument();
    });

    it('Check validation', () => {
        const { getByTestId, queryByText, container } = render(
            <AlertProvider>
                <AddTab {...props} />
            </AlertProvider>
        );

        const nameElement = getByTestId('tab-name');
        fireEvent.change(nameElement, { target: { value: 'tabs' } });
        fireEvent.blur(nameElement);

        const btn = container.getElementsByClassName('usa-button')[0];
        expect(btn.hasAttribute('disabled')).not.toBeTruthy();
    });

    describe('when add button is clicked', () => {
        it.skip('should call onAddTab', async () => {
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

            const { getByTestId, getByText } = render(
                <UserContext.Provider value={user}>
                    <AlertProvider>
                        <AddTab {...props} />
                    </AlertProvider>
                </UserContext.Provider>
            );

            const nameElement = getByTestId('tab-name');
            fireEvent.change(nameElement, { target: { value: 'tabs' } });
            fireEvent.blur(nameElement);

            const btn = getByText('Add tab');
            fireEvent.click(btn);

            await waitFor(() => {
                expect(props.onAddTab).toHaveBeenCalled();
            });
        });
    });

    describe('when the cancel button is clicked', () => {
        it('should call onCancel', () => {
            const { getByText } = render(
                <AlertProvider>
                    <AddTab {...props} />
                </AlertProvider>
            );

            const btn = getByText('Cancel');
            fireEvent.click(btn);

            expect(props.onCancel).toHaveBeenCalled();
        });
    });
});
