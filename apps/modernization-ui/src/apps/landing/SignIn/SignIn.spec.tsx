import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { SignIn } from './SignIn';

jest.mock('react-router-dom', () => ({
    ...jest.requireActual('react-router-dom'),
    NavLink: ({ children, ...props }: any) => <a {...props}>{children}</a>
}));

describe('SignIn', () => {
    it('renders without crashing', () => {
        const handleWelcomeEventMock = jest.fn();
        const { getByText, getByRole } = render(<SignIn handleWelcomeEvent={handleWelcomeEventMock} />);

        expect(getByText('Login')).toBeInTheDocument();

        expect(
            getByText(
                'Please be sure to avoid entering any real PHI/PIl data on the demo site. All information entered will be viewable by other users.'
            )
        ).toBeInTheDocument();

        const signUpButton = getByRole('button', { name: 'Sign up for demo access' });
        expect(signUpButton).toBeInTheDocument();

        userEvent.click(signUpButton);
        expect(handleWelcomeEventMock).toHaveBeenCalledWith('signUp');
    });
});