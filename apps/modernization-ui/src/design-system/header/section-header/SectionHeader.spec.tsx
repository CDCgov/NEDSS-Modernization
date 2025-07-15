import React from 'react';
import userEvent from '@testing-library/user-event';
import { render, screen, fireEvent } from '@testing-library/react';
import { SectionHeader } from './SectionHeader';


describe('SectionHeader', () => {
    it('renders the title', () => {
        render(<SectionHeader title="Test Section" />);
        expect(screen.getByRole('heading', { name: /test section/i })).toBeInTheDocument();
    });

    it('renders the count when showCounter is true', () => {
        render(<SectionHeader title="With Count" count={42} showCounter />);
        expect(screen.getByText('42')).toBeInTheDocument();
    });

    it('does not render count if showCounter is false', () => {
        render(<SectionHeader title="No Count" count={42} />);
        expect(screen.queryByText('42')).not.toBeInTheDocument();
    });

    it('renders subtext when provided', () => {
        render(<SectionHeader title="With Subtext" subtext="Helpful info" />);
        expect(screen.getByText('Helpful info')).toBeInTheDocument();
    });

    it('renders tooltip as string inside Hint when hovered (no data-testid)', async () => {
        const user = userEvent.setup();

        render(
            <SectionHeader
                title="With Tooltip"
                subtext="Some text"
                tooltip="Tooltip content"
            />
        );

        // Find the "Some text" element
        const subtext = screen.getByText('Some text');

        // Find the <svg> (icon) sibling to hover
        const icon = subtext.parentElement?.querySelector('svg');
        expect(icon).toBeInTheDocument();

        await user.hover(icon as Element);

        expect(await screen.findByText('Tooltip content')).toBeInTheDocument();
    });


    it('defaults to open and toggles collapsed state', () => {
        render(<SectionHeader title="Toggle Section" />);
        const toggleButton = screen.getByRole('button', { name: /hide toggle section/i });

        fireEvent.click(toggleButton);
        expect(toggleButton).toHaveAttribute('aria-label', 'Show Toggle Section');
    });

    it('respects defaultOpen = false', () => {
        render(<SectionHeader title="Collapsed Initially" defaultOpen={false} />);
        const toggleButton = screen.getByRole('button', { name: /show collapsed initially/i });
        expect(toggleButton).toBeInTheDocument();
    });

    it('calls onToggle callback when toggled', () => {
        const onToggle = jest.fn();
        render(<SectionHeader title="Callback Test" onToggle={onToggle} />);
        const toggleButton = screen.getByRole('button');

        fireEvent.click(toggleButton);
        expect(onToggle).toHaveBeenCalledWith(true);

        fireEvent.click(toggleButton);
        expect(onToggle).toHaveBeenCalledWith(false);
    });

    it('applies tall and slim modifiers correctly', () => {
        const { container: tallContainer } = render(<SectionHeader title="Tall" tall />);
        expect(tallContainer.firstChild).toHaveClass('tall');

        const { container: slimContainer } = render(<SectionHeader title="Slim" slim />);
        expect(slimContainer.firstChild).toHaveClass('slim');
    });

    it('applies custom className', () => {
        const { container } = render(<SectionHeader title="Styled" className="custom-style" />);
        expect(container.firstChild).toHaveClass('custom-style');
    });
});
