import { ClassicModalButton } from './ClassicModalButton';
import { render, screen } from '@testing-library/react';
import { ClassicModalProvider } from './ClassicModalContext';
import { Status } from './useClassicModal';
import userEvent from '@testing-library/user-event';
import { vi } from 'vitest';

const openMock = vi.fn();
const resetMock = vi.fn();

const mockUseClassicModal = {
    state: { status: Status.Idle },
    open: openMock,
    reset: resetMock
};

vi.mock('./useClassicModal', async (importOriginal) => {
    const actual = await importOriginal();
    return {
        ...actual,
        useClassicModal: () => mockUseClassicModal
    };
});

describe('A ClassicModalButton component', () => {
    it('should open url when clicked', async () => {
        render(
            <ClassicModalProvider>
                <ClassicModalButton url="url" onClose={vi.fn()}>
                    Button text
                </ClassicModalButton>
            </ClassicModalProvider>
        );

        const button = screen.getByRole('button', { name: 'Button text' });

        await userEvent.click(button);

        expect(openMock).toHaveBeenCalledWith('url');
    });
});
