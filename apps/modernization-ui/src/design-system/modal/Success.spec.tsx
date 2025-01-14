import { render } from '@testing-library/react';
import { axe } from 'jest-axe';

import { Success } from './Success';
import userEvent from '@testing-library/user-event';

describe('when a success is displayed', () => {
    it('should render with no accessibility violations', async () => {
        const { container } = render(<Success onClose={() => {}}>success message</Success>);

        expect(await axe(container)).toHaveNoViolations();
    });

    it('should render a success with the default title', () => {
        const { getByRole } = render(<Success onClose={() => {}}>success message</Success>);

        const title = getByRole('heading', { name: 'Success' });

        expect(title).toBeInTheDocument();
    });

    it('should render a success with the given title', () => {
        const { getByRole } = render(
            <Success title="title value" onClose={() => {}}>
                success message
            </Success>
        );

        const title = getByRole('heading', { name: 'title value' });

        expect(title).toBeInTheDocument();
    });

    it('should render a success with the given message', () => {
        const { getByText } = render(<Success onClose={() => {}}>success message</Success>);

        const title = getByText('success message');

        expect(title).toBeInTheDocument();
    });

    it('should invoke onClose when the "Go back" button is clicked', () => {
        const onClose = jest.fn();

        const { getByRole } = render(<Success onClose={onClose}>success message</Success>);

        const closer = getByRole('button', { name: 'Go back' });

        expect(closer).toBeInTheDocument();

        userEvent.click(closer);

        expect(onClose).toBeCalled();
    });

    it('should invoke onClose when the close icon is clicked', () => {
        const onClose = jest.fn();

        const { getByRole } = render(<Success onClose={onClose}>success message</Success>);

        const closer = getByRole('button', { name: 'Close Success' });

        expect(closer).toBeInTheDocument();

        userEvent.click(closer);

        expect(onClose).toBeCalled();
    });
});
