import { render } from '@testing-library/react';
import { Expired } from './Expired';

describe('Expired Component', () => {
    it('renders expired title', () => {
        const { getByRole } = render(<Expired />);

        expect(getByRole('heading', { name: 'Session time-out' })).toBeInTheDocument();
    });

    it('renders expired message', () => {
        const { getByText } = render(<Expired />);

        const message = getByText(/Your session has expired/);

        expect(message).toHaveTextContent(
            /If you received this message after clicking Submit, any changes you made were not saved. Please reconnect to NBS by clicking the link below/
        );

        expect(message).toBeInTheDocument();
    });
});
