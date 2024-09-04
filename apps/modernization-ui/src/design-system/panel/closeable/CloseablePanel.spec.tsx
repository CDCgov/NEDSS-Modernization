import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { axe } from 'jest-axe';

import { CloseablePanel } from './CloseablePanel';

describe('CloseablePanel', () => {
    it('should render with no accessibility violations', async () => {
        const { container } = render(
            <CloseablePanel title="title" onClose={jest.fn()}>
                content
            </CloseablePanel>
        );

        expect(await axe(container)).toHaveNoViolations();
    });

    it('should render the title', () => {
        const { getByText } = render(
            <CloseablePanel title="title-value" onClose={jest.fn()}>
                content
            </CloseablePanel>
        );

        const title = getByText('title-value');

        expect(title).toBeInTheDocument();
    });

    it('should render the title as an h2', () => {
        const { getByRole } = render(
            <CloseablePanel title="title-value" headingLevel={2} onClose={jest.fn()}>
                content
            </CloseablePanel>
        );

        const title = getByRole('heading');

        expect(title).toHaveTextContent('title-value');
    });

    it('should render the title as an h3', () => {
        const { getByRole } = render(
            <CloseablePanel title="title-value" headingLevel={3} onClose={jest.fn()}>
                content
            </CloseablePanel>
        );

        const title = getByRole('heading');

        expect(title).toHaveTextContent('title-value');
    });

    it('should render the title as an h4', () => {
        const { getByRole } = render(
            <CloseablePanel title="title-value" headingLevel={4} onClose={jest.fn()}>
                content
            </CloseablePanel>
        );

        const title = getByRole('heading');

        expect(title).toHaveTextContent('title-value');
    });

    it('should render the title as an h5', () => {
        const { getByRole } = render(
            <CloseablePanel title="title-value" headingLevel={5} onClose={jest.fn()}>
                content
            </CloseablePanel>
        );

        const title = getByRole('heading');

        expect(title).toHaveTextContent('title-value');
    });

    it('should render the content', () => {
        const { getByText } = render(
            <CloseablePanel title="title-value" onClose={jest.fn()}>
                content
            </CloseablePanel>
        );

        const content = getByText('content');

        expect(content).toBeInTheDocument();
    });

    it('should invoke the onClose when icon close called', () => {
        const onClose = jest.fn();
        const { getByLabelText } = render(
            <CloseablePanel title="title-value" onClose={onClose}>
                content
            </CloseablePanel>
        );

        const closer = getByLabelText('Close title-value');

        userEvent.click(closer);

        expect(onClose).toBeCalled();
    });
});
