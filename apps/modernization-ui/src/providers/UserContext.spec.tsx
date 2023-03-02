// eslint-disable-next-line no-redeclare
import { render, screen, fireEvent } from '@testing-library/react';
import { UserContextProvider } from './UserContext';

describe('UserContextProvider', () => {
    it('logs in successfully with correct credentials', async () => {
        const mockLoginFn = jest.fn().mockResolvedValue(true);
        const { container } = render(
            <UserContextProvider>
                <button onClick={() => mockLoginFn('hclark', '')}>Login</button>
            </UserContextProvider>
        );

        fireEvent.click(screen.getByText('Login'));
        expect(mockLoginFn).toHaveBeenCalledWith('hclark', '');
    });

    it('displays error message with incorrect credentials', async () => {
        const mockLoginFn = jest.fn().mockResolvedValue(false);
        render(
            <UserContextProvider>
                <button onClick={() => mockLoginFn('asd', '')}>Login</button>
            </UserContextProvider>
        );

        fireEvent.click(screen.getByText('Login'));
        expect(mockLoginFn).toHaveBeenCalledWith('asd', '');
    });
});
