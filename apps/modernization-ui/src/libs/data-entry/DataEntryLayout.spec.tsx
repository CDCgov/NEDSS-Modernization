import { render } from '@testing-library/react';
import { DataEntryLayout } from './DataEntryLayout';

describe('DataEntryLayout', () => {
    it('renders without crashing', () => {
        const { container } = render(<DataEntryLayout />);
        expect(container).toBeInTheDocument();
    });

    it('renders children correctly', () => {
        const { getByText } = render(
            <DataEntryLayout>
                <div>Test Child</div>
            </DataEntryLayout>
        );
        expect(getByText('Test Child')).toBeInTheDocument();
    });
});
