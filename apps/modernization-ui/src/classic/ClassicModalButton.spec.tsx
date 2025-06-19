import { ClassicModalButton } from './ClassicModalButton';
import { render, waitFor } from '@testing-library/react';
import { ClassicModalProvider } from './ClassicModalContext';

import { useRedirect } from './useRedirect';
import { Status, useClassicModal } from 'classic';
import userEvent from '@testing-library/user-event';

vi.mock('./useRedirect');

const mockUseRedirect = useRedirect as jest.MockedFunction<typeof useRedirect>;

vi.mock('classic');
const mockUseClassicModal = useClassicModal as jest.MockedFunction<typeof useClassicModal>;

describe('A ClassicModalButton component', () => {
    it('should redirect when clicked', async () => {
        const redirect = jest.fn();

        mockUseRedirect.mockImplementation(() => ({
            redirecting: false,
            location: 'location-value',
            redirect,
            reset: jest.fn()
        }));

        mockUseClassicModal.mockImplementation(() => ({
            state: { status: Status.Idle },
            open: jest.fn(),
            reset: jest.fn()
        }));

        const { findByText } = render(
            <ClassicModalProvider>
                <ClassicModalButton url="redirect-url">Button text</ClassicModalButton>
            </ClassicModalProvider>
        );

        const button = await findByText('Button text');

        userEvent.click(button);

        await waitFor(() => {
            expect(redirect).toHaveBeenCalledWith('redirect-url');
        });
    });

    it('should open a modal when the location is present', () => {
        mockUseRedirect.mockImplementation(() => ({
            redirecting: false,
            location: 'location-value',
            redirect: jest.fn(),
            reset: jest.fn()
        }));

        const open = jest.fn();
        mockUseClassicModal.mockImplementation(() => ({
            state: { status: Status.Idle },
            open: open,
            reset: jest.fn()
        }));

        render(
            <ClassicModalProvider>
                <ClassicModalButton url="redirect-url">Button text</ClassicModalButton>
            </ClassicModalProvider>
        );

        expect(open).toHaveBeenCalledWith('location-value');
    });
});
