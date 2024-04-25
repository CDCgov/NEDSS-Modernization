import React from 'react';
import { render } from '@testing-library/react';
import { WelcomeHeader } from './WelcomeHeader';

describe('WelcomeHeader', () => {
    test('renders the text and image correctly', () => {
        const { getByText, getByRole } = render(<WelcomeHeader />);

        const headerText = getByText(/Welcome to the NBS7 demo site/);
        expect(headerText).toBeInTheDocument();

        const image = getByRole('img');
        expect(image).toBeInTheDocument();
    });
});
