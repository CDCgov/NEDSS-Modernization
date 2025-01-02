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

        const user = userEvent.setup();

        await user.type(input, 'invalid email').then(() => user.tab());

        expect(await findByText(/Please enter a valid email address/)).toBeInTheDocument();

        const button = getByRole('button', { name: 'Sign up for demo access' });

        expect(button).toBeDisabled();
    });

    it('should enable Sign up when email address is valid', async () => {
        const { getByRole, findByRole } = render(<SignUp />);

        const input = getByRole('textbox', { name: 'Email address' });

        const user = userEvent.setup();

        await user.type(input, 'valid@email.ok').then(() => user.tab());

        const button = await findByRole('button', { name: 'Sign up for demo access' });

        expect(button).not.toBeDisabled();
    });
});
