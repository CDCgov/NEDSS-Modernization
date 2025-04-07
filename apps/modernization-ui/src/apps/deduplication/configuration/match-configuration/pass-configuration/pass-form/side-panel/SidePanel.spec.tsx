import { render, waitFor } from '@testing-library/react';
import { SidePanel } from './SidePanel';
import userEvent from '@testing-library/user-event';

const onClose = jest.fn();
class ResizeObserver {
    observe() {
        // do nothing
    }
    unobserve() {
        // do nothing
    }
    disconnect() {
        // do nothing
    }
}

window.ResizeObserver = ResizeObserver;
export default ResizeObserver;

describe('SidePanel', () => {
    it('should display when visible', async () => {
        const { queryByText } = render(
            <SidePanel heading="Some heading" footer={<div>My Footer</div>} visible={true} onClose={onClose}>
                Some content
            </SidePanel>
        );

        await waitFor(() => expect(queryByText('Some heading')).toBeInTheDocument());
        await waitFor(() => expect(queryByText('My Footer')).toBeInTheDocument());
    });

    it('should not display when not visible', async () => {
        // show content
        let visible = true;
        const { queryByText, rerender } = render(
            <SidePanel heading="Some heading" footer={<div>My Footer</div>} visible={visible} onClose={onClose}>
                Some content
            </SidePanel>
        );

        await waitFor(() => expect(queryByText('Some heading')).toBeInTheDocument());
        await waitFor(() => expect(queryByText('My Footer')).toBeInTheDocument());

        // Hide content
        visible = false;
        rerender(
            <SidePanel heading="Some heading" footer={<div>My Footer</div>} visible={visible} onClose={onClose}>
                Some content
            </SidePanel>
        );
        await waitFor(() => expect(queryByText('Some heading')).not.toBeInTheDocument());
        await waitFor(() => expect(queryByText('My Footer')).not.toBeInTheDocument());
    });

    it('should trigger on close when close button is clicked', async () => {
        // show content
        let visible = true;
        const { getByRole } = render(
            <SidePanel heading="Some heading" visible={visible} onClose={onClose}>
                Some content
            </SidePanel>
        );

        const user = userEvent.setup();

        const close = getByRole('button', { name: 'Close Some heading' });

        await user.click(close); // Close button

        expect(onClose).toHaveBeenCalled();
    });
});
