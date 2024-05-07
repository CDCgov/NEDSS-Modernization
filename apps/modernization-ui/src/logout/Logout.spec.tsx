import { render } from '@testing-library/react';
import Logout from './Logout';
import { BrowserRouter } from 'react-router-dom';

describe('Logout Component', () => {
    it('renders logout confirmation message', () => {
        const { getByText } = render(
            <BrowserRouter>
                <Logout />
            </BrowserRouter>
        );

        expect(getByText('Logout confirmation')).toBeInTheDocument();
    });

    it('renders "Return to NBS" button', () => {
        const { getByRole } = render(
            <BrowserRouter>
                <Logout />
            </BrowserRouter>
        );

        const returnButton = getByRole('link', { name: 'Return to NBS' });
        expect(returnButton).toBeInTheDocument();
    });
});
