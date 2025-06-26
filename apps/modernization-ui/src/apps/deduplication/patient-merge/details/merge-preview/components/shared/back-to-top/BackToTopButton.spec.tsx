import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { BackToTopButton } from './BackToTopButton';

describe('BackToTopButton', () => {
    beforeEach(() => {
        window.scrollTo = jest.fn();
    });

    it('renders the button with the correct text and icon', () => {
        render(<BackToTopButton />);

        const button = screen.getByRole('button', { name: /back to top/i });
        expect(button).toBeInTheDocument();
    });

    it('calls window.scrollTo when the button is clicked', async () => {
        render(<BackToTopButton />);
        const user = userEvent.setup();

        const button = screen.getByRole('button', { name: /back to top/i });
        await user.click(button);

        expect(window.scrollTo).toHaveBeenCalledWith({ top: 0, behavior: 'smooth' });
    });
});
