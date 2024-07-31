import { render } from '@testing-library/react';
import Logout from './Logout';

describe('Logout Component', () => {
    it('renders logout confirmation title', () => {
        const { getByRole } = render(<Logout />);

        expect(getByRole('heading', { name: 'Logout confirmation' })).toBeInTheDocument();
    });

    it('renders logout confirmation message', () => {
        const { getByText } = render(<Logout />);

        const message = getByText(/You have successfully logged out/);

        expect(message).toHaveTextContent(/Thank you for using NBS/);

        expect(message).toBeInTheDocument();
    });
});
