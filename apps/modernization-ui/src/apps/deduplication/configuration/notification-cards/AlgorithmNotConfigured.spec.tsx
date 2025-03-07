import { render } from '@testing-library/react';
import { AlgorithmNotConfigured } from './AlgorithmNotConfigured';

describe('AlgorithmNotConfigured', () => {
    // Pass count > 0
    it('should display proper heading text', () => {
        const { getByText } = render(<AlgorithmNotConfigured />);

        expect(getByText('Algorithm not configured')).toBeInTheDocument();
    });

    it('should display proper body text', () => {
        const { getByText } = render(<AlgorithmNotConfigured />);

        expect(
            getByText(
                (content) =>
                    content ===
                    'To configure the algorithm, first configure the data elements used in the algorithm.Get started by manually configuring the data elements or importing a configuration file.'
            )
        ).toBeInTheDocument();
    });
});
