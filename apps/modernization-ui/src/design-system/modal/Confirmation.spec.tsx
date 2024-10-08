import { render } from '@testing-library/react';
import { axe } from 'jest-axe';

import { Confirmation } from './Confirmation';
import userEvent from '@testing-library/user-event';

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

    it('should invoke onCancel when the "No, go back" button is clicked', () => {
        const onConfirm = jest.fn();
        const onCancel = jest.fn();

        const { getByRole } = render(
            <Confirmation onCancel={onCancel} onConfirm={() => {}}>
                confirmation message
            </Confirmation>
        );

        const cancel = getByRole('button', { name: 'No, go back' });

        expect(cancel).toBeInTheDocument();

        userEvent.click(cancel);

        expect(onCancel).toBeCalled();
        expect(onConfirm).not.toBeCalled();
    });

    it('should invoke onCancel when the close icon is clicked', () => {
        const onConfirm = jest.fn();
        const onCancel = jest.fn();

        const { getByRole } = render(
            <Confirmation onCancel={onCancel} onConfirm={() => {}}>
                confirmation message
            </Confirmation>
        );

        const cancel = getByRole('button', { name: 'Close Confirmation' });

        expect(cancel).toBeInTheDocument();

        userEvent.click(cancel);

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

    it('should invoke onConfirm when the "Confirm" button is clicked', () => {
        const onConfirm = jest.fn();
        const onCancel = jest.fn();

        const { getByRole } = render(
            <Confirmation onCancel={onCancel} onConfirm={onConfirm}>
                confirmation message
            </Confirmation>
        );

        const confirm = getByRole('button', { name: 'Confirm' });

        expect(confirm).toBeInTheDocument();

        userEvent.click(confirm);

        expect(onConfirm).toBeCalled();
        expect(onCancel).not.toBeCalled();
    });

    it('should render close icon by default', () => {
        const result = render(
            <Confirmation title="Hello Modal" onCancel={() => {}} onConfirm={() => {}}>
                Confirmation of something
            </Confirmation>
        );

        const closeX = result.container.querySelector('svg[data-close-modal]');

        expect(closeX).not.toBeNull();
    });

    it('should not render close icon when forceAction is true', () => {
        const result = render(
            <Confirmation title="Hello Modal" onCancel={() => {}} onConfirm={() => {}} forceAction={true}>
                Confirmation of something
            </Confirmation>
        );

        const closeX = result.container.querySelector('svg[data-close-modal]');

        expect(closeX).toBeNull();
    });
});
