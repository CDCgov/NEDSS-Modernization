import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { axe } from 'jest-axe';

import { ClosablePanel } from './ClosablePanel';

describe('CloseablePanel', () => {
    it('should render with no accessibility violations', async () => {
        const { container } = render(
            <ClosablePanel title="title" onClose={vi.fn()}>
                content
            </ClosablePanel>
        );

        expect(await axe(container)).toHaveNoViolations();
    });

    it('should render the title', () => {
        const { getByText } = render(
            <ClosablePanel title="title-value" onClose={vi.fn()}>
                content
            </ClosablePanel>
        );

        const title = getByText('title-value');

        expect(title).toBeInTheDocument();
    });

    it('should render the title as an h2', () => {
        const { getByRole } = render(
            <ClosablePanel title="title-value" headingLevel={2} onClose={vi.fn()}>
                content
            </ClosablePanel>
        );

        const title = getByRole('heading');

        expect(title).toHaveTextContent('title-value');
    });

    it('should render the title as an h3', () => {
        const { getByRole } = render(
            <ClosablePanel title="title-value" headingLevel={3} onClose={vi.fn()}>
                content
            </ClosablePanel>
        );

        const title = getByRole('heading');

        expect(title).toHaveTextContent('title-value');
    });

    it('should render the title as an h4', () => {
        const { getByRole } = render(
            <ClosablePanel title="title-value" headingLevel={4} onClose={vi.fn()}>
                content
            </ClosablePanel>
        );

        const title = getByRole('heading');

        expect(title).toHaveTextContent('title-value');
    });

    it('should render the title as an h5', () => {
        const { getByRole } = render(
            <ClosablePanel title="title-value" headingLevel={5} onClose={vi.fn()}>
                content
            </ClosablePanel>
        );

        const title = getByRole('heading');

        expect(title).toHaveTextContent('title-value');
    });

    it('should render the content', () => {
        const { getByText } = render(
            <ClosablePanel title="title-value" onClose={vi.fn()}>
                content
            </ClosablePanel>
        );

        const content = getByText('content');

        expect(content).toBeInTheDocument();
    });

    it('should invoke the onClose when icon close called', async () => {
        const user = userEvent.setup();

        const onClose = vi.fn();
        const { getByLabelText } = render(
            <ClosablePanel title="title-value" onClose={onClose}>
                content
            </ClosablePanel>
        );

        const closer = getByLabelText('Close title-value');

        await user.click(closer);

        expect(onClose).toBeCalled();
    });
});
