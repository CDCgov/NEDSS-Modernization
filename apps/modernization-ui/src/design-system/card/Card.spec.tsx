import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { Card, CardProps } from './Card';

const Fixture = ({
    id = 'test-card',
    title = 'Test Header',
    children = 'Test content',
    ...props
}: Partial<CardProps>) => {
    return (
        <Card id={id} title={title} {...props}>
            {children}
        </Card>
    );
};

describe('Card', () => {
    it('renders correctly with given props', () => {
        const { getByText, getByRole } = render(
            <Card id="test-id" title="Test Title" info="Additional info">
                <div>Child Content</div>
            </Card>
        );
        expect(getByRole('heading')).toHaveTextContent('Test Title');
        expect(getByText('Child Content')).toBeInTheDocument();
        expect(getByText('Additional info')).toBeInTheDocument();
    });

    it('renders with subtext', () => {
        // Render the Card with subtext
        const { getByText } = render(<Fixture subtext="This is the subtext" />);

        // Check that subtext is in the document
        const subtextElement = getByText(/This is the subtext/i);
        expect(subtextElement).toBeInTheDocument();
    });

    it('does not show collapse control when not collapsible', () => {
        const { queryByRole } = render(<Fixture collapsible={false} />);
        expect(queryByRole('button')).not.toBeInTheDocument();
    });

    describe('that is collapsible', () => {
        it('collapses an open card when the control is clicked', async () => {
            const { getByRole } = render(<Fixture title="card" collapsible open={true} />);

            const toggle = getByRole('button', { name: /hide card content/i });

            const user = userEvent.setup();

            // Collapse
            await user.click(toggle);
            expect(getByRole('button', { name: /show card content/i })).toBeInTheDocument();
        });

        it('expands an collapsed card when the control is clicked', async () => {
            const { getByRole } = render(<Fixture title="card" collapsible open={false} />);

            const toggle = getByRole('button', { name: /show card content/i });

            const user = userEvent.setup();

            // Collapse
            await user.click(toggle);
            expect(getByRole('button', { name: /hide card content/i })).toBeInTheDocument();
        });
    });
});
