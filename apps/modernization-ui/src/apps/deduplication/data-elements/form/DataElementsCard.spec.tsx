import { render, screen } from '@testing-library/react';
import { DataElementsCard } from './DataElementsCard';

describe('DataElementsCard', () => {
    it('renders the title and subtext correctly', () => {
        render(<DataElementsCard title="Test Title" subtext="This is a subtext">Content</DataElementsCard>);

        expect(screen.getByText('Test Title')).toBeInTheDocument();
        expect(screen.getByText('This is a subtext')).toBeInTheDocument();
    });

    it('renders children correctly', () => {
        render(
            <DataElementsCard title="Test Title">
                <p>Child Content</p>
            </DataElementsCard>
        );

        expect(screen.getByText('Child Content')).toBeInTheDocument();
    });

    it('does not render subtext when not provided', () => {
        render(<DataElementsCard title="Test Title">Content</DataElementsCard>);

        expect(screen.queryByText('This is a subtext')).not.toBeInTheDocument();
    });

});
