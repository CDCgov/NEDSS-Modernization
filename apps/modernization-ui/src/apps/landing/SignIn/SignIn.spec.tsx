import { vi } from 'vitest';
import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { SignIn } from './SignIn';

vi.mock('SkipLink/SkipLinkContext', () => ({
    useSkipLink: () => ({ skipTo: jest.fn() })
}));

describe('SignIn', () => {
    it('renders without crashing', () => {
        const { getByText, getByRole } = render(<SignIn />);

        expect(getByText('Login')).toBeInTheDocument();
        expect(
            getByText(
                'Please be sure to avoid entering any real PHI/PII data on the demo site. All information entered will be viewable by other users.'
            )
        ).toBeInTheDocument();

        expect(getByRole('link', { name: 'Login to NBS demo site' })).toBeInTheDocument();
        expect(getByRole('button', { name: 'Sign up for demo access' })).toBeInTheDocument();
    });

    it('calls handleWelcomeEvent when Sign up button is clicked', async () => {
        const user = userEvent.setup();

        const handleWelcomeEventMock = jest.fn();
        const { getByRole } = render(<SignIn handleWelcomeEvent={handleWelcomeEventMock} />);

        const signUpButton = getByRole('button', { name: 'Sign up for demo access' });
        await user.click(signUpButton);

        expect(handleWelcomeEventMock).toHaveBeenCalledWith('signUp');
    });
});
