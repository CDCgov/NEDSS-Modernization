import { render } from '@testing-library/react';
import { Shown } from './Shown';

describe('Shown', () => {
    it('should not render children by defualt', () => {
        const { queryByText } = render(<Shown>Content</Shown>);

        expect(queryByText('Content')).not.toBeInTheDocument();
    });

    it('should render children', () => {
        const { queryByText } = render(<Shown when={true}>Content</Shown>);

        expect(queryByText('Content')).toBeInTheDocument();
    });

    it('should not render children', () => {
        const { queryByText } = render(<Shown when={false}>Content</Shown>);

        expect(queryByText('Content')).not.toBeInTheDocument();
    });
});
