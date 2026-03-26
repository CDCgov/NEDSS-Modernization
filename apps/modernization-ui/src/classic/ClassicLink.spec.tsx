import { MockedFunction } from 'vitest';
import { render, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { ClassicLink } from './ClassicLink';
import { useRedirect } from './useRedirect';

vi.mock('./useRedirect');

const mockUseRedirect = useRedirect as MockedFunction<typeof useRedirect>;

describe('A ClassicLink component', () => {
    it('should redirect when clicked', async () => {
        const redirect = vi.fn();

        mockUseRedirect.mockImplementation(() => ({
            redirecting: false,
            location: 'location-value',
            redirect,
            reset: vi.fn(),
        }));

        const { findByText } = render(<ClassicLink url="redirect-url">Link text</ClassicLink>);

        const link = await findByText('Link text');

        userEvent.click(link);

        await waitFor(() => {
            expect(redirect).toHaveBeenCalledWith('redirect-url');
        });
    });
});
