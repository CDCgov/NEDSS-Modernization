import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { axe } from 'jest-axe';

import { Confirmation } from './Confirmation';

describe('when a confirmation is displayed', () => {
    it('should render with no accessibility violations', async () => {
        const { container } = render(
            <Confirmation onCancel={() => {}} onConfirm={() => {}}>
                confirmation message
            </Confirmation>
        );

        expect(await axe(container)).toHaveNoViolations();
    });

    it('should render a confirmation with the default title', () => {
        const { getByRole } = render(
            <Confirmation onCancel={() => {}} onConfirm={() => {}}>
                confirmation message
            </Confirmation>
        );

        const title = getByRole('heading', { name: 'Confirmation' });

        expect(title).toBeInTheDocument();
    });

    it('should render a confirmation with the given title', () => {
        const { getByRole } = render(
            <Confirmation title="title value" onCancel={() => {}} onConfirm={() => {}}>
                confirmation message
            </Confirmation>
        );

        const title = getByRole('heading', { name: 'title value' });

        expect(title).toBeInTheDocument();
    });

    it('should render a confirmation with the given message', () => {
        const { getByText } = render(
            <Confirmation onCancel={() => {}} onConfirm={() => {}}>
                confirmation message
            </Confirmation>
        );

        const title = getByText('confirmation message');

        expect(title).toBeInTheDocument();
    });

    it('should render a confirmation with the provided cancel button text', () => {
        const { getByRole } = render(
            <Confirmation onCancel={() => {}} onConfirm={() => {}} cancelText="Nah, nevermind">
                confirmation message
            </Confirmation>
        );

        const cancel = getByRole('button', { name: 'Nah, nevermind' });

        expect(cancel).toBeInTheDocument();
    });

    it('should render a confirmation with the default "No, go back" button', () => {
        const { getByRole } = render(
            <Confirmation onCancel={() => {}} onConfirm={() => {}}>
                confirmation message
            </Confirmation>
        );

        const cancel = getByRole('button', { name: 'No, go back' });

        expect(cancel).toBeInTheDocument();
    });

    it('should invoke onCancel when the "No, go back" button is clicked', async () => {
        const onConfirm = jest.fn();
        const onCancel = jest.fn();

        const { getByRole } = render(
            <Confirmation onCancel={onCancel} onConfirm={() => {}}>
                confirmation message
            </Confirmation>
        );

        const cancel = getByRole('button', { name: 'No, go back' });

        expect(cancel).toBeInTheDocument();

        const user = userEvent.setup();
        await user.click(cancel);

        expect(onCancel).toBeCalled();
        expect(onConfirm).not.toBeCalled();
    });

    it('should invoke onCancel when the close icon is clicked', async () => {
        const onConfirm = jest.fn();
        const onCancel = jest.fn();

        const { getByRole } = render(
            <Confirmation onCancel={onCancel} onConfirm={() => {}}>
                confirmation message
            </Confirmation>
        );

        const cancel = getByRole('button', { name: 'Close Confirmation' });

        expect(cancel).toBeInTheDocument();

        const user = userEvent.setup();
        await user.click(cancel);

        expect(onCancel).toBeCalled();
        expect(onConfirm).not.toBeCalled();
    });

    it('should render a confirmation with the default "Confirm" button', () => {
        const { getByRole } = render(
            <Confirmation onCancel={() => {}} onConfirm={() => {}}>
                confirmation message
            </Confirmation>
        );

        const confirmation = getByRole('button', { name: 'Confirm' });

        expect(confirmation).toBeInTheDocument();
    });

    it('should render a confirmation with the given Confirm button text', () => {
        const { getByRole } = render(
            <Confirmation onCancel={() => {}} onConfirm={() => {}} confirmText="Yes, proceed">
                confirmation message
            </Confirmation>
        );

        const confirmation = getByRole('button', { name: 'Yes, proceed' });

        expect(confirmation).toBeInTheDocument();
    });

    it('should invoke onConfirm when the "Confirm" button is clicked', async () => {
        const onConfirm = jest.fn();
        const onCancel = jest.fn();

        const { getByRole } = render(
            <Confirmation onCancel={onCancel} onConfirm={onConfirm}>
                confirmation message
            </Confirmation>
        );

        const confirm = getByRole('button', { name: 'Confirm' });

        expect(confirm).toBeInTheDocument();

        const user = userEvent.setup();
        await user.click(confirm);

        expect(onConfirm).toBeCalled();
        expect(onCancel).not.toBeCalled();
    });

    it('should render close icon by default', () => {
        const { getByRole } = render(
            <Confirmation title="Hello Modal" onCancel={() => {}} onConfirm={() => {}}>
                Confirmation of something
            </Confirmation>
        );

        const closeX = getByRole('button', { name: 'Close Hello Modal' });

        expect(closeX).toBeInTheDocument();
    });

    it('should not render close icon when forceAction is true', () => {
        const { queryByRole } = render(
            <Confirmation title="Hello Modal" onCancel={() => {}} onConfirm={() => {}} forceAction={true}>
                Confirmation of something
            </Confirmation>
        );

        const closeX = queryByRole('button', { name: 'Close Hello Modal' });

        expect(closeX).not.toBeInTheDocument();
    });

    it('should render a confirmation with the confirm button as destructive', () => {
        const { getByRole } = render(
            <Confirmation onCancel={() => {}} onConfirm={() => {}} confirmText="Delete" destructive>
                confirmation message
            </Confirmation>
        );

        const confirmation = getByRole('button', { name: 'Delete' });

        expect(confirmation).toBeInTheDocument();
        expect(confirmation).toHaveClass('destructive');
    });
});
