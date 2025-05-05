import { render } from '@testing-library/react';
import { DataEntryLayout } from './DataEntryLayout';
import styles from './add-layout.module.scss';

describe('DataEntryLayout', () => {
    it('renders without crashing', () => {
        const { container } = render(<DataEntryLayout />);
        expect(container).toBeInTheDocument();
    });

    it('applies the correct class name', () => {
        const { container } = render(<DataEntryLayout />);
        expect(container.firstChild).toHaveClass(styles.addLayout);
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
