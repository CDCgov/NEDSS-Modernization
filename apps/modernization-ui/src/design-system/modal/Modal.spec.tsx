import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { axe } from 'vitest-axe';

import { Modal } from './Modal';

describe('when a modal is displayed', () => {
    it('should render with no accessibility violations', async () => {
        const { container } = render(
            <Modal id={'identifier-value'} title="Title Value" onClose={() => {}}>
                Contents
            </Modal>
        );

        expect(await axe(container)).toHaveNoViolations();
    });

    it('should render the header', () => {
        const { getByRole } = render(
            <Modal id={'identifier-value'} title="Title Value" onClose={() => {}}>
                Contents
            </Modal>
        );

        const header = getByRole('heading', { name: 'Title Value', level: 2 });

        expect(header).toBeInTheDocument();
    });

    it('should render the contents', () => {
        const { getByText } = render(
            <Modal id={'identifier-value'} title="Title Value" onClose={() => {}}>
                Contents
            </Modal>
        );

        const content = getByText(/Contents/);

        expect(content).toBeInTheDocument();
    });

    it('should render close icon', () => {
        const { getByLabelText } = render(
            <Modal id={'identifier-value'} title="Title Value" onClose={() => {}}>
                Contents
            </Modal>
        );

        const closer = getByLabelText('Close Title Value');

        expect(closer).toBeInTheDocument();
    });

    it('should not render close icon when action is forced', () => {
        const { queryByLabelText } = render(
            <Modal id={'identifier-value'} title="Title Value" onClose={() => {}} forceAction>
                Contents
            </Modal>
        );

        const closer = queryByLabelText('Close Title Value');

        expect(closer).toBeNull();
    });

    it('should invoke the onClose when icon close called', async () => {
        const user = userEvent.setup();

        const onClose = jest.fn();
        const { getByRole } = render(
            <Modal id={'identifier-value'} title="Title Value" onClose={onClose}>
                Contents
            </Modal>
        );

        const closer = getByRole('button', { name: 'Close Title Value' });

        await user.click(closer);

        expect(onClose).toBeCalled();
    });

    it('should render without a footer', () => {
        const { baseElement } = render(
            <Modal id={'identifier-value'} title="Title Value" onClose={() => {}}>
                Contents
            </Modal>
        );

        expect(baseElement).toBeInTheDocument();
    });

    it('should render the footer', () => {
        const { baseElement } = render(
            <Modal id={'identifier-value'} title="Title Value" onClose={() => {}} footer={() => 'The footer'}>
                Contents
            </Modal>
        );

        expect(baseElement).toBeInTheDocument();
    });

    it('should invoke the onClose when footer close called', async () => {
        const user = userEvent.setup();

        const onClose = jest.fn();

        const { getByRole } = render(
            <Modal
                id={'identifier-value'}
                title="Title Value"
                onClose={onClose}
                footer={(close) => (
                    <button type="button" onClick={close}>
                        Close
                    </button>
                )}>
                Contents
            </Modal>
        );

        const closer = getByRole('button', { name: 'Close' });

        await user.click(closer);

        expect(onClose).toBeCalled();
    });
});
