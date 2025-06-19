import { render, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { ClassicButton } from './ClassicButton';
import { vi } from 'vitest';

import { useRedirect } from './useRedirect';
vi.mock('./useRedirect');

const mockUseRedirect = useRedirect as jest.MockedFunction<typeof useRedirect>;

describe('A ClassicButton component', () => {
    it('should redirect when clicked', async () => {
        const redirect = jest.fn();

        mockUseRedirect.mockImplementation(() => ({
            redirecting: false,
            location: 'location-value',
            redirect,
            reset: jest.fn()
        }));

        const { findByText } = render(<ClassicButton url="redirect-url">Button text</ClassicButton>);

        const button = await findByText('Button text');

        userEvent.click(button);

        await waitFor(() => {
            expect(redirect).toHaveBeenCalledWith('redirect-url');
        });
    });
});
