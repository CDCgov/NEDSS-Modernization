import { ClassicModalButton } from './ClassicModalButton';
import { render, screen } from '@testing-library/react';
import { ClassicModalProvider } from './ClassicModalContext';
import { Status } from './useClassicModal';
import userEvent from '@testing-library/user-event';

const openMock = jest.fn();
const resetMock = jest.fn();

const mockUseClassicModal = {
    state: { status: Status.Idle },
    open: openMock,
    reset: resetMock
};

jest.mock('./useClassicModal', () => ({
    ...jest.requireActual('./useClassicModal'),
    useClassicModal: () => mockUseClassicModal
}));

describe('A ClassicModalButton component', () => {
    it('should open url when clicked', async () => {
        render(
            <ClassicModalProvider>
                <ClassicModalButton url="url" onClose={jest.fn()}>
                    Button text
                </ClassicModalButton>
            </ClassicModalProvider>
        );

        const button = screen.getByRole('button', { name: 'Button text' });

        await userEvent.click(button);

        expect(openMock).toHaveBeenCalledWith('url');
    });
});
