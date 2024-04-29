import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { SignUp } from './SignUp';

describe('The Self Service Sign up', () => {
    it('should disable Sign up with out email address', () => {
        const { getByRole } = render(<SignUp />);

        const button = getByRole('button', { name: 'Sign up for demo access' });

        expect(button).toBeDisabled();
    });

    it('should disable Sign up when email address is invalid', async () => {
        const { getByRole, findByText } = render(<SignUp />);

        const input = getByRole('textbox', { name: 'Email address' });

        userEvent.type(input, 'invalid email');
        userEvent.tab();

        expect(await findByText(/Please enter a valid email address/)).toBeInTheDocument();

        const button = getByRole('button', { name: 'Sign up for demo access' });

        expect(button).toBeDisabled();
    });

    it('should enable Sign up when email address is valid', async () => {
        const { getByRole, findByRole } = render(<SignUp />);

        const input = getByRole('textbox', { name: 'Email address' });

        userEvent.type(input, 'valid@email.ok');
        userEvent.tab();

        const button = await findByRole('button', { name: 'Sign up for demo access' });

        expect(button).not.toBeDisabled();
    });
});
