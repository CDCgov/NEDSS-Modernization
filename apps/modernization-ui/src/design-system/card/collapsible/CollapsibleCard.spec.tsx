import { render } from '@testing-library/react';
import { CollapsibleCard, CollapsibleCardProps } from './CollapsibleCard';
import userEvent from '@testing-library/user-event';

const Fixture = (props: Partial<CollapsibleCardProps>) => {
    const testHeader = <div>Test Header</div>;
    const testChildren = <div>Test Content</div>;
    return (
        <CollapsibleCard id="test-card" header={testHeader} {...props}>
            {testChildren}
        </CollapsibleCard>
    );
};

describe('CollapsibleCard', () => {
    it('renders the header and children correctly', () => {
        const { getByText } = render(<Fixture />);
        expect(getByText('Test Header')).toBeInTheDocument();
        expect(getByText('Test Content')).toBeInTheDocument();
    });

    it('collapses and expands when the button is clicked', async () => {
        const { getByText, getByRole } = render(<Fixture />);
        const toggleButton = getByRole('button', { name: /hide card content/i });

        // Initially expanded
        expect(getByText('Test Content')).toBeVisible();
        expect(toggleButton).toBeInTheDocument();

        // Collapse
        await userEvent.click(toggleButton);
        expect(getByRole('button', { name: /show card content/i })).toBeInTheDocument();

        // Expand
        await userEvent.click(toggleButton);
        expect(getByRole('button', { name: /hide card content/i })).toBeInTheDocument();
    });

    it('applies custom className if provided', () => {
        const customClass = 'custom-class';
        const { container } = render(<Fixture className={customClass} />);
        expect(container.firstChild).toHaveClass(customClass);
    });

    it('sets aria-label correctly based on collapsed state', async () => {
        const { getByRole } = render(<Fixture />);
        const toggleButton = getByRole('button', { name: /hide card content/i });

        // Initially expanded
        expect(toggleButton).toHaveAttribute('aria-label', 'Hide card content');

        // Collapse
        await userEvent.click(toggleButton);
        expect(toggleButton).toHaveAttribute('aria-label', 'Show card content');
    });
});
