import { render } from '@testing-library/react';
import { Shown } from './Shown';

describe('Shown', () => {
    it('should not render children by defualt', () => {
        const { queryByText } = render(<Shown>Content</Shown>);

        expect(queryByText('Content')).not.toBeInTheDocument();
    });

    it('should render children', () => {
        const { queryByText } = render(
            <Shown when={true}>
                <p>Content</p>
            </Shown>
        );

        const content = queryByText('Content');

        expect(content).toBeInTheDocument();
    });

    it('should not render children', () => {
        const { queryByText } = render(<Shown when={false}>Content</Shown>);

        expect(queryByText('Content')).not.toBeInTheDocument();
    });

    it('should render the fallback when children not shown', () => {
        const { queryByText } = render(
            <Shown when={false} fallback="Fallback">
                Content
            </Shown>
        );

        expect(queryByText('Content')).not.toBeInTheDocument();
        expect(queryByText('Fallback')).toBeInTheDocument();
    });
});
