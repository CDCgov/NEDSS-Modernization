import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { axe } from 'vitest-axe';

import { Warning } from './Warning';

describe('when a warning is displayed', () => {
    it('should render with no accessibility violations', async () => {
        const { container } = render(<Warning onClose={() => {}}>warning message</Warning>);

        expect(await axe(container)).toHaveNoViolations();
    });

    it('should render a warning with the default title', () => {
        const { getByRole } = render(<Warning onClose={() => {}}>warning message</Warning>);

        const title = getByRole('heading', { name: 'Warning' });

        expect(title).toBeInTheDocument();
    });

    it('should render a warning with the given title', () => {
        const { getByRole } = render(
            <Warning title="title value" onClose={() => {}}>
                warning message
            </Warning>
        );

        const title = getByRole('heading', { name: 'title value' });

        expect(title).toBeInTheDocument();
    });

    it('should render a warning with the given message', () => {
        const { getByText } = render(<Warning onClose={() => {}}>warning message</Warning>);

        const title = getByText('warning message');

        expect(title).toBeInTheDocument();
    });

    it('should invoke onClose when the "Go back" button is clicked', async () => {
        const onClose = jest.fn();

        const { getByRole } = render(<Warning onClose={onClose}>warning message</Warning>);

        const closer = getByRole('button', { name: 'Go back' });

        expect(closer).toBeInTheDocument();

        const user = userEvent.setup();
        await user.click(closer);

        expect(onClose).toBeCalled();
    });

    it('should invoke onClose when the close icon is clicked', async () => {
        const onClose = jest.fn();

        const { getByRole } = render(<Warning onClose={onClose}>warning message</Warning>);

        const closer = getByRole('button', { name: 'Close Warning' });

        expect(closer).toBeInTheDocument();

        const user = userEvent.setup();
        await user.click(closer);

        expect(onClose).toBeCalled();
    });
});
