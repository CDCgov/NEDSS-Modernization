import React from 'react';
import { render, screen } from '@testing-library/react';
import { SectionHeader } from './SectionHeader';

describe('SectionHeader', () => {
    it('renders the title', () => {
        render(<SectionHeader title="My Title" />);
        expect(screen.getByRole('heading', { name: 'My Title' })).toBeInTheDocument();
    });

    it('renders the subtext', () => {
        render(<SectionHeader title="With Subtext" subtext="This is a subtext" />);
        expect(screen.getByText('This is a subtext')).toBeInTheDocument();
    });


    it('renders the counter tag when showCounter is true', () => {
        render(<SectionHeader title="With Counter" showCounter count={42} />);
        expect(screen.getByText('42')).toBeInTheDocument();
    });

    it('applies the slim class when slim is true', () => {
        const { container } = render(<SectionHeader title="Slim Header" slim />);
        expect(container.firstChild).toHaveClass('slim');
    });

    it('renders children inside the Card', () => {
        render(
            <SectionHeader title="With Children">
                <div data-testid="child">Child content</div>
            </SectionHeader>
        );
        expect(screen.getByTestId('child')).toBeInTheDocument();
    });

    it('renders without crashing when optional props are missing', () => {
        render(<SectionHeader title="Minimal Props" />);
        expect(screen.getByText('Minimal Props')).toBeInTheDocument();
    });
});
