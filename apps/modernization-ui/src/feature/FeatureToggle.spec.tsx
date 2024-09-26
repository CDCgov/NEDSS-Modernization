import { render } from '@testing-library/react';
import { FeatureToggle } from './FeatureToggle';

jest.mock('configuration', () => ({
    useConfiguration: () => ({ ready: false, loading: false, load: jest.fn() })
}));

describe('FeatureToggle', () => {
    it('should not render children when guard fails', () => {
        const { queryByText } = render(<FeatureToggle guard={() => false}>Content</FeatureToggle>);

        expect(queryByText('Content')).not.toBeInTheDocument();
    });

    it('should  render children when guard passes', () => {
        const { queryByText } = render(<FeatureToggle guard={() => true}>Content</FeatureToggle>);

        expect(queryByText('Content')).toBeInTheDocument();
    });
});
