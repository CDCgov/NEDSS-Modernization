import React from 'react';
import { render } from '@testing-library/react';
import { WelcomeHeader } from './WelcomeHeader';

describe('LoginHeader', () => {
    test('renders the text and image correctly', () => {
        const { getByText, getByRole } = render(<WelcomeHeader />);

        const headerText = getByText(/Welcome to the NBS demo site \(Version 7\.x\)/);
        expect(headerText).toBeInTheDocument();

        const image = getByRole('img');
        expect(image).toBeVisible();
    });
});
