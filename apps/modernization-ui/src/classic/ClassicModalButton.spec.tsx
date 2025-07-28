import { ClassicModalButton } from './ClassicModalButton';
import { render, waitFor } from '@testing-library/react';
import { ClassicModalProvider } from './ClassicModalContext';
import { Status, useClassicModal } from 'classic';
import userEvent from '@testing-library/user-event';

const mockUseClassicModal = useClassicModal as jest.MockedFunction<typeof useClassicModal>;

describe('A ClassicModalButton component', () => {
    it('should open url when clicked', async () => {
        const openMethod = jest.fn();

        mockUseClassicModal.mockImplementation(() => ({
            state: { status: Status.Idle },
            open: openMethod,
            reset: jest.fn()
        }));

        const { findByText } = render(
            <ClassicModalProvider>
                <ClassicModalButton url="url" secondary sizing={'small'}>
                    Button text
                </ClassicModalButton>
            </ClassicModalProvider>
        );

        const button = await findByText('Button text');

        userEvent.click(button);

        await waitFor(() => {
            expect(openMethod).toHaveBeenCalledWith('url');
        });
    });
});
